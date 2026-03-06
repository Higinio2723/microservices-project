# Microservice Project

Spring Boot microservices project that includes three services: Registry Server (Eureka), Gateway, and Employee Service.

## Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Registry Server│◄────│     Gateway     │────►│  msvc-employee  │
│   (Eureka)      │     │   (API Gateway) │     │   (REST API)    │
│   Port: 8761    │     │   Port: 4040    │     │   Port: 8082    │
└─────────────────┘     └─────────────────┘     └─────────────────┘
```

## Prerequisites

- Java 17+
- Maven 3.6+

## Services

### 1. Registry Server (Eureka) - Port 8761

Eureka service discovery server.

```bash
cd registry-server
mvn spring-boot:run
```

> **Note:** This service must be started first if you want to use service discovery.

**Dashboard URL:** http://localhost:8761

---

### 2. Gateway - Port 4040

API Gateway that routes requests to microservices.

```bash
cd gateway
mvn spring-boot:run
```

> **Note:** Requires Registry Server to be running.

---

### 3. msvc-employee - Port 8082

Employee management microservice with REST API.

```bash
cd msvc-employee
mvn spring-boot:run
```

#### Available Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| POST | `/api/employees` | Create new employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

#### Useful URLs

- **REST API:** http://localhost:8082/api/employees
- **Swagger UI:** http://localhost:8082/swagger-ui.html
- **API Docs:** http://localhost:8082/api-docs
- **H2 Console:** http://localhost:8082/h2-console
  - JDBC URL: `jdbc:h2:mem:superdb`
  - Username: `system`
  - Password: *(empty)*

---

## Startup Order

To start all services with service discovery:

1. **Registry Server** (wait for it to fully start)
2. **Gateway**
3. **msvc-employee**

### Standalone Mode (without Eureka)

If you only need to run `msvc-employee` without the other services, the current configuration has Eureka disabled:

```bash
cd msvc-employee
mvn spring-boot:run
```

---

## Useful Commands

### Build all services

```bash
# From the project root
cd msvc-employee && mvn clean install
cd ../gateway && mvn clean install
cd ../registry-server && mvn clean install
```

### Run tests

```bash
cd msvc-employee
mvn test
```

### Package as JAR

```bash
cd msvc-employee
mvn clean package -DskipTests
java -jar target/msvcemployee-0.0.1-SNAPSHOT.jar
```

---

## Configuration

### msvc-employee

| Property | Value |
|----------|-------|
| Port | 8082 |
| Database | H2 (in-memory) |
| Eureka | Disabled |

To enable Eureka, modify `application.yml`:

```yaml
eureka:
  client:
    enabled: true
    register-with-eureka: true
    fetch-registry: true
```

---

## Logs

`msvc-employee` logs are saved in:
```
msvc-employee/logs/msvc-employee.log
```
