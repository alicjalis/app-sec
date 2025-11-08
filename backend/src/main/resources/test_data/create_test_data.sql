INSERT INTO users (username, password, user_type)
VALUES
    ('admin', 'admin', 'ADMIN'),
    ('user1', 'user1', 'USER'),
    ('user2', 'user2', 'USER'),
    ('user3', 'user3', 'USER'),
    ('user4', 'user4', 'USER');

INSERT INTO posts (user_id, title, content_uri, likes_number, dislikes_number, is_deleted)
VALUES
    (2, 'Title', '/content/meme1.png', 10, 2, FALSE),
    (3, 'Also title', '/content/meme2.png', 25, 1, FALSE),
    (4, 'Not a title', '/content/meme3.pdf', 50, 0, FALSE),
    (5, 'Title1', '/content/meme4.mp4', 14, 3, TRUE),
    (5, 'Title2', '/content/meme5.pdf', 30, 5, FALSE);

INSERT INTO comments (user_id, post_id, content, likes_number, dislikes_number, is_deleted)
VALUES
    (2, 1, '67', 5, 0, FALSE),
    (3, 1, 'Nice', 8, 0, FALSE),
    (4, 2, 'Comment', 3, 1, FALSE),
    (4, 2, 'Also a comment', 7, 0, TRUE),
    (5, 3, 'Not a comment', 2, 0, FALSE),
    (1, 5, 'I am the admin', 4, 0, FALSE),
    (2, 5, 'Nice title2', 3, 0, FALSE),
    (3, 4, 'I should not be here', 6, 1, TRUE),
    (5, 4, 'Neither should I', 2, 0, FALSE);
