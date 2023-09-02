FROM postgres:14-alpine
COPY /src/main/resources/db.sql /docker-entrypoint-initdb.d/
