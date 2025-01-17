package com.project.habit.Controller;

import com.project.habit.Model.User;
import com.project.habit.Response.UserResponse;
import com.project.habit.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @PostMapping("/registerUser")
    public ResponseEntity<UserResponse<User>> registerUser(@RequestBody User user){
        return userServices.registerUser(user);
        
    }

    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse<User>> loginUser(@RequestBody User user){
        return userServices.loginUser(user);
    }
    
    @DeleteMapping("/deleteUserById/{id}")
    public boolean deleteUserById(@PathVariable int id) {
    	return userServices.deleteUserById(id);
    }
    
    @PutMapping("/updateUserById")
    public boolean updateUserById(@RequestBody User user) {
    	return userServices.updateUserById(user);
    }
}
