#!/bin/bash

FEATURE=$1

for f in interactive-complex-{1..14}.sql interactive-short-{1..7}.sql interactive-update-{1..8}.sql; do
  if [ -f "$f" ] && grep -q "$FEATURE" "$f"; then
    echo "$f: yes"
  fi
done
