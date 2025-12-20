--liquibase formatted sql
--changeset mlewandowski:5

ALTER TABLE confirmation_tokens
    ADD COLUMN IF NOT EXISTS request_type VARCHAR(16) NOT NULL DEFAULT 'EMAIL';

