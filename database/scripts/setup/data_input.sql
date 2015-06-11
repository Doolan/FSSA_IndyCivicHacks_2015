--SELECT * FROM [Queue]
--SELECT * FROM [Vists]
--SELECT * FROM [vistreasons]
--SELECT * FROM [People]
--SELECT * FROM [Offices]

--INSERT INTO [Offices] (OfficeName, PhoneNum, FaxNum, AddressL1, AddressL2, County, City, [State], zipcode, zip4)
--VALUES ('CRAWFORD Local Office','1-800-403-0864','1-800-403-0864','4030 GOODMAN RIDGE RD','STE C','CRAWFORD','MARENGO','Indiana','47140','87063')
--SELECT * FROM [Offices]



--INSERT INTO PEOPLE (DOB, first_name, last_name, [address], zipcode, ssn, mednum)
--VALUES ('2/1/2015','Judy','Porter','4 Mccormick Crossing','46324','866-12-2269','18611554')
--SELECT * FROM [People]
----ALTER TABLE PEOPLE ALTER COLUMN SSN nvarchar(11)

--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 1, 1, 1)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 2, 2, 3)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 3, 3, 3)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 7, 4,2)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 9, 5, 3)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 8, 6, 3)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 7, 5, 7, 3)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 5, 8, 1)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 4, 9, 2)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 5, 6, 10, 1)
--INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
--VALUES (CURRENT_TIMESTAMP, 7, 2, 11, 2)
SELECT * FROM [Vists]

