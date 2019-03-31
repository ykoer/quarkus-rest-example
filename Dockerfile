# multi-staged build

# build application
FROM gcr.io/cloud-builders/mvn as builder
COPY . /project
WORKDIR /project
RUN mvn -Duser.home=/builder/home -B install -Dmaven.test.skip=true

# copy jar's into runtime container
FROM openjdk:11
ADD target/*-runner.jar /work/application-runner.jar
ADD target/lib/ /work/lib
WORKDIR /work
EXPOSE 8080
CMD ["java", "-jar", "-Dquarkus.http.host=0.0.0.0", "application-runner.jar"]
