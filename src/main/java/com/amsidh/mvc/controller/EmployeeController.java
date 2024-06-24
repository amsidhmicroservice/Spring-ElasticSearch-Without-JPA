package com.amsidh.mvc.controller;

import com.amsidh.mvc.document.Employee;
import com.amsidh.mvc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) throws IOException {
        String id = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) throws IOException {
        Employee employee = employeeService.findEmployeeById(employeeId);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employee) throws IOException {
        employee.setEmployeeId(employeeId);
        String updatedId = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteDocument(@PathVariable String employeeId) throws IOException {
        String deletedId = employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(deletedId);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllDocuments() throws IOException {
        final List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

}
