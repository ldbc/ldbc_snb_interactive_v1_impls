#!/bin/bash

$STARDOG_HOME/bin/stardog-admin server start
$STARDOG_HOME/bin/stardog-admin db create $SF
$STARDOG_HOME/bin/stardog add $DB $RDF_DATA_DIR/*.ttl
