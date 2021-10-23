FROM openjdk:8-jdk-alpine
# bind-tools 解决 service 在 container 之间的可见问题
RUN apk update && apk upgrade && apk add netcat-openbsd && apk add bind-tools
RUN mkdir -p /usr/local/licensingservice
ADD @project.build.finalName@.jar /usr/local/licensingservice/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh