-- liquibase formatted sql

-- changeset vorstu:2.2
ALTER TABLE students ADD user_id BIGINT;
ALTER TABLE students ADD group_id BIGINT;

INSERT INTO groups (id, name)
SELECT nextval('groups_seq'), group_of_students
FROM students
WHERE group_of_students IS NOT NULL
GROUP BY group_of_students;

UPDATE students
SET group_id = groups.id
FROM groups
WHERE students.group_of_students = groups.name;

ALTER TABLE students ADD CONSTRAINT fk_students_users FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE students ADD CONSTRAINT fk_students_groups FOREIGN KEY (group_id) REFERENCES groups (id);

ALTER TABLE students DROP COLUMN group_of_students;

-- rollback ALTER TABLE students ADD group_of_students VARCHAR(255);
-- rollback UPDATE students s SET group_of_students = g.name FROM groups g WHERE s.group_id = g.id;
-- rollback ALTER TABLE students DROP CONSTRAINT fk_students_groups;
-- rollback ALTER TABLE students DROP CONSTRAINT fk_students_users;
-- rollback ALTER TABLE students DROP COLUMN group_id;
-- rollback ALTER TABLE students DROP COLUMN user_id;