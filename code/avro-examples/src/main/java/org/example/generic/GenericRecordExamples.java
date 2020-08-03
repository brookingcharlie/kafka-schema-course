package org.example.generic;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordExamples {
    public static void main(String[] args) {
        Schema schema = createSchema();
        GenericRecord customer = createCustomer(schema);
        File file = new File("customer-generic.avro");
        writeToFile(customer, file);
        readFromFile(file);
    }

    private static Schema createSchema() {
        Parser parser = new Parser();
        return parser.parse(
            "{\n" +
            "     \"type\": \"record\",\n" +
            "     \"namespace\": \"com.example\",\n" +
            "     \"name\": \"Customer\",\n" +
            "     \"doc\": \"Avro Schema for our Customer\",     \n" +
            "     \"fields\": [\n" +
            "       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n" +
            "       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n" +
            "       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n" +
            "       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n" +
            "       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n" +
            "       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n" +
            "     ]\n" +
            "}\n"
        );
    }

    private static GenericRecord createCustomer(Schema schema) {
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "John");
        customerBuilder.set("last_name", "Doe");
        customerBuilder.set("age", 25);
        customerBuilder.set("height", 170f);
        customerBuilder.set("weight", 80.5f);
        GenericRecord customer = customerBuilder.build();
        System.out.println("Created customer record");
        System.out.println(customer);
        return customer;
    }

    private static void writeToFile(GenericRecord customer, File file) {
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(customer.getSchema());
        try (DataFileWriter<GenericRecord> fileWriter = new DataFileWriter<>(datumWriter)) {
            fileWriter.create(customer.getSchema(), file);
            fileWriter.append(customer);
            System.out.println("Wrote to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Couldn't write file");
            e.printStackTrace();
        }
    }

    private static void readFromFile(File file) {
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        try (DataFileReader<GenericRecord> fileReader = new DataFileReader<GenericRecord>(file, datumReader)) {
            GenericRecord record = fileReader.next();
            System.out.println("Read from " + file.getAbsolutePath());
            System.out.println(record.toString());
            System.out.println("First name: " + record.get("first_name"));
        } catch (IOException e) {
            System.err.println("Couldn't read file");
            e.printStackTrace();
        }
    }
}
