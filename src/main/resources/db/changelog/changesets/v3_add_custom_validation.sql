-- liquibase formatted sql

-- changeset vorstu:3

ALTER TABLE users
ADD CONSTRAINT check_email_format
CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');

ALTER TABLE students
ADD CONSTRAINT check_phone_format
CHECK (phone_number ~ '^\+?[0-9]{10,15}$');

ALTER TABLE students
ADD CONSTRAINT check_student_fio_format
CHECK (trim(fio) ~ '^\S+\s+\S+');

ALTER TABLE teachers
ADD CONSTRAINT check_teacher_fio_format
CHECK (trim(fio) ~ '^\S+\s+\S+');

-- rollback ALTER TABLE teachers DROP CONSTRAINT check_teacher_fio_format;
-- rollback ALTER TABLE students DROP CONSTRAINT check_student_fio_format;
-- rollback ALTER TABLE students DROP CONSTRAINT check_phone_format;
-- rollback ALTER TABLE users DROP CONSTRAINT check_email_format;