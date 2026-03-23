# Exercise 3

Extend your e-commerce microservice application by introducing asynchronous communication
using Spring Cloud Stream. The goal is to decouple service interactions and enable event-driven
workflows.

## Requirements:

- Refactor/Extend the communication between at least two services to use asynchronous messaging via Spring Cloud Stream
- Use only Spring Cloud Stream binder dependencies (e.g., for RabbitMQ/Kafka)
- Implement a simple saga between two services. (Recommendation: use choreography with events)
  Example: Oder is canceled → OrderCanceledEvent → Product/Inverntory needs to update reservations
