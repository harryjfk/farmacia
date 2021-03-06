USE [Farmacia]
GO
/****** Object:  StoredProcedure [dbo].[Far_Producto_ListarComp_PorId]    Script Date: 25/02/2015 04:47:46 p.m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[Far_Producto_ListarComp_PorId]
@IdProducto INT,
@IdSolicitud INT = 0
AS
	
	--SELECT IdProducto, Descripcion, Presentacion, Concentracion, FF.NombreFormaFarmaceutica, TP.NombreTipoProducto, UM.NombreUnidadMedida, P.Activo
	--FROM Far_Producto P
	--INNER JOIN Far_Forma_Farmaceutica FF ON P.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	--INNER JOIN Far_Tipo_Producto TP ON P.IdTipoProducto = TP.IdTipoProducto
	--INNER JOIN Far_Unidad_Medida UM ON P.IdUnidadMedida = UM.IdUnidadMedida
	--WHERE IdProducto = @IdProducto

	SELECT P.IdProducto, Descripcion, Presentacion, Concentracion, FF.NombreFormaFarmaceutica, TP.NombreTipoProducto, UM.NombreUnidadMedida, P.Activo,
		ISNULL(DS.Aprobado, '') AS 'Aprobado', 
		ISNULL(DS.MotivoAprobado, '') AS 'MotivoAprobado', 
		ISNULL(DS.CondicionAprobado, '') AS 'CondicionAprobado', 
		ISNULL(DS.CantidadAprobada, '') AS 'CantidadAprobada'
	FROM Far_Producto P
	INNER JOIN Far_Forma_Farmaceutica FF ON P.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON P.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN Far_Unidad_Medida UM ON P.IdUnidadMedida = UM.IdUnidadMedida

	LEFT JOIN  Far_Detalle_Solicitud_Producto DS 
			ON P.IdProducto = DS.IdProducto
			   AND DS.IdSolicitud = @IdSolicitud
	WHERE P.IdProducto = @IdProducto
