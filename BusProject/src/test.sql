-- 계정 정보를 가지는 테이블
DROP TABLE account;

CREATE TABLE account(
	id	VARCHAR2(30)	CONSTRAINT ACC_ID_PK PRIMARY KEY,
	pw	VARCHAR2(30)
);

-- 버스 정보를 가지는 테이블
DROP TABLE bus;

CREATE TABLE bus(
	routId		NUMBER			CONSTRAINT BUS_RTID_PK PRIMARY KEY,
	routName	VARCHAR2(30),
	routType	VARCHAR2(10)
);

insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX (bus(routId)) */ into bus values (
	SELECT routId
	FROM bus
);

-- 정류장 정보를 가지는 테이블
DROP TABLE station;

CREATE TABLE station (
	stnId		NUMBER			CONSTRAINT STATION_STNID_PK PRIMARY KEY,
	arsId		CHAR(5) 		CONSTRAINT STATION_ARSID_UNI UNIQUE,
	stnName		VARCHAR2(100)	CONSTRAINT STATION_STNNAME_NN NOT NULL
);

insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX (stations(stationId)) */ into station values (
	SELECT stationId
	FROM station
);

-- 즐겨찾기 정보를 가지는 테이블
DROP TABLE favorite;

CREATE TABLE favorite (
	userId			VARCHAR2(30) CONSTRAINT FAV_UID_FK REFERENCES account(id) ON DELETE CASCADE,
	busOrStnId		NUMBER,
	busOrStnType	CHAR(1),
	CONSTRAINT FAV_UID_BSID_PK PRIMARY KEY(userId, busOrStnId)
);

-- 최근 검색 기록을 가지는 테이블
DROP TABLE history;

CREATE TABLE history(
	userId			VARCHAR2(30) CONSTRAINT HIS_UID_FK REFERENCES account(id) ON DELETE CASCADE,
	busOrStnId		NUMBER,
	busOrStnType	CHAR(1),
	indate			DATE	DEFAULT SYSDATE,
	CONSTRAINT HIS_UID_BSID_PK PRIMARY KEY(userId, busOrStnId)
);






