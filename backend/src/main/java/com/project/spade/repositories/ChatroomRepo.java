package com.project.spade.repositories;

import com.project.spade.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomRepo extends JpaRepository<Chatroom, Integer> {
    List<Chatroom> findActiveChatrooms(int isActive);
    Chatroom findByNameAndActive(String name, int isActive);
}
