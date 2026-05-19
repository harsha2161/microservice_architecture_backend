package com.example.user.service;

import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Transactional

public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private  ModelMapper modelMapper;

    public List<UserDTO> getAllUsers(){
        List<User>userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>(){}.getType());
    }




}
