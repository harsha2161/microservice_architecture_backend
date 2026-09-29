package com.example.user.controller;

import com.example.user.dto.*;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/users/")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO> createUser(@RequestBody @Valid UserResponseDTO.UserCreateDTO userCreateDTO){
        return userService.createUser(userCreateDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return userService.login(loginRequestDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return userService.refreshAccessToken(refreshTokenRequestDTO.getRefreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDTO> logout(@RequestBody @Valid RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return userService.logout(refreshTokenRequestDTO.getRefreshToken());
    }

}
