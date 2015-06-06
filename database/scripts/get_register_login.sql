USE [FSSA]

IF OBJECT_ID('[dbo].[get_register_login]') IS not NULL
    DROP PROCEDURE [dbo].get_register_login
GO

CREATE PROCEDURE get_register_login
(@First NVARCHAR(MAX),
 @Last NVARCHAR(MAX),
 @Zipcode INT,
 @SSN INT,
 @DOB DATE)
 AS
 SET NOCOUNT ON
 
 IF (SELECT COUNT(id) FROM People 
		 WHERE first_name = @First AND last_name = @Last
			AND zipcode = @Zipcode AND ssn = @SSN
			AND DOB = @DOB) <> 1
BEGIN 

INSERT INTO People (first_name, last_name, DOB, zipcode, ssn)
VALUES (@First, @Last, @DOB, @Zipcode, @SSN)

END

RETURN (SELECT id FROM People 
		 WHERE first_name = @First AND last_name = @Last
			AND zipcode = @Zipcode AND ssn = @SSN
			AND DOB = @DOB)


 GO