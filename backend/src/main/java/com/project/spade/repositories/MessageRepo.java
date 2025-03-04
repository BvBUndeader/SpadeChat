package com.project.spade.repositories;

import com.project.spade.entities.Chatroom;
import com.project.spade.entities.Message;
import com.project.spade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Integer> {
    List<Message> findAllByChatroomIdAndIsActive(int chatroomId, int isActive);
    List<Message> findAllMessagesByUserIdAndIsActive(User user, int isActive);
    List<Message> findAllByUserIdAndChatroomIdAndIsActive(int userId,int chatroomId, int isActive);


}
