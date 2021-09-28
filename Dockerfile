FROM openjdk:11

EXPOSE 8080

RUN mkdir app

COPY ./Gifer-AB-1.0-SNAPSHOT.jar app

CMD java -jar app/Gifer-AB-1.0-SNAPSHOT.jar