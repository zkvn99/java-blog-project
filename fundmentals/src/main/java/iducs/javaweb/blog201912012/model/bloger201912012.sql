create sequence seq_blogger201912012 increment by 1 start with 1;

create table blogger201912012
(
    id      number(11) not null primary key,
    email   varchar2(30) not null unique,
    pw      varchar2(20) not null,
    name    varchar2(30) not null,
    phone   varchar2(50),
    address varchar2(100)
);
drop sequence seq_blogger201912012;

drop table blogger201912012;

insert into blogger201912012 values(seq_blogger201912012.nextval, '200412345@office.induk.ac.kr', 'cometrue', '유응구', '01011111111', '주소');

select * from blogger201912012;