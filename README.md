
# Users Endpoint Application

This is a Spring Boot web application that provides a REST API for managing users. It uses Spring Security with JWT for authentication, an H2 in-memory database for data storage, and OpenAPI (Swagger UI) for API documentation.

## Technologies Used

- Java 18  
- Spring Boot 3.2.2  
- Spring Web  
- Spring Security  
- Spring Data JPA  
- H2 Database  
- Hibernate  
- Swagger UI (Springdoc OpenAPI)  
- JJWT (JWT tokens)  
- Lombok  
- Maven  

## Configuration

### `application.yaml`

- Embedded H2 database (`jdbc:h2:mem:testdb`)
- Hibernate `ddl-auto: update` (automatically updates the DB schema)
- Swagger available at `/docs`
- H2 Console at `/h2-console`
- Actuator endpoints at `/actuator`

### Swagger UI

API documentation is available at:
http://localhost:8080/docs

## Running the Project

1. Clone the repository:
    ```bash
    git clone <your-repo-url>
    cd users_endpoint
    ```

2. Build the project:
    ```bash
    mvn clean install
    ```

3. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## API Endpoints

The application exposes REST controllers located in the `controller` package and uses the following DTOs:
- `RegisterDTO`
- `LoginDTO`
- `AuthResponseDTO`

The business logic is implemented in the `UserServiceImpl` class.

## Security

JWT tokens are generated using the `JWTGenerator` and validated by the `JWTAuthenticationFilter`. Unauthorized requests are handled by `JWTAuthEntryPoint`.

## Testing

The project includes unit tests located in the `test` package, covering:
- Controllers (using `MockMvc`)
- Services

To run tests:
```bash
mvn test
```

---

## Author

Created by **Ihor Chubatenko** as a demonstration of building a secure REST API using Spring Boot and JWT authentication.

---
