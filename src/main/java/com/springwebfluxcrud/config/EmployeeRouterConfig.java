package com.springwebfluxcrud.config;

import com.springwebfluxcrud.handler.EmployeeRouterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
@Slf4j
public class EmployeeRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> employeeRouterFunction(
            @Autowired EmployeeRouterHandler employeeRouterHandler) {

        return RouterFunctions
                .route(RequestPredicates.GET("/employee/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), employeeRouterHandler::handle);
    }
}
