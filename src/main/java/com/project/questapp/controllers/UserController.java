package com.project.questapp.controllers;

import com.project.questapp.entities.User;
import com.project.questapp.exception.UserNotFoundException;
import com.project.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.createUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId){

        User user = userService.getUser(userId);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User newUser){

        User user = userService.updateUser(userId, newUser);
        if (user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private void handleUserNotFound(final Exception e, final HttpServletRequest request,
                                    Writer writer) throws IOException {
        writer.write("User not found");
    }
}
