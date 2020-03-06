#!/bin/bash

$NEO4J_HOME/bin/neo4j-admin import \
  --id-type=INTEGER \
  --nodes=Organisation="${NEO4J_CSV_DIR}/organisation${NEO4J_CSV_POSTFIX}" \
  --nodes=Person="${NEO4J_CSV_DIR}/person${NEO4J_CSV_POSTFIX}" \
  --nodes=Place="${NEO4J_CSV_DIR}/place${NEO4J_CSV_POSTFIX}" \
  --nodes=TagClass="${NEO4J_CSV_DIR}/tagclass${NEO4J_CSV_POSTFIX}" \
  --nodes=Tag="${NEO4J_CSV_DIR}/tag${NEO4J_CSV_POSTFIX}" \
  --relationships=IS_LOCATED_IN="${NEO4J_CSV_DIR}/organisation_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
  --relationships=HAS_INTEREST="${NEO4J_CSV_DIR}/person_hasInterest_tag${NEO4J_CSV_POSTFIX}" \
  --relationships=IS_LOCATED_IN="${NEO4J_CSV_DIR}/person_isLocatedIn_place${NEO4J_CSV_POSTFIX}" \
  --relationships=KNOWS="${NEO4J_CSV_DIR}/person_knows_person${NEO4J_CSV_POSTFIX}" \
  --relationships=STUDY_AT="${NEO4J_CSV_DIR}/person_studyAt_organisation${NEO4J_CSV_POSTFIX}" \
  --relationships=WORK_AT="${NEO4J_CSV_DIR}/person_workAt_organisation${NEO4J_CSV_POSTFIX}" \
  --relationships=IS_PART_OF="${NEO4J_CSV_DIR}/place_isPartOf_place${NEO4J_CSV_POSTFIX}" \
  --relationships=IS_SUBCLASS_OF="${NEO4J_CSV_DIR}/tagclass_isSubclassOf_tagclass${NEO4J_CSV_POSTFIX}" \
  --relationships=HAS_TYPE="${NEO4J_CSV_DIR}/tag_hasType_tagclass${NEO4J_CSV_POSTFIX}" \
  --delimiter '|'
