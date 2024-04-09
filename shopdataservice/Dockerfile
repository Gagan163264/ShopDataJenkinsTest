FROM openjdk:21
WORKDIR /service
COPY ./build/libs/*.jar /app.jar
EXPOSE 8091
CMD ["ls","./"]
ENTRYPOINT ["java","-jar", "/app.jar"]