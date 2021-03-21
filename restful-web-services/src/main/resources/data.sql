insert into user (id, name, birth_date) values (101, 'Art', '1983-02-07');
insert into user (id, name, birth_date) values (102, 'Kate', '1983-02-13');
insert into user (id, name, birth_date) values (103, 'Arina', '2010-07-23');
insert into user (id, name, birth_date) values (104, 'Nazar', '2016-01-01');
insert into user (id, name, birth_date) values (105, 'Kuzya', sysdate);

insert into post (id, title, content, user_id) values (201, 'Best course', 'Post 201 content', 101);
insert into post (id, title, content, user_id) values (202, 'Title 202', 'Post 202 content', 101);
insert into post (id, title, content, user_id) values (203, 'Title 203', 'Post 203 content', 102);