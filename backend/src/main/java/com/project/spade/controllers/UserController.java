package com.project.spade.controllers;

import com.project.spade.dtos.UserDTO;
import com.project.spade.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){

        UserDTO createdUser = this.userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){

//        List<UserDTO> result = this.userService.getAllUsers()

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleUser(@PathVariable int id){
        Optional<UserDTO> responseUser = this.userService.getSingleUser(id);

        if(responseUser == null){
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO){
        boolean isUpdateSuccessful = this.userService.updateUser(id, userDTO);

        if(isUpdateSuccessful){
            return ResponseEntity.status(HttpStatus.OK).body("Username updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        boolean isUserDeleted = this.userService.deleteUser(id);

        if(!isUserDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }


        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
}
