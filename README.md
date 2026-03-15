# Task Management API (Mini Jira Backend)

A **Spring Boot based Task Management REST API** inspired by Jira.
This backend system supports managing boards, sprints, issues, workflows, attachments, and reporting with **JWT authentication**.

---

## Features

* JWT Authentication & Authorization
* User Management
* Board Management
* Sprint Management
* Issue Tracking
* Workflow Management
* Attachment Upload
* Reporting APIs
* Integration APIs

---

## Tech Stack

Backend

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Hibernate / JPA

Database

* MySQL

Documentation

* Swagger (OpenAPI)

Build Tool

* Maven

API Testing

* Postman

---

## API Documentation

Swagger UI

http://localhost:8080/swagger-ui/index.html

---

## Postman Collection

The Postman collection for testing all APIs is available in the repository.

Import this file into Postman:

postman/task-management-api-postman-collection.json

---

## Project Structure

```
com.task
│
├── Config          # Configuration classes
├── Controller      # REST Controllers
├── DTO             # Data Transfer Objects
├── Entity          # Database Entities
├── ENUM            # Enums used in the system
├── Exception       # Global Exception Handling
├── Repository      # JPA Repositories
├── Security        # JWT Authentication & Filters
├── Service         # Business Logic
├── Storage         # File Upload Handling
```

---

## Running the Project

Clone the repository

git clone https://github.com/juverias/task_management_api.git

Navigate to the project folder

cd task_management_api

Run the application

mvn spring-boot:run

---

## Author

Juveria Shaikh
Mini Jira Backend – Internship Project
