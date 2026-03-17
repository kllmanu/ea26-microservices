# Exercise 2

Analyze your implementation of the e-commerce application and divide it into multiple
microservices. Try to find independent functionalities to split the application. Implement inter-
service communication using REST APIs and/or gRPC. At least one microservice must use REST for its
API. You may use gRPC for others if you wish.

Try to have some domain logic in each microservice if possible. Organize your domain models
according to DDD aggregates and use primary keys (IDs) for referencing entities via inter-service APIs.

## Requirements

- Your architecture should at least consist of three individual microservices (three Spring Boot
projects)
- Remember that each microservices has its own persistence (database)
- A user should not interact with individual microservices. Keep the openapi
  Dashboard from
- Exercise 1 and use inter service communication to maintain the same
  functionality as in Exercise 1 (in total you will have at least four Spring
  Boot Projects)

Make sure that each microservices has a unique port in the `application.properties/application.yaml`.
