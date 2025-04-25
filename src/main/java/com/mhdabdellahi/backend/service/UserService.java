package com.mhdabdellahi.backend.service;

import com.mhdabdellahi.backend.dto.ChangePasswordRequest;
import com.mhdabdellahi.backend.dto.LoginRequest;
import com.mhdabdellahi.backend.dto.RegisterRequest;
import com.mhdabdellahi.backend.model.Profile;
import com.mhdabdellahi.backend.model.User;
import com.mhdabdellahi.backend.repository.ProfileRepository;
import com.mhdabdellahi.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;


    @Autowired
    AuthenticationManager authenticationManager;

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Profile profile = new Profile();
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setSex(request.getSex());
        profile.setAge(request.getAge());
        profile.setEmail(request.getEmail());
        profileRepository.save(profile);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setProfile(profile);

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }



    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void adminResetPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUsername(String currentUsername, String newUsername) {
        User user = getUserByUsername(currentUsername);

        if (userRepository.existsByUsername(newUsername)) {
            throw new RuntimeException("Username already taken");
        }

        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    public String verify(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(request.getUsername());

        return "Fail";
    }


    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        userRepository.delete(user);
    }


    public Page<User> searchUsers(String search, String role, int page, int size) {
        Specification<User> spec = Specification
                .where(UserSpecifications.containsSearch(search))
                .and(UserSpecifications.hasRole(role));

        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), size);
        return userRepository.findAll(spec, pageable);
    }
}
