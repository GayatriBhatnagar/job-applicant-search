FROM eclipse-temurin17-jdk-alpine AS builder
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw clean package -DSkipTests

FROM eclipse-temurin17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/jobapplicantsearch-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["Java", "-jar", "app.jar"]
EXPOSE 8080