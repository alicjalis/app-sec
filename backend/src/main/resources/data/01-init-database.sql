--liquibase formatted sql
--changeset mlewandowski:1

CREATE TABLE IF NOT EXISTS person (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);