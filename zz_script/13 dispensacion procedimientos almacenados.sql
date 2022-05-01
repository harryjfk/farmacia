USE [Farmacia]
GO

/****** Object:  StoredProcedure [dbo].[Far_Demanda_Insatisfecha_Consultar]    Script Date: 02/20/2015 17:35:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


  --Procedures Yenier

  CREATE PROCEDURE [dbo].[Far_Demanda_Insatisfecha_Consultar]
	@Cliente bigint,
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@IdAlmacen bigint
AS
BEGIN
	      SELECT  *   
FROM                
                      Far_Producto INNER JOIN
                      Far_Movimiento_Producto ON Far_Producto.IdProducto = Far_Movimiento_Producto.IdProducto INNER JOIN
                      Far_Movimiento ON Far_Movimiento_Producto.IdMovimiento = Far_Movimiento.IdMovimiento INNER JOIN
                      Far_Demanda_Insatisecha ON Far_Movimiento_Producto.IdProducto = Far_Demanda_Insatisecha.Producto       
                      WHERE
                      Far_Demanda_Insatisecha.IdModulo = @IdModulo AND
                      Far_Movimiento.IdAlmacenDestino = Far_Demanda_Insatisecha.Almacen AND
                      Far_Movimiento_Producto.Lote = Far_Demanda_Insatisecha.Lote AND
                      ((Far_Demanda_Insatisecha.cliente = @Cliente) OR (@Cliente IS NULL))
                      AND ((Far_Demanda_Insatisecha.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                      AND (Far_Demanda_Insatisecha.Almacen = @IdAlmacen OR @IdAlmacen = -1)
	END



GO

/****** Object:  StoredProcedure [dbo].[Far_InventarioRutinario_Consultar]    Script Date: 01/30/2015 12:04:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




CREATE PROCEDURE [dbo].[Far_InventarioRutinario_Consultar]
	@Almacen bigint = null,
	@IdModulo bigint,
	@StartDate date = null,
	@EndDate date = null 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;      
SELECT  *   
FROM         Far_Producto LEFT JOIN
                      Far_Forma_Farmaceutica ON Far_Producto.IdFormaFarmaceutica = Far_Forma_Farmaceutica.IdFormaFarmaceutica LEFT JOIN
                      Far_Producto_SIGA ON Far_Producto.IdProductoSiga = Far_Producto_SIGA.IdProductoSiga LEFT JOIN
                      Far_Producto_SISMED ON Far_Producto.IdProductoSismed = Far_Producto_SISMED.IdProductoSismed INNER JOIN
                      Far_Tipo_Producto ON Far_Producto.IdTipoProducto = Far_Tipo_Producto.IdTipoProducto INNER JOIN
                      Far_Unidad_Medida ON Far_Producto.IdUnidadMedida = Far_Unidad_Medida.IdUnidadMedida INNER JOIN
                      Far_Movimiento_Producto ON Far_Producto.IdProducto = Far_Movimiento_Producto.IdProducto INNER JOIN
                      Far_Movimiento ON Far_Movimiento_Producto.IdMovimiento = Far_Movimiento.IdMovimiento INNER JOIN
                      Far_Inventario_Rutinario ON Far_Producto.IdProducto = Far_Inventario_Rutinario.Producto 
                      AND Far_Inventario_Rutinario.Lote = Far_Movimiento_Producto.Lote AND Far_Inventario_Rutinario.Almacen = Far_Movimiento.IdAlmacenDestino
                      WHERE Far_Inventario_Rutinario.IdModulo = @IdModulo  
AND (Far_Inventario_Rutinario.Almacen = @Almacen OR (@Almacen is null) ) 
AND ((Far_Inventario_Rutinario.FechaCreacion BETWEEN @StartDate AND @EndDate) OR (@StartDate is null AND @EndDate is null))

  
END


GO

/****** Object:  StoredProcedure [dbo].[Far_PracticaDispensacion_Consultar]    Script Date: 02/20/2015 17:40:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[Far_PracticaDispensacion_Consultar]
	@Cliente bigint,
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@NroVenta varchar
AS
BEGIN
	
	SET NOCOUNT ON;
                SELECT  *   
FROM         Far_Buenas_Practicas_Dispensacion INNER JOIN
                      Far_Venta ON Far_Buenas_Practicas_Dispensacion.venta = Far_Venta.Id INNER JOIN
                      Far_Venta_Producto ON Far_Venta.Id = Far_Venta_Producto.IdVenta 
                      WHERE Far_Buenas_Practicas_Dispensacion.IdModulo = @IdModulo 
                      AND ((Far_Venta.Cliente = @Cliente) OR (@Cliente = 0))
                      AND ((Far_Buenas_Practicas_Dispensacion.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                      AND ((Far_Venta.NroVenta LIKE '%' + @NroVenta + '%') OR (@NroVenta IS NULL))
                     
 END



GO


/****** Object:  StoredProcedure [dbo].[Far_Consumo_Salida_Consultar]    Script Date: 01/30/2015 12:07:34 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[Far_Consumo_Salida_Consultar]
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@TipoPago bigint
	
AS
BEGIN
	
	SET NOCOUNT ON;
    SELECT   *  
FROM         Far_Venta INNER JOIN
                      --Far_Cliente ON Far_Venta.Cliente = Far_Cliente.Id INNER JOIN
                      Far_Vendedor ON Far_Venta.Vendedor = Far_Vendedor.IdCajero INNER JOIN
                      Far_Turno ON Far_Venta.Turno = Far_Turno.idTurno 
                      WHERE Far_Venta.IdModulo = @IdModulo
                      AND Far_Venta.FlgProcesoVenta = 1
                      AND ((Far_Venta.FomaDePago = @TipoPago) OR (@TipoPago = 0))
                      AND ((Far_Venta.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                     
END


GO

/****** Object:  StoredProcedure [dbo].[Far_Consumo_Paciente_Consultar]    Script Date: 02/20/2015 17:52:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[Far_Consumo_Paciente_Consultar]
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@TipoPago bigint,
	@Cliente bigint,
	@TipoProducto bigint,
	@Medico bigint,
	@Diagnostico varchar
	
AS
BEGIN
	
	SET NOCOUNT ON;
      
SELECT *    
FROM         Far_Venta INNER JOIN
                      Far_Venta_Producto ON Far_Venta.Id = Far_Venta_Producto.IdVenta INNER JOIN
                      Far_Medico ON Far_Venta.Medico = Far_Medico.Medico INNER JOIN
                      Far_Producto ON Far_Venta_Producto.IdProducto = Far_Producto.IdProducto INNER JOIN
                      Far_Tipo_Producto ON Far_Producto.IdTipoProducto = Far_Tipo_Producto.IdTipoProducto
                      WHERE Far_Venta.IdModulo = @IdModulo
                      AND Far_Venta.FlgProcesoVenta = 1
                      AND ((Far_Venta.FomaDePago = @TipoPago) OR (@TipoPago = 0))
                      AND ((Far_Venta.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                      AND ((Far_Venta.Cliente = @Cliente) or (@Cliente =0))
                      AND ((Far_Venta.Medico = @Medico) or (@Medico = 0))
                      AND ((Far_Venta.CodigoDiagnosticoCIE LIKE '%' + @Diagnostico + '%') OR @Diagnostico IS NULL)
                      AND ((Far_Tipo_Producto.IdTipoProducto = @TipoProducto) or (@TipoProducto = 0)) 
END


GO


/****** Object:  StoredProcedure [dbo].[Far_Preventas_Consultar]    Script Date: 01/30/2015 12:05:24 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO



CREATE PROCEDURE [dbo].[Far_Preventas_Consultar]
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@Vendedor bigint,
	@Turno bigint
	
AS
BEGIN
	
	SET NOCOUNT ON;
    SELECT   *  
FROM         Far_Venta INNER JOIN
                      --Far_Cliente ON Far_Venta.Cliente = Far_Cliente.Id INNER JOIN
                      Far_Vendedor ON Far_Venta.Vendedor = Far_Vendedor.IdCajero INNER JOIN
                      Far_Turno ON Far_Venta.Turno = Far_Turno.idTurno 
                      WHERE Far_Venta.IdModulo = @IdModulo
                      AND ((Far_Venta.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                      AND ((Far_Venta.Vendedor = @Vendedor) or (@Vendedor = 0))
                      AND ((Far_Venta.Turno = @Turno) or (@Turno = 0))
                     
END


GO

/****** Object:  StoredProcedure [dbo].[Far_Recetas_Consultar]    Script Date: 02/18/2015 18:17:18 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================

-- =============================================
CREATE PROCEDURE [dbo].[Far_Recetas_Consultar]
	-- Add the parameters for the stored procedure here
	@IdModulo bigint,
	@Paciente bigint,
	@FechaIni date,
	@FechaFin date
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

 SELECT *    
FROM         Far_Receta_Producto
WHERE Far_Receta_Producto.subModulo = @IdModulo
AND ((Far_Receta_Producto.Paciente = @Paciente))
AND (((Far_Receta_Producto.FechaCreacion >= @FechaIni OR Far_Receta_Producto.FechaModificacion >= @FechaIni) OR @FechaIni IS NULL)) 
AND (((Far_Receta_Producto.FechaCreacion <= @FechaFin OR Far_Receta_Producto.FechaModificacion <= @FechaFin) OR @FechaFin IS NULL)) 
END


GO

/****** Object:  StoredProcedure [dbo].[Far_Ventas_Consultar]    Script Date: 02/20/2015 17:51:02 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[Far_Ventas_Consultar] 
	@IdModulo bigint,
	@StartDate date,
	@EndDate date,
	@TipoPago bigint =0,
	@Cliente bigint = 0,
	@Vendedor bigint = 0,
	@Turno bigint = 0,
        @Servicio varchar = null
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    SELECT *    
FROM         Far_Venta INNER JOIN
                      Far_Venta_Producto ON Far_Venta.Id = Far_Venta_Producto.IdVenta INNER JOIN
                      Far_Producto ON Far_Venta_Producto.IdProducto = Far_Producto.IdProducto INNER JOIN
                      Far_Tipo_Producto ON Far_Producto.IdTipoProducto = Far_Tipo_Producto.IdTipoProducto
                      WHERE Far_Venta.IdModulo = @IdModulo
                      AND Far_Venta.FlgProcesoVenta = 1
                      AND ((Far_Venta.FomaDePago = @TipoPago) OR (@TipoPago = 0))
                      AND ((Far_Venta.FechaCreacion BETWEEN @StartDate AND @EndDate) or (@StartDate is null And @EndDate is null))
                      AND ((Far_Venta.Cliente=@Cliente) or (@Cliente =0))
                      AND ((Far_Venta.Vendedor = @Vendedor) or (@Vendedor = 0))
                      AND ((Far_Venta.Turno = @Turno) OR (@Turno = 0))
                      AND ((Far_Venta.Servicio LIKE '%' + @Servicio + '%') OR (@Servicio IS NULL))
	
END



GO




/****** Object:  StoredProcedure [dbo].[Far_Intervenciones_Consultar]    Script Date: 01/30/2015 12:08:45 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[Far_Intervenciones_Consultar] 
	@Medico bigint,
	@StartDate date,
	@Paciente bigint,
	@Programada int = null,
	@Especialidad varchar = null,
	@Atendida int = null
AS
BEGIN
	SELECT *  
FROM         Far_Intervencion INNER JOIN
                      Far_Intervencion_Producto ON Far_Intervencion.IdIntervencion = Far_Intervencion_Producto.IdIntervencion INNER JOIN
                      Far_Medico ON Far_Intervencion.Medico = Far_Medico.Medico INNER JOIN HVitarteBD.dbo.PACIENTE ON 
                      HVitarteBD.dbo.PACIENTE.PACIENTE = Far_Intervencion.Paciente
                      WHERE
                      ((Far_Intervencion.Paciente = @Paciente) OR (@Paciente = 0))
                      AND ((Far_Intervencion.Medico = @Medico) OR (@Medico = 0))
                      AND((Far_Intervencion.FechaIntervencion = @StartDate) OR (@StartDate is null))
                      AND ((Far_Intervencion.Atendida = @Atendida) OR (@Atendida is null))
                      AND ((Far_Intervencion.Programada = @Programada) OR (@Programada is null))
                      AND ((Far_Intervencion.Espeicalidad LIKE '%' + @Especialidad + '%') OR (@Especialidad is null))
END

GO



