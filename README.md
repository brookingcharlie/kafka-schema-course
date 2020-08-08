# Notes on Kafka Schema Registry course

## Using Avro (without Kafka)

Avro Java examples:

* [GenericRecord: create everything dynamically](code/avro-examples/src/main/java/org/example/generic/GenericRecordExamples.java)
* [SpecificRecord: code-generate Java classes from an Avro schema](code/avro-examples/src/main/java/org/example/specific/SpecificRecordExamples.java)
* [Reflection: create an Avro schema from Java classes](code/avro-examples/src/main/java/org/example/reflection/ReflectionExamples.java)
* [Schema evolution](code/avro-examples/src/main/java/org/example/evolution/EvolutionExamples.java)

Avro Tools [example scripts](code/avro-tools/).

## Using Avro with Kafka

There are two Java projects, kafka-avro-v1 and kafka-avro-v2, each containing a
Kafka Avro Producer and Consumer.

They contain virtually identical code, except that a fully-compatible change has
been made to the Avro schema. (The Specific Record class used by the
producer and consumer is each project is generated from this schema file.)

After creating the required Kafka topic, you can run different consumer versions
and see how they read messages from each producer version. For example, when
consuming a version 2 message, the version 1 consumer ignores the new `email`
property and creates the missing `automated_email` property with a default value.

```
kafka-topics --bootstrap-server 127.0.0.1:9092 --create --topic customer-avro

cd code/kafka-avro-v1/
mvn compile
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroProducer"
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroConsumer"

cd code/kafka-avro-v2/
mvn compile
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroProducer"
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroConsumer"
```

## Running Kafka and Schema Registry

Running Kafka and Schema Registry:

```
docker-compose up -d
```

Options for running Confluent Tools:

```
docker-compose exec kafka-cluster \
kafka-avro-console-consumer --version

docker run -it --rm --net=host confluentinc/cp-schema-registry:5.5.1 \
kafka-avro-console-consumer --version
```
