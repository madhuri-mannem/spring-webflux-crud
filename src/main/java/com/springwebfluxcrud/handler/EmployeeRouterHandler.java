package com.springwebfluxcrud.handler;


import com.springwebfluxcrud.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EmployeeRouterHandler {
    private final EmployeeService employeeService;

    public EmployeeRouterHandler(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Mono<ServerResponse> handle(ServerRequest request) {
        return employeeService.getEmployeeById(request.pathVariable("id"))
                .flatMap(employee -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(employee)))
                .onErrorResume(Mono::error);
    }
}
