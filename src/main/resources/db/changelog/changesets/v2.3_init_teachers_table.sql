-- liquibase formatted sql

-- changeset vorstu:2.3
CREATE SEQUENCE teacher_seq START WITH 1 INCREMENT BY 50;
CREATE TABLE teachers(
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE CONSTRAINT fk_teachers_users REFERENCES users (id),
    fio VARCHAR(255) NOT NULL
);

CREATE TABLE teachers_groups(
    teacher_id BIGINT NOT NULL CONSTRAINT fk_tg_teachers REFERENCES teachers (id),
    group_id BIGINT NOT NULL CONSTRAINT fk_tg_groups REFERENCES groups (id)
);


-- rollback ALTER TABLE teachers_groups DROP CONSTRAINT fk_tg_teachers;
-- rollback ALTER TABLE teachers_groups DROP CONSTRAINT fk_tg_groups;
-- rollback DROP TABLE teachers_groups;
-- rollback ALTER TABLE teachers DROP CONSTRAINT fk_teachers_users;
-- rollback DROP TABLE teachers;
-- rollback DROP SEQUENCE teacher_seq;