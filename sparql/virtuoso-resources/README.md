1. Download [Virtuoso](https://sourceforge.net/projects/virtuoso/files/virtuoso/) and unzip
1. Copy the `virtuoso.ini` file to `/path/to/unzipped/virtuoso/database/virtuoso.ini`.
   - If you don't care about the security of your Virtuoso instance, simply set `DirsAllowed` to `/`
   - Otherwise, set the value of `DirsAllowed` to the path of the Turtle files
1. Copy the `start-virtuoso.sh` file to `/path/to/unzipped/virtuoso/bin/start-virtuoso.sh`
1. Start Virtuoso by typing `./start-virtuoso.sh`
1. [Load data](https://github.com/dbpedia/dbpedia-docs/wiki/Loading-Data-Virtuoso)
   - Start isql: `./isql 1127 dba dba`, then type the following commands:

      ```console
      ld_dir('/path/to/ldbc_snb_datagen/social_network_ttl', '*.ttl', 'http://www.ldbc.eu');
      rdf_loader_run();
      ```

1. Permission to execute insert SPARQL queries over web interface
   - Type these commands to isql:
   
      ```console
      grant execute on "DB.DBA.SPARQL_INS_OR_DEL_OR_MODIFY_CTOR_FIN" to "SPARQL";
      grant execute on "DB.DBA.SPARQL_INSERT_DICT_CONTENT" to "SPARQL";
      ```
