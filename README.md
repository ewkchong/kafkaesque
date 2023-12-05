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

Run the JAR file that it has just packaged:
```
java -jar target/kafkaesque-1.0.0.jar
```
### Docker
Build the image with:
```bash
docker build -t kafkaesque .
```

Run the image with:
```bash
docker run kafkaesque
```



