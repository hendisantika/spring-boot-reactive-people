# spring-boot-reactive-people

Reactive Spring Boot example covering **WebFlux**, **R2DBC**, **Testcontainers**, **OpenAPI** and **Spring REST Docs**.

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-reactive-people/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-reactive-people/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot)
![Testcontainers](https://img.shields.io/badge/Testcontainers-2.x-2F7CF6)
[![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)](.github/dependabot.yml)

## Tech Stack

| Component          | Version / Notes                                      |
|--------------------|------------------------------------------------------|
| Java               | 25 (Eclipse Temurin)                                 |
| Spring Boot        | 4.1.1 (WebFlux, Data R2DBC, Validation, Actuator)    |
| Database           | PostgreSQL 17 (R2DBC + JDBC driver)                  |
| API documentation  | springdoc-openapi 3.x (Swagger UI), Spring REST Docs |
| Testing            | JUnit 5, Reactor Test, Testcontainers 2.x            |
| Build              | Maven (wrapper included)                             |

## Prerequisites

* [JDK 25](https://adoptium.net/temurin/releases/?version=25)
* [Apache Maven](https://maven.apache.org/) (or use the bundled `./mvnw`)
* [Docker](https://www.docker.com/) – required for Testcontainers and Docker Compose
* Optional: [Postman](https://www.postman.com/)

## Themes

* Spring WebFlux
* Router Functions / Handler Functions
* R2DBC database connectivity
* Unit testing (`@WebFluxTest` + `@MockitoBean`, `@DataR2dbcTest`)
* Integration testing with Testcontainers
* Docker build / Compose
* OpenAPI / Swagger API documentation
* Spring REST Docs documentation

## Database

The app expects PostgreSQL on `localhost:5433` (see `src/main/resources/application.properties`).
The easiest way is Docker Compose, which also initialises the schema from `schema.sql`:

```bash
docker compose up -d
```

Or run PostgreSQL manually:

```bash
docker run --name postgres -e POSTGRES_USER=yuji -e POSTGRES_PASSWORD=Password123 \
  -e POSTGRES_DB=people -p 5433:5432 -d postgres:17-alpine3.20
```

Spring Boot's Docker Compose support (`spring-boot-docker-compose`) will also start
`docker-compose.yml` automatically when you run the app locally.

## How to build, test and run

```bash
# build & run all tests (Docker must be running for Testcontainers)
./mvnw clean package

# run the application
./mvnw spring-boot:run
```

You can also start the app with a throw-away Testcontainers PostgreSQL instance by running
`TestReactivePeoplePostgresqlApplication` from `src/test/java`.

### Docker image

```bash
./mvnw clean package -DskipTests
docker build -t reactive-people-postgresql .
docker run -p 8080:8080 reactive-people-postgresql
```

The image is based on `eclipse-temurin:25-jre-alpine` and runs with the `docker` Spring profile.

## API

| Method | Path               | Description           |
|--------|--------------------|-----------------------|
| GET    | `/api/people`      | List all people       |
| GET    | `/api/people/{id}` | Find a person by id   |
| GET    | `/api/people/firstByName/{name}` | Find the first person by name |
| POST   | `/api/people`      | Create a person       |
| PUT    | `/api/people/{id}` | Update a person       |
| DELETE | `/api/people/{id}` | Delete a person by id |

Example:

```bash
curl -X POST http://localhost:8080/api/people \
  -H 'Content-Type: application/json' \
  -d '{"name":"Gojo"}'
```

### Documentation

* Swagger UI: <http://localhost:8080/swagger-ui>
* OpenAPI JSON: <http://localhost:8080/v3/api-docs>
* REST Docs (generated during `package`): <http://localhost:8080/docs/index.html>

## CI/CD & Automation

* **Java CI with Maven** (`.github/workflows/maven.yml`) – builds and tests on JDK 25 for every push / PR to `main`.
  Testcontainers uses the Docker daemon of the GitHub-hosted runner.
* **Release Drafter** – drafts release notes from merged PRs.
* **Release** – publishes the JAR on `v*` tags.
* **Dependabot** (`.github/dependabot.yml`) – weekly, grouped updates for Maven
  (Spring Boot, Spring, springdoc, Testcontainers, Maven plugins), GitHub Actions,
  the Dockerfile base image and Docker Compose services.

## Notes on Spring Boot 4 migration

* Testcontainers 2.x artifacts are prefixed with `testcontainers-` (e.g. `testcontainers-postgresql`)
  and `PostgreSQLContainer` lives in `org.testcontainers.postgresql`.
* Test slices moved to dedicated modules (`spring-boot-starter-webflux-test`,
  `spring-boot-starter-data-r2dbc-test`); `@MockBean` is replaced by `@MockitoBean`.
* `@SpringBootTest` needs `@AutoConfigureWebTestClient` to inject a `WebTestClient`.
* `server.error.*` properties are now `spring.web.error.*`.
* From JDK 23 onward, annotation processors (Lombok) must be declared in
  `maven-compiler-plugin` `annotationProcessorPaths`.
