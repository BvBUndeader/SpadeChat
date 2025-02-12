package com.project.spade.repositories;

import com.project.spade.entities.ChatMemberComposite;
import com.project.spade.entities.Chatroom;
import com.project.spade.entities.ChatroomMember;
import com.project.spade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomMemberRepo extends JpaRepository<ChatroomMember, ChatMemberComposite> {
    List<ChatroomMember> findByChatroomId(Chatroom chatroom);
    ChatroomMember findByChatroomIdAndUserId(Chatroom chatroom, User user);
    boolean existsByChatroomIdAndUserId(Chatroom chatroom, User user);
    List<ChatroomMember> findByUserId(User user);
}
