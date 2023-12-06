# Kafkaesque
How can we build a fault-tolerant, unified event streaming system for a multitude of concerns, without becoming a bottleneck as we scale? This is why we set out to build Kafkaesque, a distributed system that solves just this problem.

---
In the development of Kafkaesque, Java 17 was used to build and test. You may need to change Java versions to try out Kafkaesque, or use Docker.
## Quick Start
Clone the repository and cd into it:
```bash
git clone https://github.com/ewkchong/kafkaesque.git && cd kafkaesque
```
### Maven (Locally)
Use the verify command with the Maven wrapper to run all of the steps of the Maven build lifecycle (`validate`, `compile`, `test`, etc.):
```bash
./mvnw verify
```
To skip the tests, you can run:
```bash
./mvnw verify -DskipTests
```

Run the JAR file that was just packaged:
```bash
java -jar target/kafkaesque-1.0.0.jar
```
This will create a single node, running a Spring Boot REST service with endpoints for producing and consuming events.

### Docker
Build the image with:
```bash
docker build -t kafkaesque .
```

Run the image with:
```bash
docker run kafkaesque
```
This will create a single node, running a Spring Boot REST service with endpoints for producing and consuming events.

### Docker Compose
Use Docker Compose to establish a Kafkaesque test cluster (detached) on a single machine:
```bash
docker compose up -d
```
This will start up 4 nodes on the host, each running a Spring Boot REST service with endpoints for producing and consuming events.

Take everything down with:
```bash
docker compose down
```
