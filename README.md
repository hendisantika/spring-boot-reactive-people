# spring-boot-reactive-people

# reactive-people-postgresql

#### reactive Spring Boot example, covering Webflux, R2dbc, Testcontainers, Open API, RestDocs.

![Java CI with Maven](https://github.com/hendisantika/spring-boot-reactive-people/workflows/Java%20CI%20with%20Maven/badge.svg)

**Prerequisites:**

* [Java 21](https://openjdk.net/)
* [Apache Maven](https:http://maven.apache.org/)
* [Docker](https://www.docker.com/)
* Optional [Postman](https://www.postman.com/)

**Themes:**

* Spring Webflux
* Router Functions/Handler Functions
* R2DBC Database Connectivity
* Unit Testing
* Integration Testing with Testcontainers
* Docker build/compose
* OpenAPI / Swagger API Documentation
* Spring RestDocs documentation

If you have Docker installed, grab a PostgreSQL image from Docker Hub and run the image.

```bash
docker pull postgres:17-alpine3.20
docker run --name postgresql -e POSTGRESQL_PASSWORD=password123 -e POSTGRES_DB=people -p 5432:5432 postgres:17beta1-alpine3.20
```

Or else, grab the app image via

```bash
docker pull hendisantika/reactive-people-postgresql:1.0.0
```

Application properties can be configured in

```bash
spring-boot-reactive-people/src/main/resources/application.properties
```

#### How to build and run

Type

```bash
mvn clean package
mvn clean spring-boot:run
```

to build and run the application on your local environment.

To run the app as a Docker Container type the following commands to build
an run the image:

```bash
docker build ./ -t reactive-people-postgresql
docker-compose up --force-recreate
```

Use curl, httpie etc. to call the HTTP Endpoints, or use Postman, a collection file is located under

```bash
reactive-people-postgresql.postman_collection.json
```

Point your browser to

```bash
http://localhost:8080/swagger-ui
```

to try out the API or see the documentation.

Point your browser to

```bash
http://localhost:8080/docs/index.html
```

to read the RestDOCs API documentation. 
