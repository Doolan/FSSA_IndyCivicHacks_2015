USE [FSSA]

IF OBJECT_ID('[dbo].[update_reason]') IS not NULL
    DROP PROCEDURE [dbo].update_reason
GO

CREATE PROCEDURE update_reason
(@Reason NVARCHAR(MAX),
 @locationID INT)
 AS
 SET NOCOUNT ON
 
IF(SELECT COUNT(ID) FROM vistreasons WHERE Reason = @Reason) = 0
BEGIN
	INSERT INTO vistreasons (Reason) VALUES (@Reason)
END

--get reasonID
DECLARE @reasonId INT, @visitID INT
SELECT @reasonId = ID FROM vistreasons WHERE Reason = @Reason
SELECT TOP 1 @visitID = ID 
FROM Vists 
WHERE locationID = @locationID AND Reason = 10 AND CurrentQueue = 5

UPDATE Vists SET Reason = @reasonId
WHERE id = @visitID

 GO