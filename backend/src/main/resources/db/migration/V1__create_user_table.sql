      CREATE TABLE IF NOT EXISTS users (
          id SERIAL UNIQUE PRIMARY KEY,
          username VARCHAR(50) UNIQUE NOT NULL,
          is_active INT DEFAULT 1
      );