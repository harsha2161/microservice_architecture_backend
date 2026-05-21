package com.example.user.controller;

import com.example.user.dto.UserDTO;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getuser")
    public List<UserDTO> getUser(){

        return userService.getAllUsers();
    }

    @PostMapping("/saveuser")
    public UserDTO saveUser(@RequestBody UserDTO userDTO){

        return  userService.saveUser(userDTO);
    }

    @PutMapping("/updateuser")
    public UserDTO updateUser(@RequestBody UserDTO userDTO){

        return userService.updateUser(userDTO);
    }

    @DeleteMapping("/deleteuser")
    public String deleteUser(@RequestBody UserDTO userDTO){

        return  userService.deleteUser(userDTO);
    }

}
