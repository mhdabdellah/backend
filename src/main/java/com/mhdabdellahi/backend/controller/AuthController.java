package com.mhdabdellahi.backend.controller;
import com.mhdabdellahi.backend.dto.LoginRequest;
import com.mhdabdellahi.backend.dto.RegisterRequest;
import com.mhdabdellahi.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://frontend-1azi.onrender.com", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;


    @RequestMapping(path="/register", method=RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.verify(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}















//
//@RestController
////@RequestMapping("/auth")
//public class AuthController {
//
//    @PostMapping("/")
//    public String index() {
//        return "welcome to index ";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register() {
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login() {
////        return "index.html";
//        return ResponseEntity.ok("Logged succefully ");
//    }
//}
