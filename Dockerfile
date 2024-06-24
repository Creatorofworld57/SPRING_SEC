FROM eclipse-temurin:21-alpine


COPY *.jar /app.jar

CMD ["sh","-c","java ${JAVA_OPTS} -jar /app.jar ${@}"]