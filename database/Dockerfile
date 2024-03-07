FROM mariadb:latest

COPY setup.sql /docker-entrypoint-initdb.d/
ENV MARIADB_ROOT_PASSWORD="maria-db-root"
EXPOSE 3306