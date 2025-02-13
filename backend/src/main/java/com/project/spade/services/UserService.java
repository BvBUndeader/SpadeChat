package com.project.spade.services;

import com.project.spade.dtos.UserDTO;
import com.project.spade.entities.User;
import com.project.spade.mappers.UserMapper;
import com.project.spade.repositories.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO){
        if(userRepo.existsByUsername(userDTO.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }

        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserDTO> getAllUsers(){
        return userRepo.findByIsActive(1).stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getSingleUser(int id){
        Optional<User> wrappedUser = Optional.ofNullable(userRepo.findByIdAndIsActive(id, 1));

        return wrappedUser.map(userMapper::toDto);
    }

    public boolean updateUser(int id, UserDTO userDTO){
        User user = userRepo.findByIdAndIsActive(id, 1);
        if(user != null){
            user.setUsername(userDTO.getUsername());
            User updatedUser = userRepo.save(user);
            return true;
        }
        return false;
    }

    public boolean deleteUser(int id){
        User user = userRepo.findByIdAndIsActive(id, 1);
        if(user != null){
            user.setIsActive(0);
            user.setUsername(user.getUsername() + "_deleted" + user.getId());
            this.userRepo.save(user);
            return true;
        }

        // added the _deleted{id} thing for the username in case
        // some other user wants to use the username as it would be kinda silly
        // to give someone else the error username already taken while the user
        // with that username being deleted

        return false;
    }
}
