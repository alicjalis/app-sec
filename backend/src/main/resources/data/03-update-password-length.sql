--liquibase formatted sql
--changeset mlewandowski:3

ALTER TABLE users
    ALTER COLUMN password TYPE VARCHAR(75);