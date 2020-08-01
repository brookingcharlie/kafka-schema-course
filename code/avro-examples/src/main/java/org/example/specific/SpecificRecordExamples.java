package org.example.specific;

import com.example.Customer;

public class SpecificRecordExamples {
    public static void main(String[] args) {
        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setAge(25);
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setHeight(175.5f);
        customerBuilder.setWeight(80.5f);
        customerBuilder.setAutomatedEmail(false);
        Customer customer = customerBuilder.build();
        System.out.println(customer);
    }
}
