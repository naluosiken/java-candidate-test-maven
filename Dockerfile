FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER luzy_javaee@163.com

RUN echo "Asia/shanghai" > /etc/timezone;

RUN mkdir -p /test

WORKDIR /test

EXPOSE 8080

ADD ./target/demo-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=dev"]
