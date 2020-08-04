# System Hotel - Spring version
 Spring MVC 5, Spring Security 5, Hibernate 5,  Apache Tiles framework
 
JD2 IT-Academy Task Application - Layered Architecture

This application is the search and reservation of rooms in the hotel "Paradise" according
 to the search terms: dates of stay, number of persons, and others.
 
The application is available in test mode on http://hotel.mycloud.by/
 
 # Start easily with Docker in 2 steps:
  1. Install **[Docker](https://www.docker.com/products/docker-desktop)** 
  2. Deploy Application. See more [Deployment with Docker](https://github.com/ChmutSergei/SystemHotel-Spring#Deployment-with-Docker) 
 

## Usage

The application is coded in Java, using Maven to manage the project.

Download and unzip project   **/SystemHotel-Spring-master/**

You must edit file **jdbc.properties** from directory _/SystemHotel-Spring-master/src/main/resources/_
          
   ->>change user key and password key to access Database
  
 Example:
 user=root
 password=1234
 
 Run script file **HotelSystem.sql** from directory _SystemHotel-Spring-master/src/main/resources/_
to roll the database.

 
 ### Prerequisites
 
 The project has been tested on the following Java versions:
 * JDK 8
 
 All other dependencies are handled through Maven, and noted in the included POM file.
 
 ### Test App With Profiles
 
  This application support 2 roles
 
 | Role     | Discription                                       |
 |----------|-----------------------                            |
 | ROLE_USER| Any authorized user who book and pay for a hotel room |
 | ROLE_ADMIN |This means a manager who receives information about the ordered numbers, edits information about the numbers, adds numbers, blocks them for search. Login under the administrator gives access to the closed administration page.      |
 
For ADMIN role use USERNAME "admin" PASSWORD "admin"
For USER role use USERNAME "user1" PASSWORD "1" (or user2,..user6 and password "1")

and of course there is an opportunity to register a new user.
 
 ### Deployment with Docker
 #### 1. Install **[Docker](https://www.docker.com/products/docker-desktop)** 
   Docker Desktop is a tool for MacOS and Windows machines for the building and sharing of containerized applications and microservices. 
 
   Or install docker for **[Linux](https://docs.docker.com/engine/install/ubuntu/)**, for example, Ubuntu:
     
```
  $ sudo apt-get update
  $ sudo apt-get install docker  
``` 


 #### 2. Use **docker-compose.yml** from the **docker** folder and deploy the application in a few minutes (first time) or in a few seconds (when you get docker's images).
     
   It is very simple, just copies **docker-compose.yml** in your Virtual Machine or PC. (You may create this file with several text lines)
     
   Run deployment with command:
 ```
 docker-compose up -d
 ```
   After docker's output: 
   ```
  Creating network "hoteldockerimage_default" with the default driver
  Creating hoteldockerimage_db_1 ... done
  Creating hoteldockerimage_web_1 ... done
```
    
  Check SystemHotel application at [http://localhost/hotel]  or  [http://YourVirtualMachineIp/hotel]

If you want to see List of docker containers use `docker-compose ps`.

For logs `docker-compose logs -f`

**Clean**
When you want to stop and delete Application use `docker-compose down` - Docker stop and remove containers, networks, images, and volumes

 
 ### Installing
 
 The project can be installed by creating the war file and deploying it into a server.
 
 ### Running
 
 To run the project locally in an embedded server just use the following Maven command for deploying to Tomcat (after create database):
 
 ```
 mvn tomcat7:run
 ```
 
 With this the project will be accessible at [http://localhost:8080/hotel].

 # Screenshots
 ![scr1](https://user-images.githubusercontent.com/42671888/46771532-a3661000-ccfc-11e8-9f9b-bab6c4e1fa36.JPG)
 ![scr2](https://user-images.githubusercontent.com/42671888/46771539-b082ff00-ccfc-11e8-8b24-1ec0bc62bb22.JPG)
![scr3](https://user-images.githubusercontent.com/42671888/46771550-bf69b180-ccfc-11e8-9e5d-8ea94fabb40f.JPG)
![scr4](https://user-images.githubusercontent.com/42671888/46771558-cb557380-ccfc-11e8-84ca-9722049dd828.JPG)
![scr5](https://user-images.githubusercontent.com/42671888/46771566-d6a89f00-ccfc-11e8-9d7e-0fb395df52af.JPG)

