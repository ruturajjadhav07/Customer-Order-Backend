# Customer Order Management System

## 📌 Overview

This is a backend service for managing customer orders, products, and stock levels. It provides functionalities such as adding products, placing orders, and updating order items with stock validation.

## 🏗️ Tech Stack

- **Backend:** Java, Spring Boot, Spring Security
- **Database:** MySQL
- **API Testing:** Postman

---

## 🚀 Features

### 🛍️ Product Management (Admin)

- **Add a new product** 🆕
- **Update product details** ✏️
- **Delete products** ❌

### 🔍 Product Search & Filtering (Public)

- **View all products** 📋
- **Filter products by category** 📂
- **Filter products by price range** 💰
- **Search products by name** 🔎
- **Check stock quantity of a product** 📦

### 🛡️ Security & Authentication

- **Role-based access control (RBAC)** 🔑
- **Admin-only routes for product management** 👨‍💼

---

## 📦 Project Setup

### 🔧 Prerequisites

1. Install **Java 17+**
2. Install **MySQL**
3. Install **Postman** (for API testing)

### 🛠️ Installation Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ruturajjadhav07/Customer-Order-Backend.git
   cd Customer-Order-Backend
   ```
2. **Configure Database:**
   - Create a **MySQL database** named `order_cart`.
   - Update `application.properties` with your **database credentials**.
   ```bash
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/order_cart
   spring.datasource.username=username
   spring.datasource.password=password
   spring.jpa.show-sql=true
   spring.jpa.hibernate.ddl-auto=update
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Build & Run the Application**
   ```sh
   mvn spring-boot:run
   ```
---
## 🔥 API Endpoints

### **Customer APIs**
- `POST /register` → Register a new customer  
- `POST /login` → Login customer  
- `GET /customer/{id}` → Get customer details (only for logged-in user)  
- `PUT /customer/update/{id}` → Update customer details (only for logged-in user)  
- `DELETE /customer/delete/{id}` → Delete customer (only for logged-in user)  
- `GET /admin/customers` → Get all customers (only for Admins)  

### **Order APIs**
- `POST /customer/order` → Place an order  
- `GET /customer/orders/{id}` → Get order details by ID  
- `GET /customer/orders/{customerId}/orders` → Get all orders of a specific customer  
- `DELETE /customer/order/{customerId}/{orderId}` → Delete an order (only for the customer)  
- `GET /customer/admin/allorders` → Get all orders (only for Admins)  

### **Product APIs**
- `POST /products/admin/add` → Add a product (only for Admins)  
- `GET /products` → Get all products  
- `GET /products/category?category={category}` → Get products by category  
- `GET /products/price-range?minPrice={min}&maxPrice={max}` → Get products within a price range  
- `GET /products/search?name={name}` → Search products by name  
- `GET /products/{id}/quantity` → Check stock quantity of a product  
- `PUT /products/admin/update/{id}` → Update product details (only for Admins)  
- `DELETE /products/admin/delete/{id}` → Delete a product (only for Admins)  

### **Order Item APIs**
- `GET /order-items/{customer_id}/{order_id}/{product_id}` → Fetch order details by user ID  
- `PUT /order-items/customer/{customerId}/order/{orderId}/orderitem/{orderItemId}/update?addOnQuantity={quantity}` → Update order item quantity  

---

## 📚 Documentation
- **Spring Boot:** [Official Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- **Spring Security:** [Official Documentation](https://docs.spring.io/spring-security/reference/index.html)


## 📜 License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/ruturajjadhav07/Customer-Order-Backend/blob/main/LICENSE) file for details.

