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
# Microservices Project - Docker Setup

This project contains a microservices architecture built with **Spring
Boot**, using **Docker** for containerization and **Apache Maven** for
building the applications.

## Microservices Included

-   **Registry Server** -- Service discovery using Netflix Eureka
-   **Gateway Server** -- API Gateway for routing requests
-   **Employee Service** -- Example business microservice

------------------------------------------------------------------------

# Project Structure

    microservices-project
    │
    ├── registry-server
    │   └── Dockerfile
    │
    ├── gateway-server
    │   └── Dockerfile
    │
    ├── employee-service
    │   └── Dockerfile
    │
    └── docker-compose.yml

------------------------------------------------------------------------

# Prerequisites

Make sure you have installed:

-   Docker
-   Docker Compose
-   Apache Maven
-   Java 17+

------------------------------------------------------------------------

# 1. Build the JAR files

First compile the services using Maven.

``` bash
mvn clean
mvn install package
```

This will generate the `.jar` files inside the `target` directory of
each service.

------------------------------------------------------------------------

# 2. Build Docker Images

Run the following commands inside each microservice folder.

### Registry Server

``` bash
docker build -t msvc-registry-server:latest .
```

### Gateway Server

``` bash
docker build -t msvc-gateway-server:latest .
```

### Employee Service

``` bash
docker build -t msvc-employee:latest .
```

------------------------------------------------------------------------

# 3. Verify Docker Images and Containers

List running containers:

``` bash
docker ps
```

List available images:

``` bash
docker images
```

------------------------------------------------------------------------

# 4. Run Containers

### Run Registry Server

``` bash
docker run -p 8761:8761 --name msvc-registry-server 9a93da8bfec3
```

### Run Gateway Server

``` bash
docker run -p 4040:4040 --name msvc-gateway-server 60ab4b20089f
```

### Run Employee Service

``` bash
docker run -p 8082:8082 --name msvc-employee 00be0af15e37
```

------------------------------------------------------------------------

# 5. Using Docker Compose

You can run all services together using Docker Compose.

Start the services:

``` bash
docker compose -f docker-compose registry.yaml up
docker compose -f docker-compose.yaml up
```

Stop the services:

``` bash
docker compose down

```

------------------------------------------------------------------------

# 6. Remove Containers

To remove a container:

``` bash
docker rm msvc-employee
```

------------------------------------------------------------------------

# 7. Remove Docker Images

To delete an image:

``` bash
docker rmi -f [docker-image-id]
```

Example:

``` bash
docker rmi -f 00be0af15e37
```

------------------------------------------------------------------------

# Ports Used

  Service            Port
  ------------------ ------
  Registry Server    8761
  Gateway Server     4040
  Employee Service   8082

------------------------------------------------------------------------

# Useful Docker Commands

Stop container

``` bash
docker stop container-name
```

View logs

``` bash
docker logs container-name
```

Restart container

``` bash
docker restart container-name
```
