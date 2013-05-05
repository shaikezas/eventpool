DROP TABLE IF EXISTS EVENT ;
CREATE TABLE `EVENT` (
   ID bigint(20) NOT NULL AUTO_INCREMENT,
   TITLE varchar(256) NOT NULL,
   DESCRIPTION text,
   PAY_TYPE char(10) NOT NULL,
   START_DATE timestamp NOT NULL,
   END_DATE timestamp NOT NULL,
   SUBCATEGORY_ID bigint(20) NOT NULL,
   ORGANIZER_NAME varchar(128) NOT NULL,
   ORGANIZER_DESC varchar(1000) NULL,
   contact_details varchar(1000) NULL,
   VENUE_NAME varchar(256) NOT NULL,
   VENUE_ADDRESS_ID bigint(20)  NOT NULL,
   ATTEND_TYPE char(10) NULL,
   OCCURING_TYPE char(10) NULL,
   TERMS_CONDITIONS text,
   ACTIVE char(1) NULL,
   STATUS char(10) NULL,
   KEY_WORDS varchar(256) NULL,
   WEBINAR char(1) NULL,
   PUBLISH_DATE timestamp NOT NULL,
   SHOW_EVENT char(1) NULL,
   PRIVACY_TYPE char(10) NULL,
   CREATEDBY  bigint(20) NOT NULL,
   CREATED_DATE timestamp NOT NULL,
   MODIFIED_DATE timestamp NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `EVENT_MEDIA`;
CREATE TABLE `EVENT_MEDIA` (
   EVENT_ID bigint(20) NOT NULL ,
   IMAGE_URL varchar(256) DEFAULT NULL,
   company_logo_url varchar(256),
   VIDEO_URL varchar(256) DEFAULT NULL,
   BANNER_URL varchar(256) NOT NULL,
   FACEBOOK_URL varchar(256) NOT NULL,
   OTHER_URL1 varchar(256) NOT NULL,
   OTHER_URL2 varchar(256) NOT NULL,
  PRIMARY KEY (EVENT_ID)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS ADDRESS;
CREATE TABLE ADDRESS (
   ID bigint(20) NOT NULL AUTO_INCREMENT,
   ADDR_LINE1 varchar(256) DEFAULT NULL,
   ADDR_LINE2 varchar(256) DEFAULT NULL,
   CITY_ID int NOT NULL,
   ZIP varchar(7) NOT NULL,
   FAX varchar(10) NULL,
   MAP_URL varchar(256) NULL,
   MOBILENUMBER varchar(10) NULL,
   PHONENUMBER varchar(10) NULL,
   TYPE varchar(10) NULL,
   PRIMARY KEY (ID)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS TICKET;
CREATE TABLE TICKET(
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  EVENT_ID bigint(20) NOT NULL,
  NAME varchar(128) NOT NULL,
  DESCRIPTION TINYTEXT  NULL,
  SALE_STARTDATE timestamp NOT NULL,
  SALE_ENDDATE timestamp NOT NULL,
  PRICE decimal(10,2) NOT NULL,
  QUANTITY int(10) NOT NULL,
  MIN_QTY int(10) NOT NULL,
  MAX_QTY int(10) NOT NULL,
  ACTIVE char(1) NULL,
  CREATEDBY  bigint(20) NOT NULL,
  CREATED_DATE timestamp NOT NULL,
  MODIFIED_DATE timestamp NOT NULL,
  PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS CATEGORY;
CREATE TABLE CATEGORY(
 ID  bigint(20) NOT NULL AUTO_INCREMENT,
 NAME varchar(128) NOT NULL,
 DESCRIPTION varchar(256) NULL,
 CODE varchar(10) NULL,
 PARENT_CATEGORYID bigint(20) NULL,
 ACTIVE char(1) NOT NULL DEFAULT 'Y',

 PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS `ORDER`;
CREATE TABLE `ORDER`(
 ID bigint(20) NOT NULL AUTO_INCREMENT,
 BUYER_FNAME varchar(128) NOT NULL,
 BUYER_LNAME varchar(128) NULL,
 BUYER_EMAIL varchar(255) NOT NULL,
 ADDRESS_ID bigint(20) NOT NULL,

 GROSS_AMOUNT DECIMAL(10,2) NULL,
 NET_AMOUNT DECIMAL(10,2) NULL,
 DISCOUNT_AMOUNT DECIMAL(10,2) NULL,
 DISCOUNT_COUPON VARCHAR(20) NULL,
 PAYMENT_CURRENCY VARCHAR(5) NOT NULL,

 CREATEDBY  bigint(20) NOT NULL,
 CREATED_DATE timestamp NOT NULL,
 MODIFIED_DATE timestamp NOT NULL,
 PRIMARY KEY(ID)
);


DROP TABLE IF EXISTS SUBORDER;
CREATE TABLE SUBORDER(
 ID bigint(20) NOT NULL AUTO_INCREMENT,
 TICKET_SNAPSHOT_ID bigint(20) NOT NULL,
 QUANTITY int(5) NOT NULL,
 ORDER_ID bigint(20) NOT NULL,
 SUB_CATEGORY_ID bigint(20) NOT NULL,
 TICKET_PRICE decimal(10,2) NOT NULL,
 
 GROSS_AMOUNT DECIMAL(10,2) NULL,
 NET_AMOUNT DECIMAL(10,2) NULL,
 DISCOUNT_AMOUNT DECIMAL(10,2) NULL,
 DISCOUNT_COUPON VARCHAR(20) NULL,

 STATUS char(5) NULL,
 ORGANIZER_NAME VARCHAR(128) NULL,

  CREATEDBY  bigint(20) NOT NULL,
  CREATED_DATE timestamp NOT NULL,
  MODIFIED_DATE timestamp NOT NULL,

  PRIMARY KEY(ID)
);


DROP TABLE IF EXISTS REGISTRATION;
CREATE TABLE REGISTRATION(
 ID bigint(20) NOT NULL AUTO_INCREMENT,
 SUBORDER_ID bigint(20) NOT NULL,
 NAME varchar(256) NOT NULL,
 EMAIL varchar(256) NOT NULL,
 COMPANY varchar(128) NULL,
 PHONE varchar(12) NULL,
 CREATEDBY  bigint(20) NOT NULL,
 CREATED_DATE timestamp NOT NULL,
 MODIFIED_DATE timestamp NOT NULL,

 PRIMARY KEY(ID)
);



DROP TABLE IF EXISTS COUNTRY;
CREATE TABLE COUNTRY(
 ID smallint NOT NULL AUTO_INCREMENT,
 `NAME` varchar(50) NOT NULL,
 CODE varchar(5) NOT NULL,
 ISO2 varchar(5) NULL,
 ISO3 varchar(5) NULL,
 ISON varchar(5) NULL,
 INTERNET_CODE varchar(5) NULL,
 CAPITAL varchar(50) NOT NULL,
 MAP_REFERENCE varchar(50) NULL,
 NATIONALITY_SINGULAR varchar(30) NULL,
 NATIONALITY_PLURAL varchar(30) NULL,
 CURRENCY varchar(30) NULL,
 CURRENCY_CODE varchar(5) NULL,
 POPULATION bigint(10) NULL,
 TITLE varchar(50) NULL,
 `COMMENT` varchar(256) NULL,
 PRIMARY KEY(ID) 
 ) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

 DROP TABLE IF EXISTS CITY;
CREATE TABLE CITY(
 ID int NOT NULL AUTO_INCREMENT,
 COUNTRY_ID smallint NOT NULL,
 STATE_ID smallint NULL,
 `NAME` varchar(50) NOT NULL,
 LATITUDE varchar(10) NULL,
 LONGITUDE varchar(10) NULL,
 TIME_ZONE varchar(10) NULL,
 DMA_ID smallint NULL,
 CODE varchar(5) NULL,
 PRIMARY KEY(ID) 
 ) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS STATE;
CREATE TABLE STATE(
 ID smallint NOT NULL AUTO_INCREMENT,
 COUNTRY_ID smallint NOT NULL,
 `NAME` varchar(50) NOT NULL,
 CODE varchar(5) NULL,
 ADM1_CODE varchar(10) NULL,
 PRIMARY KEY(ID) 
 ) ENGINE=InnoDB  DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS USER;
CREATE TABLE USER(
 ID bigint(20) NOT NULL AUTO_INCREMENT,
 FNAME varchar(256) NULL,
 LNAME varchar(256) NULL,
 EMAIL varchar(256) NOT NULL,
 PASSWORD varchar(20) NULL,
 COMPANY varchar(128) NULL,
 PHONE varchar(12) NULL,
 MOBILE varchar(12) NULL,
 ALT_EMAIL varchar(256) NULL,
 HOME_ADDRESS bigint(20) NULL,
 OFFICE_ADDRESS bigint(20) NULL,
 SHIPPING_ADDRESS bigint(20) NULL,
 GENDER char(1) NULL,
 DOB timestamp NULL,
 COMPANY_URL varchar(256) NULL,
 CREATED_DATE timestamp NOT NULL,
 MODIFIED_DATE timestamp NOT NULL,
 PRIMARY KEY (`ID`),
 UNIQUE KEY `UNIQ_USER_EMAIL` (EMAIL)
);

DROP TABLE IF EXISTS TICKET_INVENTORY;
CREATE TABLE `TICKET_INVENTORY` (
  `TICKET_ID` bigint(20) NOT NULL,
  `TICKET_QTY` int(11) DEFAULT '0',
  `BLOCKED_QTY` int(11) DEFAULT '0',
  `VERSION` int(11) DEFAULT '0',
  PRIMARY KEY (`TICKET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS TICKET_REGISTER;
CREATE TABLE `TICKET_REGISTER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TICKET_ID` bigint(20) NOT NULL,
  `TICKET_QTY` int(11) DEFAULT '0',
  `TICKET_PRICE` decimal(10,2) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS TICKET_SNAPSHOT;
CREATE TABLE TICKET_SNAPSHOT(
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  EVENT_ID bigint(20) NOT NULL,
  NAME varchar(128) NOT NULL,
  DESCRIPTION TINYTEXT  NULL,
  SALE_STARTDATE timestamp NOT NULL,
  SALE_ENDDATE timestamp NOT NULL,
  PRICE decimal(10,2) NOT NULL,
  QUANTITY int(10) NOT NULL,
  MIN_QTY int(10) NOT NULL,
  MAX_QTY int(10) NOT NULL,
  ACTIVE char(1) NULL,
  CREATEDBY  bigint(20) NOT NULL,
  CREATED_DATE timestamp NOT NULL,
  MODIFIED_DATE timestamp NOT NULL,
  PRIMARY KEY (ID)
);
