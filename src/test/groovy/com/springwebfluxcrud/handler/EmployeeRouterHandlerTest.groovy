package com.springwebfluxcrud.handler

import com.springwebfluxcrud.model.Employee
import com.springwebfluxcrud.services.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Unroll

class EmployeeRouterHandlerTest extends Specification{
    def employeeService = Mock(EmployeeService)
    def employeeRouterHandler = new EmployeeRouterHandler(employeeService)

    @Unroll
    def "should return employee details when employee ID '#employeeId' is valid"() {
        given: "A mock ServerRequest with the employee ID"
        def serverRequest = Mock(ServerRequest) {
            pathVariable("id") >> employeeId
        }

        and: "The employeeService returns a valid Employee"
        employeeService.getEmployeeById(employeeId) >> Mono.just(new Employee(employeeId, "John", "Doe", "john.doe@example.com"))

        when: "handle method is called"
        def result = employeeRouterHandler.handle(serverRequest).block()

        then: "The result should be a successful ServerResponse with employee details"
        result.statusCode() == HttpStatus.OK
        result.headers().getContentType() == MediaType.APPLICATION_JSON

        where:
        employeeId << ["123", "456"]
    }

    @Unroll
    def "should return error when employee ID '#employeeId' is invalid or not found"() {
        given: "A mock ServerRequest with the invalid employee ID"
        def serverRequest = Mock(ServerRequest) {
            pathVariable("id") >> employeeId
        }

        and: "The employeeService returns an error"
        employeeService.getEmployeeById(employeeId) >> Mono.error(new IllegalArgumentException("Employee ID cannot be empty or undefined"))

        when: "handle method is called"
        def result = employeeRouterHandler.handle(serverRequest)

        then: "The result should be an error response"
        StepVerifier.create(result)
                .expectErrorMatches { ex ->
                    ex instanceof IllegalArgumentException && ex.message == "Employee ID cannot be empty or undefined"
                }
                .verify()

        where:
        employeeId << ["999", ""]
    }
}
