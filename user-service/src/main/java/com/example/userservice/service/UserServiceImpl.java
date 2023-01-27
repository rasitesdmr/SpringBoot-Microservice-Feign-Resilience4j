package com.example.userservice.service;

import com.example.userservice.feignClients.DepartmentFeignClient;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.request.UserCreateRequest;
import com.example.userservice.response.DepartmentResponse;
import com.example.userservice.response.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentFeignClient departmentFeignClient;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = userMapper.userCreateRequestToUser(userCreateRequest);
        user = userRepository.save(user);

        UserResponse userResponse = new UserResponse(user);

        userResponse.setDepartmentResponse(departmentFeignClient.getById(user.getDepartmentId()));

        return userResponse;
    }

    @Override

    public UserResponse getById(long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserResponse userResponse = new UserResponse(user);
        userResponse.setDepartmentResponse(getDepartmentById(user.getDepartmentId()));
        return userResponse;
    }

    // @CircuitBreaker(name = "userService", fallbackMethod = "getDepartmentByIdFallback")
    public DepartmentResponse getDepartmentById(Long departmentId) throws IllegalArgumentException {
        DepartmentResponse departmentResponse = departmentFeignClient.getById(departmentId);
        return departmentResponse;
    }


//    public DepartmentResponse getDepartmentByIdFallback(Throwable t) {
//        return new DepartmentResponse();
//    }

}
