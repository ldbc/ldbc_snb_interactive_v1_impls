CREATE OR ALTER FUNCTION dbo.fn_DistinctList
(
  @String NVARCHAR(MAX),
  @Delimiter char(1)
)
RETURNS NVARCHAR(MAX)
WITH SCHEMABINDING
AS
BEGIN
    DECLARE @Result NVARCHAR(MAX);
    WITH MY_CTE AS (
        SELECT Distinct(value) FROM STRING_SPLIT(@String, @Delimiter)
    )
    SELECT @Result = STRING_AGG(value, @Delimiter) FROM MY_CTE
    RETURN @Result
END
