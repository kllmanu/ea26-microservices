# Exercise 1

Implement a simple e-commerce system using Java and Spring Boot. The application should allow
users to register, browse products, add products to a shopping cart, and place orders. The system should track users, products, carts, and orders, and store all data in a database of your choice. (In memory database, like h2, is comfortable for testing. Document if you use something else) Focus on developing a meaningful domain with some business logic (e.g., product availability, stock reservations/checks), but keep the scope manageable. The goal is not to build a production-ready shop, but to create a solid foundation for later exercises.

## Requirements

Your application should provide at least the following functionality:

- Create/Manage Users
- CRUD for Products (with stock/availability)
- Add/remove Products in a Cart (per user)
- Place Orders based on items in a Cart (with stock check)
- Allow users to view their order history and order details

Follow Spring Boots approach regarding code structure (controllers, services, repositories and
entities).
Document your APIs using openapi as mentioned in the lecture. (See https://springdoc.org/)
The path to access openapi will be: http://localhost:8080/swagger-ui/index.html
