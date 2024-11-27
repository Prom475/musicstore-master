package com.example.musicstore.service;

import com.example.musicstore.dto.UserDto;
import com.example.musicstore.model.User;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
}