# Flight Booking API

<h1 align="center">
  <br>
  <img width="1000" src="assets/images/airplane.png" alt="Flight Booking Project Banner"/>
  <br>
</h1>

<p align="center">
  <strong>Flight Booking API</strong> — A fully functional Spring Boot application following Clean Architecture principles, showcasing resilience, observability, and modern microservices practices.
</p>

---

## 📚 Table of Contents

- [🔍 About the Project](#-about-the-project)
  - [🔧 Tools & Plugins](#-tools--plugins)
  - [📑 Documentation](#-documentation)
  - [📊 Case Study](#-case-study)
  - [🚀 How FlightBooking Works](#-how-flightbooking-works)
  - [⚙️ Running the Project](#-running-the-project)
  

- [🏛 Architecture](#-architecture)
  - [🧱 Clean Architecture Principles](#-clean-architecture-principles)
  - [🗂 Project Structure](#-project-structure)
  - [📌 Key Terminologies](#-key-terminologies)
  

- [🧠 Patterns & Practices](#-patterns--practices)
  - [🗃 Caching](#-caching)
  - [🛡 Circuit Breaker](#-circuit-breaker)
  - [🚦 Rate Limiter](#-rate-limiter)
  - [⏱️ Time Limiter](#-time-limiter)
  

- [📈 Observability](#-observability)
  - [🔍 Actuator](#-actuator)
  - [📐 Micrometer](#-micrometer)
  - [📡 Prometheus](#-prometheus)
  - [📊 Grafana](#-grafana)
  

- [🛠 Error Handling](#-error-handling)


- [🧪 Testing](#-testing)
  - [🔬 Unit Tests - JUnit 5 + Mockito](#-unit-tests---junit-5--mockito)
  - [✅ Integration Tests - Cucumber](#-integration-tests---cucumber)


- [ℹ️ Helpful Resources](#ℹ-helpful-resources)
- [🔗 Useful Links](#-useful-links)

---

## 🔍 About the Project

This is a Spring Boot application simulating a real-world flight booking system. It demonstrates the use of Clean Architecture, caching, resilience patterns, observability, API documentation, and integration testing.

---

### 🔧 Tools & Plugins

| 🛠️ Technology      | 📦 Version             |
|---------------------|------------------------|
| ☕ Java JDK          | 23.0.1                 |
| 🌱 Spring Boot      | 3.3.5                  |
| 📦 Maven            | 3.8.7                  |
| 🥒 Cucumber         | 7.13.0                 |
| 🧪 JUnit            | 5                      |
| 🎭 Mockito          | (via Spring Boot Test) |
| ✨ Lombok            | 1.18.34               |
| 🐘 PostgreSQL       | 13                     |
| 🔍 Elasticsearch    | 8.15.3                 |
| 🐳 Docker           | 27.4.0                 |
| 📖 OpenAPI          | 2.1.0                  |
| 🛡️ Resilience4j    | 1.7.1                  | 
| 📈 Prometheus       | 2.44.0                 |
| 📊 Grafana          | 9.5.2                  |
| 📢 SLF4J            | 1.7.36                 |
| ⚡ Caffeine Cache    | 3.2.1                  |
| 💻 Developed with   | IntelliJ IDEA 2025.1.2 |

> For complete dependency information, see [`pom.xml`](pom.xml)

---

### 📑 Documentation

- [Swagger](http://localhost:8085/swagger-ui/index.html)
- [Postman Collection](https://drive.google.com/drive/folders/187wQkcLSKkIvQKHo5Jywe4cmQayKR9Bf?usp=sharing)

---

### 📊 Case Study

Access the series of four articles published on Medium and see details about the implementation of this project:

- [Part I](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-i-0847fb2833c2)
- [Part II](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-ii-839489387308)
- [Part III](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-iii-b1269635ed0c)
- [Part IV](https://medium.com/@souzaluis/applying-clean-architecture-in-java-with-spring-boot-framework-part-iv-a3cb82d5421a)

---

### 🚀 How FlightBooking Works

This flight booking system provides comprehensive flight analysis capabilities:

- **Flight Analysis**: Searches and analyzes flights by destination, date, airline, and currency
- **Price Calculation**: Computes average flight prices and baggage costs for better decision-making
- **Historical Data**: Stores analysis results in a database for future reference and trends

**External APIs used:**
- **Flights Data**: https://run.mocky.io/v3/75ff38da-58e1-4f00-8135-008c31e4415b  
- **Airport Locations**: https://run.mocky.io/v3/efb63a70-f1c8-4c01-996b-de8c179f3b5c  

---

### ⚙️ Running the Project

#### Option 1: Run in IntelliJ IDEA

```bash
# 1. Import the project into IntelliJ IDEA
# 2. Install dependencies
mvn clean install

# 3. Run the application from your IDE
```

#### Option 2: Run with Docker

```bash
# Start all services including the application
docker-compose --profile with-app-container up

# Note: The project will take a few minutes to start as all containers 
# (ElasticSearch, Postgres, Prometheus, Grafana) need to be healthy before running.
```

> See the complete Docker configuration in [`docker-compose.yml`](docker-compose.yml)

**Main endpoint example:**
```http
GET /api/v1/flights/avg?flyTo=LIS,OPO&currency=GBP&dateFrom=2024-12-01&dateTo=2024-12-02&airLines=TP,FR&page=1&rpp=11
```

> Also includes endpoints for reading and deleting historical flight records.

---

## 🏛 Architecture

### 🧱 Clean Architecture Principles

This project follows Uncle Bob's Clean Architecture:
- **Entities** (core business logic)
- **Use Cases** (application rules)
- **Interface Adapters** (API/controller layer)
- **Frameworks/Drivers** (external services like DB, cache, etc.)

> <img src="assets/images/clean-architecture.jpg" width="600"/>

---

### 🗂 Project Structure

> <img src="assets/images/project-structure.png" width="400"/>

---

### 📌 Key Terminologies

- **DTO**: Lightweight objects used for data transfer  
- **Model**: Rich domain objects with validation and logic  
- **Mapper**: Convert between models and DTOs

---

## 🧠 Patterns & Practices

### 🗃 Caching

#### Spring Boot Caching

Implements `@Cacheable` to reduce DB load and improve performance.

> <img src="assets/images/caching.png" width="500"/>

#### Hibernate Caching

Implements `@Cacheable` to reduce DB load and improve performance.

> <img src="assets/images/caching.png" width="500"/>


---

### 🛡 Circuit Breaker

Uses Resilience4j to prevent repeated failures when external services are down.

> <img src="assets/images/circuit-breaker.png" width="500"/>

---

### 🚦 Rate Limiter

The Rate Limiter pattern controls the rate of method invocations by granting a limited number of permissions per time window (for example, 5 calls per second), helping to prevent system overload.
> <img src="assets/images/rate-limiter.png" width="500"/>

---

### ⏱️ Time Limiter

Uses Resilience4j TimeLimiter to impose a hard deadline on asynchronous operations.

> <img src="assets/images/time-limiter.png" width="500"/>

---

## 📈 Observability

### 🔍 Actuator

Health Check Endpoint:  
[http://localhost:8085/actuator/health](http://localhost:8085/actuator/health)

```json
{
  "status": "UP",
  "components": {
    "db": { "status": "UP" },
    "diskSpace": { "status": "UP" },
    "elasticsearch": { "status": "UP" }
  }
}
```

### 📐 Micrometer

#### Collecting Custom Metrics

With Micrometer configured, you’ll automatically get metrics for:

- CPU, memory, JVM, HTTP server (latency, status codes)

- Data source (connection pool)

- Cache, if Spring Cache is enabled

---

### 📡 Prometheus

- Access Prometheus at: [http://localhost:9090](http://localhost:9090)  
> <img src="assets/images/prometheus.png" width="600"/>

---

### 📊 Grafana

- Access Grafana at: [http://localhost:3000](http://localhost:3000)  
- Default user/password: `admin/admin`  
> <img src="assets/images/grafana.png" width="600"/>

---

## 🛠 Error Handling

Standardized error format ensures consistency across APIs:

```json
{
  "timestamp": "2023-01-15T10:37:14",
  "httpCode": 500,
  "message": "Internal Server Error",
  "detail": "The flight codes are invalid. Use airport codes like: OPO,LIS."
}
```

> <img src="assets/images/handling.png" width="600"/>

---

## 🧪 Testing

This project follows a comprehensive testing strategy with multiple levels of testing to ensure code quality and reliability:

- **Unit Testing**: Built with **JUnit 5 + Mockito** for isolated component testing
- **Integration Testing**: Built with **Cucumber + JUnit 5** for behavior-driven development
- **Test Pyramid**: Emphasizes fast unit tests with fewer, focused integration tests
- **Prerequisites**: PostgreSQL container must be running before executing integration tests
- **Test Coverage**: Validates individual components, business logic, API endpoints, and data persistence

### 🔬 Unit Tests - JUnit 5 + Mockito

The project includes extensive unit testing to ensure individual components work correctly in isolation. Unit tests focus on testing business logic, mappers, utilities, and use cases without external dependencies.

#### Test Categories

- **Use Case Tests**: Test business logic and application services (`FlightsUseCaseImplTest`, `FlightsLogsUseCaseImplTest`)
- **Mapper Tests**: Validate data transformation between DTOs and domain models (`FlightMapperTest`, `LocationMapperTest`, etc.)
- **Utility Tests**: Test helper classes and utility functions (`StringUtilsTest`, `NumberUtilsConfigTest`, etc.)
- **Exception Tests**: Verify proper error handling and custom exceptions (`ExceptionTests`)

#### Key Features

- **Mockito Integration**: Uses `@Mock` and `@ExtendWith(MockitoExtension.class)` for dependency mocking
- **Isolated Testing**: Each test runs independently without external dependencies
- **Fast Execution**: Unit tests run quickly as they don't require Spring context or database connections
- **Clean Architecture Validation**: Tests verify that business logic is properly isolated from infrastructure concerns

#### Test Structure

```java
@ExtendWith(MockitoExtension.class)
class FlightsUseCaseImplTest {
    
    @Mock
    private FlightsGateway flightsGateway;
    
    @Mock
    private LoggerGateway loggerGateway;
    
    private FlightsUseCaseImpl flightsUseCase;
    
    @BeforeEach
    void setUp() {
        flightsUseCase = new FlightsUseCaseImpl(flightsGateway, loggerGateway);
    }
    
    @Test
    void shouldCalculateAverageFlightPrices() {
        // Test implementation
    }
}
```

#### Running Unit Tests

```bash
# Run all unit tests
mvn test

# Run specific test class
mvn test -Dtest=FlightMapperTest

# Run tests with specific pattern
mvn test -Dtest="*MapperTest"

# Run tests with coverage report
mvn test jacoco:report

# Run only unit tests (exclude integration tests)
mvn test -Dtest="!*AutomatedTests"
```

#### Test Organization

The unit tests are organized following the same package structure as the main source code:

```
src/test/java/org/pt/flightbooking/
├── application/
│   ├── mappers/           # Mapper unit tests
│   ├── usecases/impl/     # Use case unit tests
│   ├── utils/             # Utility unit tests
│   └── exception/         # Exception handling tests
└── ...
```

#### Testing Best Practices

- **AAA Pattern**: Tests follow Arrange-Act-Assert pattern for clarity
- **Descriptive Names**: Test method names clearly describe what is being tested
- **Single Responsibility**: Each test focuses on one specific behavior
- **Mock External Dependencies**: All external dependencies are mocked to ensure isolation
- **Fast Feedback**: Unit tests provide quick feedback during development

### ✅ Integration Tests - Cucumber

The project uses Cucumber for behavior-driven development (BDD) testing, allowing for human-readable test scenarios that serve as both documentation and automated tests.

#### Test Structure

- **Feature Files**: Located in `src/test/resources/features/`, these files contain human-readable test scenarios written in Gherkin syntax
- **Step Definitions**: Located in `src/test/java/org/pt/flightbooking/scenarios/`, these Java classes implement the steps defined in feature files
- **Test Runner**: `FlightsAutomatedTests.java` configures and runs all Cucumber tests

#### Key Features

- **Spring Integration**: Tests run within a Spring context using `@CucumberContextConfiguration`
- **Scenario Isolation**: Each test scenario runs in isolation with proper setup and teardown
- **Mock Components**: External dependencies like ElasticSearch are mocked for reliable testing
- **HTML Reports**: Cucumber generates detailed HTML reports showing test results

#### Test Scenarios

The project includes several test scenarios covering:

1. **Flight Search**: Testing the core flight search functionality
2. **Flight Records Management**: Testing CRUD operations for flight records
3. **Error Handling**: Testing graceful handling of invalid inputs and edge cases

#### Running the Tests

```bash
# Run all Cucumber tests
mvn test -Dtest=FlightsAutomatedTests

# Run specific feature
mvn test -Dcucumber.filter.tags="@FlightRecordsScenarioSteps"
```

> <img src="assets/images/cucumber-report.png" width="600"/>

---

## ℹ️ Helpful Resources

- **Airlines API**: https://api.skypicker.com/airlines  
- **Airport Codes**: https://airportcodes.aero/iata/

| IATA-Code | Airline          | Country   |
|-----------|------------------|-----------|
| TP        | TAP Air Portugal | Portugal  |
| FR        | Ryanair          | Ireland   |

---

## 🔗 Useful Links

- [Clean Architecture by Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Red Hat Clean Architecture Example](https://developers.redhat.com/articles/2023/08/08/implementing-clean-architecture-solutions-practical-example)