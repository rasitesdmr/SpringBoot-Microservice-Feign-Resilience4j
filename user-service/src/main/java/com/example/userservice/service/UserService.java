package com.example.userservice.service;

import com.example.userservice.request.UserCreateRequest;
import com.example.userservice.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserCreateRequest userCreateRequest);
    UserResponse getById(long id) ;
}
