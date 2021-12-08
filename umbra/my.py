import psycopg2

con = psycopg2.connect(host="localhost", user="postgres", password="mysecretpassword", port=5432)

cur = con.cursor()
cur.execute("select 42 as x")
res = cur.fetchall()
print(res)
