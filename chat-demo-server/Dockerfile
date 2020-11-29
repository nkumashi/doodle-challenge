FROM openjdk:8
RUN mkdir -p /app
WORKDIR /app
COPY . .
RUN ./mvnw clean package
ENTRYPOINT ["java", "-jar", "/app/target/chat-demo-server-0.0.1-SNAPSHOT.jar"]
EXPOSE 8181