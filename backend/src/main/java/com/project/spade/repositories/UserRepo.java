package com.project.spade.repositories;

import com.project.spade.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    List<User> findByIsActive(int isActive);
    User findByUsernameAndIsActive(String username, int isActive);
    User findByIdAndIsActive(int id, int isActive);
    boolean existsByUsername(String username);
}
