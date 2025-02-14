package com.project.spade.services;

import com.project.spade.dtos.ChatroomDTO;
import com.project.spade.entities.ChatMemberComposite;
import com.project.spade.entities.Chatroom;
import com.project.spade.entities.ChatroomMember;
import com.project.spade.entities.User;
import com.project.spade.enums.MemberRole;
import com.project.spade.exceptions.BadRequestException;
import com.project.spade.exceptions.ConflictActionException;
import com.project.spade.exceptions.ForbiddenActionException;
import com.project.spade.exceptions.NotFoundException;
import com.project.spade.mappers.ChatroomMapper;
import com.project.spade.repositories.ChatroomMemberRepo;
import com.project.spade.repositories.ChatroomRepo;
import com.project.spade.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ChatroomService {

    private final ChatroomRepo chatroomRepo;
    private final ChatroomMemberRepo chatroomMemberRepo;
    private final UserRepo userRepo;
    private final ChatroomMapper chatroomMapper;

    public ChatroomService(ChatroomRepo chatroomRepo, ChatroomMemberRepo chatroomMemberRepo, UserRepo userRepo, ChatroomMapper chatroomMapper) {
        this.chatroomRepo = chatroomRepo;
        this.chatroomMemberRepo = chatroomMemberRepo;
        this.userRepo = userRepo;
        this.chatroomMapper = chatroomMapper;
    }

    public ChatroomDTO createChatroom(ChatroomDTO chatroomDTO, int creatorId) {
        User creator = this.userRepo.findByIdAndIsActive(creatorId, 1);

        if (creator == null) {
            throw new NotFoundException("User not found");
        }


        Chatroom chatroom = this.chatroomMapper.toEntity(chatroomDTO, new HashSet<>());
        chatroom.setIsActive(1);
        chatroom = chatroomRepo.save(chatroom);

        ChatroomMember owner = new ChatroomMember();
        ChatMemberComposite compKey = new ChatMemberComposite();

        compKey.setChatroomId(chatroom.getId());
        compKey.setUserId(creator.getId());

        owner.setId(compKey);
        owner.setChatroom(chatroom);
        owner.setUser(creator);
        owner.setRole(MemberRole.OWNER);
        chatroomMemberRepo.save(owner);
        return chatroomMapper.toDto(chatroom);
    }

    public void addUserToChatroom(int chatroomId, int userId, int requesterId) {
        User user = this.userRepo.findByIdAndIsActive(requesterId, 1);

        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(chatroomId, 1);

        if (chatroom == null) {
            throw new NotFoundException("Chatroom not found");
        }

        ChatroomMember requester = this.chatroomMemberRepo.findByChatroomIdAndUserId(chatroom.getId(), user.getId());

        if (requester == null) {
            throw new ForbiddenActionException("User is not in the chatroom");
        }

        if (requester.getRole() == MemberRole.MEMBER) {
            throw new ForbiddenActionException("Insufficient permissions");
        }

        User userToBeAdded = this.userRepo.findByIdAndIsActive(userId, 1);

        if (userToBeAdded == null) {
            throw new NotFoundException("Cannot add user - User not found");
        }

        if (this.chatroomMemberRepo.existsByChatroomIdAndUserId(chatroom.getId(), userToBeAdded.getId())) {
            throw new BadRequestException("User is already in the chatroom");
        }

        ChatroomMember newMember = new ChatroomMember();
        ChatMemberComposite newMemberComposite = new ChatMemberComposite();
        newMemberComposite.setChatroomId(chatroom.getId());
        newMemberComposite.setUserId(userToBeAdded.getId());

        newMember.setId(newMemberComposite);
        newMember.setChatroom(chatroom);
        newMember.setUser(userToBeAdded);
        newMember.setRole(MemberRole.MEMBER);
        chatroomMemberRepo.save(newMember);
    }

    public void assignAdminRole(int chatroomId, int userId, int requesterId) {
        User user = this.userRepo.findByIdAndIsActive(requesterId, 1);

        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(chatroomId, 1);

        if (chatroom == null) {
            throw new NotFoundException("Chatroom not found");
        }

        ChatroomMember requester = this.chatroomMemberRepo.findByChatroomIdAndUserId(chatroom.getId(), user.getId());

        if (requester.getRole() != MemberRole.OWNER) {
            throw new ForbiddenActionException("Insufficient permissions - Only owners can give admin");
        }

        User toBeAdmin = this.userRepo.findByIdAndIsActive(userId, 1);
        ChatroomMember member = this.chatroomMemberRepo.findByChatroomIdAndUserId(chatroom.getId(), toBeAdmin.getId());

        if (member == null) {
            throw new NotFoundException("User is not a member of the chatroom");
        }

        member.setRole(MemberRole.ADMIN);
        this.chatroomMemberRepo.save(member);
    }

    public void updateChatroomName(int chatroomId, int requesterId, String updatedName) {
        User requesterObj = this.userRepo.findByIdAndIsActive(requesterId, 1);

        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(chatroomId, 1);

        if (chatroom == null) {
            throw new NotFoundException("Chatroom not found");
        }

        ChatroomMember requester = this.chatroomMemberRepo.findByChatroomIdAndUserId(chatroom.getId(), requesterObj.getId());

        if (requester.getRole() == MemberRole.MEMBER) {
            throw new ForbiddenActionException("Insufficient permissions");
        }

        if (this.chatroomRepo.findByNameAndIsActive(updatedName, 1) != null) {
            throw new ConflictActionException("Chatroom with this name already exists");
        }

        chatroom.setName(updatedName);
        this.chatroomRepo.save(chatroom);
    }

    public void deleteChatroom(int chatroomId, int requesterId){
        User user = this.userRepo.findByIdAndIsActive(requesterId, 1);

        Chatroom chatroom = this.chatroomRepo.findByIdAndIsActive(chatroomId, 1);

        if (chatroom == null) {
            throw new NotFoundException("Chatroom not found");
        }

        ChatroomMember requester = this.chatroomMemberRepo.findByChatroomIdAndUserId(chatroom.getId(), user.getId());

        if (requester.getRole() != MemberRole.OWNER) {
            throw new ForbiddenActionException("Insufficient permissions - Only owners can delete chatrooms");
        }

        chatroom.setIsActive(0);
        this.chatroomRepo.save(chatroom);
    }

}