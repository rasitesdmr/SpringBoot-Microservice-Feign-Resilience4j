package com.example.userservice.controller;

import com.example.userservice.request.UserCreateRequest;
import com.example.userservice.response.UserResponse;
import com.example.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserResponse createStudent (@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/getById/{id}")
    public UserResponse getById (@PathVariable("id") Long id) {
        return userService.getById(id);
    }

}
