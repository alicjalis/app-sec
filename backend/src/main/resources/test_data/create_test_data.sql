INSERT INTO users (username, password, user_type, email, enabled)
VALUES
    ('admin', '$2a$10$I4mYgLioHqBRewLntcD9F.vX3WR5gjogQZ.kgmrkg0OJGhI59O61a', 'ADMIN', 'admin@mail.com', true),
    ('user1', '$2a$10$CRJP1RRNwzU0n1rE66Olk.xo0HO./Zw3rZ7zFoRrhBiJK8ko0eJxC', 'USER', 'user1@mail.com', true),
    ('user2', '$2a$10$bjwf/P0ESEuU0BnHs4itWOgGLjni2PBjcWwA1mXQ91P/AVirnpNZW', 'USER', 'user2@mail.com', true),
    ('user3', '$2a$10$J1YFsyzWW5pyf3geb/EQbOrmlpDCc0LAY.tTEnzg5ZJkxemWFqpJO', 'USER', 'user3@mail.com', true),
    ('user4', '$2a$10$vacYTXVtZG8ZlL82RgO93OKVG3dE0TMSyb6iuTxTylkEDACvO416K', 'USER', 'user4@mail.com', true);

INSERT INTO posts (user_id, title, content_uri, likes_number, dislikes_number, is_deleted)
VALUES
    (2, 'Hors', '428d6c1a-0fb7-4018-bad8-393b5e60cc89_hors.png', 10, 2, FALSE),
    (3, 'Facts', 'f8a94a0b-8eb4-43c4-9999-7f63ee75568f_shrek.jpg', 25, 1, FALSE),
    (4, 'Hi', '6761c953-f102-4edc-a6d4-1b30d78124b3_dog.jpg', 50, 0, FALSE),
    (5, 'Facts', '5c4b69c6-22bb-4df6-a936-3b96b1e5469e_santa.jpg', 14, 3, TRUE),
    (5, 'So nice of him', '1edf9577-4110-4bfd-9209-01bab85a09eb_presents.jpg', 30, 5, FALSE),
    (5, 'Me', 'eae4ce50-b53f-4c5d-831f-54f576ec9087_deadlock.jpg', 41, 6, FALSE);

INSERT INTO comments (user_id, post_id, content, likes_number, dislikes_number, is_deleted)
VALUES
    (2, 1, 'Imagine him walking in', 5, 0, FALSE),
    (3, 1, 'Hors', 8, 0, FALSE),
    (4, 2, 'So true', 3, 1, FALSE),
    (4, 2, 'Me', 7, 0, TRUE),
    (5, 3, 'Damn', 2, 0, FALSE),
    (1, 5, 'Merry christmas', 4, 0, FALSE),
    (2, 5, 'So jolly', 3, 0, FALSE),
    (3, 4, 'I knew it', 6, 1, TRUE),
    (5, 6, '+1', 2, 0, FALSE);
