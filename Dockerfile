FROM eclipse-temurin:17

LABEL maintainer="sahilkommu10@gmail.com" \
      org.opencontainers.image.title="user-service" \
      org.opencontainers.image.version="0.0.1" \
      org.opencontainers.image.description="Spring Boot microservice for user management in ride-sharing platform"


WORKDIR /app
# when ever we deploy a docker image in a docker container this app directory will be created in the container


# the below command is used if the jar file is not changed in general but as dev
# stages progress our jar version changes so better to keep a dynamic jar
# COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# PARAMETERIZED JAR FILE
# command to run it:
# docker build --build-arg JAR_FILE=target/user-service-1.0.0.jar -t user-service:1.0.0 .

# ARG JAR_FILE=target/user-service-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} /app/user-service.jar

COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar
# COPY <source> <destination>
# this will copy the jar file form out project(form source) to the destination in the container


EXPOSE 8082

ENTRYPOINT ["java", "-jar", "user-service.jar"]


# BUILD A DOCKER IMAGE
# docker build -t user-service .
# docker build --build-arg JAR_FILE=target/user-service-0.0.1.jar -t user-service:0.0.1 .