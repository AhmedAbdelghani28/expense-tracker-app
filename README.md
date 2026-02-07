# Expense Tracker REST API

A simple, clean Expense Tracker REST API built with **Spring Boot 3**, **Java 21**, **Spring Data JPA**, **MySQL**, and **springdoc-openapi** (Swagger UI).
It demonstrates a typical layered architecture with DTOs, mappers, services, repositories, and global exception handling.

> **Modules**: Categories & Expenses with full CRUD.

---

##  Features

- CRUD endpoints for **Categories** and **Expenses**
- **DTO** + **Mapper** pattern to keep API models separate from JPA entities
- **Global exception handling** with consistent error payload
- **OpenAPI/Swagger UI** documentation
- Ready for **MySQL** with Spring Data JPA

---

##  Tech Stack

- **Java**: 21  
- **Spring Boot**: 3.2.x  
- **Build**: Maven  
- **DB**: MySQL (JPA/Hibernate)  
- **Docs**: springdoc-openapi (`/swagger-ui.html`)

Key dependencies (see `pom.xml`):
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `mysql-connector-j` (runtime)
- `lombok`
- `springdoc-openapi-starter-webmvc-ui`

---

##  Project Structure (key files)

```
src/main/java/net/javaguides/expense/
├─ ExpenseTrackerAppApplication.java
├─ controller/
│  ├─ CategoryController.java
│  └─ ExpenseController.java
├─ dto/
│  ├─ CategoryDto.java
│  └─ ExpenseDto.java
├─ entity/
│  ├─ Category.java
│  └─ Expense.java
├─ exceptions/
│  ├─ ErrorDetails.java
│  ├─ GlobalExceptionHandler.java
│  └─ ResourceNotFoundException.java
├─ mapper/
│  ├─ CategoryMapper.java
│  └─ ExpenseMapper.java
├─ repository/
│  ├─ CategoryRepository.java
│  └─ ExpenseRepository.java
└─ service/
   ├─ CategoryService.java
   ├─ ExpenseService.java
   └─ impl/
      ├─ CategoryServiceImpl.java
      └─ ExpenseServiceImpl.java
```

---

##  Domain Model

**Category**
- `id: Long (PK)`
- `name: String (unique, not null)`

**Expense**
- `id: Long (PK)`
- `amount: BigDecimal (not null)`
- `expenseDate: LocalDate (not null)`
- `category: Category (Many-to-One, not null)`

---

##  API Endpoints

Base URLs:
- Categories: `/api/categories`
- Expenses: `/api/expenses`

### Categories

| Method | Path                  | Body          | Response            |
|-------:|-----------------------|---------------|---------------------|
|  POST  | `/api/categories`     | `CategoryDto` | `201 Created`       |
|   GET  | `/api/categories/{id}`| —             | `CategoryDto`       |
|   GET  | `/api/categories`     | —             | `List<CategoryDto>` |
|   PUT  | `/api/categories/{id}`| `CategoryDto` | `CategoryDto`       |
| DELETE | `/api/categories/{id}`| —             | `200 OK` + text     |

**CategoryDto**
```json
{
  "id": 1,
  "name": "Food"
}
```

### Expenses

| Method | Path                 | Body         | Response             |
|-------:|----------------------|--------------|----------------------|
|  POST  | `/api/expenses`      | `ExpenseDto` | `201 Created`        |
|   GET  | `/api/expenses/{id}` | —            | `ExpenseDto`         |
|   GET  | `/api/expenses`      | —            | `List<ExpenseDto>`   |
|   PUT  | `/api/expenses/{id}` | `ExpenseDto` | `ExpenseDto`         |
| DELETE | `/api/expenses/{id}` | —            | `200 OK` + text      |

**ExpenseDto**
```json
{
  "id": 10,
  "amount": 125.50,
  "expenseDate": "2026-02-01",
  "categoryDto": {
    "id": 1,
    "name": "Food"
  }
}
```

> **Notes**
> - When creating an expense, provide the **category id** in `categoryDto.id`. Name can be omitted; service will link by id.
> - Dates use ISO-8601 (`YYYY-MM-DD`).

---

##  Error Handling

All errors return a consistent payload defined by `ErrorDetails` via `GlobalExceptionHandler`:

```json
{
  "timestamp": "2026-02-07T17:30:44.123",
  "message": "Category not found with id: 99",
  "details": "uri=/api/categories/99",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

- `RESOURCE_NOT_FOUND` (HTTP 404) for missing entities
- `INTERNAL_SERVER_ERROR` (HTTP 500) for unhandled exceptions

---

##  API Docs (Swagger)

Once the app is running, browse:

- Swagger UI: `http://localhost:8080/swagger-ui.html`  
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

The application also includes OpenAPI metadata via `@OpenAPIDefinition` in `ExpenseTrackerAppApplication`.

---

##  Configuration

Create `src/main/resources/application.properties` (or `.yml`) with your local MySQL settings:

```properties
# Server
server.port=8080

# Datasource
spring.datasource.url=jdbc:mysql://localhost:3306/expense_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_user
spring.datasource.password=your_password

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Swagger (springdoc) – defaults work, these are optional examples
# springdoc.api-docs.path=/v3/api-docs
# springdoc.swagger-ui.path=/swagger-ui.html
```

> For a fresh DB, `spring.jpa.hibernate.ddl-auto=update` will create tables automatically based on entities. For production, consider `validate`/migrations.

---

##  Build & Run

### Prerequisites
- Java 21
- Maven 3.9+
- MySQL 8.x running locally (or a compatible server)

### Run (Dev)
```bash
# build
mvn clean package

# run
mvn spring-boot:run
# or
java -jar target/expense-tracker-app-0.0.1-SNAPSHOT.jar
```

The app starts on `http://localhost:8080`.

---

##  Sample Requests

### Create Category
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{ "name": "Transport" }'
```

### Create Expense
```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 199.99,
    "expenseDate": "2026-02-01",
    "categoryDto": { "id": 1 }
  }'
```

### Get All Expenses
```bash
curl http://localhost:8080/api/expenses
```

---

##  Notes & Conventions

- DTOs are **Java `record`s**.
- Mappers are simple **static helper** classes (`CategoryMapper`, `ExpenseMapper`).
- Services encapsulate business logic; controllers are thin and annotated with **OpenAPI** metadata for docs.
- Repositories extend `JpaRepository` for CRUD.

---


