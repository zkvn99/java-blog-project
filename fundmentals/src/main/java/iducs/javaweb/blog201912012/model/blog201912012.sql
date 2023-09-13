create sequence seq_blog201912012 increment by 1 start with 1;

create table blog201912012
(
    id  number(11) not null primary key,
    name    varchar2(30) not null,
    email   varchar2(30) not null,
    title   varchar2(50),
    content varchar2(100)
);
insert into BLOG201912012 values(seq_blog201912012.nextval, 'sw', 'sw@induk.ac.kr', '블로그제목', '블로그내용');

update BLOG201912012 set name='강아지', email='dog@dog', title='인덕대학교', content='멍멍' where id='2';

/*
update blog200412345 set name=?, email=?, title=?, content=? where id=?;
*/

select * from blog201912012;

drop sequence seq_blog201912012;
drop table blog201912012;
