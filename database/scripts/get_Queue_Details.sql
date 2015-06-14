USE FSSA
IF OBJECT_ID('[dbo].[get_Queue_Details]') IS not NULL
    DROP PROCEDURE [dbo].get_Queue_Details
GO

CREATE PROCEDURE get_Queue_Details
(@QueueName NVARCHAR(MAX),
 @LocationID INT)
 AS
 SET NOCOUNT ON

IF(SELECT COUNT(ID) FROM Offices WHERE id = @LocationID)= 0
BEGIN
	PRINT 'The locationID ' + CONVERT(VARCHAR(30), @LocationID) + ' does not exist'
	RETURN 1	
END

IF(SELECT COUNT(ID) FROM [Queue] WHERE Name = @QueueName) = 0
BEGIN
	PRINT 'The Queue Name ' + CONVERT(VARCHAR(30), @QueueName) + ' does not exist'
	RETURN 1	
END

SELECT PersonID,(p.first_name + ' '+ p.last_name) AS Name, r.Reason
FROM Vists v
	JOIN [Queue] q ON q.id = CurrentQueue
	JOIN [People] p on p.id = PersonID
	JOIN [vistreasons] r on r.id = v.Reason
WHERE q.Name = @QueueName AND LocationID = @LocationID;

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