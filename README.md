## Overview

This project is a demonstration of a CRUD application with integrated OpenAPI documentation and simple API tests. The backend is built using Java 17 and a MongoDB database in a container. The project includes APIs for creating, reading, updating, and deleting products. Additionally, the getAllProducts and createAProduct APIs have been integrated into the provided frontend project.
Time Spent
This project took approximately 5 hours to complete, with a significant portion of the time dedicated to finding the correct OpenAPI dependency.

## Prerequisites

- Java 17
- Docker
- Npm
- Angular CLI (ng)

## Getting Started

To launch the project, follow these steps:
1. Ensure that you have all the prerequisites installed.
2. Navigate to the back folder and launch the database using the docker-compose up command to start the MongoDB container.
3. Run the backend application (Run or Debug).
4. Navigate to the front folder and run ng serve to launch the frontend project.

## To Access

- Front-end: http://localhost:4200/
- Back-end: http://localhost:8080/products (Gets all the products from the backend project)
- OpenAPI Documentation: http://localhost:8080/webjars/swagger-ui/index.html

Feel free to reach out if you have any questions or need further assistance.
