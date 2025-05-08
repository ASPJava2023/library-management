# library-management
Library Management System

Project Title: Library Management  web Application
Technology Stack: Spring Boot,Maven, lombok , Spring Security (JWT), H2DB (Dev) / PostgreSQL (Prod),JPA, Thymeleaf, Bootstrap, Swagger/OpenAPI, Resilience4j, Log4J,pom.xml.
1. Project Overview
   The Library Management Microservice is a secure, modular, and scalable solution for managing library operations such as book lending, user registration, and administrative tasks. It is built using Spring Boot with best practices to ensure production-readiness, observability, and resilience.

2. Key Actors
   •	Admin: System owner with control over Librarians.
   •	Librarian: Manages books and users.
   •	User (Library Member): Can borrow and return books, view history.
3. Functional Requirements
   3.1 Authentication & Authorization
   •	JWT-based authentication for secure tokenized sessions.
   •	Role-based access control (RBAC) using Spring Security.
   •	Secure login and registration endpoints for all roles.
   3.2 Admin Features
   •	Create, read, update, and delete Librarian accounts.
   3.3 Librarian Features
   •	Full CRUD on:
   o	Books (title, author, category, ISBN, availability, price, zoner, Image of Book, detail of book).
   o	Users (name, date of birth, address, photo, email).
   •	Upload and manage user and book images/files using S3-compatible storage in database/local system
   3.4 User Features
   •	View available books.
   •	Book a book and return it.
   •	View personal borrowing history and due dates.
   •	Get notifications (email ready integration points).

4. Non-Functional Requirements
   4.1 Security
   •	JWT with refresh tokens for session renewal.
   •	HTTPS enforcement with SSL termination support.
   •	Input validation & XSS/CSRF protection.
   •	Encrypted credentials and secrets management using Spring Cloud Config or Vault.
   4.2 Validation & Exception Handling
   •	Bean-level and request-level validation using javax.validation.
   •	Global Exception Handler using @ControllerAdvice.
   •	Custom exceptions with detailed error response models.
   4.3 Documentation
   •	Auto-generated Swagger / OpenAPI 3 documentation.
   •	Environment-specific API exposure (e.g., dev only).
   4.4 Observability & Monitoring
   •	Local logging using Log4J.
   4.5 Resilience & Scalability
   •	Circuit breaker, retry, rate limiter, and bulkhead isolation using Resilience4j.
   4.6 Deployment & Environment
   •	Environment profiles (dev, test, prod) with externalized configurations.

5. UI/UX Layer
   •	Built using Thymeleaf + Bootstrap 5 for a responsive, web/mobile-friendly web UI.
   •	Form-level validations and flash messages.
   •	Future-proofing for integration with React or Angular frontend.

6. Database Design
   •	H2 Database for local development.
   •	PostgreSQL or MySQL for production.
   •	Entities with auditing fields: createdBy, createdDate, updatedBy, updatedDate.

7. Logging & Audit
   •	Structured logs using  JSON format.
   •	Activity auditing for critical actions (login, borrow, return, CRUD).

8. Performance & Load Handling
   •	Optimized API responses using DTOs and pagination.
   •	Database indexing for faster queries.
   •	Asynchronous processing (e.g., email sending or large file uploads).

