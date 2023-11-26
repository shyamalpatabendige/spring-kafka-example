-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    first_name VARCHAR(256) NOT NULL,
    last_name VARCHAR(256) NOT NULL,
    CONSTRAINT uc_user_first_last_name UNIQUE(first_name, last_name),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create an index on user_id column
CREATE INDEX idx_user__id ON users(id);