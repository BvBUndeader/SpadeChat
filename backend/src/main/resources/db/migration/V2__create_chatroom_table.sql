CREATE TABLE IF NOT EXISTS chatrooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    is_active INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS chatroom_members (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    chatroom_id INT REFERENCES chatrooms(id) ON DELETE CASCADE,
    role VARCHAR(10) CHECK (role IN ('OWNER', 'ADMIN', 'MEMBER')),
    PRIMARY KEY (user_id, chatroom_id)
);