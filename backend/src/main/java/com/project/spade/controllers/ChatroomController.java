package com.project.spade.controllers;

import com.project.spade.dtos.ChatroomDTO;
import com.project.spade.exceptions.BadRequestException;
import com.project.spade.exceptions.ConflictActionException;
import com.project.spade.exceptions.ForbiddenActionException;
import com.project.spade.exceptions.NotFoundException;
import com.project.spade.services.ChatroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatrooms")
public class ChatroomController {

    private final ChatroomService chatroomService;

    public ChatroomController(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @PostMapping
    public ResponseEntity<?> createChatroom(@RequestBody ChatroomDTO chatroomDTO, @RequestParam int creatorId){
        try {
            ChatroomDTO createChatroom = this.chatroomService.createChatroom(chatroomDTO,creatorId);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("Something broke");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Chatroom created successfully");
    }

    @PostMapping("/{chatroomId}/users/{userId}")
    public ResponseEntity<?> addNewUser(@PathVariable int chatroomId, @PathVariable int userId, @RequestParam int requesterId){

        try {
            this.chatroomService.addUserToChatroom(chatroomId,userId,requesterId);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (ForbiddenActionException ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
        catch (BadRequestException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.status(200).body("User added successfully");
    }

    @PatchMapping("/{chatroomId}/users/{userId}/assign-admin")
    public ResponseEntity<?> assignAdmin(@PathVariable int chatroomId, @PathVariable int userId, @RequestParam int requesterId){
        try {
            this.chatroomService.assignAdminRole(chatroomId,userId,requesterId);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        catch (ForbiddenActionException ex){
            return ResponseEntity.status(403).body(ex.getMessage());
        }

        return ResponseEntity.status(200).body("Member successfully promoted to administrator");
    }

    @PatchMapping("/{chatroomId}/name-change")
    public ResponseEntity<?> changeChatName(@PathVariable int chatroomId, @RequestParam int requesterId, @RequestBody String updatedName){
        try {
           this.chatroomService.updateChatroomName(chatroomId,requesterId,updatedName);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        catch (ForbiddenActionException ex){
            return ResponseEntity.status(403).body(ex.getMessage());
        }
        catch (ConflictActionException ex){
            return ResponseEntity.status(409).body(ex.getMessage());
        }

        return ResponseEntity.status(200).body("Chatroom name changed successfully");

    }

    @DeleteMapping("/{chatroomId}")
    public ResponseEntity<?> deleteChatroom(@PathVariable int chatroomId, @RequestParam int requesterId){
        try {
            this.chatroomService.deleteChatroom(chatroomId, requesterId);
        }
        catch (NotFoundException ex){
            return ResponseEntity.status(404).body(ex.getMessage());
        }
        catch (ForbiddenActionException ex){
            return ResponseEntity.status(403).body(ex.getMessage());
        }

        return ResponseEntity.status(200).body("Chatroom deleted successfully");
    }

}
