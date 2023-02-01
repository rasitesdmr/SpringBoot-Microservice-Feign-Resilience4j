package com.example.userservice;

import com.example.userservice.config.RetreiveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("com.example.userservice.feignClients")
@EnableEurekaClient
public class UserServiceApplication {

    public static void main(String[] args) {


        SpringApplication.run(UserServiceApplication.class, args);
    }


}
