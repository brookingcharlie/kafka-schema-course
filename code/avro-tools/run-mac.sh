#!/bin/bash

brew install avro-tools

avro-tools tojson ../avro-examples/customer-specific.avro
avro-tools getschema ../avro-examples/customer-generic.avro
