package com.project.spade.repositories;

import com.project.spade.entities.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomRepo extends JpaRepository<Chatroom, Integer> {
    List<Chatroom> findByIsActive(int isActive);
    Chatroom findByIdAndIsActive(int id, int isActive);
    Chatroom findByNameAndIsActive(String name, int isActive);
}
