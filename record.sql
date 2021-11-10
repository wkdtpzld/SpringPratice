drop table member

create table member(
   id number(4) primary key,
   name varchar2(100),
   password varchar2(100),
   email varchar2(100)
);

CREATE SEQUENCE AAA START WITH 1 INCREMENT BY 1 MAXVALUE 100 CYCLE NOCACHE;

SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME = 'AAA';

ALTER SEQUENCE AAA INCREMENT BY -6;


insert into member(id, name, password, email) values(AAA.NEXTVAL,'asd','1','1')

Select * from member

(select nvl(max(id),0)+1 from member)