USE FSSA
IF OBJECT_ID('[dbo].[get_PersonInQueue_Details]') IS not NULL
    DROP PROCEDURE [dbo].get_PersonInQueue_Details
GO

CREATE PROCEDURE get_PersonInQueue_Details
(@QueueName NVARCHAR(MAX),
 @LocationID INT,
 @PersonID INT)
 AS
 SET NOCOUNT ON

IF(SELECT COUNT(ID) FROM Offices WHERE id = @LocationID)= 0
BEGIN
	PRINT 'The locationID ' + CONVERT(VARCHAR(30), @LocationID) + ' does not exist'
	RETURN 1	
END

DECLARE @QueueID INT;
SELECT @QueueID = id FROM [Queue] WHERE Name = 'QueueName';

IF(@QueueID) = NULL 
BEGIN
	PRINT 'The Queue Name ' + CONVERT(VARCHAR(30), @QueueName) + ' does not exist'
	RETURN 1
END

IF(SELECT COUNT(ID) FROM [Vists] WHERE PersonID = @PersonID AND CurrentQueue = @QueueID AND @LocationID =  LocationID) = 0
BEGIN
	PRINT 'The Person ' + CONVERT(VARCHAR(30), @PersonID) + ' is not in the Queue ' +CONVERT(VARCHAR(30), @QueueName)
	RETURN 1	
END

SELECT (first_name + ' ' + last_name) AS name, DOB, [address], zipcode, mednum, r.Reason
FROM People p
	JOIN Vists v on p.id = v.PersonID
	JOIN [vistreasons] r on r.id = v.Reason
WHERE p.id = @PersonID;

DECLARE @Status SMALLINT
SET @Status = @@ERROR
IF @Status <> 0 
	BEGIN
	-- Return error code to the calling program to indicate failure. 
	PRINT 'An error occurred'
	RETURN(@Status)
END
ELSE 
	BEGIN
	RETURN 0
END
GO