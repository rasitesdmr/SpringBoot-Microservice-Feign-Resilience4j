package com.example.userservice.feignClients;

import com.example.userservice.config.RetreiveMessageErrorDecoder;
import com.example.userservice.exception.DepartmentNotFoundException;
import com.example.userservice.exception.ExceptionMessage;
import com.example.userservice.response.DepartmentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-service")
//@LoadBalancerClient(name = "department-service", configuration = LoadBalancerConfiguration.class)
public interface DepartmentFeignClient {


    @GetMapping(value = "/department/{id}")
    @CircuitBreaker(name = "userService")
    DepartmentResponse createUserGetDepartmentById(@PathVariable("id") Long id);

//    default DepartmentResponse getDepartmentByIdFallback(Exception e) {
//       var a = RetreiveMessageErrorDecoder.staticCode;
//        switch (a) {
//            case 404:
//                System.out.println("Girdi");
//                 a = 0;
//                throw new DepartmentNotFoundException("Böyle bir departman yok");
//            case 0:
//                throw new DepartmentNotFoundException("Department servisinde yaşanan aksaklık sonucu kayıt gerçekleşmiyor");
//
//            default:
//                return new DepartmentResponse();
//        if(RetreiveMessageErrorDecoder.STATUS_CODE == 404){
//            throw new DepartmentNotFoundException("Department Not Found");
//        }
//        return new DepartmentResponse();
//
//    }

    @GetMapping(value = "/department/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserGetDepartmentById")
    DepartmentResponse getUserGetDepartmentById(@PathVariable("id") Long id);
    default DepartmentResponse getUserGetDepartmentById(Exception exception) {
        return new DepartmentResponse();
    }

}


