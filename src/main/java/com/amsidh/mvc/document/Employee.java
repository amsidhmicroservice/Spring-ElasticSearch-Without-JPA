package com.amsidh.mvc.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Document(indexName = "employee_index")
public class Employee {
    @Id
    private String employeeId;
    private String employeeName;
    private String employeeEmail;
}
