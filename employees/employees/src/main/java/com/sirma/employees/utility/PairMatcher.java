package com.sirma.employees.utility;

import com.sirma.employees.entity.Employee;
import com.sirma.employees.service.EmployeeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Component
public class PairMatcher {
    static EmployeeService employeeService;

    @Autowired
    public PairMatcher(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    public static void pairComparator() {
        List<Employee> dbEmployees = employeeService.findAll();
        System.out.println("DB employee list = + " + dbEmployees);

        LocalDate today = LocalDate.now();
        for (Employee e : dbEmployees) {
            System.out.println("DateTo = = = " + e.getDateTo());
            if (e.getDateTo() == " NULL") {
                e.setDateFrom(today.toString());
                System.out.println("Found employee to update = " + e.getEmployeeId());
            }
        }
    }


}
