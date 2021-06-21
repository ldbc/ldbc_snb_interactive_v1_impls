import psycopg2

con = psycopg2.connect(host="localhost", user="postgres", password="mysecretpassword", port=5432)
