FROM amazoncorretto:17
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw install
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/notes-1.1.0-SNAPSHOT.jar"]