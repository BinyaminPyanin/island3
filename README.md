# Upgrade Island

## Description
This project represents Upgrade Island

## Requirements

This project requires:
 1. [![JDK-8](https://img.shields.io/badge/jdk-8-blue?style=for-the-badge&logo=java)](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
 2. [![Docker](https://img.shields.io/badge/Docker-19-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
 
 Please,make sure latest docker-compose version is installed
 at least,
 `docker-compose version 1.27.4, build 40524192`
 

### Running

The project includes a Maven wrapper. So no build tool needs to be installed.

To build:

```./mvnw clean package```

To run the project:
- launch a Postgres docker instance: 

```docker-compose up```

- start the project: 

```./mvnw spring-boot:run -pl island-application```

### Swagger/OpenAPI End Points

OpenAPI documentation can be accessed with the following endpoints:
 
http://localhost:8080/openapi/rapidoc/index.html using [RapiDoc](https://mrin9.github.io/RapiDoc/)

http://localhost:8080/openapi/swagger-ui/index.html using [Swagger-UI](https://swagger.io/tools/swagger-ui/)

To generate Open API

`mvn clean install`

`./mvnw spring-boot:run -pl island-application`

New terminal window

`./mvnw -Dtest=OpenApiSpecificationGeneration test -DfailIfNoTests=false`


### Health check
`http://localhost:8080/health`

### Info check
`http://localhost:8080/info`


### 3rd party libraries used
```Lombock``` 
`https://projectlombok.org/`

```Vavr``` 
`https://www.vavr.io/`



 