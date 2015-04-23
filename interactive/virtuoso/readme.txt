-----------------------------
Download Virtuoso:
-----------------------------
In order to use this benchmark with Virtuoso, you have to download the
up to date version of opensource Virtuoso from git, and to use the
feature/analytics branch.

Use this command to do that:
    $ git clone -b feature/analytics https://github.com/v7fasttrack/virtuoso-opensource.git

-----------------------------
Build Virtuoso:
-----------------------------
To (re)generate the configure script and all related build files,
use use the supplied script in your working directory:

    $ ./autogen.sh

If the above command succeed without any error messages, please use the
following command to check out all the options you can use:

    $ ./configure --help

For example, in order to install architecture-independent files in
the specific directory you can use:

    $ ./configure --prefix=path_to_dir

Then:

    $ make
    $ make install

to produce the default binaries. This takes some time, principally due
to building and filling the demo database, rendering the XML
documentation into several target formats and composing various
Virtuoso application packages. It takes about 30 minutes on a 2GHz
machine.


-----------------------------
Starting Virtuoso:
-----------------------------

Go to the specified folder, and there you will find folders: bin, lib,
share and var. In bin folder, you have virtuoso-t, and isql program,
that you will use in the following commands.


------------------------------------------
Download and install SNB Data Generator:
------------------------------------------
You can find the detailed information about this task here:
https://github.com/ldbc/ldbc_snb_datagen

Generate the dataset of preferred scale, and serialize the
output in ttl, or in csv_merge_foreign.

You can load data into Virtuoso on 2 possible ways:

1. ttl
Use isql client program from bin folder of virtuoso installation
   $ isql <PORT_NUMBER> (where the port number is specified in virtuoso.ini)
Use ld_dir function of load all the ttl files from a directory to
the graph sib
   SQL>  ld_dir('path_to_the_generated_files', '%.ttl', 'sib');
Run a couple of loaders in background: (for example 8 of them)
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
   SQL>  rdf_loader_run (); &
You can check the status of loading from the table load_list:
   SQL>  select * from load_list;
When it is finished, make a checkpoint:
   SQL>  checkpoint;

2. csv_merge_foreign
Use isql client program from bin folder of virtuoso installation.
You can run the attached 4 scritps (in folder scripts) to create the schema, and to load the data.
   $ isql <PORT_NUMBER> < ldschema.sql (where the port number is specified in virtuoso.ini)
   $ isql <PORT_NUMBER> < ldfile.sql
   $ isql <PORT_NUMBER> < schema.sql
   $ isql <PORT_NUMBER> < ld.sql

After loading, you have to create some stored procedures used in querues:
   $ isql <PORT_NUMBER> < procedures.sql


-----------------------------------
Download and install LDBC Driver:
-----------------------------------

You can find the detailed information about this task here:
https://github.com/ldbc/ldbc_driver

After that, you can install the jar file of ldbc_driver to the local
maven repository. Go to the root directory of ldbc_driver, and run:
   $ mvn clean install -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7 -DskipTests


-----------------------------------
Compile Db class for Virtuoso:
-----------------------------------

Go to java/virtuoso folder from this checkout, and run:
   $ mvn clean package -Dmaven.compiler.source=1.7 -Dmaven.compiler.target=1.7


--------------------------------------
Running the driver against Virtuoso:
--------------------------------------

You have to update the paths in the run.sh script, and use it for
running the benchmark.  Before that, please update the 3 configuration
files with your options. This process is explained here:
https://github.com/ldbc/ldbc_driver/wiki
