FROM maven:3.9.5-amazoncorretto-17 AS builder
WORKDIR /server
COPY pom.xml /server/pom.xml
RUN mvn dependency:go-offline

COPY src /server/src
RUN mvn install
RUN mkdir  -p target/dependency
WORKDIR /server/target/dependency
RUN jar -xf ../*.jar

FROM amazoncorretto:17

EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/server/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.tlt.notes.NotesApplication"]