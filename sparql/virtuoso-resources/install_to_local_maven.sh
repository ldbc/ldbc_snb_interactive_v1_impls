#!/bin/bash

mvn install:install-file \
    -Dfile=virtjdbc4.jar \
    -DgroupId=com.virtuoso.virtjdbc4 \
    -DartifactId=virtjdbc4 \
    -Dversion=3.0 \
    -Dpackaging=jar
mvn install:install-file \
    -Dfile=virt_sesame4.jar \
    -DgroupId=virtuoso \
    -DartifactId=virtuoso-sesame4 \
    -Dversion=4.0.0 \
    -Dpackaging=jar
