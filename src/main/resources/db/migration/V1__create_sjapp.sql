DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  firstName varchar(45) NOT NULL,
  lastName varchar(45) NOT NULL,
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
  typeSin varchar(45) NOT NULL,
  points bigint(8) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  schoolboy_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS classroom;
CREATE TABLE classroom (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  className varchar(45) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dayofweek;
CREATE TABLE dayOfWeek (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  day varchar(45) NOT NULL,
  PRIMARY KEY (id),
);

DROP TABLE IF EXISTS disciplines;
CREATE TABLE disciplines (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  nameSubject varchar(45) NOT NULL,
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
  dayOfWeek_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);
