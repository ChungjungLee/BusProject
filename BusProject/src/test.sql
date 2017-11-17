-- 버스 정보를 가지는 테이블
DROP TABLE bus;

CREATE TABLE bus(
	routId			NUMBER			CONSTRAINT BUS_RTID_PK PRIMARY KEY,
	routName		VARCHAR2(30)	CONSTRAINT BUS_RTNAME_UNI UNIQUE,
	routType		VARCHAR2(10),
	stnFirst		VARCHAR2(100)	CONSTRAINT BUS_STNFIRST_NN NOT NULL,
	stnLast			VARCHAR2(100)	CONSTRAINT BUS_STNLAST_NN NOT NULL,
	timeFirst		CHAR(4),
	timeLast		CHAR(4),
	satTimeFirst	CHAR(4),
	satTimeLast		CHAR(4),
	holTimeFirst	CHAR(4),
	holTimeLast		CHAR(4),
	norTerms		VARCHAR2(20),
	satTerms		VARCHAR2(20),
	holTerms		VARCHAR2(20),
	companyNm		VARCHAR2(30),
	telNo			VARCHAR2(30),
	faxNo			VARCHAR2(30),
	email			VARCHAR2(100)
);

insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX (bus(routId)) */ into bus values (
	SELECT routId
	FROM bus
);

-- 정류장 정보를 가지는 테이블
DROP TABLE station;

CREATE TABLE station (
	stationId	NUMBER	CONSTRAINT STATION_STNID_PK PRIMARY KEY,
	arsId		CHAR(5) CONSTRAINT STATION_ARSID_UNI UNIQUE,
	stnName		VARCHAR2(50) CONSTRAINT STATION_STNNAME_NN NOT NULL
);

insert /*+ IGNORE_ROW_ON_DUPKEY_INDEX (stations(stationId)) */ into station values (
	SELECT stationId
	FROM station
);

-- 즐겨찾기 정보를 가지는 테이블
DROP TABLE favorite;

CREATE TABLE favorite (
	favorId	NUMBER,
	routId		NUMBER,
	stationId	NUMBER
);

-- 최근 검색 기록을 가지는 테이블
DROP TABLE history;

CREATE TABLE history(
	
	keyword	VARCHAR2(50),
	indate	DATE
);

