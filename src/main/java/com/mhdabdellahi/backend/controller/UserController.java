package com.mhdabdellahi.backend.controller;

import com.mhdabdellahi.backend.dto.*;
import com.mhdabdellahi.backend.model.Profile;
import com.mhdabdellahi.backend.model.User;
import com.mhdabdellahi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = "https://frontend-1azi.onrender.com", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/is-admin")
    public ResponseEntity<AdminCheckResponse> checkIfAdmin(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        boolean isAdmin = user.getRole().equalsIgnoreCase("ADMIN");
        AdminCheckResponse adminCheckResponse = new AdminCheckResponse();
        adminCheckResponse.setAdmin(isAdmin);
        return ResponseEntity.ok(adminCheckResponse);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal principal
    ) {
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-username")
    public ResponseEntity<?> updateUsername(
            @RequestBody ChangeUsernameRequest request,
            Principal principal
    ) {
        User updatedUser = userService.updateUsername(
                principal.getName(),
                request.getNewUsername()
        );
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }


    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody ProfileUpdateRequest request,  // Use DTO instead of Entity
            Principal principal
    ) {
        User user = userService.getUserByUsername(principal.getName());
        Profile profile = user.getProfile();

        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getSex() != null) profile.setSex(request.getSex());
        if (request.getAge() != null) profile.setAge(request.getAge());
        if (request.getEmail() != null) profile.setEmail(request.getEmail());

        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteUserByUsername(Principal principal) {
        userService.deleteUserByUsername(principal.getName());
        return ResponseEntity.ok().body(
                Map.of("message", "User account deleted successfully")
        );
    }

}