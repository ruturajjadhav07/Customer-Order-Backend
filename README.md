# Customer Order Management System

## 📌 Overview
This is a backend service for managing customer orders, products, and stock levels. It provides functionalities such as adding products, placing orders, and updating order items with stock validation.

## 🚀 Technologies Used
- **Java** (Spring Boot)
- **MySQL** (Database)
- **JPA/Hibernate** (ORM)
- **Spring Security** (Authentication & Authorization)
- **Postman** (API Testing)

## 📂 Project Structure
```
Directory structure:
└── ruturajjadhav07-customer-order-backend/
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── order/
    │   │   │           └── ordercart/
    │   │   │               ├── OrdercartApplication.java
    │   │   │               ├── config/
    │   │   │               │   ├── JWTFilter.java
    │   │   │               │   └── SecurityConfig.java
    │   │   │               ├── customer/
    │   │   │               │   ├── CustomerController.java
    │   │   │               │   ├── CustomerDetailService.java
    │   │   │               │   ├── CustomerModel.java
    │   │   │               │   ├── CustomerPrinciple.java
    │   │   │               │   ├── CustomerRepository.java
    │   │   │               │   ├── CustomerResponse.java
    │   │   │               │   └── CustomerService.java
    │   │   │               ├── exception/
    │   │   │               │   ├── CustomerNotFoundException.java
    │   │   │               │   ├── GlobalExceptionHandler.java
    │   │   │               │   ├── OrderCreationException.java
    │   │   │               │   ├── OrderItemNotFoundException.java
    │   │   │               │   ├── ProductNotFoundException.java
    │   │   │               │   └── StockUnavailableException.java
    │   │   │               ├── jwtservice/
    │   │   │               │   └── JWTService.java
    │   │   │               ├── oders/
    │   │   │               │   ├── OrderController.java
    │   │   │               │   ├── OrderModel.java
    │   │   │               │   ├── OrderRepository.java
    │   │   │               │   ├── OrderRequest.java
    │   │   │               │   ├── OrderResponse.java
    │   │   │               │   └── OrderService.java
    │   │   │               ├── orderitem/
    │   │   │               │   ├── OrderItem.java
    │   │   │               │   ├── OrderItemController.java
    │   │   │               │   ├── OrderItemRepository.java
    │   │   │               │   ├── OrderItemReq.java
    │   │   │               │   └── OrderItemService.java
    │   │   │               └── product/
    │   │   │                   ├── ProductController.java
    │   │   │                   ├── ProductModel.java
    │   │   │                   ├── ProductRepository.java
    │   │   │                   └── ProductService.java
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    │       └── java/
    │           └── com/
    │               └── order/
    │                   └── ordercart/
    │                       └── OrdercartApplicationTests.java
    └── .mvn/
        └── wrapper/
            └── maven-wrapper.properties

```

## ⚙️ Installation & Setup
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/ruturajjadhav07/Customer-Order-Backend.git
cd your-repo
```

### **2️⃣ Configure Database**
Update `src/main/resources/application.properties` with your MySQL database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **3️⃣ Build & Run the Application**
```sh
mvn spring-boot:run
```

## 🔥 API Endpoints
### **Customer APIs**
- `POST /customers` → Register a new customer

## 📜 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

