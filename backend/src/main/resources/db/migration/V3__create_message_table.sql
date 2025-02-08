  CREATE TABLE messages (
      id INT PRIMARY KEY AUTO_INCREMENT,
      chatroom_id INT NOT NULL,
      user_id INT NOT NULL,
      content VARCHAR(255) NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      is_active INT DEFAULT 1,
      FOREIGN KEY (chatroom_id) REFERENCES chatrooms(id) ON DELETE CASCADE,
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
  );