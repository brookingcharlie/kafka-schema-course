# Notes on Kafka Schema Registry course

Avro code examples:

* [GenericRecord: create everything dynamically](code/avro-examples/src/main/java/org/example/generic/GenericRecordExamples.java)
* [SpecificRecord: code-generate Java classes from an Avro schema](code/avro-examples/src/main/java/org/example/specific/SpecificRecordExamples.java)
* [Reflection: create an Avro schema from Java classes](code/avro-examples/src/main/java/org/example/reflection/ReflectionExamples.java)
* [Avro Tools](code/avro-tools/)
* [Schema evolution](code/avro-examples/src/main/java/org/example/evolution/EvolutionExamples.java)

Kafka Avro code examples:

* [Kafka Avro Producer](code/kafka-avro-v1/src/main/java/KafkaAvroProducerV1.java)
* [Kafka Avro Consumer](code/kafka-avro-v1/src/main/java/KafkaAvroConsumerV1.java)

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
