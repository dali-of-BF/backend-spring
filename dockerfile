FROM openjdk:8
ADD *.jar backend-spring.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/backend-spring.jar"]
