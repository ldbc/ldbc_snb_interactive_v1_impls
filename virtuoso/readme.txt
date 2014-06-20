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

TO BE CONTINUED ...
