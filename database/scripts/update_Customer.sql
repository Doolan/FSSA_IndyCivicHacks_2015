USE FSSA
IF OBJECT_ID('[dbo].[update_Customer]') IS not NULL
    DROP PROCEDURE [dbo].update_Customer
GO

CREATE PROCEDURE update_Customer
(@CurrentQueueName NVARCHAR(MAX),
 @NewQueue INT,
 @PersonID INT,
 @LocationID INT)
 AS
 SET NOCOUNT ON

IF(SELECT COUNT(ID) FROM Offices WHERE id = @LocationID)= 0
BEGIN
	PRINT 'The locationID ' + CONVERT(VARCHAR(30), @LocationID) + ' does not exist'
	RETURN 1	
END

DECLARE @OLDQueueID INT;
SELECT @OLDQueueID = id FROM [Queue] WHERE Name = @CurrentQueueName;

IF(@OLDQueueID) = NULL 
BEGIN
	PRINT 'The Queue Name ' + CONVERT(VARCHAR(30), @CurrentQueueName) + ' does not exist'
	RETURN 1
END

IF(SELECT COUNT(ID) FROM [Vists] WHERE PersonID = @PersonID AND CurrentQueue = @OLDQueueID AND @LocationID =  LocationID) = 0
BEGIN
	PRINT @OLDQueueID
	PRINT 'The Person ' + CONVERT(VARCHAR(30), @PersonID) + ' is not in the Queue ' +CONVERT(VARCHAR(30), @CurrentQueueName)
	RETURN 1	
END

UPDATE Vists SET CurrentQueue = @NewQueue
WHERE PersonID = @PersonID AND CurrentQueue = @OLDQueueID AND @LocationID = LocationID

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