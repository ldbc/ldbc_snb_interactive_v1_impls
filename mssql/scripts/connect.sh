#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ../..

docker exec -it snb-interactive-mssql /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P MySecr3tP4ssword