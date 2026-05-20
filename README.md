# Acme Training Portal

Acme Training Portal is a deliberately vulnerable Spring Boot application for GitHub Advanced Security bootcamps. It models a small internal training catalog with tenant-scoped courses, comments, XML imports, and downloadable training documents.

Do not use this application as a production example.

## Run locally

```bash
./mvnw spring-boot:run
```

Open `http://localhost:8080`.

The app uses an in-memory H2 database and seeds sample courses every time it starts.

## Build

```bash
./mvnw test
./mvnw package
java -jar target/training-portal-1.0.0-SNAPSHOT.jar
```

## GHAS demo map

| Area | Example | Teaching point |
| --- | --- | --- |
| CodeQL security | Course search, course detail, and comment lookup concatenate request values into SQL | User-controlled data flowing into JDBC queries |
| CodeQL security | Course comments render with `th:utext` | Stored cross-site scripting |
| CodeQL security | `/download?file=...` resolves user-controlled document paths | Path traversal |
| CodeQL security | XML import preview expands entities with default parser settings | XML external entity processing |
| Secret Protection | Training tokens and passwords are hardcoded in Java and properties files | Secret detection and remediation workflow |
| Dependabot | `commons-text` and `commons-io` are pinned to older versions | Dependency review and update workflow |
| Code Quality | Resource leak, direct `Thread.run()`, boxed reference equality, missing `@Override`, unread locals, confusing field assignment, self-assignment, identical-value comparison, array index out of bounds, null dereference, synchronization on string constants, inefficient string use, and constant interface anti-pattern | Standard findings and pull request comments from GitHub Code Quality |

## Useful demo URLs

| URL | Purpose |
| --- | --- |
| `/` | Training catalog |
| `/?tenant=northwind&q=Secure` | Search flow |
| `/courses/1` | Course detail and comments |
| `/documents` | Document listing |
| `/download?file=onboarding.txt` | Document download |
| `/download?file=../../pom.xml` | Path traversal discussion |

## XML import example

Submit the form on the home page, or send a request like this:

```bash
curl -X POST http://localhost:8080/imports/preview \
  --data-urlencode 'xml=<course><title>Imported Security Training</title></course>'
```

## Notes for instructors

This repository is designed to produce baseline findings after GHAS is enabled. Keep fixes and exercises small during workshops so participants can connect one code change to one alert or pull request comment.

The file `src/test/java/com/acme/trainingportal/ParticipantCoverageExercisesTest.java` contains commented-out tests that participants can uncomment to increase code coverage and trigger a coverage report comment on the pull request.
