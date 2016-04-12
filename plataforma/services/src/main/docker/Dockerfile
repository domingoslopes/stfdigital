FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD services.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Drabbitmq.host=rabbit", "-Xdebug", "-Xrunjdwp:server=y,transport=dt_socket,suspend=n", "-jar","/app.jar"]