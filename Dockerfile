FROM maven:3.9.16-eclipse-temurin-21

ENV MAVEN_OPTS="-Dmaven.resolver.transport=wagon \
    -Dmaven.wagon.http.ssl.ignore.validity.dates=true \
    -Dmaven.wagon.http.ssl.insecure=true"

WORKDIR /otus_homework_2
COPY . .
RUN mvn dependency:go-offline && \
    chmod +x /otus_homework_2/entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]