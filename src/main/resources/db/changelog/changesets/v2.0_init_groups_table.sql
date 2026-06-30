-- liquibase formatted sql

-- changeset vorstu:2.0
CREATE SEQUENCE groups_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE groups(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- rollback DROP TABLE groups;
-- rollback DROP SEQUENCE groups_seq;