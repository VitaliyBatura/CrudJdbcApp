FROM postgres:14-alpine
COPY /src/test/resources/db.sql /docker-entrypoint-initdb.d/
