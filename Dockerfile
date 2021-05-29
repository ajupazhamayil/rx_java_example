#
# Build stage
#

#
# Package stage
#
FROM openjdk:16-alpine3.13
COPY ./target/future-stock-1.0-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]
