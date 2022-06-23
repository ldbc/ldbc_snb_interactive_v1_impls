#!/usr/bin/env python3

import os
import psycopg

conn = psycopg.connect(
        host=os.environ.get("POSTGRES_HOST", "localhost"),
        user=os.environ.get("POSTGRES_USER", "postgres"),
        password=os.environ.get("POSTGRES_PASSWORD", "mysecretpassword"),
        port=int(os.environ.get("POSTGRES_PORT", 5432))
    )
conn.close()