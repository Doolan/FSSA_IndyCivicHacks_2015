USE FSSA
IF OBJECT_ID('[dbo].[get_Queue_Status]') IS not NULL
    DROP PROCEDURE [dbo].get_Queue_Status
GO

CREATE PROCEDURE get_Queue_Status
(@LocationID int)
 AS
 SET NOCOUNT ON
 
SELECT Name, ISNULL(size, 0) AS size FROM [Queue]
	FULL JOIN (SELECT CurrentQueue, COUNT(PersonID) AS size FROM Vists 
		WHERE LocationID = @LocationID
		GROUP BY CurrentQueue) AS details
	ON CurrentQueue = id
 
 GO