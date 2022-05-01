USE [Farmacia]
GO

/****** Object:  StoredProcedure [dbo].[Far_PRM_Consultar]    Script Date: 02/15/2015 18:07:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[Far_PRM_Consultar] 
	
    @Paciente varchar,
    @IdModulo bigint,
    @StartDate date,
    @EndDate date 
AS
BEGIN
	
	SET NOCOUNT ON;
      SELECT  *   
FROM  Far_PRM  INNER JOIN HVitarteBD.dbo.PACIENTE Paciente ON Paciente.PACIENTE = Far_PRM.Paciente
     WHERE Far_PRM.IdModulo = @IdModulo 
    AND ((Paciente.PACIENTE LIKE '%' + @Paciente + '%') OR (@Paciente = 0))
    AND ((Far_PRM.Fecha BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))   

END

GO

