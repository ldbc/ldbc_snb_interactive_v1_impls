#!/bin/bash

$STARDOG_HOME/bin/stardog-admin server start
$STARDOG_HOME/bin/stardog-admin db create $RDF_DB
$STARDOG_HOME/bin/stardog add $RDF_DB $RDF_DATA_DIR/*.ttl
