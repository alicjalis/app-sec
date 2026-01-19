--liquibase formatted sql
--changeset mlewandowski:9

INSERT INTO users (username, password, user_type, email, enabled)
VALUES
    ('admin', '$2a$10$zFtoy3e836lBDRlx55s0rup9mpj8CnAsnrz6/8bwSiE/uILXhKiLa', 'ADMIN', 'admin@mail.com', true), --password: SuperSecretAdmin!
    ('TheOneWhoKnocks', '$2a$10$gDK2FaxjANVfF.WN991k5.by4sKE3b/HJcIrTvZSM1Jy4AZGa77/y', 'USER', 'user1@mail.com', true), --password: SuperSecretUser1!
    ('JohnUser', '$2a$10$hqDhRHN1Kn6ad7.n0jrLJ.VweyWmetIka46ukMxx/jRfXPHM9aXMa', 'USER', 'user2@mail.com', true), --password: SuperSecretUser2!
    ('notAnAdmin', '$2a$10$FRv/wwcrkeOelCk666IuleDU2H7g5wd/5lNAFvgCSyFg5r/1LrCc.', 'USER', 'user3@mail.com', true), --password: SuperSecretUser3!
    ('TeslaDziekana', '$2a$10$SBmLQz/Vj/3uVB2USrX7BemXuMiY2Ei8gAeQ6FpG49xIF4yQYP8.q', 'USER', 'user4@mail.com', true); --password: SuperSecretUser4!

INSERT INTO posts (user_id, title, content_uri, is_deleted)
VALUES
    (1, 'Dog????', '47c0f0e7-5c35-4acb-9e1b-e45e5dd7e1b3_dog.mp4', FALSE),
    (2, 'Hors', '428d6c1a-0fb7-4018-bad8-393b5e60cc89_hors.png', FALSE),
    (3, 'Facts', 'f8a94a0b-8eb4-43c4-9999-7f63ee75568f_shrek.jpg', FALSE),
    (4, 'Hi', '6761c953-f102-4edc-a6d4-1b30d78124b3_dog.jpg',  FALSE),
    (5, 'Facts', '5c4b69c6-22bb-4df6-a936-3b96b1e5469e_santa.jpg', FALSE),
    (5, 'So nice of him', '1edf9577-4110-4bfd-9209-01bab85a09eb_presents.jpg', FALSE),
    (5, 'Me', 'eae4ce50-b53f-4c5d-831f-54f576ec9087_deadlock.jpg', FALSE),
    (2, 'Wish', '03a3c760-2891-41bc-963d-bc5466a19b92_cavalry.mp4', FALSE),
    (2, 'So nice of him', '6efed724-e418-4b86-b7db-13c280ad0747_horsdrill.mp4', FALSE),
    (1, 'O_o', '7a3cea6e-7626-413e-a070-32241825c7ac_bomb.mp4', FALSE),
    (1, '🙏', '17f0a769-7580-432e-a8e8-9aca45134e6f_jesuis.mp4', FALSE),
    (1, 'Fr tho', '449be4cb-fb56-4229-ae78-450532bf3351_john.png', FALSE),
    (1, 'Monke', '4645a5e6-c052-485e-b88e-7de90999e11c_wifi.mp4', FALSE),
    (1, 'Kosovo je Serbia', '9193c847-0f54-4ccd-ab0d-4de6044d164d_balkans.mp4', FALSE),
    (1, '😭', '558760fd-a6fe-4e22-b1ea-8824981e13ee_lag.mp4', FALSE),
    (1, 'Sounds legit', '0713354d-f5a2-40f7-a292-e9b11ce682ff_lion.mp4', FALSE),
    (1, 'vehimcle', '93705380-4daf-4528-bf43-91c7927c67d3_cars.mp4', FALSE),
    (1, 'Obamna', 'a1e2bced-dd2c-4cc4-8910-55f00dff0753_biden.mp4', FALSE),
    (1, 'Do not listen', 'bab3474e-0084-4fb3-91ba-63df8d6d8274_vivaldi.mp4', FALSE),
    (1, 'Dog!!!!', 'f25624a5-ed68-468f-9d43-4eda9240116a_dogball.mp4', FALSE),
    (1, 'monke', '2a968484-2413-445b-809e-96ca0b3f0976_przestepstwa.jpg', FALSE),
    (1, 'Monday motivation', '2fff4e4a-c75c-4d62-a967-9b56ac31fa6d_crackheads.jpg', FALSE),
    (1, 'still dunno', '440f5618-fb47-4014-a8a6-7c9b28116752_making.jpg', FALSE),
    (1, 'literally bro', '98954a6b-373a-49e6-9ac4-a469130db24d_jester.png', FALSE),
    (1, '😂😂😂', '99392de0-c5d5-4e4e-9161-15f13a1dc726_fries.jpg', FALSE),
    (1, 'Fakty', 'af03fe81-e2b2-4a73-aa85-c1c1d4b78e0d_pingwiny.mp4', FALSE),
    (2, 'So smart of him', 'b39b9bf2-b508-4146-be86-bbf4fa54f0a2_geniushors.mp4', FALSE),
    (1, 'feeling wonky tbh', 'b558e087-b9d0-40d8-a955-9ef51de0c165_radiation.jpg', FALSE),
    (1, 'kraków', 'dafd7f8f-a8d0-4db7-a06f-b187695ae54c_sun.jpg', FALSE),
    (1, 'Dog....', 'e4c13b7b-b634-46b7-a17a-cdeee4732e08_fatdog.mp4', FALSE),
    (1, 'once in a lifetime', 'e51d9926-3032-497b-a577-81334ae0667a_wasps.jpg', FALSE),
    (1, 'poor boi', 'fd29ddb9-70fa-47ea-84dd-bf0083af4f7b_theories.jpg', FALSE)
    ;

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
