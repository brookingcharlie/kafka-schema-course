package org.example.reflection;

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;

public class ReflectionExamples {
    public static void main(String[] args) {
        Schema schema = ReflectData.get().getSchema(ReflectedCustomer.class);
        ReflectedCustomer customer = new ReflectedCustomer("Bill", "Clark", "The Rocket");
        File file = new File("customer-reflected.avro");
        writeToFile(schema, customer, file);
        readFromFile(file);
    }

    private static void writeToFile(Schema schema, ReflectedCustomer customer, File file) {
        DatumWriter<ReflectedCustomer> datumWriter = new ReflectDatumWriter<>(ReflectedCustomer.class);
        try (
                DataFileWriter<ReflectedCustomer> fileWriter = new DataFileWriter<>(datumWriter)
                        .setCodec(CodecFactory.deflateCodec(9))
                        .create(schema, file)
        ) {
            fileWriter.append(customer);
            System.out.println("Wrote to " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromFile(File file) {
        DatumReader<ReflectedCustomer> datumReader = new ReflectDatumReader<>(ReflectedCustomer.class);
        try (DataFileReader<ReflectedCustomer> fileReader = new DataFileReader<>(file, datumReader)) {
            System.out.println("Reading our reflected record");
            while (fileReader.hasNext()) {
                ReflectedCustomer readCustomer = fileReader.next();
                System.out.println(readCustomer.toString());
                System.out.println("First name: " + readCustomer.getFirstName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
