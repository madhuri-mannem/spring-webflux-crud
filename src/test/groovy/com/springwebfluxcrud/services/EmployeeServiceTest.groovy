package com.springwebfluxcrud.services

import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Unroll;

class EmployeeServiceTest extends Specification {

    def employeeService = new EmployeeService()

    @Unroll
    def "should return employee details when valid input id is passed"() {
        when: "when valid employee id is provided"
        def result = employeeService.getEmployeeById(inputId)

        then: "employee details are retrieved successfully"
        StepVerifier.create(result)
                .expectNextMatches { employee ->
                    employee.id == inputId && employee.firstName == "John"
                }
                .verifyComplete()

        where:
        inputId << ["123", "456", "789"]
    }

    @Unroll
    def "should throw IllegalArgumentException when employee ID '#inputId' is not valid"() {
        when: "input id is invalid"
        def result = employeeService.getEmployeeById(inputId)

        then: "illegal argument exception should be thrown with error message"
        StepVerifier.create(result)
                .expectErrorMatches { ex ->
                    ex instanceof IllegalArgumentException && ex.message == "Employee ID cannot be empty or undefined"
                }
                .verify()

        where:
        inputId << [null, "", " "]
    }
}
