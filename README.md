# Spring Boot REST API for User Management

This project is a simple REST API for user management built with Spring Boot, Spring Data JDBC, and H2 database.

## Project Structure

- `src/main/java/com/hapeglloyd/mike/restapi/`
  - `model/User.java`: User entity class
  - `repository/UserRepository.java`: Repository interface for User entities
  - `service/UserService.java`: Service layer for user operations
  - `web/UserController.java`: REST controller for user endpoints
- `src/main/resources/`
  - `schema.sql`: SQL script for creating the users table
- `src/test/java/com/hapeglloyd/mike/restapi/`
  - `RestapiApplicationTests.java`: Main test configuration class
  - `service/UserServiceTests.java`: Unit tests for UserService
  - `web/UserControllerIntegrationTests.java`: Integration tests for UserController

## Features

- Create a new user
- Retrieve a user by ID
- Retrieve all users
- Update an existing user
- Delete a user

## API Endpoints

- POST `/users`: Create a new user
- GET `/users/{id}`: Retrieve a user by ID
- GET `/users`: Retrieve all users
- PUT `/users`: Update an existing user
- DELETE `/users/{id}`: Delete a user

## Technologies

- Spring Boot
- Spring Data JDBC
- H2 Database
- JUnit 5
- Mockito
