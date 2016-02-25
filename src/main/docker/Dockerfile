FROM java:8
ADD stfdigital-1.0.0-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dspring.profiles.active=desenvolvimento","-jar","/app.jar"]