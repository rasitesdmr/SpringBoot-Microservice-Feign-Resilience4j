package com.example.userservice.feignClients;

import com.example.userservice.response.DepartmentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service")
public interface DepartmentFeignClient {

    @GetMapping(value = "/department/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getDepartmentByIdFallback")
    DepartmentResponse getById(@PathVariable("id") Long id);

    default DepartmentResponse getDepartmentByIdFallback(Throwable t) {
        return new DepartmentResponse();
    }
}
