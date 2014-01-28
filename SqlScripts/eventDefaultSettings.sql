DELETE FROM EVENT_DEFAULT_SETTINGS;
INSERT INTO EVENT_DEFAULT_SETTINGS(NAME,SETTINGS,CREATEDBY,CREATED_DATE,MODIFIED_DATE) VALUES 
('EVENTINFO','[{"name":"Prefix (Mr., Mrs., etc.)","type":"DROPDOWN","group":"Contact","options":["Mr.","Mrs.","Miss"],"isValue":false,"isRequired":false},
{"name":"First Name","type":"TEXT","group":"Contact","isValue":true,"isRequired":true},
{"name":"Last Name","type":"TEXT","group":"Contact","isValue":true,"isRequired":true},
{"name":"Suffix","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Email Address","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Home Phone","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Cell Phone","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Home Address","type":"ADDRESS","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Shipping Address","type":"ADDRESS","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Category","type":"TEXT","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Job Title","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Company / Organ","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Work Address","type":"ADDRESS","group":"Work","isValue":false,"isRequired":false},
{"name":"Work Phone","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Website","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Blog","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Gender","type":"DROPDOWN","group":"Other","options":["Male","Female","Other"],"isValue":false,"isRequired":false},
{"name":"Birth Date","type":"TEXT","group":"Other","isValue":false,"isRequired":false},
{"name":"Age","type":"TEXT","group":"Other","isValue":false,"isRequired":false}]',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);


insert  into `USER`(`ID`,`FNAME`,`LNAME`,`USERNAME`,`ACCOUNT_ENABLED`,`ACCOUNT_EXPIRED`,`EMAIL`,`PASSWORD`,`COMPANY`,`PHONE`,`MOBILE`,`ALT_EMAIL`,
`HOME_ADDRESS`,`OFFICE_ADDRESS`,`GENDER`,`DOB`,`COMPANY_URL`,`CREATED_DATE`,`MODIFIED_DATE`) values (1,'Ezas','Shaik','ezas','Y','N','shaikezas@gmail.com','101c99749057c5ed2d415a9784117151',NULL,NULL,NULL,NULL,NULL,NULL,'MALE',NULL,NULL,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
COMMIT;
DELETE FROM MEMBERSHIP;
INSERT INTO MEMBERSHIP(ID,NAME,EXP_DAYS,TOTAL_POINTS,SETTINGS,CREATED_DATE,MODIFIED_DATE,POINTS_PER_EVENT) VALUES(1,'CLASSIC',3650,1000,"{showOrgName:false,showOrgDesc:false,contactDetails:false,showHostWebsite:false,showAttendeDetails:false}",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,0);
INSERT INTO MEMBERSHIP(ID,NAME,EXP_DAYS,TOTAL_POINTS,SETTINGS,CREATED_DATE,MODIFIED_DATE,POINTS_PER_EVENT) VALUES(2,'SILVER',3650,2000,"{showOrgName:false,showOrgDesc:false,contactDetails:false,showHostWebsite:false,showAttendeDetails:true}",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,2);
INSERT INTO MEMBERSHIP(ID,NAME,EXP_DAYS,TOTAL_POINTS,SETTINGS,CREATED_DATE,MODIFIED_DATE,POINTS_PER_EVENT) VALUES(3,'GOLD',3650,3000,"{showOrgName:false,showOrgDesc:false,contactDetails:false,showHostWebsite:true,showAttendeDetails:true}",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,4);
INSERT INTO MEMBERSHIP(ID,NAME,EXP_DAYS,TOTAL_POINTS,SETTINGS,CREATED_DATE,MODIFIED_DATE,POINTS_PER_EVENT) VALUES(4,'PLATINUM',3650,4000,"{showOrgName:true,showOrgDesc:false,contactDetails:false,showHostWebsite:true,showAttendeDetails:true}",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,6);
INSERT INTO MEMBERSHIP(ID,NAME,EXP_DAYS,TOTAL_POINTS,SETTINGS,CREATED_DATE,MODIFIED_DATE,POINTS_PER_EVENT) VALUES(5,'DIAMOND',3650,5000,"{showOrgName:true,showOrgDesc:true,contactDetails:true,showHostWebsite:true,showAttendeDetails:true}",CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,8);
COMMIT;



ALTER TABLE EVENT MODIFY ORGANIZER_NAME varchar(128) NULL;
ALTER TABLE USER ALTER COLUMN JOB_TITLE DROP DEFAULT;
ALTER TABLE EVENT MODIFY COLUMN START_DATE timestamp NOT NULL default CURRENT_TIMESTAMP;

ALTER TABLE `ORDER` ADD COLUMN TOKEN varchar(100) NULL; 
ALTER TABLE `ORDER` ADD COLUMN PAYERID varchar(100) NULL;
ALTER TABLE `ORDER` MODIFY COLUMN PAYMENT_STATUS varchar(10) NULL;
ALTER TABLE `EVENT` ADD COLUMN CURRENCY varchar(5) NULL;
ALTER TABLE `ORDER` ADD COLUMN STATUS varchar(10) NULL;
ALTER TABLE `SUBORDER` ADD COLUMN TICKET_REGISTER_ID bigint(20) NOT NULL;

ALTER TABLE `ORDER` MODIFY COLUMN PAYMENT_STATUS varchar(30) NULL;
ALTER TABLE `ORDER` MODIFY COLUMN STATUS varchar(15) NULL;
ALTER TABLE `SUBORDER` MODIFY COLUMN STATUS varchar(15) NULL;

UPDATE `ORDER` SET STATUS='INITIATED' WHERE STATUS='0';
UPDATE `ORDER` SET STATUS='INITIATED' WHERE STATUS IS NULL;
UPDATE `ORDER` SET PAYMENT_STATUS='WAITING_FOR_GATEWAYRESPONSE' WHERE STATUS IS NULL;
UPDATE `SUBORDER` SET STATUS='INITIATED' WHERE STATUS='0';
UPDATE `SUBORDER` SET STATUS='INITIATED' WHERE STATUS IS NULL;

ALTER TABLE `MEMBERSHIP` ADD COLUMN FEATURES varchar(100) NULL; 
ALTER TABLE `MEMBERSHIP` MODIFY COLUMN FEATURES varchar(100) NULL;

INSERT INTO FEATURES (ID,FEATURE) VALUES (1,'Create Event');
INSERT INTO FEATURES (ID,FEATURE) VALUES (2,'Dedicated Event Page & Url');
INSERT INTO FEATURES (ID,FEATURE) VALUES (3,'Banner Design');
INSERT INTO FEATURES (ID,FEATURE) VALUES (4,'Transaction Report');
INSERT INTO FEATURES (ID,FEATURE) VALUES (5,'Ticket Widget Code');
INSERT INTO FEATURES (ID,FEATURE) VALUES (6,'Payment Gateway Fee');
INSERT INTO FEATURES (ID,FEATURE) VALUES (7,'Priority Listing');
INSERT INTO FEATURES (ID,FEATURE) VALUES (8,'Newsletter');
INSERT INTO FEATURES (ID,FEATURE) VALUES (9,'Facebook Event Creation');
INSERT INTO FEATURES (ID,FEATURE) VALUES (10,'Facebook Group Promotion');
INSERT INTO FEATURES (ID,FEATURE) VALUES (11,'City Category Banner');
INSERT INTO FEATURES (ID,FEATURE) VALUES (12,'City Home Banner');
INSERT INTO FEATURES (ID,FEATURE) VALUES (13,'All Cities Category Banner');
INSERT INTO FEATURES (ID,FEATURE) VALUES (14,'Social Media Marketing');
INSERT INTO FEATURES (ID,FEATURE) VALUES (15,'All Cities Home Banner');
INSERT INTO FEATURES (ID,FEATURE) VALUES (16,'Banner in Newsletter');
INSERT INTO FEATURES (ID,FEATURE) VALUES (17,'Facebook Banner ( 3 Days )');
INSERT INTO FEATURES (ID,FEATURE) VALUES (18,'Facebook Promotion');
INSERT INTO FEATURES (ID,FEATURE) VALUES (19,'Customer Support');

INSERT INTO MEMBERSHIP_PLAN(ID,`MEMBERSHIP_ID`,`EVENT_URL`,`CURRENCY`,`SETTINGS`,`FEATURES`)
VALUES(1,1,'event url','USD',NULL,'1,2,3,4');
INSERT INTO MEMBERSHIP_PLAN(ID,`MEMBERSHIP_ID`,`EVENT_URL`,`CURRENCY`,`SETTINGS`,`FEATURES`)
VALUES(2,2,'event url','USD',NULL,'1,2,3,4,5,6,7,8');
INSERT INTO MEMBERSHIP_PLAN(ID,`MEMBERSHIP_ID`,`EVENT_URL`,`CURRENCY`,`SETTINGS`,`FEATURES`)
VALUES(3,3,'event url','USD',NULL,'1,2,3,4,5,6,7,8,9,10,11,12');
INSERT INTO MEMBERSHIP_PLAN(ID,`MEMBERSHIP_ID`,`EVENT_URL`,`CURRENCY`,`SETTINGS`,`FEATURES`)
VALUES(4,4,'event url','USD',NULL,'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15');
INSERT INTO MEMBERSHIP_PLAN(ID,`MEMBERSHIP_ID`,`EVENT_URL`,`CURRENCY`,`SETTINGS`,`FEATURES`)
VALUES(5,5,'event url','USD',NULL,'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19');

ALTER TABLE `MEMBERSHIP_PLAN` ADD COLUMN FEE decimal(10,2) NULL; 
COMMIT;
