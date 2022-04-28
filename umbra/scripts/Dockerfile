FROM python:3.8-slim
RUN pip install --no-cache-dir psycopg2-binary
# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.9.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait
# Add loading scripts
ADD load.py /home/load.py
CMD ["python3 /home/load.py ${UMBRA_CSV_DIR}"]