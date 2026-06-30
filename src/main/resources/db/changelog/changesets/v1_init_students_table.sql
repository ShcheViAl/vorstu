-- liquibase formatted sql

-- changeset vorstu:1
CREATE SEQUENCE students_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE students(
    id BIGINT PRIMARY KEY,
    fio VARCHAR(255),
    group_of_students VARCHAR(255),
    phone_number VARCHAR(255)
);

-- rollback DROP TABLE students;
-- rollback DROP SEQUENCE students_seq;