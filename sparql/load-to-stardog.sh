#!/bin/bash

$STARDOG_HOME/bin/stardog-admin server start
$STARDOG_HOME/bin/stardog-admin db create -n $RDF_DB
$STARDOG_HOME/bin/stardog data add $RDF_DB $RDF_DATA_DIR/*.ttl
