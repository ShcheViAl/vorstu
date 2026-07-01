-- liquibase formatted sql

-- changeset vorstu:2.1
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE users(
    id BIGINT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- rollback DROP TABLE users;
-- rollback DROP SEQUENCE users_seq;