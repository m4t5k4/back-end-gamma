# Microservice met PostgreSQL

![Test, Build and Upload artifact](https://github.com/m4t5k4/back-end-gamma/workflows/Test,%20Build%20and%20Upload%20artifact/badge.svg)

## Docker setup

docker run -d --name some-postgres -e POSTGRES_PASSWORD=abc123 -p 5432:5432 postgres

troubleshooting:

psql -h localhost -U postgres

db's - \l

users - \du

connect - \c postgres

tables - \dt

TABLE prices;

## Spring application properties

De microservice zal de default database gebruiken

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres