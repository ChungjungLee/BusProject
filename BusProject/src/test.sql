
--회원 정보 테이블
create table userinfo (
	id 			varchar2(20) 	primary key,	--회원 ID
	name 		varchar2(20) 	not null,  		--작성자 이름
	gender 		char(1) 		default 'M' 	--성별
);

-- 회원들의 주소 정보 테이블
create table useraddr (
	id			varchar2(20) unique, 		--회원 ID
	phone		varchar2(50) not null, 		--전화번호
	address		varchar2(100) not null,  	--주소
	constraint useraddr_fk foreign key(id) 
	references userinfo(id) on delete cascade
);

-- 샘플 데이터
insert into userinfo values ('aaa', '홍길동', 'M');
insert into userinfo values ('bbb', '김철수', 'M');
insert into userinfo values ('ccc', '이영희', 'F');
insert into useraddr values ('aaa', '010-1111-1111', '서울시 강남구 삼성동 1');
insert into useraddr values ('bbb', '010-2222-2222', '경기도 고양시 일산동구 백석동 1');

-- Outer Join (useraddr 쪽에 데이터가 없어도 결과로 나오도록)
-- 선택 정보가 없는 user가 있을 수 있으므로 useraddr 쪽 데이터를 강제로 생성해줘야 한다
-- 즉 outer join의 기준 table은 userinfo
select
    i.id, i.name, i.gender, a.phone, a.address
from userinfo i, useraddr a 
where i.id = a.id(+);


SELECT
	ID, NAME, GENDER, PHONE, ADDRESS
FROM 
	USERINFO
LEFT OUTER JOIN 
	USERADDR
ON 
	USERTINFO.ID = USERADDR.ID;
