package com.project.spade.mappers;

import com.project.spade.dtos.MessageDTO;
import com.project.spade.entities.Chatroom;
import com.project.spade.entities.Message;
import com.project.spade.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper {

    private final ModelMapper modelMapper;

    public MessageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MessageDTO toDto(Message message){
        MessageDTO dto = modelMapper.map(message, MessageDTO.class);
        dto.setUserId(message.getUser().getId());
        dto.setChatroomId(message.getChatroom().getId());
        dto.setCreatedAt(message.getCreatedAt().toString());
        return dto;
    }

    public Message toEntity(MessageDTO dto, User user, Chatroom chatroom){
        Message message = modelMapper.map(dto, Message.class);
        message.setUser(user);
        message.setChatroom(chatroom);
        message.setCreatedAt(LocalDateTime.parse(dto.getCreatedAt()));
        return message;
    }
}
