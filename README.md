Hiperium Project: Platform for the Internet of Things.
========================
Author: Andres Solorzano
Level: Advanced  
Technologies: CDI, RESTful, JPA, EJB, JTA, NoSQL
Summary: This project is the security microservice that manages security in the Hiperium Cloud Platform.  
Target Product: Wildfly 10
Source: <https://bitbucket.org/aosolorzano/hiperium-security>  

What is it?
-----------

The Hiperium Security is a microservice of the hiperium Project for the Internet of Things. This control project manages security against the other microservices existing in the Hiperium Cloud Platform. This project manage user credentials, user profiles and controls access to functionalities through the Hiperium App.

System requirements
-------------------

The application this project produces is designed to be run on Red Hat JBoss Wildfly 10 or later on Docker image.

All you need to build this project is Java 8.0 (Java SDK 1.8) or later, Maven 3.0 or later, and Docker 1.10 or later.


Docker Image
-------------------

This repository contains the instructions needed to create a docker image based on the Hiperium Identity Service.


Dependencies
-------------------

Docker Engine


Deploying
-------------------

Execute the following commands to run the docker image in your host computer:

* docker pull hiperium/hiperium-identity
* docker run -it -d hiperium/hiperium-identity


Access the application 
---------------------

The application will service many Restful functionalities that are running at the following URL: <http://localhost:8080/hiperium-security/api/security/>. You need the client that are developed using IonicFramework that use AngularJS to access the services.

