package com.excellia.wallet.controller;

import com.excellia.wallet.dto.UserDto;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private User getCurrentUser() {
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/me")
    public UserDto getProfile() {
        User user = getCurrentUser();
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    @PutMapping("/me")
    public UserDto updateProfile(@RequestBody UserDto dto) {
        User user = getCurrentUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        User updated = userService.updateUser(user);
        UserDto response = new UserDto();
        response.setId(updated.getId());
        response.setPhoneNumber(updated.getPhoneNumber());
        response.setFirstName(updated.getFirstName());
        response.setLastName(updated.getLastName());
        response.setEmail(updated.getEmail());
        response.setRole(updated.getRole());
        response.setActive(updated.isActive());
        response.setCreatedAt(updated.getCreatedAt());
        return response;
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount() {
        userService.deleteUser(getCurrentUser());
        return ResponseEntity.ok().build();
    }
}