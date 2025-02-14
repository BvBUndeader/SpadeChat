package com.project.spade.services;

import com.project.spade.dtos.MessageDTO;
import com.project.spade.entities.Chatroom;
import com.project.spade.entities.Message;
import com.project.spade.entities.User;
import com.project.spade.exceptions.ForbiddenActionException;
import com.project.spade.exceptions.NotFoundException;
import com.project.spade.mappers.MessageMapper;
import com.project.spade.repositories.ChatroomMemberRepo;
import com.project.spade.repositories.ChatroomRepo;
import com.project.spade.repositories.MessageRepo;
import com.project.spade.repositories.UserRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final ChatroomRepo chatroomRepo;
    private final ChatroomMemberRepo chatroomMemberRepo;
    private final UserRepo userRepo;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageService(MessageRepo messageRepo, ChatroomRepo chatroomRepo, ChatroomMemberRepo chatroomMemberRepo, UserRepo userRepo, MessageMapper messageMapper, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageRepo = messageRepo;
        this.chatroomRepo = chatroomRepo;
        this.chatroomMemberRepo = chatroomMemberRepo;
        this.userRepo = userRepo;
        this.messageMapper = messageMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public MessageDTO sendMessageToChatroom(MessageDTO messageDTO){
        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(messageDTO.getChatroomId(),1);
        if(chatroom == null){
            throw new NotFoundException("Chatroom not found");
        }

        User sender = this.userRepo.findByIdAndIsActive(messageDTO.getUserId(),1);

        if(sender == null){
            throw new NotFoundException("User not found");
        }

        if (!chatroomMemberRepo.existsByChatroomIdAndUserId(chatroom.getId(), sender.getId())){
            throw new ForbiddenActionException("User isn't inside the chatroom");
        }

        Message message = new Message();
        message.setChatroom(chatroom);
        message.setUser(sender);
        message.setContent(messageDTO.getContent());
        message.setCreatedAt(LocalDateTime.now());

        message = this.messageRepo.save(message);
        MessageDTO responseMessage = messageMapper.toDto(message);

        simpMessagingTemplate.convertAndSend("/topic/chatroom/" + chatroom.getId(), responseMessage);

        return responseMessage;
    }

    public List<MessageDTO> getChatroomMessages(int chatroomId){
        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(chatroomId,1);
        if(chatroom == null){
            throw new NotFoundException("Chatroom not found");
        }

        List<Message> messages = messageRepo.findAllByChatroomIdAndIsActive(chatroom.getId(),1);
        return messages.stream().map(messageMapper::toDto).toList();
    }

}
