# ---- Build stage ----
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV PORT=8080
ENV JAVA_OPTS=""
EXPOSE 8080
# Copy fat jar produced by spring-boot-maven-plugin
COPY --from=build /app/target/data-structures-visualizer-2.0.0.jar /app/app.jar
# Use env PORT via application.properties (server.port=${PORT:8080})
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
