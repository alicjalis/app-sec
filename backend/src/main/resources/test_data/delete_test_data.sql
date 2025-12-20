DELETE FROM comments;
DELETE FROM posts;
DELETE FROM users;
DELETE FROM confirmation_tokens;

ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE confirmation_tokens_id_seq RESTART WITH 1;