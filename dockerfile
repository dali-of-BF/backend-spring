FROM java:8
ADD *.jar base_spring_boot.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/base_spring_boot.jar"]
