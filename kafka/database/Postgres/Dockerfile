# docker build -t postgreDatabase .
FROM postgres
COPY init.sql /docker-entrypoint-initdb.d/init.sql
COPY postgre_db_population.sql /docker-entrypoint-initdb.d/postgre_db_population.sql

ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=user
ENV POSTGRES_DB=postgre