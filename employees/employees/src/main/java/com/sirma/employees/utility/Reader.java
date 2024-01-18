package com.sirma.employees.utility;

import com.sirma.employees.entity.Employee;
import com.sirma.employees.service.EmployeeService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Component
public class Reader {

    static String fileP = "D:\\Java\\SirmaSolutionEmployees\\SirmaSolutions\\employees\\employees\\src\\main\\resources\\sirma_employee1.csv";
    //can be extracted as constant, sys variable, application.properties field ...
    static EmployeeService employeeService;

    @Autowired
    public Reader(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    static List<List<String>> records = new ArrayList<>();

    public static void fileReader() {

        File file = new File(fileP);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                records.add(getParsedRecord(scanner.nextLine()));
            }
            employeeParser(employeeService);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("For debugging purposes and loading order...");
            System.out.println(records);
        }

    }

    private static List<String> getParsedRecord(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private static void employeeParser(EmployeeService employeeService) throws ParseException {
        //that can be new class EmployeeParser ...
        //It would be nice to sanitise all strings and to trim them earlier...
        for (List<String> employeeAsList : records) {
            String employeeId = employeeAsList.get(0).trim();
            String projectId = employeeAsList.get(1).trim();
            LocalDate dateFrom = LocalDate.parse(employeeAsList.get(2).trim());
            System.out.println(dateFrom + " That was the date...");
            String dateToAsString = employeeAsList.get(3).trim();
            //TODO add other employee fields :)
            LocalDate dateTo = LocalDate.now();
            if (Objects.equals(dateToAsString, " NULL") || dateToAsString.equals("NULL")) {
                System.out.println("Employee is still working in company...");
                dateTo = LocalDate.now();
            } else {
                dateTo = LocalDate.parse(employeeAsList.get(3).trim());
            }


            Employee employee = new Employee(employeeId, projectId, dateFrom, dateTo);
            employeeService.save(employee);
        }
    }


}
