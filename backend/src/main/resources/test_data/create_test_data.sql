INSERT INTO users (username, password, user_type, email, enabled)
VALUES
    ('admin', '$2a$10$I4mYgLioHqBRewLntcD9F.vX3WR5gjogQZ.kgmrkg0OJGhI59O61a', 'ADMIN', 'admin@mail.com', true),
    ('user1', '$2a$10$CRJP1RRNwzU0n1rE66Olk.xo0HO./Zw3rZ7zFoRrhBiJK8ko0eJxC', 'USER', 'user1@mail.com', true),
    ('user2', '$2a$10$bjwf/P0ESEuU0BnHs4itWOgGLjni2PBjcWwA1mXQ91P/AVirnpNZW', 'USER', 'user2@mail.com', true),
    ('user3', '$2a$10$J1YFsyzWW5pyf3geb/EQbOrmlpDCc0LAY.tTEnzg5ZJkxemWFqpJO', 'USER', 'user3@mail.com', true),
    ('user4', '$2a$10$vacYTXVtZG8ZlL82RgO93OKVG3dE0TMSyb6iuTxTylkEDACvO416K', 'USER', 'user4@mail.com', true);

INSERT INTO posts (user_id, title, content_uri, is_deleted)
VALUES
    (1, 'Dog????', '47c0f0e7-5c35-4acb-9e1b-e45e5dd7e1b3_dog.mp4', FALSE),
    (2, 'Hors', '428d6c1a-0fb7-4018-bad8-393b5e60cc89_hors.png', FALSE),
    (3, 'Facts', 'f8a94a0b-8eb4-43c4-9999-7f63ee75568f_shrek.jpg', FALSE),
    (4, 'Hi', '6761c953-f102-4edc-a6d4-1b30d78124b3_dog.jpg',  FALSE),
    (5, 'Facts', '5c4b69c6-22bb-4df6-a936-3b96b1e5469e_santa.jpg', FALSE),
    (5, 'So nice of him', '1edf9577-4110-4bfd-9209-01bab85a09eb_presents.jpg', FALSE),
    (5, 'Me', 'eae4ce50-b53f-4c5d-831f-54f576ec9087_deadlock.jpg', FALSE);

INSERT INTO comments (user_id, post_id, content, is_deleted)
VALUES
    (2, 2, 'Imagine him walking in', FALSE),
    (3, 2, 'Hors', FALSE),
    (4, 3, 'So true', FALSE),
    (4, 3, 'Me', FALSE),
    (5, 4, 'Damn', FALSE),
    (1, 6, 'Merry christmas', FALSE),
    (2, 6, 'So jolly', FALSE),
    (3, 5, 'I knew it', FALSE),
    (5, 7, '+1', FALSE);

INSERT INTO post_reactions ( user_id, post_id, reaction_value)
VALUES
    (2, 4, 1),
    (2, 5, 1),
    (2, 6, 1),
    (3, 1, 1),
    (3, 5, -1),
    (4, 1, 1),
    (4, 5, -1),
    (5, 1, 1),
    (5, 6, 1);


INSERT INTO comment_reactions ( user_id, comment_id, reaction_value)
VALUES
    (2, 1, 1),
    (2, 2, 1),
    (2, 3, 1),
    (3, 4, 1),
    (3, 5, -1),
    (4, 6, 1),
    (4, 7, -1),
    (5, 8, 1),
    (5, 9, 1);
