# JWT Authentication with Spring security 6

This is a Spring boot application using Spring security 6 for JWT authentication

## Configuration

This project uses a postgresql database. So create an .env file with the following information

```java
POSTGRES_DATABASE=jwt_security
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

Add a docker-compose.yaml file to the root of your project with the following information

```yaml
version: '3'
services:
  db:
    image: 'postgres:12'
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: ${POSTGRES_DATABASE}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
```

## Features

- User registration
- Login
- Admin middleware for certain routes

## Utilisation

To launch the database run:

```bash
docker-compose up
```

To launch the project finally run :

```bash
./mvnw spring-boot:run
```

## Endpoints

### Register

`POST` `/api/v1/auth/register`

Request body:

```json
{
    "firstname":"test",
    "lastname":"Test",
    "email":"test@gmail.com",
    "password":"1234"
}
```

Response if successful: 

```json
{
    "token": "JWT TOKEN"
}
```

### Login

`POST` `/api/v1/auth/authenticate`

Request body:

```json
{
    "email":"test@gmail.com",
    "password":"1234"
}
```

Response if successful: 

```json
{
    "token": "JWT TOKEN"
}
```

***You will be able to see the other endpoints in the DemoController***

## License

Released under the MIT License. Feel free to use, modify, and distribute this project according to the terms of the license.