#!/usr/bin/env python3

import os
import psycopg2

con = psycopg2.connect(
    host=os.environ.get("HYPER_HOST", "localhost"),
    user=os.environ.get("HYPER_USER", "ldbc"),
    password=os.environ.get("HYPER_PASSWORD", ""),
    port=int(os.environ.get("HYPER_PORT", 5432)),
)
con.close()
