package com.example.projecttool.User.services;

import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import com.example.projecttool.exceptions.userNameAlreadyExistsException.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");
            newUser.setNotes("Don't forget to smile!");
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists.");
        }
    }

    public User updateUser(User updatedUser, String username){
        User user = userRepository.findByUsername(username);
        user.setUsername(updatedUser.getUsername());
        user.setFullName(updatedUser.getFullName());
        user.setNotes(updatedUser.getNotes());
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
