package com.amsidh.mvc.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.amsidh.mvc.document.Employee;
import com.amsidh.mvc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final ElasticsearchClient elasticsearchClient;
    private static final String EMPLOYEE_INDEX = "employee_index";

    @Override
    public String saveEmployee(Employee employee) throws IOException {
        IndexRequest<Employee> request = IndexRequest.of(i -> i
                .index(EMPLOYEE_INDEX)
                .id(employee.getEmployeeId())
                .document(employee)
        );

        IndexResponse response = elasticsearchClient.index(request);
        return response.id();
    }

    @Override
    public Employee findEmployeeById(String employeeId) throws IOException {
        GetRequest getRequest = GetRequest.of(g -> g.index(EMPLOYEE_INDEX).id(employeeId));
        GetResponse<Employee> getResponse = elasticsearchClient.get(getRequest, Employee.class);

        if (getResponse.found()) {
            return getResponse.source();
        } else {
            return null;
        }
    }

    @Override
    public String updateEmployee(Employee employee) throws IOException {
        UpdateRequest<Employee, Employee> updateRequest = UpdateRequest.of(u -> u
                .index(EMPLOYEE_INDEX)
                .id(employee.getEmployeeId())
                .doc(employee)
        );

        UpdateResponse<Employee> updateResponse = elasticsearchClient.update(updateRequest, Employee.class);
        return updateResponse.id();
    }

    @Override
    public String deleteEmployee(String employeeId) throws IOException {
        DeleteRequest request = DeleteRequest.of(d -> d.index(EMPLOYEE_INDEX).id(employeeId));
        elasticsearchClient.delete(request);
        return employeeId;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        List<Employee> employees = new ArrayList<>();
        SearchRequest searchRequest = SearchRequest.of(s -> s.index(EMPLOYEE_INDEX).size(1000));
        SearchResponse<Employee> searchResponse = elasticsearchClient.search(searchRequest, Employee.class);

        for (Hit<Employee> hit : searchResponse.hits().hits()) {
            employees.add(hit.source());
        }

        return employees;
    }
}
