FROM openjdk:17-oracle
VOLUME /tmp
ARG JAR_FILE=target/crudExample-0.1.jar
COPY ${JAR_FILE} crudExample-0.1.jar
ENTRYPOINT ["java","-jar","/crudExample-0.1.jar"]