USE [FSSA]

IF OBJECT_ID('[dbo].[get_register_login]') IS not NULL
    DROP PROCEDURE [dbo].get_register_login
GO

CREATE PROCEDURE get_register_login
(@First NVARCHAR(MAX),
 @Last NVARCHAR(MAX),
 @Zipcode INT,
 @SSN INT,
 @DOB DATE,
 @locationID INT)
 AS
 SET NOCOUNT ON
 
 --check to see if user exists
 IF (SELECT COUNT(id) FROM People 
		 WHERE first_name = @First AND last_name = @Last
			AND zipcode = @Zipcode AND ssn = @SSN) <> 1
			--AND DOB = @DOB) <> 1
BEGIN 
	--create user
	INSERT INTO People (first_name, last_name, DOB, zipcode, ssn)
	VALUES (@First, @Last, @DOB, @Zipcode, @SSN)
END

--get personid
DECLARE @Personid INT
SELECT @Personid = id FROM People 
		WHERE first_name = @First AND last_name = @Last
		AND zipcode = @Zipcode AND ssn = @SSN

--generate login entry

INSERT INTO Vists ([Timestamp], LocationID, PersonID, Reason, CurrentQueue)
VALUES (CURRENT_TIMESTAMP, @locationID, @Personid, 10 , 5)

DECLARE @vistID INT
SELECT TOP 1 @vistID = id FROM Vists WHERE PersonID = @Personid AND LocationID =  @locationID ORDER BY [Timestamp] DESC

SELECT @personid AS personID, @vistID AS vistID

 GO