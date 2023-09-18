create sequence seq_blogger increment by 1 start with 1;

create table blogger
(
    id      number(11) not null primary key,
    email   varchar2(30) not null unique,
    pw      varchar2(20) not null,
    name    varchar2(30) not null,
    phone   varchar2(50),
    address varchar2(100)
);
drop sequence seq_blogger;

drop table blogger;

insert into blogger values(seq_blogger.nextval, '201912012@office.induk.ac.kr', 'cometrue', '이민욱', '01011111111', '주소');

select * from blogger;