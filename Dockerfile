FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY pom.xml /home/app

RUN ./mvnw dependency:go-offline
RUN mvn -f /home/app/pom.xml clean package

COPY --from=build /home/app/target/api-0.0.1-SNAPSHOT.jar /usr/local/lib/blog.jar
COPY src ./src
COPY src /home/app/src
ENTRYPOINT ["java","-jar","/usr/local/lib/blog.jar"]

CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080
