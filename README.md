# Web based database browser
A MVP of web based database browser backend that provides REST API endpoints:
- `com.dacosoft.controller.ConnectionDetailController` 
is used to manipulate with database connection details.
- `com.dacosoft.controller.DatabaseBrowserController`
is used to list some information about a local/remote database
- `com.dacosoft.controller.ConnectionDetailControllerJooq` 
has the same functionality as `ConnectionDetailController` but a JOOQ us used underneath.

An H2 database is initialized in the beginning.
H2 in this project is set upped in the embedded mode with Postgres mode and with automatic mixed mode where multiple processes can access the same database.

### Technologies
- Spring Boot
- H2 database
- JOOQ
- Maven
- Java 11

### How to start the application
1.Run `mvn clean initialize generate-sources compile`
- initialize: create and fill embedded H2 database
- generate-sources: generates JOOQ Java classes.
- test: compile and run tests

2.Start spring applicatoin in `com.dacosoft.App`

### How to run tests
`mvn test`

### features to consider
- the architecture can handle more DB vendors.
- connection passwords are encrypted.
- cached connections.
- JSON mapping can handle type org.postgresql.jdbc.PgArray
- An united exception handler with logging in REST controllers.
- JOOQ Java classes are created programmatically.