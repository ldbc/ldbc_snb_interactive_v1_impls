#!/usr/bin/env bash

set -eu
set -o pipefail

cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
cd ..

. scripts/vars.sh

echo -n "Stopping Umbra container . . ."
(docker ps -a --format {{.Names}} | grep --quiet --word-regexp ${UMBRA_CONTAINER_NAME}) && docker stop --time 1200 ${UMBRA_CONTAINER_NAME} >/dev/null
echo " Stopped."

echo -n "Removing Umbra container . . ."
(docker ps -a --format {{.Names}} | grep --quiet --word-regexp ${UMBRA_CONTAINER_NAME}) && docker rm ${UMBRA_CONTAINER_NAME} >/dev/null
echo " Removed."
