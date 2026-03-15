# Task Management API (Mini Jira Backend)

A Spring Boot based Task Management REST API inspired by Jira.
This system allows managing boards, sprints, issues, workflows, attachments, and reporting with secure JWT authentication.

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Security (JWT)
* Hibernate / JPA
* MySQL
* Maven
* Swagger (OpenAPI)

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

## API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Project Structure

```
com.task
│
├── Config
├── Controller
├── DTO
├── Entity
├── ENUM
├── Exception
├── Repository
├── Security
├── Service
└── Storage
```

---

## Running the Project

Clone repository:

```
git clone https://github.com/juverias/task_management_api.git
```

Run the application:

```
mvn spring-boot:run
```

---

## Author

Juveria Shaikh
Mini Jira Backend – Internship Project
