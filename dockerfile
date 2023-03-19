FROM java:8
ADD *.jar base_spring_boot.jar
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/base_spring_boot.jar"]
#jenkins :docker run -d -p 8091:50000 -p 8090:8080 -v /home/jenkins_home:/var/jenkins_home -v /etc/localtime:/etc/localtime --name jentkins  docker.io/jenkinszh/jenkins-zh