package com.project.spade.mappers;

import com.project.spade.dtos.UserDTO;
import com.project.spade.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO toDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }
}
