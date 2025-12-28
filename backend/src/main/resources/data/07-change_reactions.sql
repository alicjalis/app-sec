--liquibase formatted sql
--changeset mlewandowski:7

ALTER TABLE post_reactions
    DROP CONSTRAINT post_reactions_reaction_value_check;

ALTER TABLE post_reactions
    ADD CONSTRAINT post_reactions_reaction_value_check
        CHECK (reaction_value IN (-1, 0, 1));

ALTER TABLE comment_reactions
    DROP CONSTRAINT comment_reactions_reaction_value_check;

ALTER TABLE comment_reactions
    ADD CONSTRAINT comment_reactions_reaction_value_check
        CHECK (reaction_value IN (-1, 0, 1));