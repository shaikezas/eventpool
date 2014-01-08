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

COMMIT;
