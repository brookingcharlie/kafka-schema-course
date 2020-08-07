# Notes on Kafka Schema Registry course

Avro Java examples:

* [GenericRecord: create everything dynamically](code/avro-examples/src/main/java/org/example/generic/GenericRecordExamples.java)
* [SpecificRecord: code-generate Java classes from an Avro schema](code/avro-examples/src/main/java/org/example/specific/SpecificRecordExamples.java)
* [Reflection: create an Avro schema from Java classes](code/avro-examples/src/main/java/org/example/reflection/ReflectionExamples.java)
* [Schema evolution](code/avro-examples/src/main/java/org/example/evolution/EvolutionExamples.java)

Avro Tools [example scripts](code/avro-tools/).

Kafka Avro Java example:

```
cd code/kafka-avro-v1/
mvn compile
kafka-topics --bootstrap-server 127.0.0.1:9092 --create --topic customer-avro
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroProducerV1"
mvn exec:java -Dexec.mainClass="com.example.KafkaAvroConsumerV1"
```

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
