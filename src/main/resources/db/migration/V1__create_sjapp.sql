DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  first_Name varchar(45) NOT NULL,
  last_Name varchar(45) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS schoolboy;
CREATE TABLE schoolboy (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  user_id bigint(8) NOT NULL,
  classroom_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher (
  id bigint(8) NOT NULL AUTO_INCREMENT,
user_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
) ;

DROP TABLE IF EXISTS sin;
CREATE TABLE sin (
  id bigint(8)  NOT NULL AUTO_INCREMENT,
  type_Sin varchar(45) NOT NULL,
  points bigint(8) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  schoolboy_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS classroom;
CREATE TABLE classroom (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  class_Name varchar(45) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS day_Of_Week;
CREATE TABLE day_Of_Week (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  day varchar(45) NOT NULL,
  PRIMARY KEY (id),
);

DROP TABLE IF EXISTS disciplines;
CREATE TABLE disciplines (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  name_Subject varchar(45) NOT NULL,
  teachers_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS grade;
CREATE TABLE grade (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  schoolboy_id bigint(8) NOT NULL,
  discipline_id bigint(8) NOT NULL,
  mark bigint(8) NOT NULL,
  teachers_id bigint(8) NOT NULL,
  date varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS schedule;
CREATE TABLE schedule (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  classroom_id bigint(8) NOT NULL,
  discipline_id bigint(8) NOT NULL,
  day_Of_Week_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);