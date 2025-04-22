package com.mhdabdellahi.backend.dto;

import com.mhdabdellahi.backend.model.Profile;
import com.mhdabdellahi.backend.model.User;
import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String role;
    private Profile profile;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.profile = user.getProfile();
    }
}
