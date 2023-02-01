package com.example.userservice.response;

import com.example.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private DepartmentResponse departmentResponse;

//    public UserResponse(User user){
//        this.id= user.getId();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//        this.email = user.getEmail();
//    }
}
