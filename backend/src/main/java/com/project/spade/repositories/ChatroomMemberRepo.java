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
    List<ChatroomMember> findByChatroomId(int chatroomId);
    ChatroomMember findByChatroomIdAndUserId(int chatroomId, int userId);
    boolean existsByChatroomIdAndUserId(int chatroomId, int userId);
    List<ChatroomMember> findByUserId(int userId);
}
