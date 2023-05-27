boards 테이블 생성하는 sql문 작성 
h2 db 정보
드라이버 클래스 : org.h2.Driver
주소 : jdbc:h2:tcp://localhost/~/jwbookdb
username : jwbook
비번 : 1234

//테이블 삭제
drop table notices;

//테이블 생성 (aid는 자동으로 생김) (필요시 각 최대 크기 지정해줄 수 있을듯..)
CREATE TABLE notices  (
	aid INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	date TIMESTAMP,
	title VARCHAR NOT NULL,
	pwd VARCHAR NOT NULL,
	content VARCHAR NOT NULL
);

//조회
SHOW COLUMNS FROM notices;
select * from notices;

//튜플 삽입 테스트
INSERT INTO notices(name,email,date,title,pwd,content) values('홍길동', 'abc@example.com','2023-05-28','제목입니다','1234@@','내용입니다.<br>' );

