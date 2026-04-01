# E-Commerce application

## Exercise 1

DDD as we know it.

## Exercise 2

- [Traefik](https://traefik.io/traefik) as gateway/reverse proxy
- Docker Compose to run all the microservices
- Swagger OpenAPI for documenting RESTful APIs

Run `docker-compose up -d` and open:

- `http://localhost:8080/dashboard/` for the traefik dashboard
- `http://localhost/swagger/index.html` for swagger openAPI

Run `./seed_data.sh` to seed the data (requires `jq`) to be installed.

## Exercise 3

- Spring Cloud Stream with RabbitMQ
- Saga (Choreography)

Run `./seed_reservation_failure.sh` to test the insufficient stock use case.

## Exercise 4

- [Hive Gateway](https://the-guild.dev/graphql/hive/docs/gateway) for GraphQL
- [Hive Mesh](https://the-guild.dev/graphql/mesh/v1/source-handlers/openapi)
- Traeffik is still in use for service discovery :)

Graphql Playground: http://localhost/hive/graphql
