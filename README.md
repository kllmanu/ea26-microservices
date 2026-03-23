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

- Asynchronous communication using Spring Cloud Stream and RabbitMQ.
- Implemented a saga for order placement using choreography with events:

  1. `Ordering Service` places a `PENDING` order and emits `OrderPlacedEvent`.
  2. `Product Service` consumes `OrderPlacedEvent`, reserves stock atomically, and emits `StockReservedEvent` (success) or `StockReservationFailedEvent` (failure).
  3. `Ordering Service` consumes these events and updates the order status to `COMPLETED` or `CANCELLED`.

Run `./seed_reservation_failure.sh` to test the insufficient stock use case.
