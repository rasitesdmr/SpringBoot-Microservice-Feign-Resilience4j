package com.example.userservice.mapper;

import com.example.userservice.model.User;
import com.example.userservice.request.UserCreateRequest;
import com.example.userservice.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponse userToUserResponse(User user);
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);
}
