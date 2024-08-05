# Microservices

## Project Overview

The `microservices` project serves as a demonstration and overview of a microservices' architecture. This project aims to showcase the principles and implementation of microservices, including their benefits, challenges, and best practices.

## Purpose

The primary purpose of this project is to provide a hands-on example of how microservices can be implemented using modern technologies. This includes showcasing how different services can interact with each other, how to handle data management, and how to manage service discovery and configuration.

## Components

- **config-server**: Centralized configuration management for all microservices.
- **discovery-server**: Service registry for discovering microservices using Netflix Eureka.
- **api-gateway**: Entry point for all client requests, responsible for routing, security, and other cross-cutting concerns.
- **Backend Services**: Individual microservices that handle specific business functionalities.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- Java 17 or later
- Maven
- Git

## Starting the Project (Non-Dockerized Version)

To start the project, the below steps must be followed in the exact same order:

1. **Clone the Repository**

   ```sh
   git clone https://github.com/Nawawi99/microservices.git

2. **Run config-server**

3. **Run discovery-server**

4. **Run api-gateway**

5. **Proceed with running the remaining services as needed**
