#!/bin/bash

$STARDOG_INSTALL_DIR/bin/stardog-admin server start
$STARDOG_INSTALL_DIR/bin/stardog-admin db drop $RDF_DB
$STARDOG_INSTALL_DIR/bin/stardog-admin db create -n $RDF_DB
$STARDOG_INSTALL_DIR/bin/stardog data add $RDF_DB $RDF_DATA_DIR/*.ttl
