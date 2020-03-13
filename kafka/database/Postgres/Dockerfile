# docker build -t postgreDatabase .
FROM postgres
COPY init.sql /docker-entrypoint-initdb.d/init.sql

ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=user
ENV POSTGRES_DB=rainbow_database