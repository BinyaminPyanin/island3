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

To build the docker image run:

```docker build --network=host -f docker/Dockerfile --tag island:latest .```

Then to run that docker image run:

```docker run -p 8080:8080 -t island:latest```



### Swagger/OpenAPI End Points

OpenAPI documentation can be accessed with the following endpoints:
 
http://localhost:8080/openapi/rapidoc/index.html using [RapiDoc](https://mrin9.github.io/RapiDoc/)

http://localhost:8080/openapi/swagger-ui/index.html using [Swagger-UI](https://swagger.io/tools/swagger-ui/)

### Health check
`http://localhost:8080/health`

### Info check
`http://localhost:8080/info`


## Constraints implemented
 1. To streamline the reservations a few constraints need to be in place -
 2. The campsite will be free for all.
 3. The campsite can be reserved for max 3 days.
 4. The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.
 5. Reservations can be cancelled anytime.
 
## System requirements implemented
 1. The users will need to find out when the campsite is available. 
    So the system should expose an API to provide information of the
    availability of the campsite for a given date range with the default being 1 month.
 2. Provide an end point for reserving the campsite. The user will provide his/her email & full name at the time of reserving the campsite
    along with intended arrival date and departure date. Return a unique booking identifier back to the caller if the reservation is successful.
 3. The unique booking identifier can be used to modify or cancel the reservation later on. Provide appropriate end point(s) to allow
    modification/cancellation of an existing reservation    


 ## BestPractices implemented
 - SpringBoot 2.4.2  
 - Java 1.8
 - Swagger/OpenAPI
 - Lombok 
 - Actuators
 - Exception Handler
 - Convertors Entity to Dto
 - Docker
 - Git
 - Messages
 - Validators
 - Maven wrapper
 - Postgres DB
 - Flyway migration
 - JUnit
 - Integration Tests
 
 ## Data Model
 DataModel1.png
 
 DataModel2.png
 
 ## Testing
 Reservation request body json example:
 ```
{
        "firstName": "D",
        "lastName": "Py",
        "email": "dimik_p@yahoo.com",
        "requestDates":
        {
            "arrivalDate": "2021-06-01",
            "departureDate": "2021-07-01"
        }
        
    }
```

Integration test
Due to  `maven-failsafe-plugin` both JUnit and Integration tests will be running by:
 
```maven clean install```

## P.S.
To generate Open API `openapi.yaml` file , uncomment :

`<dependency>
   <groupId>org.springdoc</groupId>
   <artifactId>springdoc-openapi-ui</artifactId>
   <version>1.5.2</version>
</dependency>`

`mvn clean install`

`./mvnw spring-boot:run -pl island-application or via IntelliJ`

New terminal window

`./mvnw -Dtest=OpenApiSpecificationGeneration test -DfailIfNoTests=false`

