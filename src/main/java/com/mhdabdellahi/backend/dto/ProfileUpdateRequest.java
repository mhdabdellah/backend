package com.mhdabdellahi.backend.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String firstName;
    private String lastName;
    private String sex;
    private Integer age;
    private String email;
}
