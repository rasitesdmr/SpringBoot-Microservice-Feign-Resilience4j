package com.example.userservice.service;

import com.example.userservice.exception.DepartmentNotFoundException;
import com.example.userservice.exception.ExceptionMessage;
import com.example.userservice.exception.FirstAndLastNameAlreadyExistsException;
import com.example.userservice.feignClients.DepartmentFeignClient;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.request.UserCreateRequest;
import com.example.userservice.response.DepartmentResponse;
import com.example.userservice.response.UserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DepartmentFeignClient departmentFeignClient;

//    ExceptionMessage exceptionMessage;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {

        User user = userMapper.userCreateRequestToUser(userCreateRequest);
        if (userRepository.existsByFirstName(user.getFirstName()) &&
                userRepository.existsByLastName(user.getLastName())) {
            throw new FirstAndLastNameAlreadyExistsException("Zaten Mevcut");
        }
//        user = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setDepartmentResponse(departmentFeignClient.createUserGetDepartmentById(user.getDepartmentId()));
//
//        Long departmentId = userResponse.getDepartmentResponse().getId();
//        if(departmentId == null){
//            throw new DepartmentNotFoundException("Böyle bir departman bulunmamaktır.");
//        }

        user = userRepository.save(user);

        userResponse.setId(user.getId());
        userResponse.setLastName(user.getLastName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setEmail(user.getEmail());
//        UserResponse userResponse = new UserResponse(user);

//        userResponse.setDepartmentResponse(departmentFeignClient.getById(userCreateRequest.getDepartmentId()));



        return userResponse;
    }

    // TODO UserResponse metodunda değişiklik yapıldı. Bakılacak
    @Override
    public UserResponse getById(long id) {
        User user = userRepository.findById(id).orElseThrow();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setLastName(user.getLastName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setEmail(user.getEmail());
        userResponse.setDepartmentResponse(getDepartmentById(user.getDepartmentId()));
        return userResponse;
    }

    // @CircuitBreaker(name = "userService", fallbackMethod = "getDepartmentByIdFallback")
    public DepartmentResponse getDepartmentById(Long departmentId) throws IllegalArgumentException {
        DepartmentResponse departmentResponse = departmentFeignClient.getUserGetDepartmentById(departmentId);
        return departmentResponse;
    }


//    public DepartmentResponse getDepartmentByIdFallback(Throwable t) {
//        return new DepartmentResponse();
//    }

}
