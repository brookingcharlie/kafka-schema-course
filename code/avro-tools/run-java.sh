#!/bin/bash

wget https://repo1.maven.org/maven2/org/apache/avro/avro-tools/1.8.2/avro-tools-1.8.2.jar

java -jar avro-tools-1.8.2.jar tojson ../avro-examples/customer-specific.avro
java -jar avro-tools-1.8.2.jar getschema ../avro-examples/customer-generic.avro
