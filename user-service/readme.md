# User Service - Mini UPI Payment Gateway Simulation

## Overview

The User Service is a Spring Boot microservice responsible for managing users in the Mini UPI Payment Gateway Simulation. It handles user registration, profile updates, and KYC status management.

This service is part of a larger microservices architecture simulating UPI transactions.

---

## Features

* User registration (CUSTOMER, MERCHANT, ADMIN)
* Fetch user by ID
* List all users
* Update KYC status (PENDING, VERIFIED, REJECTED)
* Update user profile (name, phone)
* Delete user
* Spring Security configured to allow open registration/login endpoints

---

## Models

### User

```java
public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private UserType userType;
    private KYCStatus kycStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
```

### Enums

```java
public enum UserType { CUSTOMER, MERCHANT, ADMIN }
public enum KYCStatus { PENDING, VERIFIED, REJECTED }
```

---

## APIs

### 1. Register User

* **POST** `/users`
* **Request Body:**

```json
{
  "name": "Adhish Pawar",
  "email": "adhish@example.com",
  "phone": "9876543210",
  "userType": "CUSTOMER"
}
```

* **Response:**

```json
{
  "userId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "name": "Adhish Pawar",
  "email": "adhish@example.com",
  "phone": "9876543210",
  "userType": "CUSTOMER",
  "kycStatus": "PENDING",
  "createdAt": "2025-09-12T09:30:00Z",
  "updatedAt": "2025-09-12T09:30:00Z"
}
```

### 2. Get User by ID

* **GET** `/users/{userId}`
* **Response:** JSON of the user (same as above)

### 3. List All Users

* **GET** `/users`
* **Response:** List of all users

### 4. Update KYC Status

* **PATCH** `/users/{userId}/kyc`
* **Request Body:**

```json
{
  "kycStatus": "VERIFIED"
}
```

* **Response:** Updated user JSON

### 5. Update User Profile

* **PATCH** `/users/{userId}/profile`
* **Request Body:**

```json
{
  "name": "Adhish Pawar Updated",
  "phone": "9123456780"
}
```

* **Response:** Updated user JSON

### 6. Delete User

* **DELETE** `/users/{userId}`
* **Response:** HTTP 204 No Content

---

## Spring Security Configuration

* CSRF disabled for APIs
* Open access to `/users` (POST) and `/auth/**` endpoints
* All other endpoints require authentication
* Passwords are hashed using BCrypt

---

## Repository

* **UserRepository**: Spring Data JPA repository
* Methods: `findByEmail`, `findByPhone`, `existsByEmail`, `existsByPhone`

## Service Layer

* **UserService** handles all business logic for CRUD operations and KYC updates.

## Controller Layer

* **UserController** exposes REST endpoints for all user operations.

---

## Running the Service

1. Configure database in `application.properties`.
2. Run the Spring Boot application:

```bash
mvn spring-boot:run
```

3. Test APIs using Postman or curl.

---

## Commit Message Example

```
feat(user-service): implement full User CRUD and KYC management with Spring Boot
- Added User entity, enums (UserType, KYCStatus)
- Created UserRepository for JPA operations
- Developed UserService with register, fetch, update KYC, delete
- Exposed REST endpoints via UserController (register, get, list, update KYC, delete, update profile)
- Configured Spring Security to allow open registration/login endpoints
```
