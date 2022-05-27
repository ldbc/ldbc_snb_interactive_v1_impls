FROM python:3.8-slim
RUN apt-get update && apt-get install -y curl gnupg2 build-essential \
    libpq5 \
    && pip install --no-cache-dir psycopg
# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.9.0
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait
# Add loading scripts
ADD load.py /home/load.py
CMD ["python3 /home/load.py"]