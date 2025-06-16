# Library Application

This project is a simple Spring Boot application for managing books and authors. It uses PostgreSQL for data storage.

## Running

1. Ensure PostgreSQL is running and create a database named `librarydb`.
2. Update `src/main/resources/application.properties` with your database credentials if they differ from the defaults.
3. Build the application with Maven:

```bash
./mvnw package
```

4. Run the application:

```bash
java -jar target/Lista5-0.0.1-SNAPSHOT.jar
```

The application exposes CRUD endpoints under `/authors` and `/books`. Each entity can be created, updated, retrieved or deleted. Books can also be listed by author via `/books/author/{authorId}`.

Swagger UI is available once the application is running at `http://localhost:8080/swagger-ui.html` for interactive API documentation.

### Web UI

A basic browser-based interface is served from `/index.html`. After starting the
application open `http://localhost:8080/` and use the forms to register, log in
and manage authors and books. All API calls are performed from the page using
HTTP Basic authentication.

### Authentication

Two endpoints are available for managing user accounts:

* `POST /auth/register` &ndash; create a new account with a chosen role (`USER` or `ADMIN`).
* `POST /auth/login` &ndash; authenticate with a username and password.

All other endpoints require authentication using HTTP Basic credentials.

