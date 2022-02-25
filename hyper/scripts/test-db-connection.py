#!/usr/bin/env python3

import os
import psycopg2

con = psycopg2.connect(
    host=os.environ.get("POSTGRES_HOST", "localhost"),
    user=os.environ.get("POSTGRES_USER", "ldbc"),
    password=os.environ.get("POSTGRES_PASSWORD", ""),
    port=int(os.environ.get("POSTGRES_PORT", 5432)),
)
con.close()
