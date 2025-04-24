package com.mhdabdellahi.backend.controller;

import com.mhdabdellahi.backend.dto.ChangePasswordRequest;
import com.mhdabdellahi.backend.dto.ChangeUsernameRequest;
import com.mhdabdellahi.backend.dto.UserResponse;
import com.mhdabdellahi.backend.model.User;
import com.mhdabdellahi.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "https://frontend-1azi.onrender.com", allowCredentials = "true")
@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role
    ) {
        Page<User> userPage = userService.searchUsers(search, role, page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("data", userPage.getContent().stream().map(UserResponse::new).toList());
        response.put("count", userPage.getTotalElements());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/{username}/change-password")
    public ResponseEntity<?> adminChangePassword(
            @PathVariable String username,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(username, request);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{username}/change-username")
    public ResponseEntity<?> adminChangeUsername(
            @PathVariable String username,
            @RequestBody ChangeUsernameRequest request
            ) {
        User updatedUser = userService.updateUsername(
                username,
                request.getNewUsername()
        );
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok().body(
                Map.of("message", "User account deleted successfully")
        );
    }
}