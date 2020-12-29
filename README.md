# Microservice met PostgreSQL

## Docker setup

docker run -d --name some-postgres -e POSTGRES_PASSWORD=abc123 -p 5432:5432 postgres

## Spring application properties

De microservice zal de default database gebruiken

spring.datasource.url=jdbc:postgresql://localhost:5432/postgres