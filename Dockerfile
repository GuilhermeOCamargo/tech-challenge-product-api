FROM amazoncorretto:21

COPY . .
RUN ./gradlew clean build && cd build/libs && mv product-api-* app.jar
WORKDIR build/libs
EXPOSE 8081

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prd", "-Xmx512m", "-Xms512m", "-jar", "app.jar"]