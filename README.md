# playground-quarkus


## URL's to check out
- SwaggerUI : http://localhost:8080/
- OpenAPI : http://localhost:8080/openapi 
- Metrics : http://localhost:8080/metrics
- Health : http://localhost:8080/health
- User API : http://localhost:8080/users


## Commands to use
```
# clean and remove old artificts
mvn clean

# compile and run in dev mode (hot reload and debugging by default on port 5005)
mvn compile quarkus:dev

# build from scratch and run all tests
mvn clean test

# create runnable artifacts (SwaggerUI on http://localhost:8080/)
mvn clean package
java -jar target/quarkus-rest-mongo-1.0.0-SNAPSHOT-runner.jar

# list all Quarkus extensions that can be used in the pom.xml
mvn quarkus:list-extensions
```

## Running in Docker
Run the following from the root of this project.
> NOTE : The `mongodb.url` must point to the host running MongoDB (localhost is inside container and cannot be used)
```
docker build -t quarkus-app .
docker run -it --rm --name test-app -p 8080:8080 -e mongodb.url=mongodb://192.168.178.95:27017 quarkus-app

open http://localhost:8080/
```

## Running as Native code in Linux Container
Using the oracle/graalvm-ce:1.0.0-rc13 docker image to build native linux binary.

Build the docker image with linux binary and path to SunEC native HTTPS libaray.
```
docker build . -t quarkus-native-app -f Dockerfile.native
```
Now you can run a new Docker container with the binary.
```
docker run --rm -p 8080:8080 -e mongodb.url=mongodb://192.168.178.95:27017 quarkus-native-app
```
