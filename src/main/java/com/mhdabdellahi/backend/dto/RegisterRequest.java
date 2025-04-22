package com.mhdabdellahi.backend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String sex;
    private Integer age;
    private String email;
}
