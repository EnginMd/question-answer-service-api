package com.project.questapp.services;

import com.project.questapp.entities.User;
import com.project.questapp.exception.UserNotFoundException;
import com.project.questapp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(Long userId, User newUser) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            user.get().setUserName(newUser.getUserName());
            user.get().setPassword(newUser.getPassword());
            return userRepository.save(user.get());
        }
        else{
            return null;
        }
    }

    public void deleteUser(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            userRepository.deleteById(userId);
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
