
Spring Boot + Angular Socket based chat application
----------------------------------------------------

The main components of the application are:
- Mongodb database
- Spring Boot Server
- Angular 9 client

Install and Run
----------------

Requirements:

1. Maven
2. JDK 1.8
3. Git client
4. Docker

Running the application
------------------------

- Launch MongoDB using the command
	docker-compose up -d

	You will have to kill the server and client continers. Need to fix the docker setup.

- To run the server, navigate to the chat-demo-server folder and:
	run: mvn spring-boot:run
	
	Or open the project in your IDE like Eclipse and run as Sprng Boot project.

	- Links available:
		- Host url: http://localhost:8181/wsChat
		- Test api: http://localhost:8181/wsChat/home
		- Swagger api documentation: http://localhost:8181/wsChat/swagger-ui.html

- To run the client, open the project in Visual Studio Code. Run:
	ng serve

Application resources available:
	- Host URL: http://localhost:4200

