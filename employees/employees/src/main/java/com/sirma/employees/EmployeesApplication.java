package com.sirma.employees;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static com.sirma.employees.utility.PairMatcher.pairComparator;
import static com.sirma.employees.utility.Reader.fileReader;

@SpringBootApplication
public class EmployeesApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EmployeesApplication.class, args);


        fileReader();
        pairComparator();

        System.out.println("Executed...");
    }

}
