#!/usr/bin/env python3
"""Load LDBC SNB vanilla CSV test data into an Apache AGE graph.

Usage:
    python3 load-test-data.py <vanilla_dir> [connection_string]

    vanilla_dir        path to test-data/vanilla/ (contains static/ and dynamic/)
    connection_string  default: postgresql://postgres:postgres@localhost:5432/postgres
"""

import csv
import json
import os
import sys

import psycopg2

GRAPH = "ldbc_snb"
BATCH = 500


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------

def connect(cs):
    conn = psycopg2.connect(cs)
    conn.autocommit = False
    cur = conn.cursor()
    cur.execute("LOAD 'age'")
    cur.execute("SET search_path = ag_catalog, '$user', public")
    conn.commit()
    return conn, cur


def read_csv(path):
    with open(path, newline="", encoding="utf-8") as f:
        return list(csv.DictReader(f, delimiter="|"))


def read_csv_rows(path):
    """Return (headers, list-of-lists) for CSVs with duplicate header names."""
    with open(path, newline="", encoding="utf-8") as f:
        reader = csv.reader(f, delimiter="|")
        headers = next(reader)
        return headers, [row for row in reader]


def cypher_exec(cur, conn, cypher_query):
    """Execute a raw Cypher statement. Values must already be embedded as Cypher literals."""
    cur.execute(f"SELECT * FROM cypher('{GRAPH}', $$ {cypher_query} $$) AS (r agtype)")
    conn.commit()


# ---------------------------------------------------------------------------
# Cypher literal serialisation
# AGE/Cypher map syntax differs from JSON: keys are unquoted, strings use
# single quotes.  JSON cannot be embedded directly inside $$ ... $$ blocks.
# ---------------------------------------------------------------------------

def _cypher_val(v):
    """Recursively convert a Python value to a Cypher literal string."""
    if v is None:
        return "null"
    if isinstance(v, bool):
        return "true" if v else "false"
    if isinstance(v, int):
        return str(v)
    if isinstance(v, float):
        return str(v)
    if isinstance(v, str):
        escaped = v.replace("\\", "\\\\").replace("'", "\\'")
        return f"'{escaped}'"
    if isinstance(v, list):
        return "[" + ", ".join(_cypher_val(i) for i in v) + "]"
    if isinstance(v, dict):
        parts = [f"{k}: {_cypher_val(val)}" for k, val in v.items()]
        return "{" + ", ".join(parts) + "}"
    raise TypeError(f"Cannot convert {type(v)} to Cypher literal")


def _cypher_map(d):
    parts = [f"{k}: {_cypher_val(v)}" for k, v in d.items()]
    return "{" + ", ".join(parts) + "}"


def _cypher_list(rows):
    return "[" + ", ".join(_cypher_map(r) for r in rows) + "]"


def bulk_insert_vertices(cur, conn, label, rows):
    """Insert vertex rows in batches using UNWIND with inline Cypher map literals."""
    for i in range(0, len(rows), BATCH):
        batch = rows[i : i + BATCH]
        cypher_list = _cypher_list(batch)
        cypher_exec(
            cur, conn,
            f"UNWIND {cypher_list} AS row CREATE (n:{label}) SET n = row RETURN 1",
        )
    print(f"  {label}: {len(rows)} vertices")


def bulk_insert_edges(cur, conn, src_label, tgt_label, edge_label, rows):
    """rows: flat dicts of {srcId, tgtId, [prop1, prop2, ...]}.
    Edge property keys are inferred as all keys except srcId/tgtId.
    CREATE clause is built with inline property access (row.propName).
    """
    if not rows:
        print(f"  ({src_label})-[:{edge_label}]->({tgt_label}): 0 edges")
        return
    edge_props = [k for k in rows[0] if k not in ("srcId", "tgtId")]
    if edge_props:
        props_clause = "{" + ", ".join(f"{k}: row.{k}" for k in edge_props) + "}"
        create_stmt = f"CREATE (a)-[:{edge_label} {props_clause}]->(b)"
    else:
        create_stmt = f"CREATE (a)-[:{edge_label}]->(b)"

    for i in range(0, len(rows), BATCH):
        batch = rows[i : i + BATCH]
        cypher_list = _cypher_list(batch)
        cypher_exec(
            cur, conn,
            f"""UNWIND {cypher_list} AS row
            MATCH (a:{src_label} {{id: row.srcId}}), (b:{tgt_label} {{id: row.tgtId}})
            {create_stmt}
            RETURN 1""",
        )
    print(f"  ({src_label})-[:{edge_label}]->({tgt_label}): {len(rows)} edges")


def edge_rows(csv_rows, src_col, tgt_col, extra_props=None):
    """Build flat edge rows: {srcId, tgtId, [prop...]}. extra_props: [(csv_col, prop_name, converter)]."""
    result = []
    for r in csv_rows:
        row = {"srcId": int(r[src_col]), "tgtId": int(r[tgt_col])}
        if extra_props:
            for csv_col, prop_name, converter in extra_props:
                row[prop_name] = converter(r[csv_col])
        result.append(row)
    return result


# ---------------------------------------------------------------------------
# Loader
# ---------------------------------------------------------------------------

def setup_graph(cur, conn):
    print("Setting up graph...")
    # Drop if exists
    cur.execute(
        "SELECT count(*) FROM ag_catalog.ag_graph WHERE name = %s", (GRAPH,)
    )
    if cur.fetchone()[0] > 0:
        cur.execute(f"SELECT drop_graph('{GRAPH}', true)")
        conn.commit()
    cur.execute(f"SELECT create_graph('{GRAPH}')")
    conn.commit()

    # Vertex labels
    for lbl in ["Person", "Post", "Comment", "Forum",
                "Tag", "TagClass", "City", "Country", "Continent",
                "University", "Company"]:
        cur.execute(f"SELECT create_vlabel('{GRAPH}', '{lbl}')")
    # Edge labels
    for lbl in ["KNOWS", "HAS_INTEREST", "IS_LOCATED_IN", "LIKES",
                "STUDY_AT", "WORK_AT", "HAS_CREATOR", "HAS_TAG",
                "CONTAINER_OF", "HAS_MEMBER", "HAS_MODERATOR",
                "REPLY_OF", "IS_PART_OF", "HAS_TYPE", "IS_SUBCLASS_OF"]:
        cur.execute(f"SELECT create_elabel('{GRAPH}', '{lbl}')")
    conn.commit()
    print("  Graph and labels created.")


def load_vertices(cur, conn, static_dir, dynamic_dir):
    print("Loading vertices...")

    # TagClass
    rows = read_csv(os.path.join(static_dir, "tagclass_0_0.csv"))
    bulk_insert_vertices(cur, conn, "TagClass",
        [{"id": int(r["id"]), "name": r["name"], "url": r["url"]} for r in rows])

    # Tag
    rows = read_csv(os.path.join(static_dir, "tag_0_0.csv"))
    bulk_insert_vertices(cur, conn, "Tag",
        [{"id": int(r["id"]), "name": r["name"], "url": r["url"]} for r in rows])

    # Places — split by type
    rows = read_csv(os.path.join(static_dir, "place_0_0.csv"))
    place_label = {}
    by_type = {"city": [], "country": [], "continent": []}
    for r in rows:
        pid = int(r["id"])
        ptype = r["type"]
        place_label[pid] = {"city": "City", "country": "Country", "continent": "Continent"}[ptype]
        by_type[ptype].append({"id": pid, "name": r["name"], "url": r["url"]})
    for ptype, label in [("city", "City"), ("country", "Country"), ("continent", "Continent")]:
        bulk_insert_vertices(cur, conn, label, by_type[ptype])

    # Organisations — split by type
    rows = read_csv(os.path.join(static_dir, "organisation_0_0.csv"))
    org_label = {}
    by_type = {"university": [], "company": []}
    for r in rows:
        oid = int(r["id"])
        otype = r["type"]
        org_label[oid] = {"university": "University", "company": "Company"}[otype]
        by_type[otype].append({"id": oid, "name": r["name"], "url": r["url"]})
    bulk_insert_vertices(cur, conn, "University", by_type["university"])
    bulk_insert_vertices(cur, conn, "Company", by_type["company"])

    # Person (language → speaks, email → list)
    rows = read_csv(os.path.join(dynamic_dir, "person_0_0.csv"))
    bulk_insert_vertices(cur, conn, "Person", [
        {
            "id": int(r["id"]),
            "firstName": r["firstName"],
            "lastName": r["lastName"],
            "gender": r["gender"],
            "birthday": int(r["birthday"]),
            "creationDate": int(r["creationDate"]),
            "locationIP": r["locationIP"],
            "browserUsed": r["browserUsed"],
            "speaks": r["language"].split(";"),
            "email": r["email"].split(";"),
        }
        for r in rows
    ])

    # Forum
    rows = read_csv(os.path.join(dynamic_dir, "forum_0_0.csv"))
    bulk_insert_vertices(cur, conn, "Forum", [
        {"id": int(r["id"]), "title": r["title"], "creationDate": int(r["creationDate"])}
        for r in rows
    ])

    # Post
    rows = read_csv(os.path.join(dynamic_dir, "post_0_0.csv"))
    bulk_insert_vertices(cur, conn, "Post", [
        {
            "id": int(r["id"]),
            "imageFile": r["imageFile"],
            "creationDate": int(r["creationDate"]),
            "locationIP": r["locationIP"],
            "browserUsed": r["browserUsed"],
            "language": r["language"],
            "content": r["content"],
            "length": int(r["length"]),
        }
        for r in rows
    ])

    # Comment
    rows = read_csv(os.path.join(dynamic_dir, "comment_0_0.csv"))
    bulk_insert_vertices(cur, conn, "Comment", [
        {
            "id": int(r["id"]),
            "creationDate": int(r["creationDate"]),
            "locationIP": r["locationIP"],
            "browserUsed": r["browserUsed"],
            "content": r["content"],
            "length": int(r["length"]),
        }
        for r in rows
    ])

    return place_label, org_label


def load_edges(cur, conn, static_dir, dynamic_dir, place_label, org_label):
    print("Loading edges...")

    # tagclass IS_SUBCLASS_OF tagclass — duplicate "TagClass.id" header, read positionally
    _, sub_rows = read_csv_rows(os.path.join(static_dir, "tagclass_isSubclassOf_tagclass_0_0.csv"))
    bulk_insert_edges(cur, conn, "TagClass", "TagClass", "IS_SUBCLASS_OF",
        [{"srcId": int(r[0]), "tgtId": int(r[1])} for r in sub_rows])

    # tag HAS_TYPE tagclass
    rows = read_csv(os.path.join(static_dir, "tag_hasType_tagclass_0_0.csv"))
    bulk_insert_edges(cur, conn, "Tag", "TagClass", "HAS_TYPE",
        edge_rows(rows, "Tag.id", "TagClass.id"))

    # place IS_PART_OF place — batch by (src_label, tgt_label)
    # CSV has duplicate header "Place.id|Place.id" — read positionally
    _, part_of_rows = read_csv_rows(os.path.join(static_dir, "place_isPartOf_place_0_0.csv"))
    groups = {}
    for row in part_of_rows:
        s, t = int(row[0]), int(row[1])
        key = (place_label[s], place_label[t])
        groups.setdefault(key, []).append({"srcId": s, "tgtId": t})
    for (sl, tl), batch_rows in groups.items():
        bulk_insert_edges(cur, conn, sl, tl, "IS_PART_OF", batch_rows)

    # organisation IS_LOCATED_IN place — batch by (src_label, tgt_label)
    rows = read_csv(os.path.join(static_dir, "organisation_isLocatedIn_place_0_0.csv"))
    groups = {}
    for r in rows:
        oid = int(r["Organisation.id"])
        pid = int(r["Place.id"])
        key = (org_label[oid], place_label[pid])
        groups.setdefault(key, []).append({"srcId": oid, "tgtId": pid})
    for (sl, tl), batch_rows in groups.items():
        bulk_insert_edges(cur, conn, sl, tl, "IS_LOCATED_IN", batch_rows)

    # person IS_LOCATED_IN city
    rows = read_csv(os.path.join(dynamic_dir, "person_isLocatedIn_place_0_0.csv"))
    bulk_insert_edges(cur, conn, "Person", "City", "IS_LOCATED_IN",
        edge_rows(rows, "Person.id", "Place.id"))

    # person HAS_INTEREST tag
    rows = read_csv(os.path.join(dynamic_dir, "person_hasInterest_tag_0_0.csv"))
    bulk_insert_edges(cur, conn, "Person", "Tag", "HAS_INTEREST",
        edge_rows(rows, "Person.id", "Tag.id"))

    # person KNOWS person — duplicate "Person.id" header, read positionally
    knows_headers, knows_rows = read_csv_rows(os.path.join(dynamic_dir, "person_knows_person_0_0.csv"))
    bulk_insert_edges(cur, conn, "Person", "Person", "KNOWS",
        [{"srcId": int(r[0]), "tgtId": int(r[1]), "creationDate": int(r[2])} for r in knows_rows])

    # person STUDY_AT university
    rows = read_csv(os.path.join(dynamic_dir, "person_studyAt_organisation_0_0.csv"))
    study_rows = [
        {"srcId": int(r["Person.id"]), "tgtId": int(r["Organisation.id"]), "classYear": int(r["classYear"])}
        for r in rows if org_label.get(int(r["Organisation.id"])) == "University"
    ]
    bulk_insert_edges(cur, conn, "Person", "University", "STUDY_AT", study_rows)

    # person WORK_AT company
    rows = read_csv(os.path.join(dynamic_dir, "person_workAt_organisation_0_0.csv"))
    work_rows = [
        {"srcId": int(r["Person.id"]), "tgtId": int(r["Organisation.id"]), "workFrom": int(r["workFrom"])}
        for r in rows if org_label.get(int(r["Organisation.id"])) == "Company"
    ]
    bulk_insert_edges(cur, conn, "Person", "Company", "WORK_AT", work_rows)

    # post HAS_CREATOR person
    rows = read_csv(os.path.join(dynamic_dir, "post_hasCreator_person_0_0.csv"))
    bulk_insert_edges(cur, conn, "Post", "Person", "HAS_CREATOR",
        edge_rows(rows, "Post.id", "Person.id"))

    # post IS_LOCATED_IN country
    rows = read_csv(os.path.join(dynamic_dir, "post_isLocatedIn_place_0_0.csv"))
    bulk_insert_edges(cur, conn, "Post", "Country", "IS_LOCATED_IN",
        [{"srcId": int(r["Post.id"]), "tgtId": int(r["Place.id"])} for r in rows])

    # post HAS_TAG tag
    rows = read_csv(os.path.join(dynamic_dir, "post_hasTag_tag_0_0.csv"))
    bulk_insert_edges(cur, conn, "Post", "Tag", "HAS_TAG",
        edge_rows(rows, "Post.id", "Tag.id"))

    # forum CONTAINER_OF post
    rows = read_csv(os.path.join(dynamic_dir, "forum_containerOf_post_0_0.csv"))
    bulk_insert_edges(cur, conn, "Forum", "Post", "CONTAINER_OF",
        edge_rows(rows, "Forum.id", "Post.id"))

    # forum HAS_MEMBER person
    rows = read_csv(os.path.join(dynamic_dir, "forum_hasMember_person_0_0.csv"))
    bulk_insert_edges(cur, conn, "Forum", "Person", "HAS_MEMBER",
        edge_rows(rows, "Forum.id", "Person.id",
                  [("joinDate", "joinDate", int)]))

    # forum HAS_MODERATOR person
    rows = read_csv(os.path.join(dynamic_dir, "forum_hasModerator_person_0_0.csv"))
    bulk_insert_edges(cur, conn, "Forum", "Person", "HAS_MODERATOR",
        edge_rows(rows, "Forum.id", "Person.id"))

    # forum HAS_TAG tag
    rows = read_csv(os.path.join(dynamic_dir, "forum_hasTag_tag_0_0.csv"))
    bulk_insert_edges(cur, conn, "Forum", "Tag", "HAS_TAG",
        edge_rows(rows, "Forum.id", "Tag.id"))

    # comment HAS_CREATOR person
    rows = read_csv(os.path.join(dynamic_dir, "comment_hasCreator_person_0_0.csv"))
    bulk_insert_edges(cur, conn, "Comment", "Person", "HAS_CREATOR",
        edge_rows(rows, "Comment.id", "Person.id"))

    # comment IS_LOCATED_IN country
    rows = read_csv(os.path.join(dynamic_dir, "comment_isLocatedIn_place_0_0.csv"))
    bulk_insert_edges(cur, conn, "Comment", "Country", "IS_LOCATED_IN",
        edge_rows(rows, "Comment.id", "Place.id"))

    # comment HAS_TAG tag
    rows = read_csv(os.path.join(dynamic_dir, "comment_hasTag_tag_0_0.csv"))
    bulk_insert_edges(cur, conn, "Comment", "Tag", "HAS_TAG",
        edge_rows(rows, "Comment.id", "Tag.id"))

    # comment REPLY_OF comment — duplicate "Comment.id" header, read positionally
    _, roc_rows = read_csv_rows(os.path.join(dynamic_dir, "comment_replyOf_comment_0_0.csv"))
    bulk_insert_edges(cur, conn, "Comment", "Comment", "REPLY_OF",
        [{"srcId": int(r[0]), "tgtId": int(r[1])} for r in roc_rows])

    # comment REPLY_OF post
    rows = read_csv(os.path.join(dynamic_dir, "comment_replyOf_post_0_0.csv"))
    bulk_insert_edges(cur, conn, "Comment", "Post", "REPLY_OF",
        edge_rows(rows, "Comment.id", "Post.id"))

    # person LIKES post
    rows = read_csv(os.path.join(dynamic_dir, "person_likes_post_0_0.csv"))
    bulk_insert_edges(cur, conn, "Person", "Post", "LIKES",
        edge_rows(rows, "Person.id", "Post.id",
                  [("creationDate", "creationDate", int)]))

    # person LIKES comment
    rows = read_csv(os.path.join(dynamic_dir, "person_likes_comment_0_0.csv"))
    bulk_insert_edges(cur, conn, "Person", "Comment", "LIKES",
        edge_rows(rows, "Person.id", "Comment.id",
                  [("creationDate", "creationDate", int)]))


def main():
    vanilla_dir = sys.argv[1] if len(sys.argv) > 1 else "test-data/vanilla"
    cs = sys.argv[2] if len(sys.argv) > 2 else \
        os.environ.get("CONNECTION_STRING", "postgresql://postgres:postgres@localhost:5432/postgres")

    static_dir = os.path.join(vanilla_dir, "static")
    dynamic_dir = os.path.join(vanilla_dir, "dynamic")

    print(f"Connecting to: {cs}")
    conn, cur = connect(cs)

    setup_graph(cur, conn)
    place_label, org_label = load_vertices(cur, conn, static_dir, dynamic_dir)
    load_edges(cur, conn, static_dir, dynamic_dir, place_label, org_label)

    cur.close()
    conn.close()
    print("Done. Run create-indexes.sql and vacuum-analyze.sh next.")


if __name__ == "__main__":
    main()
