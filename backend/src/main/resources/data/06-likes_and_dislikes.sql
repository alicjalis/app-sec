--liquibase formatted sql
--changeset mlewandowski:6

ALTER TABLE posts
    DROP COLUMN IF EXISTS likes_number,
    DROP COLUMN IF EXISTS dislikes_number;

ALTER TABLE comments
    DROP COLUMN IF EXISTS likes_number,
    DROP COLUMN IF EXISTS dislikes_number;

CREATE TABLE IF NOT EXISTS post_reactions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    post_id INTEGER NOT NULL,

    reaction_value SMALLINT NOT NULL CHECK (reaction_value IN (1, -1)),

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,

    CONSTRAINT unique_user_post_reaction UNIQUE (user_id, post_id)
);

CREATE TABLE IF NOT EXISTS comment_reactions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    comment_id INTEGER NOT NULL,

    reaction_value SMALLINT NOT NULL CHECK (reaction_value IN (1, -1)),

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_post FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,

    CONSTRAINT unique_user_comment_reactions UNIQUE (user_id, comment_id)
);
