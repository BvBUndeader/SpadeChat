CREATE TABLE IF NOT EXISTS chatrooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) UNIQUE NOT NULL,
    is_active INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS chatroom_members (
    user_id INT NOT NULL,
    chatroom_id INT NOT NULL,
    role VARCHAR(10) NOT NULL,
    PRIMARY KEY (chatroom_id, user_id),
    FOREIGN KEY (chatroom_id) REFERENCES chatrooms(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);