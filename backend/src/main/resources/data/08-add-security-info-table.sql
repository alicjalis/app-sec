--liquibase formatted sql
--changeset mlewandowski:8

CREATE TABLE IF NOT EXISTS user_activity (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,

    last_login_date TIMESTAMP,
    last_login_ip VARCHAR(50),
    last_user_agent TEXT,

    last_post_date TIMESTAMP,

    failed_login_attempts INTEGER DEFAULT 0,

    password_last_changed_date TIMESTAMP,

    CONSTRAINT uq_user_activity_user_id UNIQUE (user_id),
    CONSTRAINT fk_user_activity_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);