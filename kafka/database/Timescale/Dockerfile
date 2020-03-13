# docker build -t timeScaleDatabase .
FROM timescale/timescaledb
COPY init.sql /docker-entrypoint-initdb.d/init.sql

ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=user
ENV POSTGRES_DB=timescale