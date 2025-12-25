DELETE FROM comments;
DELETE FROM posts;
DELETE FROM users;
DELETE FROM confirmation_tokens;
DELETE FROM post_reactions;
DELETE FROM comment_reactions;

ALTER SEQUENCE comments_id_seq RESTART WITH 1;
ALTER SEQUENCE posts_id_seq RESTART WITH 1;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE confirmation_tokens_id_seq RESTART WITH 1;
ALTER SEQUENCE post_reactions_id_seq RESTART WITH 1;
ALTER SEQUENCE comment_reactions_id_seq RESTART WITH 1;