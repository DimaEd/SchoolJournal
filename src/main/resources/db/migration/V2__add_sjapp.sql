
INSERT INTO roles VALUES (1,'ROLE_TEACHER'),(2,'ROLE_PARENT'),(3,'ROLE_SCHOOLBOY'),(4,'ROLE_ADMIN');

INSERT INTO users VALUES (1,'Moroz','Pavel','morozPavel@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K'),(2,'Petrova','Maria','petrovaMaria@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K'),(3,'Gausa','Irina','gausaIrina@mail.ru',''),(4,'Moroz','Vala','morozGala@mail.ru',''),(8,'Ednach','Dmitry','ednachDmitry@mail.ru',''),(9,'Ivanov','Roma','ivanovRoma@mail.ru','$2a$10$l/D6AGt8vYJG.cW/lIT44uy.TAYkV9UYJ8bPuGKBwuva/ERc9Ct4K');

INSERT INTO schoolboy VALUES (1,1,1),(2,8,2);

INSERT INTO teacher VALUES (1,2),(2,3);

INSERT INTO sin VALUES (1,'fight',5,1,1);

INSERT INTO classroom VALUES (1,'10A',1),(2,'11B',2);

INSERT INTO dayofweek VALUES (1,'monday'),(2,'tuesday'),(3,'wednesday'),(4,'thursday'),(5,'friday');

INSERT INTO disciplines VALUES (1,'english',1),(2,'maths',2),(3,'biology',1),(4,'russian',1),(5,'physics',1);

INSERT INTO grade VALUES (1,2,1,10,2,'15.08.2018');

INSERT INTO schedule VALUES (1,1,1,1),(2,2,3,1),(3,1,5,2),(4,2,4,2);

INSERT INTO user_role VALUES (9,1),(1,3),(2,4),(3,1);
