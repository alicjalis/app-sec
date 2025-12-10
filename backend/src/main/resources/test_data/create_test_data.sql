INSERT INTO users (username, password, user_type, email, enabled)
VALUES
    ('admin', '$2a$10$I4mYgLioHqBRewLntcD9F.vX3WR5gjogQZ.kgmrkg0OJGhI59O61a', 'ADMIN', 'admin@mail.com', true),
    ('user1', '$2a$10$CRJP1RRNwzU0n1rE66Olk.xo0HO./Zw3rZ7zFoRrhBiJK8ko0eJxC', 'USER', 'user1@mail.com', true),
    ('user2', '$2a$10$bjwf/P0ESEuU0BnHs4itWOgGLjni2PBjcWwA1mXQ91P/AVirnpNZW', 'USER', 'user2@mail.com', true),
    ('user3', '$2a$10$J1YFsyzWW5pyf3geb/EQbOrmlpDCc0LAY.tTEnzg5ZJkxemWFqpJO', 'USER', 'user3@mail.com', true),
    ('user4', '$2a$10$vacYTXVtZG8ZlL82RgO93OKVG3dE0TMSyb6iuTxTylkEDACvO416K', 'USER', 'user4@mail.com', true);

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
