#!/bin/bash

$NEO4J_HOME/bin/neo4j-admin import \
  --id-type=INTEGER \
  --nodes=Organisation="${NEO4J_DATA_DIR}/organisation${POSTFIX}" \
  --nodes=Person="${NEO4J_DATA_DIR}/person${POSTFIX}" \
  --nodes=Place="${NEO4J_DATA_DIR}/place${POSTFIX}" \
  --nodes=TagClass="${NEO4J_DATA_DIR}/tagclass${POSTFIX}" \
  --nodes=Tag="${NEO4J_DATA_DIR}/tag${POSTFIX}" \
  --relationships=IS_LOCATED_IN="${NEO4J_DATA_DIR}/organisation_isLocatedIn_place${POSTFIX}" \
  --relationships=HAS_INTEREST="${NEO4J_DATA_DIR}/person_hasInterest_tag${POSTFIX}" \
  --relationships=IS_LOCATED_IN="${NEO4J_DATA_DIR}/person_isLocatedIn_place${POSTFIX}" \
  --relationships=KNOWS="${NEO4J_DATA_DIR}/person_knows_person${POSTFIX}" \
  --relationships=STUDY_AT="${NEO4J_DATA_DIR}/person_studyAt_organisation${POSTFIX}" \
  --relationships=WORK_AT="${NEO4J_DATA_DIR}/person_workAt_organisation${POSTFIX}" \
  --relationships=IS_PART_OF="${NEO4J_DATA_DIR}/place_isPartOf_place${POSTFIX}" \
  --relationships=IS_SUBCLASS_OF="${NEO4J_DATA_DIR}/tagclass_isSubclassOf_tagclass${POSTFIX}" \
  --relationships=HAS_TYPE="${NEO4J_DATA_DIR}/tag_hasType_tagclass${POSTFIX}" \
  --delimiter '|'
