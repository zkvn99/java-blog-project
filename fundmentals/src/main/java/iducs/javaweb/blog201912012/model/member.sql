create table member (
                        id      number(11) not null primary key,
                        email   varchar2(30) not null unique,
                        pw      varchar2(30) not null,
                        name    varchar2(30) not null,
                        phone   varchar2(50),
                        address varchar2(100)
);
insert into member values(1, 'sw201912012@naver.com', 1234, 'minwook', '010504','451');
