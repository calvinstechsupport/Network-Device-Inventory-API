# Network Device Inventory API

A Java 21 / Spring Boot REST API for registering, searching, updating and deleting network infrastructure assets such as routers, switches, firewalls, access points and servers. This is a learning and portfolio project with a real relational database schema, input validation, API documentation and automated integration tests.

## Technology stack

- Java 21
- Spring Boot 4.1.2 and Spring MVC
- Spring Data JPA / Hibernate
- PostgreSQL for hosted use; H2 in-memory database for a quick local start
- Flyway database migrations
- Bean Validation
- OpenAPI 3 and Swagger UI (springdoc-openapi 3.2.0)
- Spring Boot Actuator health endpoint
- JUnit 6 / Spring Boot MVC integration tests
- Docker and Render deployment configuration

## Architecture

The code is separated into layers so that each class has one main responsibility:

```text
HTTP client (Swagger UI / Postman / curl)
                  |
                  v
       NetworkDeviceController
                  |
                  v
        NetworkDeviceService
                  |
                  v
       NetworkDeviceRepository
                  |
                  v
        PostgreSQL / H2 database
```

- **Controller:** receives HTTP requests, validates request bodies and returns HTTP responses.
- **Service:** applies business rules, including duplicate-IP checks and search filters.
- **Repository:** uses Spring Data JPA to read and write relational records.
- **Entity:** maps the Java `NetworkDevice` class to the `network_devices` SQL table.
- **DTO records:** define the fields accepted from clients and returned by the API.
- **Flyway:** creates the database schema from versioned SQL migrations.

## Features

- Full CRUD: create, list, retrieve, update and delete devices.
- Search by name, manufacturer, model, IP address or location using `q`.
- Filter by device type and operational status.
- Validates required values, lengths and IPv4/IPv6 address syntax.
- Prevents two devices from using the same IP address.
- Returns consistent HTTP errors using RFC 9457-style Problem Details.
- Automatically records creation and update timestamps in UTC.
- Interactive OpenAPI/Swagger documentation.
- Health endpoint for hosting-platform health checks.
- Integration tests for successful CRUD, validation, duplicate IP protection, filtering and 404 handling.

## API endpoints

Base path: `/api/v1/devices`

| Method | Path | Description | Success status |
|---|---|---|---:|
| GET | `/api/v1/devices` | List devices | 200 |
| GET | `/api/v1/devices/{id}` | Retrieve one device | 200 |
| POST | `/api/v1/devices` | Register a device | 201 |
| PUT | `/api/v1/devices/{id}` | Replace editable device fields | 200 |
| DELETE | `/api/v1/devices/{id}` | Delete a device | 204 |
| GET | `/actuator/health` | Service health | 200 |
| GET | `/swagger-ui.html` | Interactive API documentation | 200 |
| GET | `/v3/api-docs` | OpenAPI JSON definition | 200 |

List filters can be combined, for example:

```text
GET /api/v1/devices?q=core&type=ROUTER&status=ACTIVE
```

Device types: `ROUTER`, `SWITCH`, `FIREWALL`, `ACCESS_POINT`, `SERVER`, `MODEM`, `OTHER`.

Statuses: `ACTIVE`, `INACTIVE`, `MAINTENANCE`, `RETIRED`.

## Quick start on Windows

### Prerequisites

1. Install a Java 21 JDK.
2. Install Apache Maven 3.9.11 or newer.
3. Install Git (optional for running locally, required for version control).

Check the installations in PowerShell:

```powershell
java -version
mvn -version
```

### Run the tests

From the project folder:

```powershell
mvn test
```

### Run locally

```powershell
mvn spring-boot:run
```

The default local profile uses an in-memory H2 database and applies the Flyway migration automatically. No database server or credentials are required for this quick-start mode.

Open:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Health: http://localhost:8080/actuator/health
- Devices: http://localhost:8080/api/v1/devices

Data in the default H2 database is temporary and disappears when the application stops. Use PostgreSQL for data that must persist.

## Database learning

The database table is created by `src/main/resources/db/migration/V1__create_network_devices.sql`. Open `docs/sql-practice.sql` for practice with `SELECT`, `WHERE`, `LIKE`, `COUNT`, `GROUP BY`, `UPDATE` and `DELETE`.

Example request:

```http
POST /api/v1/devices
Content-Type: application/json

{
  "name": "Core Router",
  "deviceType": "ROUTER",
  "manufacturer": "Cisco",
  "model": "ISR 1000",
  "ipAddress": "192.168.10.1",
  "location": "Networking Lab",
  "status": "ACTIVE",
  "notes": "Sample inventory record"
}
```

The file `docs/api-examples.http` contains ready-to-run requests for an IDE HTTP client. See `docs/LEARNING_GUIDE.md` for a walkthrough of the request flow, entity mapping, SQL concepts and API exercises.

## Deploy free (portfolio/demo use)

The repository includes `Dockerfile` and `render.yaml` for a Render Web Service. Use a Neon PostgreSQL database for the hosted relational data.

1. Create a PostgreSQL project on Neon and copy its connection details.
2. Create a new GitHub repository named `network-device-inventory-api` and push this project to its `main` branch.
3. In Render, create a Web Service from that repository and select the Free plan.
4. Set the following environment variables in Render (never commit their actual values):
   - `SPRING_DATASOURCE_URL` = `jdbc:postgresql://YOUR_NEON_HOST/YOUR_DATABASE?sslmode=require`
   - `SPRING_DATASOURCE_USERNAME` = your Neon username
   - `SPRING_DATASOURCE_PASSWORD` = your Neon password
   - `SPRING_FLYWAY_ENABLED` = `true`
5. Keep the health check path at `/actuator/health` and deploy.
6. Test `/actuator/health`, `/swagger-ui.html` and `/api/v1/devices` on the Render URL.

Render free web services sleep after 15 minutes without inbound traffic and can take around a minute to wake. Neon Free currently includes 1 GB of storage and 100 compute-hours per project per month. Treat this deployment as a demo, not a production service. Review current provider limits before relying on a free tier.

## Security and data handling

This portfolio build intentionally has no user authentication or role-based access control. Keep it for non-sensitive demonstration data until authentication, authorization, rate limiting, request logging controls and production database backups have been designed and tested. Do not add real customer records or publish database credentials.

## Tests

The integration test suite uses an isolated H2 test database. Run:

```powershell
mvn verify
```

GitHub Actions is configured in `.github/workflows/ci.yml` to run the build and tests on pushes and pull requests to `main`.

## Portfolio description

> Developed a Java 21 REST API using Spring Boot, Spring Data JPA and PostgreSQL to manage network infrastructure inventory. Implemented CRUD endpoints, layered architecture, SQL schema migrations, request validation, duplicate-IP protection, searchable filters, OpenAPI documentation, health checks and automated integration tests.

Only describe the project as completed after you have run the tests, tried the API and understood the code. This is a personal learning project, not professional employment experience.
