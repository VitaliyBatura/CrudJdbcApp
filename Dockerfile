FROM postgres:14-alpine
COPY /src/main/resources/db.sql /docker-entrypoint-initdb.d/db.sql
COPY /src/main/resources/tables.sql /docker-entrypoint-initdb.d/tables.sql