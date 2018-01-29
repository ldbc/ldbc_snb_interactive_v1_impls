#!/bin/bash

java -cp blazegraph.jar com.bigdata.rdf.store.DataLoader blazegraph.properties $RDF_DATA_DIR/*.ttl
