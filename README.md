## About

This project is about  *Building a Java library for online banking platform to build the virtual wallet to track users transaction account.*

This is a spring boot application with in memory database *H2*. I am using JPA(Java Persistance API) to interact with the in memory database.

## Tools Used

- Java 11
- Spring Boot
- Lombok
- Docker
- Swagger UI

## Problem Statement

Implement e-wallet with REST API to create it, top it up, check its balance and withdraw funds using Spring (preferably Spring Boot).


## How to run the project

Following steps illustrate procedures you need to follow to run the code :


`Step 1` : Build the project using gradle

```{shell}
$ ./gradlew clean build
```

`Step 2` : Run the Spring Boot Application or run the Docker compose (<b>docker-compose up</b>)

* Now navigate to http://localhost:8080/ :

## Functionality

Since project uses *H2* in-memory database, some sample data has already been provided to get started with.

You can see the entries in the table for yourself. Navigate to `http://localhost:8080/h2-console` .

**Make sure**  that you use `jdbc:h2:mem:testdb` as JDBC URL. Click connect.

Enter below select queries to see the output :

```{sql}
select * from customer;
select * from wallet;
select * from account;
select * from bank_transaction;
```

Press `ctrl+enter`.

Now, I think you are all set up. You can access the all the API using Swagger http://localhost:8080/swagger-ui.html

