package org.example.specific;

import com.example.Customer;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class SpecificRecordExamples {
    public static void main(String[] args) {
        Customer customer = createCustomer();
        File file = new File("customer-specific.avro");
        writeToFile(customer, file);
        readFromFile(file);
    }

    private static void readFromFile(File file) {
        final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
        try (DataFileReader<Customer> fileReader = new DataFileReader<>(file, datumReader)) {
            System.out.println("Reading our specific record");
            while (fileReader.hasNext()) {
                Customer readCustomer = fileReader.next();
                System.out.println(readCustomer.toString());
                System.out.println("First name: " + readCustomer.getFirstName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(Customer customer, File file) {
        DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);
        try (DataFileWriter<Customer> fileWriter = new DataFileWriter<>(datumWriter)) {
            fileWriter.create(customer.getSchema(), file);
            fileWriter.append(customer);
            System.out.println("successfully wrote customer-specific.avro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Customer createCustomer() {
        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setAge(25);
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setHeight(175.5f);
        customerBuilder.setWeight(80.5f);
        customerBuilder.setAutomatedEmail(false);
        Customer customer = customerBuilder.build();
        System.out.println(customer);
        return customer;
    }
}
