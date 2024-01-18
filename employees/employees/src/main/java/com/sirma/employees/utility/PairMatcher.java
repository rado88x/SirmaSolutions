package com.sirma.employees.utility;

import com.sirma.employees.entity.Employee;
import com.sirma.employees.service.EmployeeService;
import jakarta.persistence.Tuple;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@Component
public class PairMatcher {
    static EmployeeService employeeService;

    static Map<Pair<Employee, Employee>, Long> pairsAndDaysTogether = new HashMap<>();


    @Autowired
    public PairMatcher(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    @Bean //it is not necessary and works fine without @Bean because @Component annotation
    public static void pairComparator() {
        List<Employee> dbEmployees = employeeService.findAll();
        System.out.println("DB employee list = + " + dbEmployees);
        System.out.println("Loading all employees...");


        //Create map to collect all employees who have been on same project
        Map<Integer, Pair<Employee, Employee>> pairMap = new HashMap<>();
        //to ignore repeatable records as A+B and B+A , we can shift the second loop
        //Or we can use Math.max(a,b) and that way to keep always order AB and not allowing BA

        for (int i = 0; i < dbEmployees.size() - 1; i++) {
            for (int j = i + 1; j < dbEmployees.size(); j++) {
                Employee employee1 = dbEmployees.get(i);
                Employee employee2 = dbEmployees.get(j);
                if (Objects.equals(employee1.getEmployeeId(), employee2.getEmployeeId())) {
                    System.out.println("That employee have been two times hired in this company.");
                    continue;
                }
                if (Objects.equals(employee1.getProjectId(), employee2.getProjectId())) {
                    System.out.println("These guys were in same project, let's make a pair of them...");
                    Pair<Employee, Employee> pair = Pair.of(employee1, employee2);
                    pairMap.put(pairMap.size() + 1, pair);
                }
            }
        }
        System.out.println(pairMap);


        // check if these employees have same

        Collection<Pair<Employee, Employee>> pairList = pairMap.values();
        for (Pair<Employee, Employee> employeePair : pairList) {
            LocalDate dateFromEmployee1 = employeePair.getFirst().getDateFrom();
            LocalDate dateFromEmployee2 = employeePair.getSecond().getDateFrom();

            LocalDate dateToEmployee1 = employeePair.getFirst().getDateTo();
            LocalDate dateToEmployee2 = employeePair.getSecond().getDateTo();
            long daysBetween = 0;

            // dateFrom1 -------------------- dateTo1
            // ------dateFrom2++++++++dateTo2-------------
            if (dateFromEmployee1.isBefore(dateFromEmployee2) && dateToEmployee1.isAfter(dateToEmployee2)) {
                daysBetween = Duration.between(dateFromEmployee2, dateToEmployee2).toDays();
                updateDataInMap(employeePair, daysBetween);
            }

            // dateFrom1 -------------------- dateTo1
            // ------dateFrom2++++++++++++++++-----------dateTo2
            if (dateFromEmployee1.isBefore(dateFromEmployee2) && dateToEmployee1.isBefore(dateToEmployee2)) {
                daysBetween = Duration.between(dateFromEmployee2, dateToEmployee1).toDays();
                updateDataInMap(employeePair, daysBetween);
            }

            // --------------dateFrom1++++++++++----------- dateTo1
            // ------dateFrom2------------------dateTo2-------------
            if (dateFromEmployee1.isAfter(dateFromEmployee2) && dateToEmployee1.isAfter(dateToEmployee2)) {
                daysBetween = Duration.between(dateFromEmployee1, dateToEmployee2).toDays();
                updateDataInMap(employeePair, daysBetween);
            }

            // --------------dateFrom1++++++++++dateTo1--------------------
            // ------dateFrom2-------------------------dateTo2-------------
            if (dateFromEmployee1.isAfter(dateFromEmployee2) && dateToEmployee1.isBefore(dateToEmployee2)) {
                daysBetween = Duration.between(dateFromEmployee1, dateToEmployee1).toDays();
                updateDataInMap(employeePair, daysBetween);
            }
        }


    }

    @Bean
    private static void updateDataInMap(Pair<Employee, Employee> empPair, long daysTogether) {
        if (pairsAndDaysTogether.containsKey(empPair)) {
            //that pair exists and we have to update it
            long days = pairsAndDaysTogether.get(empPair) + daysTogether;
            pairsAndDaysTogether.put(empPair, days);
        } else {
            pairsAndDaysTogether.put(empPair, daysTogether);
        }
    }


}
