create sequence seq_blog increment by 1 start with 1;

create table blog
(
    id  number(11) not null primary key,
    name    varchar2(30) not null,
    email   varchar2(30) not null,
    title   varchar2(50),
    content varchar2(100)
);
drop sequence seq_blog;
drop table blog;

insert into BLOG values(seq_blog.nextval, 'sw', 'sw@induk.ac.kr', '블로그제목', '블로그내용');

update BLOG set name='강아지', email='dog@dog', title='인덕대학교', content='멍멍' where id='2';

/*
update blog set name=?, email=?, title=?, content=? where id=?;
*/

select * from blog;
