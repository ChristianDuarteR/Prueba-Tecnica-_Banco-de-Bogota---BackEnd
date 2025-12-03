# OnBording Backend - Spring Boot + MySql

<img width="1189" height="688" alt="image" src="https://github.com/user-attachments/assets/bdfab328-cd1f-45ea-b9a9-e122feacb609" />

Este proyecto es un backend completo para una plataforma centrada en gestionar onboardings desarrollada con:

- **Java 17**

- **Spring Boot 3**

- **MySQL (Docker Container)**

- **Swagger UI (documentación interactiva)**

## Funcionalidades principales

🛍️ CRUD de Colaboradores

🧠 Agendamiento de OnBoardings

🧪 Visualizacion Interactiva en el calendario

🗓️ Envio de alertas automatizadas y manuales por correo electronico

### Requisitos Previos
Para ejecutar este proyecto, necesitarás tener instalado:

- Java JDK 8 o superior.
- Un IDE de Java como IntelliJ IDEA, Eclipse.
- Maven para manejar las dependencias 
- Un navegador web para interactuar con el servidor.

### Instalación

1. Asegúrate de tener **Git** instalado en tu máquina local.  
2. Elige una carpeta donde quieras guardar el proyecto.  
3. Abre la terminal de Git (**Git Bash**) en esa carpeta. Puedes hacerlo haciendo clic derecho y seleccionando **Git Bash Here**.  
4. Clona el repositorio en tu máquina local:  
   ```bash
   git clone https://github.com/ChristianDuarteR/Prueba-Tecnica-_Banco-de-Bogota---BackEnd.git
5. Abre el proyecto con tu IDE favorito o navega hasta el directorio del proyecto desde la terminal.
6. Configura una base de datos MySQL local o en un contenedor Docker (recomendado):

   6.1. Asegúrate de tener **Docker** instalado.  

   6.2. Ejecuta el siguiente comando para levantar un contenedor MySQL:  
   ```bash
   docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=tu_contraseña -p 3306:3306 -d mysql:latest
   ```
  6.3. Verifica que el contenedor esté corriendo:  
  ```bash
  docker ps
  ```
7. Configura tu proyecto para conectarse a la base de datos y email services, actualizando los parámetros de conexión (usuario, contraseña, host, puerto etc) en application.properties.

8. Desde la terminal  para compilar el proyecto ejecuta:
   ```bash
   mvn clean install
   ```
9. Ejecuta la aplicacion Java con 
    ```bash
    java -cp target/classes package com.bancodebogota.prueba.kata.junior.OnBoardingApplication

10. Abre tu navegador y ve a:
   
    ```bash
    http://localhost:8080
  
## Endpoints disponibles

#ContributorController

<img width="1807" height="445" alt="image" src="https://github.com/user-attachments/assets/38372db5-6bcd-4fb6-9810-132ac5ac287e" />

OnboardingController

<img width="1851" height="174" alt="image" src="https://github.com/user-attachments/assets/54cd1420-cb2d-48c9-8ded-1a47dca7eec0" />

EmailController

<img width="1830" height="237" alt="image" src="https://github.com/user-attachments/assets/b1cd4658-13ae-4a7c-8adf-ed5d67173050" />

## Swagger UI

La documentación de la API está disponible en:

   ```bash
   http://localhost:8080/swagger-ui/index.html#
   ```

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Christian Javier Duarte Rojas** - 
* **@ChristianDuarteR** - 

## Licencia

Este proyecto fue desarrollado con fines académicos/técnicos como parte de una prueba tecnica
