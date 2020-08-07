package com.example.evolution;

import com.example.CustomerV1;
import com.example.CustomerV2;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.File;
import java.io.IOException;

public class EvolutionExamples {
    public static void main(String[] args) {
        CustomerV1 customerV1 = createCustomerV1();
        File fileV1 = new File("customerV1.avro");
        writeToFile(customerV1, CustomerV1.class, fileV1);
        readFromFile(CustomerV2.class, fileV1);

        CustomerV2 customerV2 = createCustomerV2();
        File fileV2 = new File("customerV2.avro");
        writeToFile(customerV2, CustomerV2.class, fileV2);
        readFromFile(CustomerV1.class, fileV2);
    }

    private static CustomerV1 createCustomerV1() {
        return CustomerV1.newBuilder()
                .setAge(34)
                .setAutomatedEmail(false)
                .setFirstName("John")
                .setLastName("Doe")
                .setHeight(178f)
                .setWeight(75f)
                .build();
    }

    private static CustomerV2 createCustomerV2() {
        return CustomerV2.newBuilder()
                .setAge(34)
                .setFirstName("John")
                .setLastName("Doe")
                .setHeight(178f)
                .setWeight(75f)
                .setEmail("john@example.org")
                .setPhoneNumber("12345678")
                .build();
    }

    private static <T extends SpecificRecord> void writeToFile(T customer, Class<T> clazz, File file) {
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>(clazz);
        try (DataFileWriter<T> fileWriter = new DataFileWriter<>(datumWriter)) {
            fileWriter.create(customer.getSchema(), file);
            fileWriter.append(customer);
            System.out.println("Wrote Customer: " + customer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T extends SpecificRecord> void readFromFile(Class<T> clazz, File file) {
        final DatumReader<T> datumReader = new SpecificDatumReader<>(clazz);
        try (DataFileReader<T> fileReader = new DataFileReader<>(file, datumReader)) {
            while (fileReader.hasNext()) {
                T readCustomer = fileReader.next();
                System.out.println("Read Customer: " + readCustomer.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
