1. Download [Virtuoso](https://sourceforge.net/projects/virtuoso/files/virtuoso/) and unzip
2. Copy the `virtuoso.ini` file to `/path/to/unzipped/virtuoso/database/virtuoso.ini`
   - Replace the `/path/to/ldbc_snb_datagen/social_network_ttl` path with the real path
3. Copy the start-virtuoso.sh file to `/path/to/unzipped/virtuoso/bin/start-virtuoso.sh`
4. Start Virtuoso by typing `./start-virtuoso.sh`
5. [Load data](https://github.com/dbpedia/dbpedia-docs/wiki/Loading-Data-Virtuoso)
   - Start isql: `./isql 1127 dba dba`, then type the following commands:
     - `ld_dir('/path/to/ldbc_snb_datagen/social_network_ttl', '*.ttl', 'http://www.ldbc.eu');`
     - `rdf_loader_run();`
6. Permission to execute insert SPARQL queries over web interface
   - Type these commands to isql:
     - `grant execute on "DB.DBA.SPARQL_INS_OR_DEL_OR_MODIFY_CTOR_FIN" to "SPARQL";`
     - `grant execute on "DB.DBA.SPARQL_INSERT_DICT_CONTENT" to "SPARQL";`

