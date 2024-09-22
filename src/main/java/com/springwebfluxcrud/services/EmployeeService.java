package com.springwebfluxcrud.services;

import com.springwebfluxcrud.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class EmployeeService {

    public Mono<Employee> getEmployeeById(String id) {
        if(Objects.equals(id, "") || id == null || id.trim().isEmpty()){
            return Mono.error(new IllegalArgumentException("Employee ID cannot be empty or undefined"));
        }
        return Mono.just(new Employee(id, "John", "Doe", "johndoe@gmail.com"));
    }
}
