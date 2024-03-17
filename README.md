**Library Management System**

- This is a RESTful API project for managing books in a library. It provides endpoints for creating, reading, updating, and deleting books.

<hr>

**Technologies Used**

- Java 17

- Spring Boot

- Spring Security

- Spring Data JPA

- Mockito

- MySQL

- Swagger for documentation of API endpoints

- Postman for API testing

  <hr>
  
**Getting Started**

- To run this application locally, you need to have Java version 17 and Maven installed on your machine. Clone this repository and run the following command:

  - **mvn spring-boot:run**
  
The application will start on port 8080 by default.

<hr>

**JSON Format of Book Model**

**{
    "title": "",
    "author": "",
    "publicationYear": ""
}**

<hr>
  
**API Endpoints**

**GET /api/books**

- Get all books in the library.

**GET /api/books/{id}**

- Get a book by its ID.

**POST /api/books**

- Add a new book to the library.

**PUT /api/books/{id}**

- Update an existing book.

**DELETE /api/books/{id}**

- Delete a book from the library.

<hr>

**Authentication**

Basic authentication is used to secure the API. You can use the following credentials to authenticate:

Username: anuj

Password: anuj123

<hr>

**Database**

MySQL database is used for this project.
