--liquibase formatted sql
--changeset mlewandowski:4

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS email VARCHAR(255) UNIQUE,
    ADD COLUMN IF NOT EXISTS enabled BOOLEAN DEFAULT FALSE;

CREATE TABLE IF NOT EXISTS confirmation_tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);