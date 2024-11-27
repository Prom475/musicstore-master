package com.example.musicstore.service;

import com.example.musicstore.dto.UserDto;
import com.example.musicstore.model.Role;
import com.example.musicstore.model.User;
import com.example.musicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Role.USER);
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}