FROM eclipse-temurin:21-jdk

EXPOSE 4001

EXPOSE 9001

COPY target/billing-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java","-jar","app.jar" ]