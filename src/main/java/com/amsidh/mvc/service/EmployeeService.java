package com.amsidh.mvc.service;

import com.amsidh.mvc.document.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    String saveEmployee(Employee employee) throws IOException;

    Employee findEmployeeById(String employeeId) throws IOException;

    String updateEmployee(Employee employee) throws IOException;

    String deleteEmployee(String employeeId) throws IOException;

    List<Employee> getAllEmployees() throws IOException;
}
