# Shopper Buddy

Backend API for managing products, tags, and shared shopping lists. The [Android frontend](https://github.com/SirMeows/shopper-buddy-app) is maintained separately.

## Features

- Create, update, and delete products and shopping lists.
- Organize products with tags.
- Share lists with owner, editor, and viewer roles.
- Authenticate with Google.

Built with Java 17, Spring Boot 3, Spring Security, Spring Data JPA, and MySQL.

## Run locally

Requirements: JDK 17, Maven, a running MySQL server, and Google OAuth credentials.

1. Create a MySQL database.
2. Create a Google OAuth web client. For local browser sign-in, register `http://localhost:8080/login/oauth2/code/google` as an authorized redirect URI.
3. Set these environment variables in the shell or IDE used to start the application:

| Variable | Value |
| --- | --- |
| `DATABASE_URL` | MySQL host and port, e.g. `localhost:3306` |
| `DATABASE_SCHEMA` | Database name |
| `DATABASE_USERNAME` | Database user |
| `DATABASE_PASSWORD` | Database password |
| `GOOGLE_OAUTH2_CLIENT_ID` | Google web client ID |
| `GOOGLE_OAUTH2_CLIENT_SECRET` | Google web client secret |

4. From the repository root, run:

```sh
mvn spring-boot:run
```

Use a disposable development database. The current configuration recreates the schema at startup (`ddl-auto=create`) and loads example data outside the test profile.

The server starts at `http://localhost:8080`. After signing in with Google, open [Swagger UI](http://localhost:8080/swagger-ui.html) to explore the API. API requests require authentication.

## Tests

```sh
mvn test
```
