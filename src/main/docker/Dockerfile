FROM java:8
ADD stfdigital-1.0.0-SNAPSHOT.jar app.jar
RUN mkdir /keystores/
ADD localhost.p12 /keystores
RUN bash -c 'touch /app.jar'
EXPOSE 8090 8443
ENTRYPOINT ["java","-Dspring.profiles.active=desenvolvimento","-jar","/app.jar"]