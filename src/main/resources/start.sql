
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO roles VALUES (1,'ROLE_TEACHER'),(2,'ROLE_PARENT'),(3,'ROLE_SCHOOLBOY'),(4,'ROLE_ADMIN');

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  firstName varchar(45) NOT NULL,
  lastName varchar(45) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO users VALUES (1,'Moroz','Pavel','morozPavel@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K'),(2,'Petrova','Maria','petrovaMaria@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K'),(3,'Gausa','Irina','gausaIrina@mail.ru',''),(4,'Moroz','Vala','morozGala@mail.ru',''),(8,'Ednach','Dmitry','ednachDmitry@mail.ru',''),(9,'Ivanov','Roma','ivanovRoma@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K');


DROP TABLE IF EXISTS schoolboy;
CREATE TABLE schoolboy (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  user_id bigint(8) NOT NULL,
  classroom_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO schoolboy VALUES (1,1,1),(2,8,2);

DROP TABLE IF EXISTS teacher;
CREATE TABLE teacher (
  id bigint(8) NOT NULL AUTO_INCREMENT,
user_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
) ;
INSERT INTO teacher VALUES (1,2),(2,3);

DROP TABLE IF EXISTS sin;
CREATE TABLE sin (
  id bigint(8)  NOT NULL AUTO_INCREMENT,
  typeSin varchar(45) NOT NULL,
  points bigint(8) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  schoolboy_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO sin VALUES (1,'fight',5,1,1);

DROP TABLE IF EXISTS classroom;
CREATE TABLE classroom (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  className varchar(45) NOT NULL,
  teacher_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO classroom VALUES (1,'10A',1),(2,'11B',2);

DROP TABLE IF EXISTS dayofweek;
CREATE TABLE dayOfWeek (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  day varchar(45) NOT NULL,
  PRIMARY KEY (id),
);
INSERT INTO dayofweek VALUES (1,'monday'),(2,'tuesday'),(3,'wednesday'),(4,'thursday'),(5,'friday');

DROP TABLE IF EXISTS disciplines;
CREATE TABLE disciplines (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  nameSubject varchar(45) NOT NULL,
  teachers_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO disciplines VALUES (1,'english',1),(2,'maths',2),(3,'biology',1),(4,'russian',1),(5,'physics',1);

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

INSERT INTO grade VALUES (1,2,1,10,2,'15.08.2018');

DROP TABLE IF EXISTS schedule;
CREATE TABLE schedule (
  id bigint(8) NOT NULL AUTO_INCREMENT,
  classroom_id bigint(8) NOT NULL,
  discipline_id bigint(8) NOT NULL,
  dayOfWeek_id bigint(8) NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO schedule VALUES (1,1,1,1),(2,2,3,1),(3,1,5,2),(4,2,4,2);

CREATE TABLE IF NOT EXISTS USER_ROLE
(
    USER_ID BIGINT,
    ROLE_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USERS (ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);
INSERT INTO user_role VALUES (9,1),(1,3),(2,4),(3,1);
