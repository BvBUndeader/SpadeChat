package com.project.spade.controllers;

import com.project.spade.dtos.MessageDTO;
import com.project.spade.entities.Message;
import com.project.spade.exceptions.ForbiddenActionException;
import com.project.spade.exceptions.NotFoundException;
import com.project.spade.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/chatroom")
    public ResponseEntity<?> sendMessageInChatroom(@RequestBody MessageDTO messageDTO){
        try {
            MessageDTO createMessage = this.messageService.sendMessageToChatroom(messageDTO);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        catch (ForbiddenActionException ex){
            return ResponseEntity.status(403).body(ex.getMessage());
        }

        return ResponseEntity.status(200).body("Message sent");
    }

    @GetMapping("/chatroom/{chatroomId}")
    public ResponseEntity<?> getChatroomMessages(@PathVariable int chatroomId){
        List<MessageDTO> messagesList;

        try {
            messagesList = this.messageService.getChatroomMessages(chatroomId);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(404).body("Chatroom not found");
        }

        return ResponseEntity.ok(messagesList);
    }
}
