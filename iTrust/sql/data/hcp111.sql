INSERT INTO personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
phone,
specialty,
email)
VALUES (
9800000022,
null,
'hcp',
'Duck',
'Daffy',
'200 W Adams',
'',
'Mars',
'AR',
'12345',
'893-555-4833',
'OB/GYN',
'dduck@iTrust.org'
)ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) VALUES(9800000022, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'hcp', 'first letter?', 'a')
ON DUPLICATE KEY UPDATE MID = MID;
/*password: pw*/

INSERT INTO hcpassignedhos(HCPID, HosID) VALUES(9800000022,'9191919191'), (9800000022,'8181818181')
ON DUPLICATE KEY UPDATE HCPID = HCPID;
