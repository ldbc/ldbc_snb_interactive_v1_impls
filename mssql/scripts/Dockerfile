FROM python:3.10.4-slim-bullseye
# Add loading scripts
ENV WAIT_VERSION 2.9.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /home/wait
RUN chmod +x /home/wait

RUN apt-get update && apt-get install -y curl gnupg2 build-essential \
&& curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - \
&& curl https://packages.microsoft.com/config/debian/11/prod.list > /etc/apt/sources.list.d/mssql-release.list \
&& apt-get update && ACCEPT_EULA=Y apt-get install -y \
  msodbcsql18 \
  unixodbc-dev \
&& pip install --no-cache-dir pyodbc pandas numpy
ADD . /home/.
# Add docker-compose-wait tool -------------------
CMD ["python3 /home/load.py ${MSSQL_CSV_DIR}"]
