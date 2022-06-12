# Restaurant reservations API server

A simple Java & Spring server application for managing restaurant reservations through REST APIs.

# How to run it?

- Execute `./mvnw spring-boot:run` and open SwaggerUI at http://localhost:8080/swagger-ui.html
- You can run Postman collection with basic tests from `postman/Reservation API.postman_collection.json`
- Also check out the [**markdown API docs**](docs/README.md) (generated
  via [OpenAPI Generator](https://openapi-generator.tech/))

# Requirements

Implement REST API for restaurant reservations management by using Spring Boot.
The following features should be supported:

- Create/add new reservations (name, date, time, number of people);
- Modification and deletion of existing reservations;
- Querying for reservations by name, date and time;
- Support for HTTP basic authentication;
- REST API docs (SwaggerUI);
- Embedded DB (by choice);

# Technologies used

- Java 17, Maven, Spring Boot (Data JPA, Security, Web, Validation), H2 Embedded DB;
- JUnit 5 + JaCoCo, Postman for API testing;
- OpenAPI 3 and SwaggerUI (OpenAPI UI);

# Some comments

- OpenAPI spec & swaggerUI
    - Currently, swagger/openapi doesn't seem to support `java.time.LocalTime` (and other `time` classes)
        - [A GitHub issue for reference](https://github.com/quarkusio/quarkus/issues/6065#issuecomment-564578870)
        - In the spec/UI these objects will be rendered like this:
           ```json 
             "time": {
               "hour": 0,
               "minute": 0,
               "second": 0,
               "nano": 0
             }
           ```
          which is not what we'd like to see and use, but anyway, the API works :) It could be worked around by
          taking `java.time.LocalDateTime` instead two separate date and time objects.
    - API response docs - by not using `@ResponseStatus(...)` on controller handler methods we don't utilize the
      automatic response documentation. Personally I'd prefer to use `ResponseEntity.status(...).body(...).build()`.
      Same applies for `@ExceptionHandler` methods. Alternatively response statuses can be verbosely described via
      annotations
- API and DB models could be improved; For now we rely on Hibernate to define the DB schema and app code to init tables
- Nothing more than the required model (reservation) and properties (name, date, time, number of people) has been
  defined and implemented. A better real-world app would (of course) consist of richer structure and relations

# TODOs

- [X] Include code coverage tools/libs (JaCoCo)
- [ ] Cover codebase with unit tests
- [X] Add some integration tests
- [ ] e2e feature verification tests
- [ ] Containerize application
- [ ] Configure to persist console logs to file
- [ ] Sorting, paging and filtering for reservations query API (SPaF)
- [ ] Set-up CI/CD (Github Actions) pipelines
- [X] 90%+ (line) code coverage
