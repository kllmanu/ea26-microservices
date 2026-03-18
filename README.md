# E-Commerce application

- [Traefik](https://traefik.io/traefik) as gateway/reverse proxy
- Docker Compose to run all the microservices
- Swagger OpenAPI for documenting RESTful APIs

## Setup

Run `docker-compose up -d` and open:

- `http://localhost:8080/dashboard/` for the traefik dashboard
- `http://localhost/swagger/index.html` for swagger openAPI

## Domain architecture

```mermaid
classDiagram
    %% Aggregate Roots
    class User {
        +UserId id
        +String name
        +String email
        +String password
    }

    class Product {
        +ProductId id
        +String name
        +BigDecimal price
        +Integer stock
        +Boolean available
        +isAvailable() boolean
        +isInStock(int quantity) boolean
    }

    class Cart {
        +CartId id
        +UserId userId
        +List~CartItem~ items
        +getTotalAmount() BigDecimal
        +addOrUpdateItem(ProductId, String, BigDecimal, int)
        +removeItem(ProductId)
        +clear()
    }

    class Order {
        +OrderId id
        +UserId userId
        +List~OrderItem~ items
        +BigDecimal totalAmount
        +LocalDateTime orderDate
        +OrderStatus status
        +create(UserId, List~OrderItem~) Order$
    }

    %% Value Objects & Child Entities
    class UserId {
        +UUID value
        +of(UUID) UserId$
        +generate() UserId$
    }

    class ProductId {
        +UUID value
        +of(UUID) ProductId$
    }

    class CartId {
        +UUID value
    }

    class OrderId {
        +UUID value
        +generate() OrderId$
    }

    class CartItem {
        +CartItemId id
        +ProductId productId
        +String productName
        +BigDecimal unitPrice
        +int quantity
        +getTotalPrice() BigDecimal
    }

    class OrderItem {
        +OrderItemId id
        +ProductId productId
        +String productName
        +BigDecimal unitPrice
        +int quantity
        +getTotalPrice() BigDecimal
    }

    %% Domain Relationships (Composition within Aggregates)
    Cart *-- "0..*" CartItem : contains
    Order *-- "0..*" OrderItem : contains

    %% Domain IDs & Cross-Aggregate Links
    User "1" --* UserId : identified by
    Product "1" --* ProductId : identified by
    Cart "1" --* CartId : identified by
    Order "1" --* OrderId : identified by

    %% Cross-Aggregate References via IDs
    Cart "0..1" ..> "1" UserId : references
    Order "0..*" ..> "1" UserId : references
    CartItem "0..*" ..> "1" ProductId : references
    OrderItem "0..*" ..> "1" ProductId : references

    %% Coloring by Aggregates
    %% User Aggregate (Blue)
    style User fill:#bbf,stroke:#333,stroke-width:2px
    style UserId fill:#bbf,stroke:#333,stroke-width:2px

    %% Product Aggregate (Green)
    style Product fill:#bfb,stroke:#333,stroke-width:2px
    style ProductId fill:#bfb,stroke:#333,stroke-width:2px

    %% Cart Aggregate (Orange)
    style Cart fill:#fdb,stroke:#333,stroke-width:2px
    style CartId fill:#fdb,stroke:#333,stroke-width:2px
    style CartItem fill:#fdb,stroke:#333,stroke-width:2px

    %% Order Aggregate (Pink/Light Red)
    style Order fill:#ffcccc,stroke:#333,stroke-width:2px
    style OrderId fill:#ffcccc,stroke:#333,stroke-width:2px
    style OrderItem fill:#ffcccc,stroke:#333,stroke-width:2px
```
