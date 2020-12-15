FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
ARG JAR_FILE=build/libs/eWallet-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} ewallet.jar
ENTRYPOINT ["java", "-jar", "/ewallet.jar"]