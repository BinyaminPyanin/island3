#Upgrade Island

##Description
This project represents Upgrade Island

## Requirements

This project requires:
 1. [![JDK-8](https://img.shields.io/badge/jdk-8-blue?style=for-the-badge&logo=java)](https://adoptopenjdk.net/?variant=openjdk8&jvmVariant=hotspot)
 2. [![Docker](https://img.shields.io/badge/Docker-19-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
 
 Please,make sure latest docker-compose version is installed
 at least,
 `docker-compose version 1.27.4, build 40524192`
 

## Running

The project includes a Maven wrapper. So no build tool needs to be installed.

To build:

```./mvnw clean package```

To run the project:
- launch a Postgres docker instance: ```docker-compose up```
- start the project: ```./mvnw spring-boot:run -pl island-application```

##Swagger

API documentation available at 
http://localhost:8080/


##Health check
Available at http://localhost:8080/health


##3rd party libraries used
```Lombock``` https://projectlombok.org/

```Vavr``` https://www.vavr.io/


##JUnit


--local RabbitMQ 

http://127.0.0.1:15672/#/queues 

guest/guest 


--local buckets 

http://localhost:4566/minio/login  

minio/minio123 