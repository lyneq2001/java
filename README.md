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

The application exposes basic CRUD endpoints under `/authors` and `/books`.
