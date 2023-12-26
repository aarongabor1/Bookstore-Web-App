# SYSC4806_Project

Azure link: https://sysc4806-project.azurewebsites.net/

## To Access Database

1. Download DBeaver
2. Ensure these settings are in your application.properties file
```
    spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    
    # Enable H2 console
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    
    # H2 Server mode configuration
    spring.datasource.hikari.data-source-properties.DATABASE_TO_UPPER=false
```
3. Start the SYSC4806ProjectApplication.java
4. Use DBeaver to connect to our database
   1. Click on New Database Connection
   2. Select H2 Embedded V.2
   3. Enter Path: "mem:testdb"
   4. Enter Username: "sa"
   5. Enter Password: ""
   6. Hit Test Connection, Should see Connected 

## UML Diagrams

### Models UML
 
![img_1.png](img_1.png)

```plantuml
@startuml
class Book {
    - String ISBN
    - String title
    - String picture
    - String description
    - String author
    - String publisher
    - Integer quantity
    - Double price
}

class User {
    - Long userId
    - String name
    - UserRole role
    - List<Purchase> purchaseHistory
    - ShoppingCart shoppingCart
}

class ShoppingCart {
    - Long cartId
    - User user
    - List<Book> books
}

class Purchase {
    - Long purchaseId
    - User user
    - List<Book> books
    - Double totalCost
}

class BookRecommendations {
    - Long recommendationId
    - User user
    - List<Book> recommendedBooks
}

enum UserRole {
    BOOKSTORE_OWNER
    REGULAR_USER
}

User "1" -- "1" ShoppingCart
User "1" -- "*" Purchase
User "1" -- "*" BookRecommendations
Book "0..*" -- "0..*" ShoppingCart : contains >
Purchase "0..*" -- "0..*" Book : includes >
@enduml
```

### Rough System Diagram

![img.png](img.png)

```plantuml
@startuml
package "Model" {
    class Book {
       - String ISBN
       - String title
       - String picture
       - String description
       - String author
       - String publisher
       - Integer quantity
       - Double price
   }
   
   class User {
       - Long userId
       - String name
       - UserRole role
       - List<Purchase> purchaseHistory
       - ShoppingCart shoppingCart
   }
   
   class ShoppingCart {
       - Long cartId
       - User user
       - List<Book> books
   }
   
   class Purchase {
       - Long purchaseId
       - User user
       - List<Book> books
       - Double totalCost
   }
   
   class BookRecommendations {
       - Long recommendationId
       - User user
       - List<Book> recommendedBooks
   }
   
   enum UserRole {
       BOOKSTORE_OWNER
       REGULAR_USER
   }
   
   User "1" -- "1" ShoppingCart
   User "1" -- "*" Purchase
   User "1" -- "*" BookRecommendations
   Book "0..*" -- "0..*" ShoppingCart : contains >
   Purchase "0..*" -- "0..*" Book : includes >
}

package "Repository" {
    class BookRepository {
    }

    class UserRepository {
    }

    class ShoppingCartRepository {
    }

    class PurchaseRepository {
    }

    BookRepository ..> Book : accesses
    UserRepository ..> User : accesses
    ShoppingCartRepository ..> ShoppingCart : accesses
    PurchaseRepository ..> Purchase : accesses
}

package "Controller" {
    class BookController {
    }

    class UserController {
    }

    class ShoppingCartController {
    }

    class PurchaseController {
    }

    BookController ..> BookRepository : uses
    UserController ..> UserRepository : uses
    ShoppingCartController ..> ShoppingCartRepository : uses
    PurchaseController ..> PurchaseRepository : uses
}
@enduml
```
