FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/dion-backend.jar /dion-backend/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/dion-backend/app.jar"]
