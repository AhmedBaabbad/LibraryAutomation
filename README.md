# Library Automation System

## Project Overview

This Library Management System is developed using Java and Spring Boot, focusing on efficient backend operations for library automation. The project includes features for managing books and members, with a robust security configuration, and utilizes the H2 database for data storage.

## Project Design

The project is designed as a backend-only application, providing a scalable solution for library automation. Leveraging Java and the Spring Boot framework, it ensures a clean and efficient design for handling library operations.

## Technology Stack

- Java
- Spring Boot
- H2 Database

## Features

### Librarian Role

- Add, update, and remove books from the system.
- Add, update, view, and remove members from the system.

### Member Role

- View available books.
- Borrow and return available books.
- Book status changes to BORROWED upon borrowing and AVAILABLE upon return.
- Delete your own account.

### Security Configuration

The project implements a secure authentication system using Spring Security. This ensures that only authorized users (librarians and members) can access and perform library operations.

### Database

The H2 database is utilized to store the necessary data for efficient library management. This in-memory database allows for seamless testing and development.

## Testing

The project incorporates unit tests to ensure the reliability and functionality of individual components. The unit tests cover various aspects of the application, verifying that each part behaves as expected.

## Getting Started

1. Clone the repository.
   ```bash
   git clone https://github.com/AhmedBaabbad/LibraryAutomation.git
