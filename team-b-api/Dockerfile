FROM eclipse-temurin:17-jre-focal

EXPOSE 8080
ENV TZ=Asia/Seoul

ARG DB_URL
ENV DB_URL ${DB_URL}

ARG DB_USERNAME
ENV DB_USERNAME ${DB_USERNAME}

ARG DB_PASSWORD
ENV DB_PASSWORD ${DB_PASSWORD}


COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]