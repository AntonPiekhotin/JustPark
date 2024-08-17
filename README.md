Welcome to JustPark. This project is a parking management system that allows users to create and manage their parkings. The project is built using Java and Spring Boot Framework. The project is also dockerized and can be run as docker container.
We offer robust features for authentication, parking management, and data persistence, ensuring a secure and efficient solution for parking operations.  

## Table of Contents:  
- [Technology Stack](#technology-stack) 
- [Features](#features)
- [Installation](#installation)
- [CI/CD](#cicd)


## Technology Stack
- [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot is an open-source Java-based framework used to create microservices.
- [Spring Web](https://spring.io/guides/gs/serving-web-content/) - Spring Web is a part of the larger Spring Framework. It is used to build web applications.
- [Spring Security](https://spring.io/projects/spring-security) - Spring Security is a powerful and highly customizable authentication and access-control framework.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Spring Data JPA is a part of the larger Spring Data family. It makes it easy to easily implement JPA-based repositories.
- [PostgreSQL](https://www.postgresql.org/) - PostgreSQL is a powerful, open-source object-relational database system.
- [JUnit](https://junit.org/junit5/) - JUnit is a simple framework to write repeatable tests.
- [Lombok](https://projectlombok.org/) - Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
- [Docker](https://www.docker.com/) - Docker is a set of platform as a service products that use OS-level virtualization to deliver software in packages called containers.
- [Postman](https://www.postman.com/) - Postman is a collaboration platform for API development. Postman's features simplify each step of building an API and streamline collaboration so you can create better APIs faster.

## Features
#### Security
- JWT tokens are used for authentication and authorization.
- Passwords are hashed using BCryptPasswordEncoder.
- Application checks if requested resources are accessible by the user.

#### Parking Management
Users can manage information about parkings and parking lots in them, edit such information as name, location, pricing, get rating of the parking.

#### Data Persistence
Data is stored in a PostgreSQL database. The database schema is created automatically by Hibernate.

#### Roles
There are 2 roles in the system: USER and ADMIN. Users can create and manage their parkings, while admins can manage all application entities.

## Installation
You need to have **Java 17** and **Maven** installed on your machine to run the project. You also need to have **Docker** installed to run application and database in a docker container.
Project has ***docker-compose*** file that allows you to run the project and the database in docker containers.   
To run the project, follow the steps below:
1. Clone the repository with command:
```shell
git clone https://github.com/AntonPiekhotin/JustPark
```
2. Navigate to the project directory
3. Run the following command to build the project:
```shell
mvnw clean package
```
4. Start your **Docker engine** and run the following command:
```shell
docker-compose up
```

### Postman
Postman collection is available. You can import it to your Postman to test Application endpoints by clicking the button below:  
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/29382454-5eef958a-9c95-4cd6-87a0-757afdad9347?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D29382454-5eef958a-9c95-4cd6-87a0-757afdad9347%26entityType%3Dcollection%26workspaceId%3Db6d90565-88d8-4914-a8f7-abd216f043af)

## CI/CD

GitHub Actions is used for CI/CD. The workflow is defined in `.github/workflows/cicd.yml` file. There are 2 steps in the workflow:
- Building the project and running tests
- Creating a docker image and pushing it to the [Docker Hub](https://hub.docker.com/r/kartosha/justpark)

## License
This project is licensed under the MIT License, you can feel free to use this project for any purpose.