package com.project.spade.mappers;

import com.project.spade.dtos.ChatroomDTO;
import com.project.spade.dtos.UserRoleDTO;
import com.project.spade.entities.Chatroom;
import com.project.spade.entities.ChatroomMember;
import com.project.spade.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChatroomMapper {
    private final ModelMapper modelMapper;

    public ChatroomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ChatroomDTO toDto(Chatroom chatroom){
        ChatroomDTO dto = modelMapper.map(chatroom, ChatroomDTO.class);
        dto.setMembers(chatroom.getMembers().stream()
                .map(member -> new UserRoleDTO(
                        member.getUser().getId(),
                        member.getUser().getUsername(),
                        member.getRole().toString()
                )).collect(Collectors.toList()));
        return dto;
    }

    public Chatroom toEntity(ChatroomDTO dto, Set<ChatroomMember> members){
        Chatroom chatroom = modelMapper.map(dto, Chatroom.class);
        chatroom.setMembers(members);
        return chatroom;
    }


}
