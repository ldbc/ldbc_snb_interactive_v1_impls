#!/usr/bin/env python3

import os
import psycopg

conn = psycopg.connect(
        host=os.environ.get("UMBRA_HOST", "localhost"),
        user=os.environ.get("UMBRA_USER", "postgres"),
        password=os.environ.get("UMBRA_PASSWORD", "mysecretpassword"),
        port=int(os.environ.get("UMBRA_PORT", 8000))
    )
conn.close()
