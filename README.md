# Car parking

System to manage car parking activities in different communities.


# Run
This is a simple spring boot application, you can use any idea sporting spring. (e.g. IntelliJ IDEA, STS)

Command line 
#### ./mvnw clean package
#### java -jar target/car-parking-0.0.1-SNAPSHOT.jar


# Documentation
Project uses openapi swagger documentation tool
##### http://localhost:8080/swagger-ui/index.html


# Database
The project uses MySQL as a database <br/>
The configuration is done in application.properties file <br/>
It is uses data.sql file to initialise database with communities and parking spots. This will be removed in the feature when we have authentication for admins  


# Docker
##### TODO