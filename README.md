# Spring Transactional

A Spring Boot application that demonstrates the correct usage of the `@Transactional` annotation, focusing on rollback mechanisms for domain-specific exceptions. The project highlights how to ensure atomic operations in service layers using custom exceptions, transactional annotations, and proper unit testing strategies.

---

## 🚀 Features

- **Spring Boot & JPA Integration**: Easily manage persistence for domain entities (`Ship`, `Container`) using Spring Data JPA.
- **Transactional Integrity**: Ensures data consistency by rolling back on business rule violations.
- **Custom Exceptions**: Domain-specific exceptions (`InvalidTonnageException`, `NegativeWeightException`) to enforce business validation rules.
- **Lombok Simplification**: Reduces boilerplate with `@Data`, constructors, and equals/hashCode implementations.
- **In-Memory Testing**: Uses H2 and Mockito for fast, isolated testing.
- **Atomic Batch Operations**: Demonstrates how transactional boundaries protect against partial inserts.

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/app/
│   │   ├── Exceptions/
│   │   ├── Model/
│   │   ├── Repository/
│   │   ├── Service/
│   │   └── Application.java
├── test/
│   └── java/com/app/ShipServiceTestSuite.java
```

---

## 🧱 Core Modules

### 1. `Container` and `Ship` Entities
- Defined with JPA annotations.
- Managed via Spring Data repositories.
- Use Lombok to minimize boilerplate code.

### 2. Service Layer
- `ContainerService`: Uses `@Transactional` to ensure rollback on `NegativeWeightException`.
- `ShipService`: Uses `@Transactional(rollbackFor = InvalidTonnageException.class)` to roll back the entire operation when invalid tonnage is detected.

### 3. Custom Exceptions
- `InvalidTonnageException`: Thrown for non-positive ship tonnage.
- `NegativeWeightException`: Raised when a container’s weight is negative.

### 4. Application Entry Point
- Demonstrates transactional success and rollback using test data.
- Logs results after operations to verify data consistency.

### 5. Unit Testing
- Reflection-based assertions confirm `@Transactional` and `rollbackFor` configuration in `ShipService`.

---

## ✅ How It Works

### Example: Valid Container Insertion
```java
containerService.addListContainers(List.of(
    new Container("toys", 5),
    new Container("candy", 5)
));
```
- Commits the entire batch.

### Example: Invalid Container Insertion
```java
containerService.addListContainers(List.of(
    new Container("books", 10),
    new Container("helium", -1) // Triggers rollback
));
```
- Throws `NegativeWeightException` → transaction is rolled back → no containers are persisted.

### Example: Invalid Ship Insertion
```java
shipService.addListShips(List.of(
    new Ship("Titanic", 100000),
    new Ship("Ghost", 0) // Invalid tonnage triggers rollback
));
```
- Throws `InvalidTonnageException` → triggers rollback of all ships in the list.

---

## 🛠 Build & Run

### Requirements
- Java 21
- Maven 3.8+

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

---

## 🔍 Testing

Run tests with:
```bash
mvn test
```

Includes:
- Unit test coverage for `@Transactional` configuration.
- Integration-level demonstration via `main()` method output.

---

## 📚 Documentation Reference

- Spring `@Transactional`:
  - Ensures atomic method execution.
  - Supports rollback for unchecked and explicitly declared checked exceptions.

---

## 📌 Notes

- Lombok must be properly configured in IDE to prevent compilation errors.
- Uses H2 in-memory database by default for testing and demonstration purposes.
- Main method delays output slightly using `Thread.sleep(500)` to ensure context readiness.

---

## 👨‍💻 Author

This project was crafted to illustrate transactional boundaries and business-rule-based rollbacks using Spring Boot and Java 21.