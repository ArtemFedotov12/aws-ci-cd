FROM amazoncorretto:17.0.7-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-demo.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/spring-demo.jar"]