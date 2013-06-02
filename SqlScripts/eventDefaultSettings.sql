DELETE FROM EVENT_DEFAULT_SETTINGS;
INSERT INTO EVENT_DEFAULT_SETTINGS(NAME,SETTINGS,CREATEDBY,CREATED_DATE,MODIFIED_DATE) VALUES 
('EVENTINFO','[{"name":"Prefix (Mr., Mrs., etc.)","type":"DROPDOWN","group":"Contact","options":["Mr.","Mrs."],"isValue":false,"isRequired":false},
{"name":"First Name","type":"TEXT","group":"Contact","isValue":true,"isRequired":true},
{"name":"Last Name","type":"TEXT","group":"Contact","isValue":true,"isRequired":true},
{"name":"Suffix","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Email Address","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Home Phone","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Cell Phone","type":"TEXT","group":"Contact","isValue":false,"isRequired":false},
{"name":"Home Address","type":"TEXT","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Shipping Addres","type":"TEXT","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Category","type":"TEXT","group":"Address Information","isValue":false,"isRequired":false},
{"name":"Job Title","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Company / Organ","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Work Address","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Work Phone","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Website","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Blog","type":"TEXT","group":"Work","isValue":false,"isRequired":false},
{"name":"Gender","type":"TEXT","group":"Other","isValue":false,"isRequired":false},
{"name":"Birth Date","type":"TEXT","group":"Other","isValue":false,"isRequired":false},
{"name":"Age","type":"TEXT","group":"Other","isValue":false,"isRequired":false}]',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
COMMIT;