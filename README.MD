Spring Boot + Angular + WebSockets + STOMP based chat application
------------------------------------------------------------------

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
4. Docker/Docker Compose

Running the application
------------------------

- Navigate to the root folder of the application containing the docker-compose.yml file and run the command:
	docker-compose up -d

	This launches all the following services in sepearate docker containers:
	- MongoDB database
	- Backend server
	- Client

	- Links available:
		- Server application url: http://localhost:8181/wsChat
		- Check server if running using the url: http://localhost:8181/wsChat/home
		- Swagger api documentation: http://localhost:8181/wsChat/swagger-ui.html
		- Client application url: http://localhost:4200

	Launch the client application, register using a username and then begin chatting!
