-- https://stackoverflow.com/a/24077148
Create FUNCTION [dbo].[Split](@String VARCHAR(MAX), @Delimiter CHAR(1))       
RETURNS @temptable TABLE (items VARCHAR(MAX), rownum int)       
 AS       
 BEGIN       
 DECLARE @idx INT       
 DECLARE @slice VARCHAR(8000)       

 SELECT @idx = 1       
     IF len(@String)<1 OR @String IS NULL  RETURN       

 WHILE @idx!= 0       
 BEGIN       
     SET @idx = charindex(@Delimiter,@String)       
     IF @idx!=0       
         SET @slice = LEFT(@String,@idx - 1)       
     ELSE       
         SET @slice = @String       

     IF(LEN(@slice)>0)  
         INSERT INTO @temptable(Items) VALUES(@slice)       

     SET @String = RIGHT(@String,len(@String) - @idx)       
     IF LEN(@String) = 0 BREAK 
     
     --Added Rownumber here:
     ;With CTE as
     (
         Select Row_number() over (Order By (select 1)) as rownum
         ,Items
         FROM  @temptable
      )      
     Update T
     set T.rownum = CTE.rownum
     From @temptable T
     JOIN CTE ON T.Items = CTE.Items
 END   
 RETURN       
 END
 