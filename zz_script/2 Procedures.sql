GO
USE Farmacia

GO
CREATE PROCEDURE Far_Periodo_Listar
AS
	SELECT IdPeriodo, Activo
	FROM Far_Periodo
GO

CREATE PROCEDURE Far_Periodo_Insertar
@IdPeriodo INT,
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Periodo(IdPeriodo, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES(@IdPeriodo, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Periodo_CambiarEstado
@IdPeriodo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Periodo
	SET Activo = CASE Activo WHEN  1 THEN 0 ELSE 1 END,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdPeriodo = @IdPeriodo
GO

CREATE PROCEDURE Far_Periodo_ListarPorAnio
@Anio INT
AS
	SELECT IdPeriodo, Activo
	FROM Far_Periodo
	WHERE SUBSTRING(CAST(IdPeriodo AS VARCHAR), 1, 4) = CAST(@Anio AS VARCHAR)
GO

CREATE PROCEDURE Far_Periodo_Existe
@IdPeriodo INT,
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 FROM Far_Periodo WHERE IdPeriodo = @IdPeriodo)
		 
 BEGIN
	SET @Existe = 1
 END

GO

CREATE FUNCTION Func_PeriodoActivo()
RETURNS TABLE
AS
RETURN
	SELECT IdPeriodo, Activo
	FROM Far_Periodo
	WHERE Activo = 1
GO

CREATE PROCEDURE Far_Periodo_Activo
AS
	SELECT IdPeriodo, Activo
	FROM Func_PeriodoActivo()
GO

CREATE PROCEDURE Far_Unidad_Listar
AS
	SELECT Unidad IdUnidad, LTRIM(RTRIM(Nombre)) NombreUnidad
	FROM HVitarteBD.dbo.PER_UNIDAD
	WHERE Activo = 1
	AND Unidad != 0
	ORDER BY NombreUnidad
GO

CREATE PROCEDURE Far_Personal_ListarPorUnidad
@Unidad CHAR(6)
AS
	SELECT Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			Personal.NRO_COLEGIATURA 'Colegiatura'
	FROM HVitarteBD.dbo.PER_PERSONAL Personal	
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	WHERE Personal.PERSONAL != '00000 '
	AND Personal.Activo = 1
	AND (Personal.Unidad = @Unidad OR @Unidad = '')
GO

CREATE PROCEDURE Far_Personal_Listar
AS
	SELECT Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			Personal.NRO_COLEGIATURA 'Colegiatura'
	FROM HVitarteBD.dbo.PER_PERSONAL Personal
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	WHERE Personal.PERSONAL != '00000 '
	AND Personal.Activo = 1
GO

CREATE PROCEDURE Far_Personal_ListarSinUsuario
AS
	SELECT Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			Personal.NRO_COLEGIATURA 'Colegiatura'
	FROM HVitarteBD.dbo.PER_PERSONAL Personal
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	LEFT JOIN Far_Usuario Usuario ON Personal.PERSONAL = Usuario.IdPersonal
	WHERE Personal.PERSONAL != '00000 '
	AND Personal.Activo = 1
	AND Usuario.IdUsuario IS NULL
GO

CREATE PROCEDURE Far_Personal_ListarMedico
AS
	SELECT  Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			Personal.NRO_COLEGIATURA 'Colegiatura'
	FROM HVitarteBD.dbo.PER_PERSONAL Personal
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	WHERE Personal.PERSONAL != '00000 '
	AND Personal.NRO_COLEGIATURA <> ''
	AND Personal.Activo = 1
	ORDER BY 2 asc, 3 asc, 4 asc
GO

CREATE PROCEDURE Far_Usuario_IniciarSesion
@NombreUsuario VARCHAR(70),
@Clave VARCHAR(30)
AS
	SELECT Far_Usuario.IdUsuario,
			Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			NombreUsuario,
			Clave,
			Far_Usuario.Correo,
			Far_Usuario.Activo,
			Far_Usuario_Perfil.IdPerfil,
			Far_Perfil.NombrePerfil
	FROM Far_Usuario
	INNER JOIN HVitarteBD.dbo.PER_PERSONAL Personal ON Far_Usuario.IdPersonal = Personal.PERSONAL
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	INNER JOIN Far_Usuario_Perfil ON Far_Usuario.IdUsuario = Far_Usuario_Perfil.IdUsuario AND Far_Usuario_Perfil.Activo = 1
	INNER JOIN Far_Perfil ON Far_Usuario_Perfil.IdPerfil = Far_Perfil.IdPerfil
	WHERE UPPER(NombreUsuario) = UPPER(@NombreUsuario)
	AND Clave = @Clave
GO

CREATE PROCEDURE Far_Usuario_Listar
AS	
	SELECT Far_Usuario.IdUsuario,
			Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			NombreUsuario,
			Clave,
			Far_Usuario.Correo,
			Far_Usuario.Activo,
			Far_Usuario_Perfil.IdPerfil,
			Far_Perfil.NombrePerfil
	FROM Far_Usuario
	INNER JOIN HVitarteBD.dbo.PER_PERSONAL Personal ON Far_Usuario.IdPersonal = Personal.PERSONAL
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	INNER JOIN Far_Usuario_Perfil ON Far_Usuario.IdUsuario = Far_Usuario_Perfil.IdUsuario AND Far_Usuario_Perfil.Activo = 1
	INNER JOIN Far_Perfil ON Far_Usuario_Perfil.IdPerfil = Far_Perfil.IdPerfil
GO

CREATE PROCEDURE Far_Usuario_ListarPorId
@IdUsuario INT
AS
	SELECT Far_Usuario.IdUsuario,
			Personal.PERSONAL 'IdPersonal',
			Personal.NOMBRES 'PersonalNombre',
			Personal.APELLIDO_PATERNO 'PersonalApePaterno',
			Personal.APELLIDO_MATERNO 'PersonalApeMaterno',
			TipoDocumento.NOMBRE 'PersonalTipoDocumento',
			Personal.NRO_DOCUMENTO 'PersonalNroDocumento',			
			Unidad.NOMBRE 'PersonalUnidad',			
			Cargo.NOMBRE 'PersonalCargo',
			NombreUsuario,
			Clave,
			Far_Usuario.Correo,
			Far_Usuario.Activo,
			Far_Usuario_Perfil.IdPerfil,
			Far_Perfil.NombrePerfil
	FROM Far_Usuario
	INNER JOIN HVitarteBD.dbo.PER_PERSONAL Personal ON Far_Usuario.IdPersonal = Personal.PERSONAL
	INNER JOIN HVitarteBD.dbo.GEN_TIPO_DOCUMENTO TipoDocumento ON Personal.TIPO_DOCUMENTO = TipoDocumento.TIPO_DOCUMENTO
	INNER JOIN HVitarteBD.dbo.PER_UNIDAD Unidad ON Personal.UNIDAD = Unidad.UNIDAD
	INNER JOIN HVitarteBD.dbo.PER_CARGO Cargo ON Personal.CARGO = Cargo.CARGO
	INNER JOIN Far_Usuario_Perfil ON Far_Usuario.IdUsuario = Far_Usuario_Perfil.IdUsuario AND Far_Usuario_Perfil.Activo = 1
	INNER JOIN Far_Perfil ON Far_Usuario_Perfil.IdPerfil = Far_Perfil.IdPerfil
	WHERE Far_Usuario.IdUsuario = 1
GO

CREATE PROCEDURE Far_Usuario_ExisteCorreo
@IdUsuario INT,
@Correo VARCHAR(70),
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 FROM Far_Usuario WHERE Correo = @Correo AND @IdUsuario != IdUsuario)
		 
 BEGIN
	SET @Existe = 1
 END

GO

CREATE PROCEDURE Far_Usuario_ExisteUsuario
@NombreUsuario VARCHAR(70),
@IdUsuario INT,
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 FROM Far_Usuario WHERE NombreUsuario = @NombreUsuario AND @IdUsuario != IdUsuario)
		 
 BEGIN
	SET @Existe = 1
 END

GO

CREATE PROCEDURE Far_Usuario_Insertar
@IdPersonal CHAR(6),
@NombreUsuario VARCHAR(70),
@Correo VARCHAR(70),
@Clave VARCHAR(30),
@Activo INT,
@UsuarioCreacion INT,
@IdUsuario INT OUTPUT
AS
	INSERT INTO Far_Usuario(IdPersonal, NombreUsuario, Clave, Correo, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES(@IdPersonal, @NombreUsuario, @Clave, @Correo, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)

	SELECT @IdUsuario = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Usuario_Modificar
@IdUsuario INT,
@IdPersonal CHAR(6),
@NombreUsuario VARCHAR(70),
@Correo VARCHAR(70),
@Clave VARCHAR(30),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Usuario
	SET IdPersonal = @IdPersonal,
		NombreUsuario = @NombreUsuario,
		Clave = @Clave,
		Correo = @Correo,
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdUsuario = @IdUsuario
GO

CREATE PROCEDURE Far_Usuario_Perfil_Listar
AS
	SELECT IdUsuarioPerfil, IdUsuario, IdPerfil, Activo
	FROM Far_Usuario_Perfil
GO

CREATE PROCEDURE Far_Usuario_Perfil_Insertar
@IdUsuario INT,
@IdPerfil INT,
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Usuario_Perfil(IdUsuario, IdPerfil, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES(@IdUsuario, @IdPerfil, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Usuario_Perfil_Modificar
@IdUsuarioPerfil INT,
@IdUsuario INT,
@IdPerfil INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Usuario_Perfil
	SET IdUsuario = @IdUsuario,
		IdPerfil = @IdPerfil,
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdUsuarioPerfil = @IdUsuarioPerfil
GO

CREATE PROCEDURE Far_Perfil_Listar
AS
	SELECT IdPerfil, NombrePerfil, Activo
	FROM Far_Perfil
GO

CREATE PROCEDURE Far_Perfil_ListarPorId
@IdPerfil INT
AS
	SELECT IdPerfil, NombrePerfil, Activo
	FROM Far_Perfil
	WHERE IdPerfil = @IdPerfil
GO

CREATE PROCEDURE Far_Perfil_EsUsado
@IdPerfil INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Perfil_Opcion WHERE IdPerfil = @IdPerfil)
		 OR EXISTS(SELECT 1 FROM Far_Usuario_Perfil WHERE IdPerfil = @IdPerfil))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Perfil_Insertar
@NombrePerfil VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Perfil(NombrePerfil, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES (@NombrePerfil, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Perfil_Modificar
@IdPerfil INT,
@NombrePerfil VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Perfil
	SET NombrePerfil = @NombrePerfil, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdPerfil = @IdPerfil
GO

CREATE PROCEDURE Far_Perfil_Eliminar
@IdPerfil INT
AS
	DELETE FROM Far_Perfil
	WHERE IdPerfil = @IdPerfil
GO

CREATE PROCEDURE Far_Modulo_Listar
AS
	SELECT IdModulo, NombreModulo, Orden, Activo
	FROM Far_Modulo	
GO

CREATE PROCEDURE Far_Modulo_ListarPorId
@IdModulo INT
AS
	SELECT IdModulo, NombreModulo, Orden, Activo
	FROM Far_Modulo
	WHERE IdModulo = @IdModulo	
GO

CREATE PROCEDURE Far_Modulo_CambiarOrden
@IdModulo INT,
@Subida BIT
AS
DECLARE @Orden INT

SELECT @Orden = Orden 
FROM Far_Modulo 
WHERE IdModulo = @IdModulo

IF @Subida = 1
BEGIN
	UPDATE Far_Modulo
	SET Orden = Orden - 1
	WHERE Orden = @Orden + 1

	UPDATE Far_Modulo
	SET Orden = Orden + 1
	WHERE IdModulo = @IdModulo
END
ELSE
BEGIN
	UPDATE Far_Modulo
	SET Orden = Orden + 1
	WHERE Orden = @Orden - 1

	UPDATE Far_Modulo
	SET Orden = Orden - 1
	WHERE IdModulo = @IdModulo	
END	
GO

CREATE PROCEDURE Far_Modulo_Modificar
@IdModulo INT,
@NombreModulo VARCHAR(70),
@Orden INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Modulo
	SET NombreModulo = @NombreModulo,
		Orden = @Orden,
		Activo = @Activo,		
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdModulo = @IdModulo
GO

CREATE PROCEDURE Far_Submodulo_Listar
AS
	SELECT IdSubmodulo, NombreSubmodulo, IdModulo, Orden, Activo
	FROM Far_Submodulo
GO

CREATE PROCEDURE Far_Submodulo_ListarPorId
@IdSubmodulo INT
AS
	SELECT IdSubmodulo, NombreSubmodulo, IdModulo, Orden, Activo
	FROM Far_Submodulo
	WHERE IdSubmodulo = @IdSubmodulo
GO

CREATE PROCEDURE Far_Submodulo_CambiarOrden
@IdSubmodulo INT,
@Subida BIT
AS
DECLARE @Orden INT
DECLARE @IdModulo INT

SELECT @Orden = Orden,
		@IdModulo = IdModulo
FROM Far_Submodulo 
WHERE IdSubmodulo = @IdSubmodulo

IF @Subida = 1
BEGIN
	UPDATE Far_Submodulo
	SET Orden = Orden - 1
	WHERE Orden = @Orden + 1
	AND IdModulo = @IdModulo

	UPDATE Far_Submodulo
	SET Orden = Orden + 1
	WHERE IdSubmodulo = @IdSubmodulo	
END
ELSE
BEGIN
	UPDATE Far_Submodulo
	SET Orden = Orden + 1
	WHERE Orden = @Orden - 1
	AND IdModulo = @IdModulo

	UPDATE Far_Submodulo
	SET Orden = Orden - 1
	WHERE IdSubmodulo = @IdSubmodulo
END
GO

CREATE PROCEDURE Far_Submodulo_Modificar
@IdSubmodulo INT,
@NombreSubmodulo VARCHAR(70),
@IdModulo INT,
@Orden INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Submodulo
	SET NombreSubmodulo = @NombreSubmodulo, 
	IdModulo = @IdModulo,
	Orden = @Orden,
	Activo = @Activo, 	
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdSubmodulo = @IdSubmodulo
GO

CREATE PROCEDURE Far_Menu_Listar
AS
	SELECT IdMenu, NombreMenu, IdSubmodulo, Orden, Activo
	FROM Far_Menu	
GO

CREATE PROCEDURE Far_Menu_ListarPorId
@IdMenu INT
AS
	SELECT IdMenu, NombreMenu, IdSubmodulo, Orden, Activo
	FROM Far_Menu
	WHERE IdMenu = @IdMenu
GO

CREATE PROCEDURE Far_Menu_CambiarOrden
@IdMenu INT,
@Subida BIT
AS
DECLARE @Orden INT
DECLARE @IdSubmodulo INT

SELECT @Orden = Orden,
		@IdSubmodulo = IdSubmodulo
FROM Far_Menu
WHERE IdMenu = @IdMenu

IF @Subida = 1
BEGIN
	UPDATE Far_Menu
	SET Orden = Orden - 1
	WHERE Orden = @Orden + 1
	AND IdSubmodulo = @IdSubmodulo

	UPDATE Far_Menu
	SET Orden = Orden + 1
	WHERE IdMenu = @IdMenu	
END
ELSE
BEGIN
	UPDATE Far_Menu
	SET Orden = Orden + 1
	WHERE Orden = @Orden - 1
	AND IdSubmodulo = @IdSubmodulo

	UPDATE Far_Menu
	SET Orden = Orden - 1
	WHERE IdMenu = @IdMenu
END
GO

CREATE PROCEDURE Far_Menu_Modificar
@IdMenu INT,
@NombreMenu VARCHAR(70),
@IdSubmodulo INT,
@Orden INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Menu
	SET NombreMenu = @NombreMenu,
	IdSubmodulo = @IdSubmodulo,
	Orden = @Orden,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdMenu = @IdMenu
GO

CREATE PROCEDURE Far_Submenu_Listar
AS
	SELECT IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo
	FROM Far_Submenu
GO

CREATE PROCEDURE Far_Submenu_ListarPorEnlace
@Enlace VARCHAR(100)
AS
	SELECT IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo
	FROM Far_Submenu
	WHERE Enlace = @Enlace
GO

CREATE PROCEDURE Far_Submenu_CambiarOrden
@IdSubmenu INT,
@Subida BIT
AS
DECLARE @Orden INT
DECLARE @IdMenu INT

SELECT @Orden = Orden,
		@IdMenu = IdMenu
FROM Far_Submenu
WHERE IdSubmenu = @IdSubmenu

IF @Subida = 1
BEGIN
	UPDATE Far_Submenu
	SET Orden = Orden - 1
	WHERE Orden = @Orden + 1
	AND IdMenu = @IdMenu

	UPDATE Far_Submenu
	SET Orden = Orden + 1
	WHERE IdSubmenu = @IdSubmenu	
END
ELSE
BEGIN
	UPDATE Far_Submenu
	SET Orden = Orden + 1
	WHERE Orden = @Orden - 1
	AND IdMenu = @IdMenu

	UPDATE Far_Submenu
	SET Orden = Orden - 1
	WHERE IdSubmenu = @IdSubmenu
END
GO

CREATE PROCEDURE Far_Submenu_Modificar
@IdSubmenu INT,
@NombreSubmenu VARCHAR(70),
@Enlace VARCHAR(100),
@IdMenu INT,
@Orden INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Submenu
	SET NombreSubmenu = @NombreSubmenu, 
	Enlace = @Enlace, 
	IdMenu = @IdMenu,
	Orden = @Orden,
	Activo = @Activo, 
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdSubmenu = @IdSubmenu
GO

CREATE PROCEDURE Far_Opcion_Listar
AS
	SELECT IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo
	FROM Far_Opcion
GO

CREATE PROCEDURE Far_Opcion_ListarPorId
@IdOpcion INT
AS
	SELECT IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo
	FROM Far_Opcion
	WHERE IdOpcion = @IdOpcion
GO

CREATE PROCEDURE Far_Opcion_ListarPorSubmenu
@IdSubmenu INT
AS
	SELECT IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo
	FROM Far_Opcion
	WHERE IdSubmenu = @IdSubmenu
GO

CREATE PROCEDURE Far_Opcion_ListarParaSession
@IdUsuario INT,
@IdSubmenu INT
AS
	SELECT IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo 
	FROM Func_Opcion_ListarParaSession(@IdUsuario)
	WHERE IdSubmenu = @IdSubmenu OR @IdSubmenu = 0
GO

CREATE FUNCTION Func_Opcion_ListarParaSession (@IdUsuario INT)
RETURNS TABLE
AS
RETURN
    SELECT OPCION_CLEAN.IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, OPCION_CLEAN.Activo
	FROM Far_Opcion OPCION_CLEAN
	INNER JOIN (SELECT OPC.IdOpcion
	FROM Far_Opcion OPC
	INNER JOIN Far_Perfil_Opcion PER_OPC ON OPC.IdOpcion = PER_OPC.IdOpcion AND PER_OPC.Activo = 1
	INNER JOIN (SELECT IdPerfil 
				FROM Far_Usuario_Perfil
				WHERE Activo = 1 
				AND IdUsuario = @IdUsuario) USU_PER ON PER_OPC.IdPerfil = USU_PER.IdPerfil
	WHERE OPC.Activo = 1
	GROUP BY OPC.IdOpcion) OPCION_GROUP ON OPCION_CLEAN.IdOpcion = OPCION_GROUP.IdOpcion
GO

CREATE PROCEDURE Far_Submenu_ListarParaSession
@IdUsuario INT
AS
	SELECT IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo
	FROM Func_Submenu_ListarParaSession(@IdUsuario)
	ORDER BY Orden
GO

CREATE FUNCTION Func_Submenu_ListarParaSession (@IdUsuario INT)
RETURNS TABLE
AS
RETURN
	SELECT SUBMENU.IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo
	FROM Far_Submenu SUBMENU
	INNER JOIN (SELECT IdSubmenu 
				FROM Func_Opcion_ListarParaSession(@IdUsuario)
				GROUP BY IdSubmenu) SUBMENU_GROUP ON SUBMENU.IdSubmenu = SUBMENU_GROUP.IdSubmenu
	WHERE SUBMENU.Activo = 1
GO

CREATE PROCEDURE Far_Menu_ListarParaSession
@IdUsuario INT
AS
	SELECT IdMenu, NombreMenu, IdSubmodulo, Orden, Activo
	FROM Func_Menu_ListarParaSession(@IdUsuario)
	ORDER BY Orden
GO

CREATE FUNCTION Func_Menu_ListarParaSession (@IdUsuario INT)
RETURNS TABLE
AS
RETURN
	SELECT MENU.IdMenu, NombreMenu, IdSubmodulo, Orden, Activo
	FROM Far_Menu MENU
	INNER JOIN (SELECT IdMenu 
				FROM Func_Submenu_ListarParaSession(@IdUsuario)
				GROUP BY IdMenu) MENU_GROUP ON MENU.IdMenu = MENU_GROUP.IdMenu
	WHERE MENU.Activo = 1
GO

CREATE PROCEDURE Far_Submodulo_ListarParaSession
@IdUsuario INT
AS
	SELECT IdSubmodulo, NombreSubmodulo, IdModulo, Orden, Activo
	FROM Func_Submodulo_ListarParaSession(@IdUsuario)
	ORDER BY Orden
GO

CREATE FUNCTION Func_Submodulo_ListarParaSession (@IdUsuario INT)
RETURNS TABLE
AS
RETURN
	SELECT SUBMODULO.IdSubmodulo, NombreSubmodulo, IdModulo, Orden, Activo
	FROM Far_Submodulo SUBMODULO
	INNER JOIN (SELECT IdSubmodulo 
				FROM Func_Menu_ListarParaSession(@IdUsuario)
				GROUP BY IdSubmodulo) SUBMODULO_GROUP ON SUBMODULO.IdSubmodulo = SUBMODULO_GROUP.IdSubmodulo
	WHERE SUBMODULO.Activo = 1
GO

CREATE PROCEDURE Far_Modulo_ListarParaSession
@IdUsuario INT
AS
	SELECT IdModulo, NombreModulo, Orden, Activo 
	FROM Func_Modulo_ListarParaSession(@IdUsuario)
	ORDER BY Orden
GO

CREATE FUNCTION Func_Modulo_ListarParaSession (@IdUsuario INT)
RETURNS TABLE
AS
RETURN
	SELECT MODULO.IdModulo, NombreModulo, Orden, Activo
	FROM Far_Modulo MODULO
	INNER JOIN (SELECT IdModulo
				FROM Func_Submodulo_ListarParaSession(@IdUsuario)
				GROUP BY IdModulo) MODULO_GROUP ON MODULO.IdModulo = MODULO_GROUP.IdModulo
	WHERE MODULO.Activo = 1
GO

CREATE PROCEDURE Far_Opcion_Modificar
@IdOpcion INT,
@AppOpcion VARCHAR(30),
@NombreOpcion VARCHAR(30),
@IdSubmenu INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Opcion
	SET AppOpcion = @AppOpcion,
	NombreOpcion = @NombreOpcion,
	IdSubmenu = @IdSubmenu,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdOpcion = @IdOpcion
GO

CREATE PROCEDURE Far_Perfil_Opcion_ListarConf
@IdSubmenu INT,
@IdPerfil INT
AS
	SELECT ISNULL(IdPerfilOpcion, 0) IdPerfilOpcion, Opcion.IdOpcion, NombreOpcion, ISNULL(Activo, 0) Activo
	FROM (SELECT IdOpcion, NombreOpcion 
	FROM Far_Opcion
	WHERE IdSubmenu = @IdSubmenu) Opcion
	LEFT JOIN (SELECT IdPerfilOpcion, IdPerfil, IdOpcion, Activo
	FROM Far_Perfil_Opcion
	WHERE IdPerfil = @IdPerfil) PerfilOpcion
	ON Opcion.IdOpcion = PerfilOpcion.IdOpcion
GO

CREATE PROCEDURE Far_Perfil_Opcion_Listar
AS
	SELECT IdPerfilOpcion, IdPerfil, IdOpcion, Activo
	FROM Far_Perfil_Opcion
GO

CREATE PROCEDURE Far_Perfil_Opcion_Insertar
@IdPerfil INT,
@IdOpcion INT,
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Perfil_Opcion(IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES (@IdPerfil, @IdOpcion, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Perfil_Opcion_Modificar
@IdPerfilOpcion INT,
@IdPerfil INT,
@IdOpcion INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Perfil_Opcion
	SET IdPerfil = @IdPerfil, 
	IdOpcion = @IdOpcion, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdPerfilOpcion = @IdPerfilOpcion	
GO

CREATE PROCEDURE Far_Tipo_Accion_Listar
AS
	SELECT IdTipoAccion, NombreTipoAccion, Activo
	FROM Far_Tipo_Accion
GO

CREATE PROCEDURE Far_Tipo_Accion_Insertar
@NombreTipoAccion VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Accion(NombreTipoAccion, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoAccion, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Accion_Modificar
@IdTipoAccion INT,
@NombreTipoAccion VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Accion
	SET NombreTipoAccion = @NombreTipoAccion, 
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdTipoAccion = @IdTipoAccion
GO

CREATE PROCEDURE Far_Tipo_Accion_EsUsado
@IdTipoAccion INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Documento WHERE IdTipoAccion = @IdTipoAccion))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Tipo_Accion_Eliminar
@IdTipoAccion INT
AS
	DELETE FROM Far_Tipo_Accion
	WHERE IdTipoAccion = @IdTipoAccion
GO

CREATE PROCEDURE Far_Tipo_Documento_Listar
AS
	SELECT IdTipoDocumento, NombreTipoDocumento, Activo
	FROM Far_Tipo_Documento
GO

CREATE PROCEDURE Far_Tipo_Documento_Insertar
@NombreTipoDocumento VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Documento(NombreTipoDocumento, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoDocumento, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Documento_Modificar
@IdTipoDocumento INT,
@NombreTipoDocumento VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Documento
	SET NombreTipoDocumento = @NombreTipoDocumento, 
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdTipoDocumento = @IdTipoDocumento
GO

CREATE PROCEDURE Far_Tipo_Documento_EsUsado
@IdTipoDocumento INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Documento WHERE IdTipoDocumento = @IdTipoDocumento))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Tipo_Documento_Eliminar
@IdTipoDocumento INT
AS
	DELETE FROM Far_Tipo_Documento
	WHERE IdTipoDocumento = @IdTipoDocumento
GO

CREATE PROCEDURE Far_Documento_Listar_Comp
@IdUsuario INT
AS
	SELECT  IdDocumento, 
			NumeracionInterna,
			IdUsuario,
			FechaDocumento, 
			FechaSalida, 
			Far_Tipo_Documento.IdTipoDocumento, 
			Far_Tipo_Documento.NombreTipoDocumento, 
			Far_Tipo_Accion.IdTipoAccion,
			Far_Tipo_Accion.NombreTipoAccion, 
			NroDocumento, 
			Asunto, 
			Destino,
			NumeracionDireccion,
			Despacho, 
			FechaDespacho,
			Extension,
			Far_Documento.Activo,
			Observacion,
			Remitente
	FROM Far_Documento 
	INNER JOIN Far_Tipo_Documento ON Far_Documento.IdTipoDocumento = Far_Tipo_Documento.IdTipoDocumento
	INNER JOIN Far_Tipo_Accion ON Far_Documento.IdTipoAccion = Far_Tipo_Accion.IdTipoAccion
	WHERE IdUsuario = @IdUsuario
GO

CREATE PROCEDURE Far_Documento_Listar
AS
	SELECT IdDocumento, NumeracionInterna, IdUsuario, FechaDocumento, FechaSalida, IdTipoDocumento, IdTipoAccion, NroDocumento, Asunto, Remitente, Destino, NumeracionDireccion, Observacion, Despacho, FechaDespacho, Extension, Activo
	FROM Far_Documento
GO

CREATE PROCEDURE Far_Documento_Insertar
@NumeracionInterna VARCHAR(20),
@IdUsuario INT,
@FechaDocumento DATETIME,
@FechaSalida DATETIME,
@IdTipoAccion INT,
@IdTipoDocumento INT,
@NroDocumento VARCHAR(50),
@Asunto VARCHAR(200),
@Remitente VARCHAR(200),
@Destino VARCHAR(200),
@NumeracionDireccion VARCHAR(100),
@Observacion VARCHAR(200),
@Despacho INT,
@FechaDespacho DATETIME,
@Extension VARCHAR(10),
@Activo INT,
@UsuarioCreacion INT,
@IdDocumento INT OUT
AS
	INSERT INTO Far_Documento(NumeracionInterna, IdUsuario, FechaDocumento, FechaSalida, IdTipoAccion, IdTipoDocumento, NroDocumento, Asunto, Remitente, Destino, NumeracionDireccion, Observacion, Despacho, FechaDespacho, Extension, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NumeracionInterna, @IdUsuario, @FechaDocumento, @FechaSalida, @IdTipoAccion, @IdTipoDocumento, @NroDocumento, @Asunto, @Remitente, @Destino, @NumeracionDireccion, @Observacion, @Despacho, @FechaDespacho, @Extension, @Activo, @UsuarioCreacion, GETDATE())

	SELECT @IdDocumento = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Documento_Modificar
@IdDocumento INT,
@NumeracionInterna VARCHAR(20),
@IdUsuario INT,
@FechaDocumento DATETIME,
@FechaSalida DATETIME,
@IdTipoAccion INT,
@IdTipoDocumento INT,
@NroDocumento VARCHAR(50),
@Asunto VARCHAR(200),
@Remitente VARCHAR(200),
@Destino VARCHAR(200),
@NumeracionDireccion VARCHAR(100),
@Observacion VARCHAR(200),
@Despacho INT,
@FechaDespacho DATETIME,
@Extension VARCHAR(10),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Documento
	SET NumeracionInterna = @NumeracionInterna, 
	IdUsuario = @IdUsuario,
	FechaDocumento = @FechaDocumento, 
	FechaSalida = @FechaSalida,
	IdTipoAccion = @IdTipoAccion,
	IdTipoDocumento = @IdTipoDocumento, 
	NroDocumento = @NroDocumento, 
	Asunto = @Asunto, 
	Remitente = @Remitente, 
	Destino = @Destino, 
	NumeracionDireccion = @NumeracionDireccion, 
	Observacion = @Observacion, 
	Despacho = @Despacho, 
	FechaDespacho = @FechaDespacho,
	Extension = @Extension,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdDocumento = @IdDocumento
GO

CREATE PROCEDURE Far_Documento_Eliminar
@IdDocumento INT
AS
	DELETE FROM Far_Documento
	WHERE IdDocumento = @IdDocumento
GO

CREATE PROCEDURE Far_Tipo_Almacen_Listar
AS
	SELECT IdTipoAlmacen, NombreTipoAlmacen, Activo
	FROM Far_Tipo_Almacen
GO

CREATE PROCEDURE Far_Tipo_Almacen_Insertar
@NombreTipoAlmacen VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Almacen(NombreTipoAlmacen, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoAlmacen, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Almacen_Modificar
@IdTipoAlmacen INT,
@NombreTipoAlmacen VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Almacen
	SET NombreTipoAlmacen = @NombreTipoAlmacen, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdTipoAlmacen = @IdTipoAlmacen
GO

CREATE PROCEDURE Far_Tipo_Almacen_Eliminar
@IdTipoAlmacen INT
AS
	DELETE FROM Far_Tipo_Almacen
	WHERE IdTipoAlmacen = @IdTipoAlmacen
GO

CREATE PROCEDURE Far_Ubigeo_Listar
AS
	SELECT IdUbigeo, NombreUbigeo, Activo
	FROM Far_Ubigeo
GO

CREATE PROCEDURE Far_Ubigeo_ListarDepartamento
AS
	SELECT IdUbigeo, NombreUbigeo, Activo
	FROM Far_Ubigeo
	WHERE LEN(IdUbigeo) = 2
GO

CREATE PROCEDURE Far_Ubigeo_ListarProvincia
@IdUbigeo VARCHAR(2)
AS
	SELECT IdUbigeo, NombreUbigeo, Activo
	FROM Far_Ubigeo
	WHERE LEN(IdUbigeo) = 4
	AND (SUBSTRING(IdUbigeo, 1,2) = @IdUbigeo
	OR @IdUbigeo = '')
GO

CREATE PROCEDURE Far_Ubigeo_ListarDistrito
@IdUbigeo VARCHAR(4)
AS
	SELECT IdUbigeo, NombreUbigeo, Activo
	FROM Far_Ubigeo
	WHERE LEN(IdUbigeo) = 6
	AND (SUBSTRING(IdUbigeo, 1,4) = @IdUbigeo
	OR @IdUbigeo = '')
GO

CREATE PROCEDURE Far_Ubigeo_Insertar
@IdUbigeo VARCHAR(6),
@NombreUbigeo VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Ubigeo(IdUbigeo,NombreUbigeo, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdUbigeo, @NombreUbigeo, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Ubigeo_Modificar
@IdUbigeo VARCHAR(6),
@NombreUbigeo VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Ubigeo
	SET NombreUbigeo = @NombreUbigeo, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdUbigeo = @IdUbigeo
GO

CREATE PROCEDURE Far_Ubigeo_Eliminar
@IdUbigeo VARCHAR(6)
AS
	DELETE FROM Far_Ubigeo
	WHERE IdUbigeo = @IdUbigeo
GO

CREATE PROCEDURE Far_Concepto_Listar
AS
	SELECT IdConcepto, NombreConcepto, TipoMovimientoConcepto, TipoPrecio, Activo
	FROM Far_Concepto
GO

CREATE PROCEDURE Far_Concepto_ListarPorId
@IdConcepto INT
AS
	SELECT IdConcepto, NombreConcepto, TipoMovimientoConcepto, TipoPrecio, Activo
	FROM Far_Concepto
	WHERE IdConcepto = @IdConcepto
GO

CREATE PROCEDURE Far_Concepto_Insertar
@NombreConcepto VARCHAR(70),
@TipoMovimientoConcepto CHAR(1),
@TipoPrecio CHAR(1),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Concepto(NombreConcepto, TipoMovimientoConcepto, TipoPrecio, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreConcepto, @TipoMovimientoConcepto, @TipoPrecio, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Concepto_Modificar
@IdConcepto INT,
@NombreConcepto VARCHAR(70),
@TipoMovimientoConcepto CHAR(1),
@TipoPrecio CHAR(1),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Concepto
	SET NombreConcepto = @NombreConcepto,
	TipoMovimientoConcepto = @TipoMovimientoConcepto,
	TipoPrecio = @TipoPrecio,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdConcepto = @IdConcepto
GO

CREATE PROCEDURE Far_Concepto_Eliminar
@IdConcepto INT
AS
	DELETE FROM Far_Concepto
	WHERE IdConcepto = @IdConcepto
GO

CREATE PROCEDURE Far_Tipo_Compra_Listar
AS
	SELECT IdTipoCompra, NombreTipoCompra, Activo
	FROM Far_Tipo_Compra
GO

CREATE PROCEDURE Far_Tipo_Compra_Insertar
@NombreTipoCompra VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Compra(NombreTipoCompra, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoCompra, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Compra_Modificar
@IdTipoCompra INT,
@NombreTipoCompra VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Compra
	SET NombreTipoCompra = @NombreTipoCompra, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdTipoCompra = @IdTipoCompra
GO

CREATE PROCEDURE Far_Tipo_Compra_Eliminar
@IdTipoCompra INT
AS
	DELETE FROM Far_Tipo_Compra
	WHERE IdTipoCompra = @IdTipoCompra
GO

CREATE PROCEDURE Far_Documento_Origen_Listar
AS
	SELECT IdDocumentoOrigen, NombreDocumentoOrigen, Activo
	FROM Far_Documento_Origen
GO

CREATE PROCEDURE Far_Documento_Origen_ListarPorId
@IdDocumentoOrigen INT
AS
	SELECT IdDocumentoOrigen, NombreDocumentoOrigen, Activo
	FROM Far_Documento_Origen
	WHERE IdDocumentoOrigen = @IdDocumentoOrigen
GO

CREATE PROCEDURE Far_Documento_Origen_Existe
@IdDocumentoOrigen INT,
@NombreDocumentoOrigen VARCHAR(70),
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Documento_Origen WHERE NombreDocumentoOrigen = @NombreDocumentoOrigen AND IdDocumentoOrigen != @IdDocumentoOrigen))
 BEGIN
	SET @Existe = 1
 END

GO

CREATE PROCEDURE Far_Documento_Origen_EsUsado
@IdDocumentoOrigen INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Movimiento WHERE IdDocumentoOrigen = @IdDocumentoOrigen))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Documento_Origen_Insertar
@NombreDocumentoOrigen VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Documento_Origen(NombreDocumentoOrigen, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES (@NombreDocumentoOrigen, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Documento_Origen_Modificar
@IdDocumentoOrigen INT,
@NombreDocumentoOrigen VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Documento_Origen
	SET NombreDocumentoOrigen = @NombreDocumentoOrigen, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdDocumentoOrigen = @IdDocumentoOrigen
GO

CREATE PROCEDURE Far_Documento_Origen_Eliminar
@IdDocumentoOrigen INT
AS
	DELETE FROM Far_Documento_Origen
	WHERE IdDocumentoOrigen = @IdDocumentoOrigen
GO

CREATE PROCEDURE Far_Concepto_Documento_Origen_Listar
@IdConcepto INT
AS
	SELECT IdConceptoDocumentoOrigen, IdConcepto, cdo.IdDocumentoOrigen, do.NombreDocumentoOrigen, do.Activo ActivoDocumentoOrigen
	FROM Far_Concepto_Documento_Origen cdo
	INNER JOIN Far_Documento_Origen do ON do.IdDocumentoOrigen = cdo.IdDocumentoOrigen
	WHERE IdConcepto = @IdConcepto
GO

CREATE PROCEDURE Far_Concepto_Documento_Origen_Insertar
@IdConcepto INT,
@IdDocumentoOrigen INT,
@UsuarioCreacion INT,
@IdConceptoDocumentoOrigen INT OUTPUT
AS
	INSERT INTO Far_Concepto_Documento_Origen(IdConcepto, IdDocumentoOrigen, UsuarioCreacion, FechaCreacion)
	VALUES(@IdConcepto, @IdDocumentoOrigen, @UsuarioCreacion, GETDATE())

	SET @IdConceptoDocumentoOrigen = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Concepto_Documento_Origen_Eliminar
@IdConceptoDocumentoOrigen INT
AS
	DELETE FROM Far_Concepto_Documento_Origen
	WHERE IdConceptoDocumentoOrigen = @IdConceptoDocumentoOrigen
GO

CREATE PROCEDURE Far_Tipo_Proceso_Listar
AS
	SELECT IdTipoProceso, NombreTipoProceso, Activo
	FROM Far_Tipo_Proceso
GO

CREATE PROCEDURE Far_Tipo_Proceso_ListarPorId
@IdTipoProceso INT
AS
	SELECT IdTipoProceso, NombreTipoProceso, Activo
	FROM Far_Tipo_Proceso
	WHERE IdTipoProceso = @IdTipoProceso
GO

CREATE PROCEDURE Far_Tipo_Proceso_Existe
@IdTipoProceso INT,
@NombreTipoProceso VARCHAR(70),
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Tipo_Proceso WHERE NombreTipoProceso = @NombreTipoProceso AND IdTipoProceso != @IdTipoProceso))
 BEGIN
	SET @Existe = 1
 END

GO

CREATE PROCEDURE Far_Tipo_Proceso_EsUsado
@IdTipoProceso INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Movimiento WHERE IdTipoProceso = @IdTipoProceso))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Tipo_Proceso_Insertar
@NombreTipoProceso VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Proceso(NombreTipoProceso, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES (@NombreTipoProceso, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)
GO

CREATE PROCEDURE Far_Tipo_Proceso_Modificar
@IdTipoProceso INT,
@NombreTipoProceso VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Proceso
	SET NombreTipoProceso = @NombreTipoProceso, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdTipoProceso = @IdTipoProceso
GO

CREATE PROCEDURE Far_Tipo_Proceso_Eliminar
@IdTipoProceso INT
AS
	DELETE FROM Far_Tipo_Proceso
	WHERE IdTipoProceso = @IdTipoProceso
GO

CREATE PROCEDURE Far_Almacen_Listar
AS
	SELECT IdAlmacen, F.IdAlmacenPadre, ISNULL(F_TEMP.CantidadHijos, 0) AS 'CantidadHijos', IdTipoAlmacen, Descripcion, Abreviatura, Direccion, Fax, Telefono, Ruc, IdUbigeo, Responsable, Farmacia, CodigoAlmacen, Activo
	FROM Far_Almacen F
	LEFT JOIN (SELECT IdAlmacenPadre, COUNT(1) CantidadHijos
				FROM Far_Almacen
				WHERE IdAlmacenPadre IS NOT NULL
				GROUP BY IdAlmacenPadre) F_TEMP ON F.IdAlmacen = F_TEMP.IdAlmacenPadre
GO

CREATE PROCEDURE Far_Almacen_ListarPadres
AS
	SELECT IdAlmacen, F.IdAlmacenPadre, ISNULL(F_TEMP.CantidadHijos, 0) AS 'CantidadHijos', IdTipoAlmacen, Descripcion, Abreviatura, Direccion, Fax, Telefono, Ruc, IdUbigeo, Responsable, Farmacia, CodigoAlmacen, Activo
	FROM Far_Almacen F 
	LEFT JOIN (SELECT IdAlmacenPadre, COUNT(1) CantidadHijos
				FROM Far_Almacen
				WHERE IdAlmacenPadre IS NOT NULL
				GROUP BY IdAlmacenPadre) F_TEMP ON F.IdAlmacen = F_TEMP.IdAlmacenPadre
	WHERE F.IdAlmacenPadre IS NULL
GO

CREATE PROCEDURE Far_Almacen_ListarPorPadre
@IdAlmancenPadre INT
AS
	SELECT IdAlmacen, IdAlmacenPadre, 0 AS 'CantidadHijos', IdTipoAlmacen, Descripcion, Abreviatura, Direccion, Fax, Telefono, Ruc, IdUbigeo, Responsable, Farmacia, CodigoAlmacen, Activo
	FROM Far_Almacen
	WHERE IdAlmacenPadre = @IdAlmancenPadre
GO

CREATE PROCEDURE Far_Almacen_ListarPorId
@IdAlmacen INT
AS
	SELECT IdAlmacen, F.IdAlmacenPadre, ISNULL(F_TEMP.CantidadHijos, 0) AS 'CantidadHijos', IdTipoAlmacen, Descripcion, Abreviatura, Direccion, Fax, Telefono, Ruc, IdUbigeo, Responsable, Farmacia, CodigoAlmacen, Activo
	FROM Far_Almacen F 
	LEFT JOIN (SELECT IdAlmacenPadre, COUNT(1) CantidadHijos
				FROM Far_Almacen
				WHERE IdAlmacenPadre IS NOT NULL
				GROUP BY IdAlmacenPadre) F_TEMP ON F.IdAlmacen = F_TEMP.IdAlmacenPadre
	WHERE IdAlmacen = @IdAlmacen
GO

CREATE PROCEDURE Far_Almacen_Tree
AS
	SELECT AL.IdAlmacen, AL.Descripcion 'Almacen', AL_PA.IdAlmacen 'IdAlmacenPadre'
	FROM Far_Almacen AL
	LEFT JOIN Far_Almacen AL_PA ON AL.IdAlmacenPadre = AL_PA.IdAlmacen
	ORDER BY AL.IdAlmacenPadre
GO

CREATE PROCEDURE Far_Almacen_VirtualEstado
@IdAlmacenPadre INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Almacen
	SET Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdAlmacenPadre LIKE @IdAlmacenPadre	
GO

CREATE PROCEDURE Far_Almacen_Insertar
@IdAlmacenPadre INT,
@IdTipoAlmacen INT,
@Descripcion VARCHAR(50),
@Abreviatura VARCHAR(14),
@Direccion VARCHAR(50),
@Fax VARCHAR(20),
@Telefono VARCHAR(20),
@Ruc CHAR(11),
@IdUbigeo VARCHAR(6),
@Responsable VARCHAR(70),
@Farmacia INT,
@CodigoAlmacen VARCHAR(10),
@Activo INT,
@UsuarioCreacion INT,
@IdAlmacen INT OUTPUT
AS
	INSERT INTO Far_Almacen(IdAlmacenPadre, IdTipoAlmacen, Descripcion, Abreviatura, Direccion, Fax, Telefono, Ruc, IdUbigeo, Responsable, Farmacia, CodigoAlmacen, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdAlmacenPadre, @IdTipoAlmacen, @Descripcion, @Abreviatura, @Direccion, @Fax, @Telefono, @Ruc, @IdUbigeo, @Responsable, @Farmacia, @CodigoAlmacen, @Activo, @UsuarioCreacion, GETDATE())

	SET @IdAlmacen = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Almacen_Modificar
@IdAlmacen INT,
@IdAlmacenPadre INT,
@IdTipoAlmacen INT,
@Descripcion VARCHAR(50),
@Abreviatura VARCHAR(14),
@Direccion VARCHAR(50),
@Fax VARCHAR(20),
@Telefono VARCHAR(20),
@Ruc CHAR(11),
@IdUbigeo VARCHAR(6),
@Responsable VARCHAR(70),
@Farmacia INT,
@CodigoAlmacen VARCHAR(10),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Almacen
	SET IdAlmacenPadre = @IdAlmacenPadre, 
	IdTipoAlmacen = @IdTipoAlmacen, 
	Descripcion = @Descripcion, 
	Abreviatura = @Abreviatura,
	Direccion = @Direccion, 
	Fax = @Fax, 
	Telefono = @Telefono, 
	Ruc = @Ruc, 
	IdUbigeo = @IdUbigeo, 
	Responsable = @Responsable, 
	Farmacia = @Farmacia,
	CodigoAlmacen = @CodigoAlmacen, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdAlmacen = @IdAlmacen
GO

CREATE PROCEDURE Far_Almacen_EsUsado
@IdAlmacen INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Almacen WHERE IdAlmacenPadre = @IdAlmacen)
		 OR EXISTS(SELECT 1 FROM Far_Movimiento WHERE IdAlmacenOrigen = @IdAlmacen)
		 OR EXISTS(SELECT 1 FROM Far_Movimiento WHERE IdAlmacenDestino = @IdAlmacen))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Almacen_ExisteCodigo
@IdAlmacen INT,
@CodigoAlmacen VARCHAR(10),
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 FROM Far_Almacen WHERE CodigoAlmacen = @CodigoAlmacen AND @IdAlmacen != IdAlmacen)
		 
 BEGIN
	SET @Existe = 1
 END

GO

CREATE PROCEDURE Far_Almacen_Eliminar
@IdAlmacen INT
AS
	DELETE FROM Far_Almacen
	WHERE IdAlmacen = @IdAlmacen
GO

CREATE PROCEDURE Far_Unidad_Medida_Listar
AS
	SELECT IdUnidadMedida, NombreUnidadMedida, Abreviatura, Activo
	FROM Far_Unidad_Medida
GO

CREATE PROCEDURE Far_Unidad_Medida_ListarPorId
@IdUnidadMedida INT
AS
	SELECT IdUnidadMedida, NombreUnidadMedida, Abreviatura, Activo
	FROM Far_Unidad_Medida
	WHERE IdUnidadMedida = @IdUnidadMedida
GO

CREATE PROCEDURE Far_Unidad_Medida_Insertar
@NombreUnidadMedida VARCHAR(70),
@Abreviatura VARCHAR(20),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Unidad_Medida(NombreUnidadMedida, Abreviatura, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreUnidadMedida, @Abreviatura, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Unidad_Medida_Modificar
@IdUnidadMedida INT,
@NombreUnidadMedida VARCHAR(70),
@Abreviatura VARCHAR(20),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Unidad_Medida
	SET NombreUnidadMedida = @NombreUnidadMedida, 
	Abreviatura = @Abreviatura,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdUnidadMedida = @IdUnidadMedida
GO

CREATE PROCEDURE Far_Unidad_Medida_Eliminar
@IdUnidadMedida INT
AS
	DELETE FROM Far_Unidad_Medida
	WHERE IdUnidadMedida = @IdUnidadMedida
GO

CREATE PROCEDURE Far_Tipo_Producto_Listar
AS
	SELECT IdTipoProducto, NombreTipoProducto, Activo
	FROM Far_Tipo_Producto
GO

CREATE PROCEDURE Far_Tipo_Producto_ListarPorId
@IdTipoProducto INT
AS
	SELECT IdTipoProducto, NombreTipoProducto, Activo
	FROM Far_Tipo_Producto
	WHERE IdTipoProducto = @IdTipoProducto
GO

CREATE PROCEDURE Far_Tipo_Producto_Insertar
@NombreTipoProducto VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Producto(NombreTipoProducto, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoProducto, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Producto_Modificar
@IdTipoProducto INT,
@NombreTipoProducto VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Producto
	SET NombreTipoProducto = @NombreTipoProducto, 
	Activo = @Activo,	
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdTipoProducto = @IdTipoProducto
GO

CREATE PROCEDURE Far_Tipo_Producto_Eliminar
@IdTipoProducto INT
AS
	DELETE FROM Far_Tipo_Producto
	WHERE IdTipoProducto = @IdTipoProducto
GO

CREATE PROCEDURE Far_Forma_Farmaceutica_Listar
AS
	SELECT IdFormaFarmaceutica, NombreFormaFarmaceutica, Abreviatura, Activo
	FROM Far_Forma_Farmaceutica
GO

CREATE PROCEDURE Far_Forma_Farmaceutica_ListarPorId
@IdFormaFarmaceutica INT
AS
	SELECT IdFormaFarmaceutica, NombreFormaFarmaceutica, Abreviatura, Activo
	FROM Far_Forma_Farmaceutica
	WHERE IdFormaFarmaceutica = @IdFormaFarmaceutica
GO

CREATE PROCEDURE Far_Forma_Farmaceutica_Insertar
@NombreFormaFarmaceutica VARCHAR(70),
@Abreviatura VARCHAR(20),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Forma_Farmaceutica(NombreFormaFarmaceutica, Abreviatura, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreFormaFarmaceutica, @Abreviatura, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Forma_Farmaceutica_Modificar
@IdFormaFarmaceutica INT,
@NombreFormaFarmaceutica VARCHAR(70),
@Abreviatura VARCHAR(20),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Forma_Farmaceutica
	SET NombreFormaFarmaceutica = @NombreFormaFarmaceutica, 
	Abreviatura = @Abreviatura,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
	WHERE IdFormaFarmaceutica = @IdFormaFarmaceutica
GO

CREATE PROCEDURE Far_Forma_Farmaceutica_Eliminar
@IdFormaFarmaceutica INT
AS
	DELETE FROM Far_Forma_Farmaceutica
	WHERE IdFormaFarmaceutica = @IdFormaFarmaceutica
GO

CREATE PROCEDURE Far_Proveedor_Listar
AS
	SELECT IdProveedor, Ruc, RazonSocial, Direccion, Telefono, Contacto, TelefonoContacto, Fax, Correo, Activo
	FROM Far_Proveedor
GO

CREATE PROCEDURE Far_Proveedor_ListarPorId
@IdProveedor INT
AS
	SELECT IdProveedor, Ruc, RazonSocial, Direccion, Telefono, Contacto, TelefonoContacto, Fax, Correo, Activo
	FROM Far_Proveedor
	WHERE IdProveedor = @IdProveedor
GO

CREATE PROCEDURE Far_Proveedor_ListarPorRUC
@Ruc CHAR(11)
AS
	SELECT IdProveedor, Ruc, RazonSocial, Direccion, Telefono, Contacto, TelefonoContacto, Fax, Correo, Activo
	FROM Far_Proveedor
	WHERE Ruc = @Ruc
GO

CREATE PROCEDURE Far_Proveedor_Insertar
@Ruc CHAR(11),
@RazonSocial VARCHAR(100),
@Direccion VARCHAR(100),
@Telefono VARCHAR(20),
@Contacto VARCHAR(70),
@TelefonoContacto VARCHAR(20),
@Fax VARCHAR(20),
@Correo VARCHAR(100),
@Activo INT,
@UsuarioCreacion INT,
@IdProveedor INT OUTPUT
AS
	INSERT INTO Far_Proveedor(Ruc, RazonSocial, Direccion, Telefono, Contacto, TelefonoContacto, Fax, Correo, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@Ruc, @RazonSocial, @Direccion, @Telefono, @Contacto, @TelefonoContacto, @Fax, @Correo, @Activo, @UsuarioCreacion, GETDATE())

	SET @IdProveedor = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Proveedor_Modificar
@IdProveedor INT,
@Ruc CHAR(11),
@RazonSocial VARCHAR(100),
@Direccion VARCHAR(100),
@Telefono VARCHAR(20),
@Contacto VARCHAR(70),
@TelefonoContacto VARCHAR(20),
@Fax VARCHAR(20),
@Correo VARCHAR(100),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Proveedor
	SET Ruc = @Ruc, 
	RazonSocial = @RazonSocial, 
	Direccion = @Direccion, 
	Telefono = @Telefono,
	Contacto = @Contacto,
	TelefonoContacto = @TelefonoContacto,
	Fax = @Fax, 
	Correo = @Correo, 
	Activo = @Activo,	
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdProveedor = @IdProveedor
GO

CREATE PROCEDURE Far_Proveedor_Eliminar
@IdProveedor INT
AS
	DELETE FROM Far_Proveedor
	WHERE IdProveedor = @IdProveedor
GO

CREATE PROCEDURE Far_Producto_Listar
AS
	SELECT IdProducto, Descripcion, IdProductoSismed, IdProductoSiga, Abreviatura, IdFormaFarmaceutica, IdTipoProducto, IdUnidadMedida, Presentacion, Concentracion, Petitorio, EstrSop, EstrVta, TraNac, TraLoc, Narcotico, StockMin, StockMax, Requerimiento, Adscrito, Activo
	FROM Far_Producto
GO

CREATE PROCEDURE Far_Producto_ListarPorId
@IdProducto INT
AS
	SELECT IdProducto, Descripcion, IdProductoSismed, IdProductoSiga, Abreviatura, IdFormaFarmaceutica, IdTipoProducto, IdUnidadMedida, Presentacion, Concentracion, Petitorio, EstrSop, EstrVta, TraNac, TraLoc, Narcotico, StockMin, StockMax, Requerimiento, Adscrito, Activo
	FROM Far_Producto
	WHERE IdProducto = @IdProducto
GO

CREATE PROCEDURE Far_Producto_Listar_Comp
@Descripcion VARCHAR(100),
@IdFormaFarmaceutica INTEGER,
@IdTipoProducto INTEGER,
@IdUnidadMedida INTEGER,
@EstrSop INTEGER,
@EstrVta INTEGER,
@TraNac INTEGER,
@TraLoc INTEGER,
@Narcotico INTEGER
AS

	SELECT IdProducto, Descripcion, Presentacion, Concentracion, FF.NombreFormaFarmaceutica, TP.NombreTipoProducto, UM.NombreUnidadMedida, P.Activo
	FROM Far_Producto P
	INNER JOIN Far_Forma_Farmaceutica FF ON P.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON P.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN Far_Unidad_Medida UM ON P.IdUnidadMedida = UM.IdUnidadMedida
	WHERE Descripcion LIKE '%' + @Descripcion + '%'
	AND (P.IdFormaFarmaceutica = @IdFormaFarmaceutica OR @IdFormaFarmaceutica = 0)
	AND (P.IdTipoProducto = @IdTipoProducto OR @IdTipoProducto = 0)
	AND (P.IdUnidadMedida = @IdUnidadMedida OR @IdUnidadMedida = 0)
	AND (P.EstrSop = @EstrSop OR @EstrSop = 2)
	AND (P.EstrVta = @EstrVta OR @EstrVta = 2)
	AND (P.TraNac = @TraNac OR @TraNac = 2)
	AND (P.TraLoc = @TraLoc OR @TraLoc = 2)
	AND (P.Narcotico = @Narcotico OR @Narcotico = 2)
GO

CREATE PROCEDURE Far_Producto_AyudaIngresos
@Criterio VARCHAR(100)
AS
	SELECT IdProducto, CodigoSismed, Producto, FormaFarmaceutica
	FROM (SELECT PRO.IdProducto, PS.CodigoSismed, PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.NombreFormaFarmaceutica + ' - ' + PRO.Presentacion Producto, FF.Abreviatura 'FormaFarmaceutica'
	FROM Far_Producto PRO
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica	
	LEFT JOIN Far_Producto_Sismed PS ON PS.IdProductoSismed = PRO.IdProductoSismed
	AND PRO.Activo = 1) TPRO
	WHERE Producto LIKE '%' + @Criterio + '%'
	OR CodigoSismed = @Criterio	
GO

CREATE PROCEDURE Far_Producto_ListarComp_PorId
@IdProducto INT
AS
	
	SELECT IdProducto, Descripcion, Presentacion, Concentracion, FF.NombreFormaFarmaceutica, TP.NombreTipoProducto, UM.NombreUnidadMedida, P.Activo
	FROM Far_Producto P
	INNER JOIN Far_Forma_Farmaceutica FF ON P.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON P.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN Far_Unidad_Medida UM ON P.IdUnidadMedida = UM.IdUnidadMedida
	WHERE IdProducto = @IdProducto
GO

CREATE PROCEDURE Far_Producto_ListarComp_PorAlmacen
@Descripcion VARCHAR(100),
@IdFormaFarmaceutica INTEGER,
@IdTipoProducto INTEGER,
@IdUnidadMedida INTEGER,
@IdAlmacen INT
AS
	
	SELECT PRO.IdProducto, PRO.Descripcion, PRO.Presentacion, PRO.Concentracion, FF.NombreFormaFarmaceutica, TP.NombreTipoProducto, UM.NombreUnidadMedida, PRO.Activo
	FROM (SELECT IdProducto
			FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.TipoMovimiento = 'I'
			AND (MOV.IdAlmacenDestino = @IdAlmacen OR @IdAlmacen = 0)
			GROUP BY IdProducto) MOV_PRO
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN Far_Unidad_Medida UM ON PRO.IdUnidadMedida = UM.IdUnidadMedida	
	AND Descripcion LIKE '%' + @Descripcion + '%'
	AND (PRO.IdFormaFarmaceutica = @IdFormaFarmaceutica OR @IdFormaFarmaceutica = 0)
	AND (PRO.IdTipoProducto = @IdTipoProducto OR @IdTipoProducto = 0)
	AND (PRO.IdUnidadMedida = @IdUnidadMedida OR @IdUnidadMedida = 0)
GO

CREATE PROCEDURE Far_Medicamento_Listar
AS
	SELECT a.[IdProducto],a.[Descripcion],a.[Concentracion],b.[NombreFormaFarmaceutica]
    FROM [Farmacia].[dbo].[Far_Producto] a
    LEFT JOIN [Farmacia].[dbo].[Far_Forma_Farmaceutica] b
    ON b.[IdFormaFarmaceutica] = a.[IdFormaFarmaceutica]
    WHERE a.[IdTipoProducto] = 2 and a.[Activo] = 1
    ORDER BY 2 asc
GO

CREATE PROCEDURE Far_Producto_Insertar
@Descripcion VARCHAR(100),
@IdProductoSismed INT,
@IdProductoSiga INT,
@Abreviatura VARCHAR(20),
@IdFormaFarmaceutica INT,
@IdTipoProducto INT,
@IdUnidadMedida INT,
@Presentacion VARCHAR(100),
@Concentracion VARCHAR(100),
@Petitorio INT,
@EstrSop INT,
@EstrVta INT,
@TraNac INT,
@TraLoc INT,
@Narcotico INT,
@StockMin NUMERIC,
@StockMax NUMERIC,
@Requerimiento NUMERIC,
@Adscrito INT,
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Producto(Descripcion, IdProductoSismed, IdProductoSiga, Abreviatura, IdFormaFarmaceutica, IdTipoProducto, IdUnidadMedida, Presentacion, Concentracion, Petitorio, EstrSop, EstrVta, TraNac, TraLoc, Narcotico, StockMin, StockMax, Requerimiento, Adscrito, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@Descripcion, @IdProductoSismed, @IdProductoSiga, @Abreviatura, @IdFormaFarmaceutica, @IdTipoProducto, @IdUnidadMedida, @Presentacion, @Concentracion, @Petitorio, @EstrSop, @EstrVta, @TraNac, @TraLoc, @Narcotico, @StockMin, @StockMax, @Requerimiento, @Adscrito, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Producto_Modificar
@IdProducto INT,
@Descripcion VARCHAR(100),
@IdProductoSismed INT,
@IdProductoSiga INT,
@Abreviatura VARCHAR(20),
@IdFormaFarmaceutica INT,
@IdTipoProducto INT,
@IdUnidadMedida INT,
@Presentacion VARCHAR(100),
@Concentracion VARCHAR(100),
@Petitorio INT,
@EstrSop INT,
@EstrVta INT,
@TraNac INT,
@TraLoc INT,
@Narcotico INT,
@StockMin NUMERIC,
@StockMax NUMERIC,
@Requerimiento NUMERIC,
@Adscrito INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Producto
	SET Descripcion = @Descripcion, 
	IdProductoSismed = @IdProductoSismed, 
	IdProductoSiga = @IdProductoSiga, 
	Abreviatura = @Abreviatura, 
	IdFormaFarmaceutica = @IdFormaFarmaceutica, 
	IdTipoProducto = @IdTipoProducto, 
	IdUnidadMedida = @IdUnidadMedida, 
	Presentacion = @Presentacion, 
	Concentracion = @Concentracion,	
	Petitorio = @Petitorio, 
	EstrSop = @EstrSop, 
	EstrVta = @EstrVta, 
	TraNac = @TraNac, 
	TraLoc = @TraLoc, 
	Narcotico = @Narcotico, 
	StockMin = @StockMin, 
	StockMax = @StockMax, 
	Requerimiento = @Requerimiento, 
	Adscrito = @Adscrito, 
	Activo = @Activo,	
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdProducto = @IdProducto
GO

CREATE PROCEDURE Far_Producto_Eliminar
@IdProducto INT
AS
	DELETE FROM Far_Producto
	WHERE IdProducto = @IdProducto
GO

CREATE PROCEDURE Far_Producto_StockProd_Almacen
@IdProducto INT,
@IdAlmacen INT,
@Lote VARCHAR(20),
@Stock INT OUTPUT
AS
	DECLARE @CantidadInicial INT
	DECLARE @CantidadIngreso INT
	DECLARE @CantidadSalida INT

	DECLARE @IdPeriodo INT
	DECLARE @ConceptoInicial INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()
	SELECT @ConceptoInicial = Valor FROM Far_Parametro WHERE NombreParametro = 'INICIO'

	SELECT @CantidadInicial = MOV_PRO.Cantidad
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
	AND IdPeriodo = @IdPeriodo
	AND IdConcepto = @ConceptoInicial
	AND (MOV.IdAlmacenDestino = @IdAlmacen OR @IdAlmacen = 0)
	AND MOV_PRO.Lote = @Lote
	WHERE IdProducto = @IdProducto

	SELECT @CantidadIngreso = SUM(Cantidad)
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento 
	AND MOV.TipoMovimiento = 'I'
	AND IdPeriodo = @IdPeriodo
	AND (MOV.IdAlmacenDestino = @IdAlmacen OR @IdAlmacen = 0)
	AND IdConcepto != @ConceptoInicial
	AND MOV_PRO.Lote = @Lote
	WHERE IdProducto = @IdProducto

	SELECT @CantidadSalida = SUM(Cantidad)
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento 
	AND MOV.TipoMovimiento = 'S' 
	AND (MOV.IdAlmacenOrigen = @IdAlmacen OR @IdAlmacen = 0)
	AND IdPeriodo = @IdPeriodo
	AND MOV_PRO.Lote = @Lote
	WHERE IdProducto = @IdProducto

	IF(@CantidadInicial IS NULL)
	BEGIN
		SET @CantidadInicial = 0
	END

	IF(@CantidadIngreso IS NULL)
	BEGIN
		SET @CantidadIngreso = 0
	END

	IF(@CantidadSalida IS NULL)
	BEGIN
		SET @CantidadSalida = 0
	END
	
	SET @Stock = (@CantidadInicial + @CantidadIngreso) - @CantidadSalida
GO

CREATE PROCEDURE Far_MovimientoProducto_ExisteLote
@Lote VARCHAR(20),
@IdProducto INT
AS
	SELECT TOP 1 Precio, FechaVencimiento, RegistroSanitario
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
	WHERE IdProducto = @IdProducto
	AND TipoMovimiento = 'I'
	AND Lote = @Lote
GO

CREATE PROCEDURE Far_Producto_StockGeneral
@Descripcion VARCHAR(100)
AS
	DECLARE @IdPeriodo INT 

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()

	SELECT PRO.IdProducto, PRO.Descripcion, Cantidad, FF.NombreFormaFarmaceutica, PRO.Presentacion, PRO.Concentracion, 0 PrecioRef, PRO.StockMin, PRO.StockMax, PRO_SIS.CodigoSismed
	FROM Far_Producto PRO
	INNER JOIN (SELECT PRO.IdProducto, SUM(CASE
							WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
							THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
							ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
						   END) Cantidad
				FROM Far_Producto PRO
				LEFT JOIN
				(SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto
					FROM Far_Movimiento MOV
					INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
					AND IdPeriodo = @IdPeriodo) MOV_PRO_TEMP ON PRO.IdProducto = MOV_PRO_TEMP.IdProducto
				LEFT JOIN Far_Producto_Sismed PRO_SIS ON PRO.IdProductoSismed = PRO_SIS.IdProductoSismed
				WHERE (Descripcion LIKE '%' + @Descripcion + '%'
				OR PRO_SIS.CodigoSismed LIKE '%' + @Descripcion + '%')
				GROUP BY PRO.IdProducto) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto	
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	LEFT JOIN Far_Producto_Sismed PRO_SIS ON PRO.IdProductoSismed = PRO_SIS.IdProductoSismed	
GO

CREATE PROCEDURE Far_Producto_StockPorAlmacen
@IdAlmacen INT,
@Criterio VARCHAR(100)
AS
	DECLARE @IdPeriodo INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()

	SELECT IdProducto, CodigoSismed, Producto, Abreviatura FormaFarmaceutica, Cantidad
	FROM (SELECT PRO.IdProducto, PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.Abreviatura + ' - ' + PRO.Presentacion Producto, FF.Abreviatura, PS.CodigoSismed, Cantidad
		FROM Far_Producto PRO
		INNER JOIN (SELECT PRO.IdProducto, SUM(CASE
								WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
								THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
								ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
							   END) Cantidad
					FROM Far_Producto PRO
					LEFT JOIN 
					(SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto
						FROM Far_Movimiento MOV
						INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
						AND IdPeriodo = @IdPeriodo
						AND ((IdAlmacenOrigen = @IdAlmacen AND TipoMovimiento = 'S')
								OR (IdAlmacenDestino = @IdAlmacen AND TipoMovimiento = 'I'))
						) MOV_PRO_TEMP ON PRO.IdProducto = MOV_PRO_TEMP.IdProducto				
					GROUP BY PRO.IdProducto) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto	
		INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
		LEFT JOIN Far_Producto_Sismed PS ON PRO.IdProductoSismed = PS.IdProductoSismed) PRO_TEMP
	WHERE Producto LIKE '%' + @Criterio + '%'
	OR CodigoSismed = @Criterio
GO

CREATE PROCEDURE Far_Producto_StockEnAlmacenes
@IdProducto INT
AS
	DECLARE @IdPeriodo INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()

	SELECT AP.Descripcion + ' - ' + ALM.Descripcion Almacen, ALM.CodigoAlmacen, Cantidad, 0 Precio, 0 Importe
	FROM Far_Almacen ALM 
	LEFT JOIN Far_Almacen AP ON AP.IdAlmacen = ALM.IdAlmacenPadre
	INNER JOIN (SELECT IdProducto, SUM(CASE
											WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
											THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
											ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
											END) Cantidad, 
											IdAlmacenDestino IdAlmacen
								FROM (SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto, CASE 
																										WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
																										ELSE IdAlmacenDestino 
																										END AS IdAlmacenDestino
									FROM Far_Movimiento MOV
									INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
									AND IdPeriodo = @IdPeriodo
									WHERE MOV_PRO.IdProducto = @IdProducto) MOV_PRO_TEMP
								GROUP BY IdProducto, IdAlmacenDestino) MOV_STOCK_PRO
	ON MOV_STOCK_PRO.IdAlmacen = ALM.IdAlmacen
	WHERE Cantidad > 0		
GO

CREATE PROCEDURE Far_Producto_StockEnAlmacen
@IdProducto INT,
@IdAlmacen INT
AS
	DECLARE @IdPeriodo INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()

	SELECT Cantidad, Lote, FechaVencimiento	
	FROM (SELECT IdProducto, SUM(CASE
											WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
											THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
											ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
											END) Cantidad, 
											IdAlmacenDestino IdAlmacen, Lote, FechaVencimiento
								FROM (SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, Lote, FechaVencimiento, IdProducto, CASE 
																										WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
																										ELSE IdAlmacenDestino 
																										END AS IdAlmacenDestino
									FROM Far_Movimiento MOV
									INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
									AND IdPeriodo = @IdPeriodo
									WHERE MOV_PRO.IdProducto = @IdProducto) MOV_PRO_TEMP
								GROUP BY IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) MOV_STOCK_PRO
	WHERE Cantidad > 0
	AND MOV_STOCK_PRO.IdAlmacen =  @IdAlmacen
GO

CREATE PROCEDURE Far_Producto_Lote
@IdProducto INT,
@IdAlmacen INT
AS
	
	DECLARE @IdPeriodo INT
	DECLARE @ConceptoInicial INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()
	SELECT @ConceptoInicial = Valor FROM Far_Parametro WHERE NombreParametro = 'INICIO'
	
	SELECT MOV_PRO.Lote
	FROM Far_Movimiento MOV
	INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
	AND MOV.IdAlmacenDestino = @IdAlmacen
	AND IdPeriodo = @IdPeriodo
	AND IdProducto = @IdProducto
	LEFT JOIN (SELECT MOV_PRO.Cantidad, MOV_PRO.Lote
				FROM Far_Movimiento_Producto MOV_PRO
				INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
				AND IdPeriodo = @IdPeriodo
				AND IdConcepto = @ConceptoInicial
				AND MOV.IdAlmacenDestino = @IdAlmacen
				WHERE IdProducto = @IdProducto) CANT_INI ON MOV_PRO.Lote = CANT_INI.Lote
	LEFT JOIN (SELECT SUM(Cantidad) Cantidad, MOV_PRO.Lote
				FROM Far_Movimiento_Producto MOV_PRO
				INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento 
				AND MOV.TipoMovimiento = 'I' 
				AND IdPeriodo = @IdPeriodo
				AND MOV.IdAlmacenDestino = @IdAlmacen
				AND IdConcepto != @ConceptoInicial
				WHERE IdProducto = @IdProducto
				GROUP BY Lote) CANT_ING ON MOV_PRO.Lote = CANT_ING.Lote
	LEFT JOIN (SELECT SUM(Cantidad) Cantidad, MOV_PRO.Lote
				FROM Far_Movimiento_Producto MOV_PRO
				INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
				AND MOV.TipoMovimiento = 'S' 
				AND MOV.IdAlmacenOrigen = @IdAlmacen
				AND IdPeriodo = @IdPeriodo
				WHERE IdProducto = @IdProducto
				GROUP BY Lote) CANT_SAL ON MOV_PRO.Lote = CANT_SAL.Lote
	WHERE ISNULL(CANT_INI.Cantidad, 0) + ISNULL(CANT_ING.Cantidad, 0) - ISNULL(CANT_SAL.Cantidad, 0) > 0
	GROUP BY MOV_PRO.Lote
GO
 
CREATE PROCEDURE Far_Movimiento_Producto_Stock
@IdProducto INT,
@IdAlmacen INT
AS

	DECLARE @IdPeriodo INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()	

	SELECT MOV_PRO_TEMP.IdProducto, PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.Abreviatura + ' - ' + PRO.Presentacion Producto, MOV_PRO_TEMP.Lote, MOV_PRO_TEMP.Cantidad, Lotes.Precio, Lotes.FechaVencimiento, Lotes.RegistroSanitario
	FROM (SELECT IdProducto, MOV_PRO.Lote, SUM(CASE 
					WHEN ISNULL(MOV.TipoMovimiento, 'I') = 'I'
					THEN ISNULL(MOV_PRO.Cantidad, 0)
					ELSE ISNULL(MOV_PRO.Cantidad, 0) * -1
					END) Cantidad
	FROM Far_Movimiento MOV
	INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
	AND ((IdAlmacenOrigen = @IdAlmacen AND TipoMovimiento = 'S')
			OR (IdAlmacenDestino = @IdAlmacen AND TipoMovimiento = 'I'))
	AND IdPeriodo = @IdPeriodo
	AND IdProducto = @IdProducto
	GROUP BY MOV_PRO.Lote, IdProducto
	HAVING SUM(CASE 
				WHEN ISNULL(MOV.TipoMovimiento, 'I') = 'I'
				THEN ISNULL(MOV_PRO.Cantidad, 0)
				ELSE ISNULL(MOV_PRO.Cantidad, 0) * -1
				END) > 0) MOV_PRO_TEMP
	INNER JOIN Far_Producto PRO ON PRO.IdProducto = MOV_PRO_TEMP.IdProducto
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN (SELECT Lote, Precio, FechaVencimiento, RegistroSanitario
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
	WHERE TipoMovimiento = 'I'
	AND IdProducto = @IdProducto
	GROUP BY Lote, Precio, FechaVencimiento, RegistroSanitario) Lotes ON Lotes.Lote = MOV_PRO_TEMP.Lote
	ORDER BY FechaVencimiento ASC
GO

CREATE PROCEDURE Far_Tipo_Documento_Mov_Listar
AS
	SELECT IdTipoDocumentoMov, NombreTipoDocumentoMov, Activo
	FROM Far_Tipo_Documento_Mov
GO

CREATE PROCEDURE Far_Tipo_Documento_Mov_ListarPorId
@IdTipoDocumentoMov INT
AS
	SELECT IdTipoDocumentoMov, NombreTipoDocumentoMov, Activo
	FROM Far_Tipo_Documento_Mov
	WHERE IdTipoDocumentoMov = @IdTipoDocumentoMov
GO

CREATE PROCEDURE Far_Tipo_Documento_Mov_Insertar
@NombreTipoDocumentoMov VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Tipo_Documento_Mov(NombreTipoDocumentoMov, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@NombreTipoDocumentoMov, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Tipo_Documento_Mov_Modificar
@IdTipoDocumentoMov INT,
@NombreTipoDocumentoMov VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Tipo_Documento_Mov
	SET NombreTipoDocumentoMov = @NombreTipoDocumentoMov, 
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdTipoDocumentoMov = @IdTipoDocumentoMov
GO

CREATE PROCEDURE Far_Tipo_Documento_Mov_Eliminar
@IdTipoDocumentoMov INT
AS
	DELETE FROM Far_Tipo_Documento_Mov
	WHERE IdTipoDocumentoMov = @IdTipoDocumentoMov
GO

CREATE PROCEDURE Far_Concepto_Tipo_Documento_Mov_Listar
@IdConcepto INT
AS
	SELECT IdConceptoTipoDocumentoMov, IdConcepto, ctdm.IdTipoDocumentoMov, NombreTipoDocumentoMov, Activo ActivoTipoDocumentoMov
	FROM Far_Concepto_Tipo_Documento_Mov ctdm
	INNER JOIN Far_Tipo_Documento_Mov tdm ON ctdm.IdTipoDocumentoMov = tdm.IdTipoDocumentoMov
	WHERE IdConcepto = @IdConcepto
GO

CREATE PROCEDURE Far_Concepto_Tipo_Documento_Mov_Insertar
@IdConcepto INT,
@IdTipoDocumentoMov INT,
@UsuarioCreacion INT,
@IdConceptoTipoDocumentoMov INT OUTPUT
AS
	INSERT INTO Far_Concepto_Tipo_Documento_Mov(IdConcepto, IdTipoDocumentoMov, UsuarioCreacion, FechaCreacion)
	VALUES(@IdConcepto, @IdTipoDocumentoMov, @UsuarioCreacion, GETDATE())

	SET @IdConceptoTipoDocumentoMov = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Concepto_Tipo_Documento_Mov_Eliminar
@IdConceptoTipoDocumentoMov INT
AS
	DELETE FROM Far_Concepto_Tipo_Documento_Mov
	WHERE IdConceptoTipoDocumentoMov = @IdConceptoTipoDocumentoMov
GO

CREATE PROCEDURE Far_Movimiento_ExisteSaldoInicial
@IdPeriodo INT,
@IdConcepto INT,
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 FROM Far_Movimiento
			WHERE TipoMovimiento = 'I'
			AND IdPeriodo = @IdPeriodo
			AND IdConcepto = @IdConcepto)		 
 BEGIN
	SET @Existe = 1
 END	
GO

CREATE PROCEDURE Far_Movimiento_Consulta
@IdPeriodo INT,
@TipoMovimiento CHAR(1),
@IdConcepto INT,
@IdAlmacenOrigen INT,
@IdAlmacenDestino INT,
@FechaDesde DATETIME,
@FechaHasta DATETIME,
@Activo INT
AS
	SELECT MOV.FechaRegistro, MOV.TipoMovimiento, MOV.NumeroMovimiento, 
			AOP.Descripcion + ' - ' + AO.Descripcion AS 'AlmacenOrigen', 
			ADP.Descripcion + ' - ' +  AD.Descripcion AS 'AlmacenDestino',
			CON.NombreConcepto, TOTAL.Total, FechaRecepcion, NombreTipoDocumentoMov, NumeroDocumentoMov, PRO.RazonSocial
	FROM Far_Movimiento MOV
	LEFT JOIN Far_Almacen AO ON MOV.IdAlmacenOrigen = AO.IdAlmacen
	LEFT JOIN Far_Almacen AD ON MOV.IdAlmacenDestino = AD.IdAlmacen
	INNER JOIN Far_Concepto	CON ON MOV.IdConcepto = CON.IdConcepto
	LEFT JOIN Far_Tipo_Documento_Mov TIPO_DOC ON MOV.IdTipoDocumentoMov = TIPO_DOC.IdTipoDocumentoMov
	LEFT JOIN Far_Proveedor PRO ON MOV.IdProveedor = PRO.IdProveedor
	LEFT JOIN Far_Almacen AOP ON AOP.IdAlmacen = AO.IdAlmacenPadre
	LEFT JOIN Far_Almacen ADP ON ADP.IdAlmacen = AD.IdAlmacenPadre
	INNER JOIN (SELECT SUM(Total) Total, IdMovimiento
				FROM Far_Movimiento_Producto			
				GROUP BY IdMovimiento) TOTAL ON MOV.IdMovimiento = TOTAL.IdMovimiento
	WHERE IdPeriodo = @IdPeriodo
	AND (TipoMovimiento = @TipoMovimiento OR @TipoMovimiento IS NULL)
	AND (MOV.IdConcepto = @IdConcepto OR @IdConcepto = 0)
	AND (MOV.IdAlmacenOrigen = @IdAlmacenOrigen OR @IdAlmacenOrigen = 0)
	AND (MOV.IdAlmacenDestino = @IdAlmacenDestino OR @IdAlmacenDestino = 0)
	AND (MOV.FechaRegistro >= @FechaDesde OR @FechaDesde IS NULL)
	AND (@FechaHasta >= MOV.FechaRegistro  OR @FechaHasta IS NULL)
	AND (@Activo = MOV.Activo OR @Activo = -1)
GO

CREATE PROCEDURE Far_Movimiento_Listar
AS
	SELECT M.IdMovimiento, IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, AOP.Descripcion + ' - ' + AO.Descripcion AS 'AlmacenOrigen', M.IdAlmacenDestino, ADP.Descripcion + ' - ' +  AD.Descripcion AS 'AlmacenDestino', M.IdConcepto, C.NombreConcepto, M.IdTipoDocumentoMov, TD.NombreTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, M.IdDocumentoOrigen, DO.NombreDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, M.IdProveedor, P.RazonSocial, M.IdTipoCompra, M.IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, TOTAL.Total, M.Activo
	FROM Far_Movimiento M
	INNER JOIN (SELECT IdMovimiento, SUM(Total) Total
				FROM Far_Movimiento_Producto 
				GROUP BY IdMovimiento) TOTAL ON M.IdMovimiento = TOTAL.IdMovimiento
	LEFT JOIN Far_Almacen AO ON AO.IdAlmacen = M.IdAlmacenOrigen
	LEFT JOIN Far_Almacen AD ON AD.IdAlmacen = M.IdAlmacenDestino
	INNER JOIN Far_Concepto C ON C.IdConcepto = M.IdConcepto
	LEFT JOIN Far_Tipo_Documento_Mov TD ON TD.IdTipoDocumentoMov = M.IdTipoDocumentoMov
	LEFT JOIN Far_Documento_Origen DO ON DO.IdDocumentoOrigen = M.IdDocumentoOrigen
	LEFT JOIN Far_Proveedor P ON P.IdProveedor = M.IdProveedor
	LEFT JOIN Far_Almacen AOP ON AOP.IdAlmacen = AO.IdAlmacenPadre
	LEFT JOIN Far_Almacen ADP ON ADP.IdAlmacen = AD.IdAlmacenPadre
GO

CREATE PROCEDURE Far_Movimiento_ListarPorTipo 
@FechaDesde DATETIME,
@FechaHasta DATETIME,
@TipoMovimiento CHAR(1)
AS
	SELECT M.IdMovimiento, IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, M.IdAlmacenOrigen, AOP.Descripcion + ' - ' + AO.Descripcion AS 'AlmacenOrigen', M.IdAlmacenDestino, ADP.Descripcion + ' - ' +  AD.Descripcion AS 'AlmacenDestino', M.IdConcepto, C.NombreConcepto, M.IdTipoDocumentoMov, TD.NombreTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, M.IdDocumentoOrigen, DO.NombreDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, M.IdProveedor, P.RazonSocial, M.IdTipoCompra, M.IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, TOTAL.Total, M.Activo
	FROM Far_Movimiento M
	INNER JOIN (SELECT IdMovimiento, SUM(Total) Total
				FROM Far_Movimiento_Producto 
				GROUP BY IdMovimiento) TOTAL ON M.IdMovimiento = TOTAL.IdMovimiento
	LEFT JOIN Far_Almacen AO ON AO.IdAlmacen = M.IdAlmacenOrigen
	LEFT JOIN Far_Almacen AD ON AD.IdAlmacen = M.IdAlmacenDestino
	INNER JOIN Far_Concepto C ON C.IdConcepto = M.IdConcepto
	LEFT JOIN Far_Tipo_Documento_Mov TD ON TD.IdTipoDocumentoMov = M.IdTipoDocumentoMov
	LEFT JOIN Far_Documento_Origen DO ON DO.IdDocumentoOrigen = M.IdDocumentoOrigen
	LEFT JOIN Far_Proveedor P ON P.IdProveedor = M.IdProveedor
	LEFT JOIN Far_Almacen AOP ON AOP.IdAlmacen = AO.IdAlmacenPadre
	LEFT JOIN Far_Almacen ADP ON ADP.IdAlmacen = AD.IdAlmacenPadre
	WHERE TipoMovimiento = @TipoMovimiento	
	AND CONVERT(DATE, FechaRegistro) >= @FechaDesde
	AND CONVERT(DATE, FechaRegistro) <= @FechaHasta
	ORDER BY FechaRegistro DESC
GO

CREATE PROCEDURE Far_Movimiento_ListarPorId
@IdMovimiento INT
AS
	SELECT M.IdMovimiento, IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, M.IdAlmacenOrigen, AOP.Descripcion + ' - ' + AO.Descripcion AS 'AlmacenOrigen', M.IdAlmacenDestino, ADP.Descripcion + ' - ' +  AD.Descripcion AS 'AlmacenDestino', M.IdConcepto, C.NombreConcepto, M.IdTipoDocumentoMov, TD.NombreTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, M.IdDocumentoOrigen, DO.NombreDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, M.IdProveedor, P.RazonSocial, M.IdTipoCompra, M.IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, TOTAL.Total, M.Activo
	FROM Far_Movimiento M
	INNER JOIN (SELECT IdMovimiento, SUM(Total) Total
				FROM Far_Movimiento_Producto 
				GROUP BY IdMovimiento) TOTAL ON M.IdMovimiento = TOTAL.IdMovimiento
	LEFT JOIN Far_Almacen AO ON AO.IdAlmacen = M.IdAlmacenOrigen
	LEFT JOIN Far_Almacen AD ON AD.IdAlmacen = M.IdAlmacenDestino
	INNER JOIN Far_Concepto C ON C.IdConcepto = M.IdConcepto
	LEFT JOIN Far_Tipo_Documento_Mov TD ON TD.IdTipoDocumentoMov = M.IdTipoDocumentoMov
	LEFT JOIN Far_Documento_Origen DO ON DO.IdDocumentoOrigen = M.IdDocumentoOrigen
	LEFT JOIN Far_Proveedor P ON P.IdProveedor = M.IdProveedor
	LEFT JOIN Far_Almacen AOP ON AOP.IdAlmacen = AO.IdAlmacenPadre
	LEFT JOIN Far_Almacen ADP ON ADP.IdAlmacen = AD.IdAlmacenPadre
	WHERE M.IdMovimiento = @IdMovimiento
GO

CREATE PROCEDURE Far_Movimiento_ListarPorIdIngreso
@IdMovimientoIngreso INT
AS
	SELECT M.IdMovimiento, IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, M.IdAlmacenOrigen, AO.Descripcion AS 'AlmacenOrigen', M.IdAlmacenDestino, AD.Descripcion AS 'AlmacenDestino', M.IdConcepto, C.NombreConcepto, M.IdTipoDocumentoMov, TD.NombreTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, M.IdDocumentoOrigen, DO.NombreDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, M.IdProveedor, P.RazonSocial, M.IdTipoCompra, M.IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, TOTAL.Total, M.Activo
	FROM Far_Movimiento M
	INNER JOIN (SELECT IdMovimiento, SUM(Total) Total
				FROM Far_Movimiento_Producto 
				GROUP BY IdMovimiento) TOTAL ON M.IdMovimiento = TOTAL.IdMovimiento
	LEFT JOIN Far_Almacen AO ON AO.IdAlmacen = M.IdAlmacenOrigen
	LEFT JOIN Far_Almacen AD ON AD.IdAlmacen = M.IdAlmacenDestino
	INNER JOIN Far_Concepto C ON C.IdConcepto = M.IdConcepto
	LEFT JOIN Far_Tipo_Documento_Mov TD ON TD.IdTipoDocumentoMov = M.IdTipoDocumentoMov
	LEFT JOIN Far_Documento_Origen DO ON DO.IdDocumentoOrigen = M.IdDocumentoOrigen
	LEFT JOIN Far_Proveedor P ON P.IdProveedor = M.IdProveedor
	WHERE IdMovimientoIngreso = @IdMovimientoIngreso
GO

CREATE PROCEDURE Far_Movimiento_Numero_Ingreso
@IdPeriodo INTEGER,
@IdAlmacenDestino INTEGER,
@NumeroMovimiento INTEGER OUTPUT
AS
	SELECT @NumeroMovimiento = ISNULL(MAX(NumeroMovimiento), 0) + 1
	FROM Far_Movimiento
	WHERE SUBSTRING(CAST(IdPeriodo AS VARCHAR), 1, 4) = SUBSTRING(CAST(@IdPeriodo AS VARCHAR), 1, 4)	
	AND IdAlmacenDestino = @IdAlmacenDestino
	AND TipoMovimiento = 'I'
GO

CREATE PROCEDURE Far_Movimiento_Numero_Salida
@IdPeriodo INTEGER,
@IdAlmacenOrigen INTEGER,
@NumeroMovimiento INTEGER OUTPUT
AS
	SELECT @NumeroMovimiento = ISNULL(MAX(NumeroMovimiento), 0) + 1
	FROM Far_Movimiento
	WHERE SUBSTRING(CAST(IdPeriodo AS VARCHAR), 1, 4) = SUBSTRING(CAST(@IdPeriodo AS VARCHAR), 1, 4)	
	AND IdAlmacenOrigen = @IdAlmacenOrigen
	AND TipoMovimiento = 'S'
GO

CREATE PROCEDURE Far_Movimiento_TarjetaKardex 
@IdPeriodo INT,
@FechaDesde DATETIME,
@FechaHasta DATETIME,
@IdAlmacen INT,
@IdProducto INT
AS
	DECLARE @CantidadInicial INT;
	DECLARE @CantidadIngreso INT;
	DECLARE @CantidadSalida INT;
	DECLARE @ConceptoInicial INT;
	DECLARE @SaldoInicial INT;

	SELECT @ConceptoInicial = Valor FROM Far_Parametro WHERE NombreParametro = 'INICIO'

	SELECT @CantidadInicial = SUM(MOV_PRO.Cantidad)
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento
	AND IdPeriodo = @IdPeriodo
	AND IdConcepto = @ConceptoInicial
	AND MOV.IdAlmacenDestino = @IdAlmacen	
	WHERE IdProducto = @IdProducto

	SELECT @CantidadIngreso = SUM(Cantidad)
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento 
	AND MOV.TipoMovimiento = 'I'
	AND IdPeriodo = @IdPeriodo
	AND MOV.IdAlmacenDestino = @IdAlmacen
	AND IdConcepto != @ConceptoInicial	
	WHERE IdProducto = @IdProducto	
	AND @FechaDesde > MOV.FechaRegistro

	SELECT @CantidadSalida = SUM(Cantidad)
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Movimiento MOV ON MOV_PRO.IdMovimiento = MOV.IdMovimiento 
	AND MOV.TipoMovimiento = 'S' 
	AND MOV.IdAlmacenOrigen = @IdAlmacen
	AND IdPeriodo = @IdPeriodo	
	WHERE IdProducto = @IdProducto
	AND @FechaDesde > MOV.FechaRegistro

	IF(@CantidadInicial IS NULL)
	BEGIN
		SET @CantidadInicial = 0
	END

	IF(@CantidadIngreso IS NULL)
	BEGIN
		SET @CantidadIngreso = 0
	END

	IF(@CantidadSalida IS NULL)
	BEGIN
		SET @CantidadSalida = 0
	END
	
	SET @SaldoInicial = (@CantidadInicial + @CantidadIngreso) - @CantidadSalida;

	WITH Kardex AS (
		SELECT ROW_NUMBER() OVER(ORDER BY FechaRegistro ASC) AS NumTransaction, MOV.FechaRegistro, MOV.TipoMovimiento, MOV.NumeroMovimiento, PRO.Descripcion 'DescripcionProducto', CON.NombreConcepto, 
				MOV_PRO.Lote, MOV_PRO.FechaVencimiento,
				CASE WHEN TipoMovimiento = 'I' THEN MOV_PRO.Cantidad ELSE 0 END AS 'Ingresos', 
				CASE WHEN TipoMovimiento = 'S' THEN MOV_PRO.Cantidad ELSE 0 END AS 'Salidas'
		FROM Far_Movimiento MOV
		INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
		INNER JOIN Far_Concepto CON ON MOV.IdConcepto = CON.IdConcepto
		INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
		WHERE MOV.IdPeriodo = @IdPeriodo
		AND ((IdAlmacenOrigen = @IdAlmacen AND TipoMovimiento = 'S')
								OR (IdAlmacenDestino = @IdAlmacen AND TipoMovimiento = 'I'))
		AND MOV_PRO.IdProducto = @IdProducto		
		AND (CONVERT(DATE, MOV.FechaRegistro) >= @FechaDesde OR @FechaDesde IS NULL)
		AND (@FechaHasta >= CONVERT(DATE,MOV.FechaRegistro) OR @FechaHasta IS NULL)
	)	

	SELECT NumTransaction, FechaRegistro, TipoMovimiento, NumeroMovimiento, DescripcionProducto, NombreConcepto,
			Lote, FechaVencimiento, CASE 
										WHEN KARDEX_MAIN.NumTransaction = 1 THEN @SaldoInicial
										ELSE (SELECT SUM(Ingresos) - SUM(Salidas) + @SaldoInicial
											FROM Kardex KARDEX_CHILD
											WHERE KARDEX_CHILD.NumTransaction < KARDEX_MAIN.NumTransaction) 
									END AS 'SaldoInicial', 
									Ingresos, Salidas, (SELECT SUM(Ingresos) - SUM(Salidas) + @SaldoInicial
														FROM Kardex KARDEX_CHILD
														WHERE KARDEX_CHILD.NumTransaction <= KARDEX_MAIN.NumTransaction) AS 'Saldos'
														 
	FROM Kardex KARDEX_MAIN
GO

CREATE PROCEDURE Far_Movimiento_IngresoPorAlmacen
@IdAlmacenOrigen INT,
@IdAlmacenDestino INT,
@FechaDesde DATETIME,
@FechaHasta DATETIME,
@IdPeriodo INT,
@IdProveedor INT
AS
	SELECT MOV.FechaRegistro, PRO_SIS.CodigoSismed, Descripcion
	FROM Far_Movimiento MOV
	INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	LEFT JOIN Far_Producto_Sismed PRO_SIS ON PRO.IdProductoSismed = PRO_SIS.IdProductoSismed
	WHERE MOV.IdAlmacenDestino = @IdAlmacenDestino
	AND IdPeriodo = @IdPeriodo
	AND TipoMovimiento = 'I'
	AND (IdProveedor = @IdProveedor OR @IdProveedor = 0)
	AND (MOV.IdAlmacenOrigen = @IdAlmacenOrigen OR @IdAlmacenOrigen = 0)
	AND (MOV.FechaRegistro >= @FechaDesde OR @FechaDesde IS NULL)
	AND (@FechaHasta >= MOV.FechaRegistro OR @FechaHasta IS NULL)
GO

CREATE PROCEDURE Far_Movimiento_Insertar
@IdPeriodo INT,
@TipoMovimiento CHAR(1),
@NumeroMovimiento INT,
@FechaRegistro DATETIME,
@IdAlmacenOrigen INT,
@IdAlmacenDestino INT,
@IdConcepto INT,
@IdTipoDocumentoMov INT,
@NumeroDocumentoMov VARCHAR(20),
@FechaRecepcion DATETIME,
@IdDocumentoOrigen INT,
@NumeroDocumentoOrigen VARCHAR(20),
@FechaDocumentoOrigen DATETIME,
@IdProveedor INT,
@IdTipoCompra INT,
@IdTipoProceso INT,
@NumeroProceso VARCHAR(20),
@Referencia VARCHAR(20),
@IdMovimientoIngreso INT,
@Activo INT,
@UsuarioCreacion INT,
@IdMovimiento INT OUTPUT
AS	
	INSERT INTO Far_Movimiento(IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, IdAlmacenOrigen, IdAlmacenDestino, IdConcepto, IdTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, IdDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, IdProveedor, IdTipoCompra, IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdPeriodo, @TipoMovimiento, @NumeroMovimiento, @FechaRegistro, @IdAlmacenOrigen, @IdAlmacenDestino, @IdConcepto, @IdTipoDocumentoMov, @NumeroDocumentoMov, @FechaRecepcion, @IdDocumentoOrigen, @NumeroDocumentoOrigen, @FechaDocumentoOrigen, @IdProveedor, @IdTipoCompra, @IdTipoProceso, @NumeroProceso, @Referencia, @IdMovimientoIngreso, @Activo, @UsuarioCreacion, GETDATE())

	SELECT @IdMovimiento = SCOPE_IDENTITY()	
GO

CREATE PROCEDURE Far_Movimiento_Modificar
@IdMovimiento INT,
@IdPeriodo INT,
@TipoMovimiento CHAR(1),
@NumeroMovimiento INT,
@FechaRegistro DATETIME,
@IdAlmacenOrigen INT,
@IdAlmacenDestino INT,
@IdConcepto INT,
@IdTipoDocumentoMov INT,
@NumeroDocumentoMov VARCHAR(20),
@FechaRecepcion DATETIME,
@IdDocumentoOrigen INT,
@NumeroDocumentoOrigen VARCHAR(20),
@FechaDocumentoOrigen DATETIME,
@IdProveedor INT,
@IdTipoCompra INT,
@IdTipoProceso INT,
@NumeroProceso VARCHAR(20),
@Referencia VARCHAR(20),
@IdMovimientoIngreso INT,
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Movimiento
	SET IdPeriodo = @IdPeriodo, 
	TipoMovimiento = @TipoMovimiento, 
	NumeroMovimiento = @NumeroMovimiento, 
	FechaRegistro = @FechaRegistro, 
	IdAlmacenOrigen = @IdAlmacenOrigen, 
	IdAlmacenDestino = @IdAlmacenDestino, 
	IdConcepto = @IdConcepto, 
	IdTipoDocumentoMov = @IdTipoDocumentoMov, 
	NumeroDocumentoMov = @NumeroDocumentoMov, 
	FechaRecepcion = @FechaRecepcion, 
	IdDocumentoOrigen = @IdDocumentoOrigen, 
	NumeroDocumentoOrigen = @NumeroDocumentoOrigen, 
	FechaDocumentoOrigen = @FechaDocumentoOrigen,		
	IdProveedor = @IdProveedor, 
	IdTipoCompra = @IdTipoCompra,
	IdTipoProceso = @IdTipoProceso, 
	NumeroProceso = @NumeroProceso,
	Referencia = @Referencia,
	IdMovimientoIngreso = @IdMovimientoIngreso,
	Activo = @Activo,
	UsuarioModificacion = @UsuarioModificacion, 
	FechaModificacion = GETDATE()
	WHERE IdMovimiento = @IdMovimiento
GO

CREATE PROCEDURE Far_Movimiento_Eliminar
@IdMovimiento INT
AS
	DELETE FROM Far_Movimiento
	WHERE IdMovimiento = @IdMovimiento
GO

CREATE PROCEDURE Far_Movimiento_Producto_Insertar
@IdMovimiento INT,
@IdProducto INT,
@Cantidad INT,
@Precio DECIMAL(14,8),
@Total DECIMAL(14,8),
@Lote VARCHAR(20),
@FechaVencimiento DATETIME,
@RegistroSanitario VARCHAR(20),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Movimiento_Producto(IdMovimiento, IdProducto, Cantidad, Precio, Total, Lote, FechaVencimiento, RegistroSanitario, Activo, UsuarioCreacion, FechaCreacion)
	VALUES(@IdMovimiento, @IdProducto, @Cantidad, @Precio, @Total, @Lote, @FechaVencimiento, @RegistroSanitario, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Movimiento_Producto_ListarPorMov
@IdMovimiento INT
AS
	SELECT IdMovimientoProducto, IdMovimiento, MOV_PRO.IdProducto, PRO.Descripcion, Cantidad, Precio, Total, Lote, FechaVencimiento, RegistroSanitario
	FROM Far_Movimiento_Producto MOV_PRO
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	WHERE IdMovimiento = @IdMovimiento
GO

CREATE PROCEDURE Far_Movimiento_Producto_EliminarPorMov
@IdMovimiento INT
AS
	DELETE FROM Far_Movimiento_Producto
	WHERE IdMovimiento = @IdMovimiento
GO

CREATE PROCEDURE Far_Parametro_Listar
AS	
	SELECT IdParametro, NombreParametro, DescripcionParametro, Valor
	FROM Far_Parametro	
GO

CREATE PROCEDURE Far_Parametro_ListarSinDependencia
AS	
	SELECT IdParametro, NombreParametro, DescripcionParametro, Valor
	FROM Far_Parametro	
	WHERE IdParametro NOT IN(1,2,3,4)
GO

CREATE PROCEDURE Far_Parametro_ListarDependencia
AS	
	SELECT IdParametro, NombreParametro, DescripcionParametro, Valor
	FROM Far_Parametro	
	WHERE IdParametro IN(1,2,3,4)
	ORDER BY IdParametro
GO

CREATE PROCEDURE Far_Parametro_PorNombre
@NombreParametro VARCHAR(70)
AS
	SELECT IdParametro, NombreParametro, DescripcionParametro, Valor
	FROM Far_Parametro
	WHERE UPPER(NombreParametro) = UPPER(@NombreParametro)
GO

CREATE PROCEDURE Far_Parametro_ListarPorId
@IdParametro INT
AS
	SELECT IdParametro, NombreParametro, DescripcionParametro, Valor
	FROM Far_Parametro
	WHERE IdParametro = @IdParametro
GO

CREATE PROCEDURE Far_Parametro_Insertar
@NombreParametro VARCHAR(70),
@DescripcionParametro VARCHAR(120),
@Valor VARCHAR(250)
AS
	INSERT INTO Far_Parametro(NombreParametro, DescripcionParametro, Valor) 
	VALUES(@NombreParametro, @DescripcionParametro, @Valor)
GO

CREATE PROCEDURE Far_Parametro_Modificar
@IdParametro INT,
@NombreParametro VARCHAR(70),
@DescripcionParametro VARCHAR(120),
@Valor VARCHAR(250)
AS
	UPDATE Far_Parametro
	SET NombreParametro = @NombreParametro, 
		DescripcionParametro = @DescripcionParametro,
		Valor = @Valor
	WHERE IdParametro = @IdParametro
GO

CREATE PROCEDURE Far_Producto_Siga_Listar
AS
	SELECT IdProductoSiga, CodigoSiga, NombreProductoSiga, Activo
	FROM Far_Producto_Siga
GO

CREATE PROCEDURE Far_Producto_Siga_PorId
@IdProductoSiga INT
AS
	SELECT IdProductoSiga, CodigoSiga, NombreProductoSiga, Activo
	FROM Far_Producto_Siga
	WHERE IdProductoSiga = @IdProductoSiga
GO

CREATE PROCEDURE Far_Producto_Siga_EsUsado
@IdProductoSiga INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Producto WHERE IdProductoSiga = @IdProductoSiga))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Producto_Siga_Insertar
@CodigoSiga VARCHAR(20),
@NombreProductoSiga VARCHAR(70),
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Producto_Siga(CodigoSiga, NombreProductoSiga, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@CodigoSiga, @NombreProductoSiga, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Producto_Siga_Modificar
@IdProductoSiga INT,
@CodigoSiga VARCHAR(20),
@NombreProductoSiga VARCHAR(70),
@Activo INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Producto_Siga
	SET CodigoSiga = @CodigoSiga,
		NombreProductoSiga = @NombreProductoSiga,
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdProductoSiga = @IdProductoSiga
GO

CREATE PROCEDURE Far_Producto_Siga_Eliminar
@IdProductoSiga INT
AS
	DELETE FROM Far_Producto_Siga
	WHERE IdProductoSiga = @IdProductoSiga
GO

CREATE PROCEDURE Far_Producto_Sismed_Listar
AS
	SELECT IdProductoSismed, CodigoSismed, NombreProductoSismed, Activo
	FROM Far_Producto_Sismed
GO

CREATE PROCEDURE Far_Producto_Sismed_PorId
@IdProductoSismed INT
AS
	SELECT IdProductoSismed, CodigoSismed, NombreProductoSismed, Activo
	FROM Far_Producto_Sismed
	WHERE IdProductoSismed = @IdProductoSismed
GO

CREATE PROCEDURE Far_Producto_Sismed_EsUsado
@IdProductoSismed INT,
@EsUsado BIT OUT
AS

SET @EsUsado = 0

IF EXISTS(SELECT 1
		 WHERE EXISTS(SELECT 1 FROM Far_Producto WHERE IdProductoSismed = @IdProductoSismed))
 BEGIN
	SET @EsUsado = 1
 END

 GO

CREATE PROCEDURE Far_Producto_Sismed_Insertar
@CodigoSismed VARCHAR(20), 
@NombreProductoSismed VARCHAR(70), 
@Activo INT,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Producto_Sismed(CodigoSismed, NombreProductoSismed, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@CodigoSismed, @NombreProductoSismed, @Activo, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Producto_Sismed_Modificar
@IdProductoSismed INT, 
@CodigoSismed VARCHAR(20), 
@NombreProductoSismed VARCHAR(70), 
@Activo INT,
@UsuarioModificacion INT
AS

	UPDATE Far_Producto_Sismed
	SET CodigoSismed = @CodigoSismed,
		NombreProductoSismed = @NombreProductoSismed,
		Activo = @Activo,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdProductoSismed = @IdProductoSismed
GO

CREATE PROCEDURE Far_Producto_Sismed_Eliminar
@IdProductoSismed INT
AS
	DELETE FROM Far_Producto_Sismed
	WHERE IdProductoSismed = @IdProductoSismed
GO

CREATE PROCEDURE Far_Solicitud_Listar
AS
	SELECT a.IdSolicitud, a.IdMedico, b.NOMBRES+' '+b.APELLIDO_PATERNO+' '+b.APELLIDO_MATERNO as Medico, a.Establecimiento, a.Institucion, a.Fecha, a.Extension, a.Activo, a.Motivo, a.Justificacion, a.Motivo, a.Justificacion, a.ExisteMedicamento
	FROM Far_Solicitud a
	INNER JOIN [HVitarteBD].[dbo].[PER_PERSONAL] b ON b.PERSONAL = a.IdMedico AND b.NRO_COLEGIATURA <> ''
GO

CREATE PROCEDURE Far_Solicitud_Insertar
@IdMedico VARCHAR(6),
@Motivo VARCHAR(100),
@Justificacion VARCHAR(100),
@Institucion VARCHAR(100),
@Establecimiento VARCHAR(100),
@Fecha DATETIME,
@Extension VARCHAR(10),
@Activo INT,
@ExisteMedicamento INT,
@UsuarioCreacion INT,
@IdSolicitud INT OUT
AS
	INSERT INTO Far_Solicitud(IdMedico, Motivo, Justificacion, Institucion, Establecimiento, Fecha, Extension, Activo, ExisteMedicamento, UsuarioCreacion, FechaCreacion)
	VALUES (@IdMedico, @Motivo, @Justificacion, @Institucion, @Establecimiento, @Fecha, @Extension, @Activo, @ExisteMedicamento, @UsuarioCreacion, GETDATE())
	
	SET @idSolicitud = SCOPE_IDENTITY();
GO

CREATE PROCEDURE [dbo].[Far_Solicitud_Modificar]
@IdSolicitud INT,
@IdMedico VARCHAR(6),
@Motivo VARCHAR(100),
@Justificacion VARCHAR(100),
@Institucion VARCHAR(100),
@Establecimiento VARCHAR(100),
@Fecha DATETIME,
@Extension VARCHAR(10),
@Activo INT,
@ExisteMedicamento INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Solicitud
	SET	IdMedico = @IdMedico,	
		Motivo = @Motivo, 
		Justificacion = @Justificacion,
		Institucion = @Institucion,
		Establecimiento = @Establecimiento,
		Fecha = @Fecha,	
		Extension = @Extension,
		Activo = @Activo,
		ExisteMedicamento = @ExisteMedicamento,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdSolicitud = @IdSolicitud
GO

CREATE PROCEDURE [dbo].[Far_Solicitud_Eliminar]
@IdSolicitud INT
AS
	DELETE FROM Far_Detalle_Solicitud_Producto
	WHERE IdSolicitud = @IdSolicitud
	
	DELETE FROM Far_Solicitud
	WHERE IdSolicitud = @IdSolicitud
GO

CREATE PROCEDURE Far_MedicamentoSolicitud_Listar
@IdSolicitud INT,
@TipoMedicamento INT
AS
	SELECT a.[IdSolicitudDetalleProducto],b.[Descripcion],a.[DescripcionMedicamento],a.Activo, a.IdSolicitud, a.IdProducto,c.NombreFormaFarmaceutica,b.Concentracion, a.Aprobado, a.CantidadAprobada, a.CondicionAprobado, a.MotivoAprobado, a.Extension
	FROM [Farmacia].[dbo].[Far_Detalle_Solicitud_Producto] a
	INNER JOIN [Farmacia].[dbo].[Far_Producto] b ON a.[IdProducto] = b.[IdProducto]
	LEFT JOIN [Farmacia].[dbo].[Far_Forma_Farmaceutica] c ON c.[IdFormaFarmaceutica] = b.[IdFormaFarmaceutica]
	WHERE [IdSolicitud] = @IdSolicitud and [TipoMedicamento] = @TipoMedicamento and a.[Activo] = 1
	ORDER BY 2 asc
GO

CREATE PROCEDURE Far_BalanceSemestral_Listar
@Fecha VARCHAR(8)
AS
	select a.IdSolicitud, b.IdSolicitudDetalleProducto,a.Institucion, a.Establecimiento, b.FechaAprobacion, case when b.Aprobado = 1 then 'Aprobado' else 'No Aprobado' end as Aprobado,
	c.Concentracion, d.NombreFormaFarmaceutica, c.Descripcion, b.DescripcionMedicamento, c.Presentacion, b.CantidadAprobada, b.MotivoAprobado, b.CondicionAprobado, b.FechaCreacion, case when b.TipoMedicamento = 1 then 'Medicamento Solicitado' else 'Medicamento Alternativo' end as TipoMedicamento
	from dbo.Far_Solicitud a 
	inner join dbo.Far_Detalle_Solicitud_Producto b on b.IdSolicitud = a.IdSolicitud and b.Aprobado = 1
	inner join dbo.Far_Producto c on c.IdProducto = b.IdProducto
	left join dbo.Far_Forma_Farmaceutica d on d.IdFormaFarmaceutica = c.IdFormaFarmaceutica
	where a.Activo = 1 and CONVERT(VARCHAR(10), b.FechaAprobacion, 112) between CONVERT(VARCHAR(10), DATEADD(month, -6,  CONVERT(DATETIME,b.FechaAprobacion,102)), 112) and @Fecha
	order by 15 desc, 9 asc
GO

CREATE PROCEDURE Far_ConsultaSolMedicamentos_Listar
AS
	select a.IdSolicitud, b.IdSolicitudDetalleProducto,a.Institucion, a.Establecimiento, b.FechaAprobacion, case when b.Aprobado = 1 then 'Aprobado' else 'No Aprobado' end as Aprobado,
	c.Concentracion, d.NombreFormaFarmaceutica, c.Descripcion, b.DescripcionMedicamento, c.Presentacion, b.CantidadAprobada, b.MotivoAprobado, b.CondicionAprobado, b.FechaCreacion, case when b.TipoMedicamento = 1 then 'Medicamento Solicitado' else 'Medicamento Alternativo' end as TipoMedicamento
	from dbo.Far_Solicitud a 
	inner join dbo.Far_Detalle_Solicitud_Producto b on b.IdSolicitud = a.IdSolicitud and b.Aprobado = 1
	inner join dbo.Far_Producto c on c.IdProducto = b.IdProducto
	left join dbo.Far_Forma_Farmaceutica d on d.IdFormaFarmaceutica = c.IdFormaFarmaceutica
	where a.Activo = 1
	order by 15 desc, 9 asc
GO

CREATE PROCEDURE Far_ConsultaSolNoAprobados_Listar
AS
	select a.IdSolicitud, b.IdSolicitudDetalleProducto,a.Institucion, a.Establecimiento, b.FechaAprobacion, case when b.Aprobado = 1 then 'Aprobado' else 'No Aprobado' end as Aprobado,
	c.Concentracion, d.NombreFormaFarmaceutica, c.Descripcion, b.DescripcionMedicamento, c.Presentacion, b.CantidadAprobada, b.MotivoAprobado, b.CondicionAprobado, b.FechaCreacion, case when b.TipoMedicamento = 1 then 'Medicamento Solicitado' else 'Medicamento Alternativo' end as TipoMedicamento
	from dbo.Far_Solicitud a 
	inner join dbo.Far_Detalle_Solicitud_Producto b on b.IdSolicitud = a.IdSolicitud and (b.Aprobado is null or b.Aprobado <> 1)
	inner join dbo.Far_Producto c on c.IdProducto = b.IdProducto
	left join dbo.Far_Forma_Farmaceutica d on d.IdFormaFarmaceutica = c.IdFormaFarmaceutica
	where a.Activo = 1
	order by 9 asc
GO

CREATE PROCEDURE Far_ConsultaSolHistoricos_Listar
@FecIni VARCHAR(8),
@FecFin VARCHAR(8)
AS
	select distinct y.FechaAprobacion,y.descripcion, y.Concentracion, y.Presentacion, y.NombreFormaFarmaceutica,y.anio,
	case when cast(y.mes AS varchar) = '01' then y.cantidad else 0 end enero,
	case when cast(y.mes AS varchar) = '02' then y.cantidad else 0 end febrero,
	case when cast(y.mes AS varchar) = '03' then y.cantidad else 0 end marzo,
	case when cast(y.mes AS varchar) = '04' then y.cantidad else 0 end abril,
	case when cast(y.mes AS varchar) = '05' then y.cantidad else 0 end mayo,
	case when cast(y.mes AS varchar) = '06' then y.cantidad else 0 end junio,
	case when cast(y.mes AS varchar) = '07' then y.cantidad else 0 end julio,
	case when cast(y.mes AS varchar) = '08' then y.cantidad else 0 end agosto,
	case when cast(y.mes AS varchar) = '09' then y.cantidad else 0 end setiembre,
	case when cast(y.mes AS varchar) = '10' then y.cantidad else 0 end octubre,
	case when cast(y.mes AS varchar) = '11' then y.cantidad else 0 end noviembre,
	case when cast(y.mes AS varchar) = '12' then y.cantidad else 0 end diciembre
	from (
	select x.FechaAprobacion,x.anio,case when cast(x.mes AS integer)>9 then cast(x.mes AS varchar) else '0'+cast(x.mes as varchar) end as mes, case when cast(x.dia AS integer)>9 then cast(x.dia AS varchar) else '0'+cast(x.dia as varchar) end as dia, x.descripcion,x.Concentracion, x.Presentacion, x.NombreFormaFarmaceutica,x.CantidadAprobada as cantidad
	from (
	select distinct b.FechaAprobacion,year(a.fecha) as anio,month(a.fecha) as mes,day(a.fecha) as dia,b.IdProducto as producto, c.Descripcion as descripcion, c.Concentracion,c.Presentacion, d.NombreFormaFarmaceutica, b.CantidadAprobada
	from Far_Solicitud a
	inner join Far_Detalle_Solicitud_Producto b on b.IdSolicitud = a.IdSolicitud
	left join Far_Producto c on c.IdProducto = b.IdProducto
	inner join Far_Forma_Farmaceutica d on d.IdFormaFarmaceutica = c.IdFormaFarmaceutica
	where b.Aprobado = 1 and a.Activo = 1) x
	) y 
	where (cast(y.anio AS varchar)+cast(y.mes AS varchar)+cast(y.dia AS varchar)) between @FecIni and @FecFin 
	order by y.FechaAprobacion asc, y.descripcion asc
GO

CREATE PROCEDURE Far_SolicitudDetalle_Insertar
@IdSolicitud INT,
@IdProducto INT,
@DescripcionMedicamento VARCHAR(100),
@TipoMedicamento INT,
@Activo INT,
@UsuarioCreacion INT,
@idSolicitudDetalle INT OUT
AS
	INSERT INTO Far_Detalle_Solicitud_Producto(IdSolicitud, IdProducto, DescripcionMedicamento, TipoMedicamento, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdSolicitud, @IdProducto, @DescripcionMedicamento, @TipoMedicamento, @Activo, @UsuarioCreacion, GETDATE())
	
	SET @idSolicitudDetalle = SCOPE_IDENTITY();
GO

CREATE PROCEDURE Far_SolicitudDetalle_Aprobar
@IdSolicitudDetalleProducto INT,
@IdSolicitud INT,
@CondicionAprobado VARCHAR(100),
@MotivoAprobado VARCHAR(100),
@Extension VARCHAR(10),
@Aprobado INT,
@CantidadAprobada INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Detalle_Solicitud_Producto
	SET	CondicionAprobado = @CondicionAprobado,
		MotivoAprobado = @MotivoAprobado,	
		Aprobado = @Aprobado,
		Extension = @Extension,
		CantidadAprobada = @CantidadAprobada,
		UsuarioModificacion = @UsuarioModificacion,
		FechaAprobacion = GETDATE()
	WHERE IdSolicitudDetalleProducto = @IdSolicitudDetalleProducto
GO

CREATE PROCEDURE Far_SolicitudDetalle_Eliminar
@IdSolicitudDetalleProducto INT
AS
	DELETE FROM Far_Detalle_Solicitud_Producto
	WHERE IdSolicitudDetalleProducto = @IdSolicitudDetalleProducto
GO

CREATE PROCEDURE Far_Movimento_IndicadorGestion
@FechaRegistroDesde DATETIME,
@FechaRegistroHasta DATETIME
AS

	SELECT PRO.IdProducto, PRO.Descripcion, MOV_PRO.Cantidad
	FROM Far_Movimiento MOV
	INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	WHERE MOV.TipoMovimiento = 'S'
	AND @FechaRegistroDesde <= FechaRegistro
	AND @FechaRegistroHasta >= FechaRegistro
GO

CREATE PROCEDURE Far_ICI_DET_Proceso
@IdPeriodo INT,
@IdPeriodoAnterior INT
AS
	DECLARE @IdICI INT

	INSERT INTO Far_ICI(IdPeriodo, IdTipoSuministro)
	VALUES(@IdPeriodo, NULL)
	SELECT @IdICI = SCOPE_IDENTITY()

  INSERT INTO Far_ICI_Detalle(IdICI, IdProducto, PrecioOperacion, SaldoAnterior, Ingresos, Ventas, SIS, IntervSanit, FactPerd, DefensaNacional,
							Exoneracion, SOAT, CreditoHospitalario, OtrosConvenios, Devolucion, Vencido, QuiebreStock, Merma, Otras_Sal, Vencimiento, Requerimiento, Activo)

  SELECT @IdICI, PRO.IdProducto, 0 AS PrecioOperacion, ISNULL(SaldoAnterior, 0) SaldoAnterior, 
		ISNULL(CantidadIngresos, 0) CantidadIngresos, ISNULL(CantidadVentas, 0) CantidadVentas, 
		ISNULL(CantidadSis, 0) CantidadSis, ISNULL(CantidadIntSanitaria, 0) CantidadIntSanitaria, 0 FactPerdida, ISNULL(CantidadDefNacional, 0) CantidadDefNacional,
		ISNULL(CantidadExoneracion, 0) CantidadExoneracion, ISNULL(CantidadSoat, 0) CantidadSoat, ISNULL(CantidadCreHospital, 0) CantidadCreHospital,
		ISNULL(CantidadOtrosConv, 0) CantidadOtrosConv, 0 CantidadDevolucion, 0 CantidadVencidos, ISNULL(CantidadQuiebreStock, 0) CantidadQuiebreStock, 0 CantidadMerma, 0 Otras_Sal, GETDATE() Vencimiento, 0 Requerimiento, 1 Activo  
  FROM Far_Producto PRO
  LEFT JOIN (SELECT SaldoAnterior, IdProducto
			  FROM Far_ICI ICI
			  INNER JOIN Far_ICI_Detalle ICI_DET ON ICI.IdICI = ICI_DET.IdICI
			  WHERE ICI.IdPeriodo = @IdPeriodoAnterior) SALDO ON PRO.IdProducto = SALDO.IdProducto
  LEFT JOIN (SELECT SUM(Cantidad) CantidadIngresos, IdProducto
			  FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.TipoMovimiento = 'I'
			  AND MOV.IdPeriodo = @IdPeriodo			  
			  GROUP BY IdProducto) INGRESOS ON PRO.IdProducto = INGRESOS.IdProducto
 LEFT JOIN (SELECT SUM(Cantidad) CantidadVentas, IdProducto FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 9
			  OR IdConcepto = 10
			  OR IdConcepto = 11
			  GROUP BY IdProducto) VENTAS ON PRO.IdProducto = VENTAS.IdProducto
 LEFT JOIN (SELECT SUM(Cantidad) CantidadSis, IdProducto FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 12
			  GROUP BY IdProducto) SIS ON PRO.IdProducto = SIS.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadIntSanitaria, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'			
			AND IdConcepto = 15
			GROUP BY IdProducto) INT_SANI ON PRO.IdProducto = INT_SANI.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadDefNacional, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 21
			GROUP BY IdProducto) DEF_NAC ON PRO.IdProducto = DEF_NAC.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadExoneracion, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 14
			GROUP BY IdProducto) EXO ON PRO.IdProducto = EXO.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadSoat, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 13
			GROUP BY IdProducto) SOAT ON PRO.IdProducto = SOAT.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadCreHospital, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 16
			GROUP BY IdProducto) CRE_HOSP ON PRO.IdProducto = CRE_HOSP.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadOtrosConv, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 22
			GROUP BY IdProducto) OTROS_CON ON PRO.IdProducto = OTROS_CON.IdProducto
LEFT JOIN (SELECT SUM(Cantidad) CantidadQuiebreStock, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 27
			GROUP BY IdProducto) QUIEBRE_STOCK ON PRO.IdProducto = QUIEBRE_STOCK.IdProducto
GO

CREATE PROCEDURE Far_ICI_DET_Consulta
@FechaDesde DATETIME,
@FechaHasta DATETIME
AS

  SELECT 1 IdICI_Detalle, 1 IdICI, PRO.IdProducto, PRO.Descripcion, UM.NombreUnidadMedida, 
		0 AS PrecioOperacion, 
		0 SaldoAnterior, 
		ISNULL(CantidadIngresos, 0) Ingresos, 
		ISNULL(CantidadVentas, 0) Ventas, 
		ISNULL(CantidadSis, 0) SIS,
		ISNULL(CantidadIntSanitaria, 0) IntervSanit, 
		0 FactPerd, 
		ISNULL(CantidadDefNacional, 0) DefensaNacional,
		ISNULL(CantidadExoneracion, 0) Exoneracion, 
		ISNULL(CantidadSoat, 0) SOAT, 
		ISNULL(CantidadCreHospital, 0) CreditoHospitalario,
		ISNULL(CantidadOtrosConv, 0) OtrosConvenios, 
		0 Devolucion, 
		0 Vencido, 
		ISNULL(CantidadQuiebreStock, 0) QuiebreStock,		
		0 Merma, 
		0 Otras_Sal, 
		GETDATE() Vencimiento, 
		0 Requerimiento, 
		1 Activo  
  FROM Far_Producto PRO  
	LEFT JOIN (SELECT SUM(Cantidad) CantidadIngresos, IdProducto
				  FROM Far_Movimiento MOV
				  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				  WHERE MOV.TipoMovimiento = 'I'
				  AND MOV.FechaRegistro >= @FechaDesde
				  AND MOV.FechaRegistro <= @FechaHasta			  
				  GROUP BY IdProducto) INGRESOS ON PRO.IdProducto = INGRESOS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadVentas, IdProducto FROM Far_Movimiento MOV
				  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				  WHERE MOV.FechaRegistro >= @FechaDesde
				  AND MOV.FechaRegistro <= @FechaHasta
				  AND MOV.TipoMovimiento = 'S'			  
				  AND IdConcepto = 9
				  OR IdConcepto = 10
				  OR IdConcepto = 11
				  GROUP BY IdProducto) VENTAS ON PRO.IdProducto = VENTAS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadSis, IdProducto FROM Far_Movimiento MOV
				  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				  WHERE MOV.FechaRegistro >= @FechaDesde
				  AND MOV.FechaRegistro <= @FechaHasta
				  AND MOV.TipoMovimiento = 'S'			  
				  AND IdConcepto = 12
				  GROUP BY IdProducto) SIS ON PRO.IdProducto = SIS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadIntSanitaria, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'			
				AND IdConcepto = 15
				GROUP BY IdProducto) INT_SANI ON PRO.IdProducto = INT_SANI.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadDefNacional, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 21
				GROUP BY IdProducto) DEF_NAC ON PRO.IdProducto = DEF_NAC.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadExoneracion, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 14
				GROUP BY IdProducto) EXO ON PRO.IdProducto = EXO.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadSoat, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 13
				GROUP BY IdProducto) SOAT ON PRO.IdProducto = SOAT.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadCreHospital, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 16
				GROUP BY IdProducto) CRE_HOSP ON PRO.IdProducto = CRE_HOSP.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadOtrosConv, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 22
				GROUP BY IdProducto) OTROS_CON ON PRO.IdProducto = OTROS_CON.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadQuiebreStock, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 27
				GROUP BY IdProducto) QUIEBRE_STOCK ON PRO.IdProducto = QUIEBRE_STOCK.IdProducto
	INNER JOIN Far_Unidad_Medida UM ON PRO.IdUnidadMedida = UM.IdUnidadMedida
GO

CREATE PROCEDURE Far_ICI_ListarPorPeriodo
@IdPeriodo INT
AS
	SELECT IdICI, IdTipoSuministro, IdPeriodo 
	FROM Far_ICI
	WHERE IdPeriodo = @IdPeriodo
GO

CREATE PROCEDURE Far_ICI_DET_ListarPorIci
@IdICI INT
AS
	SELECT IdICI_Detalle,
      IdICI,
      ICI_DET.IdProducto,
	  PRO.Descripcion, 
	  UM.NombreUnidadMedida,
      PrecioOperacion,
      SaldoAnterior,
      Ingresos,
      Ventas,
      SIS,
      IntervSanit,
      FactPerd,
      DefensaNacional,
      Exoneracion,
      SOAT,
      CreditoHospitalario,
      OtrosConvenios,
      Devolucion,
      Vencido,
	  QuiebreStock,
      Merma,
      Otras_Sal,
      Vencimiento,
      ICI_DET.Requerimiento,
      ICI_DET.Activo
  FROM Far_ICI_Detalle ICI_DET
  INNER JOIN Far_Producto PRO ON PRO.IdProducto = ICI_DET.IdProducto
  INNER JOIN Far_Unidad_Medida UM ON PRO.IdUnidadMedida = UM.IdUnidadMedida
  WHERE IdICI = @IdICI
GO

CREATE PROCEDURE Far_IDI_ListarPorPeriodo
@IdPeriodo INT
AS
	SELECT IdIDI, IdTipoSuministro, IdPeriodo 
	FROM Far_IDI
	WHERE IdPeriodo = @IdPeriodo
GO

CREATE PROCEDURE Far_IDI_Det_Proceso
@IdPeriodo INT,
@IdPeriodoAnterior INT
AS
	DECLARE @IdIDI INT

	INSERT INTO Far_IDI(IdPeriodo, IdTipoSuministro)
	VALUES(@IdPeriodo, NULL)
	SELECT @IdIDI = SCOPE_IDENTITY()

	INSERT INTO Far_IDI_Detalle(IdIDI, IdProducto, PrecioOperacion, SaldoAnterior, Ingresos, Reingresos, Distribucion, Transferecia, Vencido, QuiebreStock, Merma, Venta, Exoneracion, OtrasSal, Vencimiento, Activo)
	SELECT @IdIDI, PRO.IdProducto, 0 AS PrecioOperacion, ISNULL(SaldoAnterior, 0) SaldoAnterior, ISNULL(CantidadIngresos, 0) CantidadIngresos, 
	0 CantidadReingresos, ISNULL(CantidadDistribuciones, 0) CantidadDistribuciones, ISNULL(CantidadTransferencias, 0) CantidadTransferencias, 0 CantidadVencidos, 
	ISNULL(CantidadQuiebreStock, 0) CantidadQuiebreStock, 0 CantidadMerma, 0 CantidadVentas, ISNULL(CantidadExoneraciones, 0), 0 OtrasSal,  GETDATE() Vencimiento, 1
	FROM Far_Producto PRO
	LEFT JOIN (SELECT SaldoAnterior, IdProducto
			  FROM Far_IDI IDI
			  INNER JOIN Far_IDI_Detalle IDI_DET ON IDI.IdIDI = IDI_DET.IdIDI
			  WHERE IDI.IdPeriodo = @IdPeriodoAnterior) SALDO ON PRO.IdProducto = SALDO.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadIngresos, IdProducto
			  FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.TipoMovimiento = 'I'
			  AND MOV.IdPeriodo = @IdPeriodo			  
			  GROUP BY IdProducto) INGRESOS ON PRO.IdProducto = INGRESOS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadDistribuciones, IdProducto 
			  FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 4
			  GROUP BY IdProducto) DISTRIBUCIONES ON PRO.IdProducto = DISTRIBUCIONES.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadTransferencias, IdProducto 
		      FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 23
			  AND IdConcepto = 8
			  GROUP BY IdProducto) TRANSFERENCIAS ON PRO.IdProducto = TRANSFERENCIAS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadVentas, IdProducto 
			  FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 9
			  OR IdConcepto = 10
			  OR IdConcepto = 11
			  GROUP BY IdProducto) VENTAS ON PRO.IdProducto = VENTAS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadExoneraciones, IdProducto 
			  FROM Far_Movimiento MOV
			  INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			  WHERE MOV.IdPeriodo = @IdPeriodo
			  AND MOV.TipoMovimiento = 'S'			  
			  AND IdConcepto = 14
			  GROUP BY IdProducto) EXONERACIONES ON PRO.IdProducto = EXONERACIONES.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadQuiebreStock, IdProducto FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.IdPeriodo = @IdPeriodo
			AND MOV.TipoMovimiento = 'S'
			AND IdConcepto = 27
			GROUP BY IdProducto) QUIEBRE_STOCK ON PRO.IdProducto = QUIEBRE_STOCK.IdProducto
GO

CREATE PROCEDURE Far_IDI_Det_Consulta
@FechaDesde DATETIME,
@FechaHasta DATETIME
AS

	SELECT 1 IdIDI_Detalle, 1 IdIDI, 
			PRO.IdProducto, TP.NombreTipoProducto, PRO.Descripcion, FF.NombreFormaFarmaceutica, 
			0 AS PrecioOperacion, 
			0 SaldoAnterior, 
			ISNULL(CantidadIngresos, 0) Ingresos, 
			0 Reingresos, 
			ISNULL(CantidadDistribuciones, 0) Distribucion, 
			ISNULL(CantidadTransferencias, 0) Transferecia, 
			0 Vencido, 
			ISNULL(CantidadQuiebreStock, 0) QuiebreStock, 
			0 Merma, 
			0 Venta, 
			ISNULL(CantidadExoneraciones, 0) Exoneracion, 
			0 OtrasSal,  
			GETDATE() Vencimiento,
			1 Activo
	FROM Far_Producto PRO
	LEFT JOIN (SELECT SUM(Cantidad) CantidadIngresos, IdProducto
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.TipoMovimiento = 'I'
				AND MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				GROUP BY IdProducto) INGRESOS ON PRO.IdProducto = INGRESOS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadDistribuciones, IdProducto 
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'			  
				AND IdConcepto = 4
				GROUP BY IdProducto) DISTRIBUCIONES ON PRO.IdProducto = DISTRIBUCIONES.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadTransferencias, IdProducto 
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'			  
				AND IdConcepto = 23
				AND IdConcepto = 8
			  GROUP BY IdProducto) TRANSFERENCIAS ON PRO.IdProducto = TRANSFERENCIAS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadVentas, IdProducto 
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'			  
				AND IdConcepto = 9
				OR IdConcepto = 10
				OR IdConcepto = 11
				GROUP BY IdProducto) VENTAS ON PRO.IdProducto = VENTAS.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadExoneraciones, IdProducto 
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 14
				GROUP BY IdProducto) EXONERACIONES ON PRO.IdProducto = EXONERACIONES.IdProducto
	LEFT JOIN (SELECT SUM(Cantidad) CantidadQuiebreStock, IdProducto FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				WHERE MOV.FechaRegistro >= @FechaDesde
				AND MOV.FechaRegistro <= @FechaHasta
				AND MOV.TipoMovimiento = 'S'
				AND IdConcepto = 27
				GROUP BY IdProducto) QUIEBRE_STOCK ON PRO.IdProducto = QUIEBRE_STOCK.IdProducto
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
GO

CREATE PROCEDURE Far_IDI_DET_ListarPorIdi
@IdIDI INT
AS
	SELECT IdIDI_Detalle,
			IdIDI, 
			IDI_DET.IdProducto,
			TP.NombreTipoProducto,
			PRO.Descripcion,
			FF.NombreFormaFarmaceutica,
			PrecioOperacion,
			SaldoAnterior,
			Ingresos,
			Reingresos,
			Distribucion,
			Transferecia,
			Vencido,
			QuiebreStock,
			Merma, 
			Venta,
			Exoneracion,
			OtrasSal,
			Vencimiento,
			IDI_DET.Activo
  FROM Far_IDI_Detalle IDI_DET
  INNER JOIN Far_Producto PRO ON PRO.IdProducto = IDI_DET.IdProducto
  INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
  INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
  WHERE IdIDI = @IdIDI  
GO

CREATE PROCEDURE Far_Movimiento_IndicarGestion
@IdTipoProducto INT,
@FechaDesde DATETIME,
@FechaHasta DATETIME
AS
	SELECT PRO.IdProducto, PRO.Descripcion, FF.NombreFormaFarmaceutica, PRO.EstrSop, PRO.Petitorio, MOV.FechaRegistro, MOV_PRO.Cantidad
	FROM Far_Movimiento MOV
	INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	WHERE MOV.TipoMovimiento = 'S'
	AND (PRO.IdTipoProducto = @IdTipoProducto OR @IdTipoProducto = 0)
	AND MOV.FechaRegistro > @FechaDesde AND MOV.FechaRegistro < @FechaHasta
GO

CREATE PROCEDURE Far_Inventario_Existe
@IdAlmacen INT,
@FechaProceso DATETIME,
@IdPeriodo INT,
@Existe BIT OUT
AS

SET @Existe = 0

IF EXISTS(SELECT 1 
			FROM Far_Inventario 
			WHERE IdPeriodo = @IdPeriodo
			AND IdAlmacen = @IdAlmacen
			AND FechaProceso = @FechaProceso)		 
 BEGIN
	SET @Existe = 1
 END

 GO

CREATE PROCEDURE Far_Invertario_Consulta
@IdAlmacen INT,
@FechaProceso DATETIME,
@IdPeriodo INT
AS
	SELECT IdInventario, IdPeriodo, NumeroInventario, IdAlmacen, FechaProceso, FechaCierre, Activo
	FROM Far_Inventario
	WHERE IdPeriodo = @IdPeriodo
	AND IdAlmacen = @IdAlmacen
	AND FechaProceso = @FechaProceso
GO

CREATE PROCEDURE Far_Inventario_Insertar
@IdPeriodo INT,
@NumeroInventario INT,
@IdAlmacen INT,
@FechaProceso DATETIME,
@FechaCierre DATETIME,
@Activo INT,
@UsuarioCreacion INT,
@IdInventario INT OUTPUT
AS
	INSERT INTO Far_Inventario(IdPeriodo, NumeroInventario, IdAlmacen, FechaProceso, FechaCierre, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES(@IdPeriodo, @NumeroInventario, @IdAlmacen, @FechaProceso, @FechaCierre, @Activo, @UsuarioCreacion, GETDATE(), NULL, NULL)

	SELECT @IdInventario = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Inventario_Producto_PorProductoInventario 
@IdProducto INT,
@IdInventario INT
AS
	SELECT IdInventarioProducto, IdInventario, IdProducto, Lote, FechaVencimiento, Cantidad, Precio, Total, Conteo, CantidadFaltante, CantidadSobrante, CantidadAlterado, Activo
	FROM Far_Inventario_Producto
	WHERE @IdInventario = IdInventario
	AND IdProducto = @IdProducto
GO

CREATE PROCEDURE Far_Inventario_Producto_PorId
@IdInventarioProducto INT
AS
	SELECT IdInventarioProducto, IdInventario, IdProducto, Lote, FechaVencimiento, Cantidad, Precio, Total, Conteo, CantidadFaltante, CantidadSobrante, CantidadAlterado, Activo
	FROM Far_Inventario_Producto
	WHERE @IdInventarioProducto = IdInventarioProducto
GO

CREATE PROCEDURE Far_Inventario_Producto_Preparar
@IdInventario INT,
@UsuarioCreacion INT
AS

	DECLARE @IdPeriodo INT
	DECLARE @IdAlmacen INT
	DECLARE @FechaProceso DATETIME

	SELECT @FechaProceso = FechaProceso,
			@IdPeriodo = IdPeriodo,
			@IdAlmacen = IdAlmacen
	FROM Far_Inventario 
	WHERE IdInventario = @IdInventario

	INSERT INTO Far_Inventario_Producto(IdInventario, IdProducto, Lote, FechaVencimiento, Cantidad, Precio, Total, Conteo, CantidadFaltante, CantidadSobrante, CantidadAlterado, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	
	SELECT @IdInventario AS 'IdInventario', PRO.IdProducto, Lote, FechaVencimiento, Cantidad, Precio, Cantidad * Precio AS 'Total', 0 Conteo, Cantidad AS 'CantidadFaltante', 0 AS 'CantidadSobrante', 0 AS 'CantidadAlterado', 1 AS 'Activo', @UsuarioCreacion AS 'UsuarioCreacion', GETDATE() 'FechaCreacion', NULL 'UsuarioModificacion', NULL 'FechaModificacion'
	FROM (SELECT IdProducto
			FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			WHERE MOV.TipoMovimiento = 'I'
			AND MOV.IdAlmacenDestino = @IdAlmacen
			GROUP BY IdProducto) MOV_PRO
	INNER JOIN Far_Producto PRO ON MOV_PRO.IdProducto = PRO.IdProducto
	LEFT JOIN (SELECT PRO.IdProducto, SUM(CASE
							WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
							THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
							ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
						   END) Cantidad, Lote, Precio, FechaVencimiento
				FROM Far_Producto PRO
				LEFT JOIN 
				(SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto, Lote, Precio, FechaVencimiento
					FROM Far_Movimiento MOV
					INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
					AND IdPeriodo = @IdPeriodo
					WHERE FechaRegistro <= @FechaProceso) MOV_PRO_TEMP ON PRO.IdProducto = MOV_PRO_TEMP.IdProducto	
	 			GROUP BY PRO.IdProducto, Lote, Precio, FechaVencimiento) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto
	WHERE Lote IS NOT NULL
GO

CREATE PROCEDURE Far_Inventario_Producto_ListarTotales
@IdInventario INT
AS
	SELECT INV_PRO.IdProducto, PRO.Descripcion, FF.NombreFormaFarmaceutica, Cantidad, 0 Precio, Conteo, CantidadFaltante, CantidadSobrante, CantidadAlterado
	FROM (SELECT IdProducto, SUM(Cantidad) Cantidad, SUM(Conteo) Conteo, SUM(CantidadFaltante) CantidadFaltante, SUM(CantidadSobrante) CantidadSobrante, SUM(CantidadAlterado) CantidadAlterado
	FROM Far_Inventario_Producto
	WHERE @IdInventario = IdInventario
	GROUP BY IdProducto) INV_PRO 
	INNER JOIN Far_Producto PRO ON INV_PRO.IdProducto = PRO.IdProducto
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
GO

CREATE PROCEDURE Far_Inventario_Producto_Insertar
@IdInventario INT,
@IdProducto INT,
@Lote VARCHAR(20),
@FechaVencimiento DATETIME,
@Cantidad INT,
@Precio DECIMAL(10,4),
@Total  DECIMAL(10,4),
@Conteo INT,
@CantidadFaltante INT,
@CantidadSobrante INT,
@CantidadAlterado INT,
@UsuarioCreacion INT
AS

	INSERT INTO Far_Inventario_Producto(IdInventario, IdProducto, Lote, FechaVencimiento, Cantidad, Precio, Total, Conteo, CantidadFaltante, CantidadSobrante, CantidadAlterado, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
	VALUES (@IdInventario, @IdProducto, @Lote, @FechaVencimiento, @Cantidad, @Precio, @Total, @Conteo, @CantidadFaltante, @CantidadSobrante, @CantidadAlterado, 1, @UsuarioCreacion, GETDATE(), NULL, NULL)

GO

CREATE PROCEDURE Far_Inventario_Producto_Modificar
@IdInventarioProducto INT,
@IdInventario INT,
@IdProducto INT,
@Lote VARCHAR(20),
@FechaVencimiento DATETIME,
@Cantidad INT,
@Precio DECIMAL(10,4),
@Total  DECIMAL(10,4),
@Conteo INT,
@CantidadFaltante INT,
@CantidadSobrante INT,
@CantidadAlterado INT,
@UsuarioModificacion INT
AS
	UPDATE Far_Inventario_Producto
	SET IdInventario = @IdInventario,
		IdProducto = @IdProducto,
		Lote = @Lote,
		FechaVencimiento = @FechaVencimiento, 
		Cantidad = @Cantidad, 
		Precio = @Precio, 
		Total = @Total,
		Conteo = @Conteo, 
		CantidadFaltante = @CantidadFaltante, 
		CantidadSobrante = @CantidadSobrante, 
		CantidadAlterado = @CantidadAlterado,
		UsuarioModificacion = @UsuarioModificacion,
		FechaModificacion = GETDATE()
	WHERE IdInventarioProducto = @IdInventarioProducto
GO

CREATE PROCEDURE Far_Inventario_NumeroInventario
@IdPeriodo INT,
@NumeroInventario INT OUTPUT
AS
	SELECT @NumeroInventario = ISNULL(MAX(NumeroInventario), 0) + 1 
	FROM Far_Inventario
GO

CREATE PROCEDURE Far_Inventario_Producto_Procesar
@IdInventario INT,
@UsuarioModificacion INT
AS

DECLARE @IdAlmacen INT
DECLARE @IdPeriodo INT
DECLARE @IdConcepto INT
DECLARE @NumeroMovimientoIngreso INT
DECLARE @NumeroMovimientoSalida INT
DECLARE @IdMovimientoIngreso INT
DECLARE @IdMovimientoSalida INT

SELECT @IdAlmacen = IdAlmacen,
	   @IdPeriodo = IdPeriodo
FROM Far_Inventario
WHERE IdInventario = @IdInventario

UPDATE Far_Inventario
SET FechaCierre = DATEADD(DD, 0, DATEDIFF(dd, 0, GETDATE())),
	UsuarioModificacion = @UsuarioModificacion,
	FechaModificacion = GETDATE()
WHERE IdInventario = @IdInventario

EXECUTE Far_Movimiento_Numero_Ingreso @IdPeriodo, @IdAlmacen, @NumeroMovimientoIngreso OUTPUT
EXECUTE Far_Movimiento_Numero_Salida @IdPeriodo, @IdAlmacen, @NumeroMovimientoSalida OUTPUT

SELECT @IdConcepto = Valor FROM Far_Parametro WHERE NombreParametro = 'AJUSTEIV'

IF EXISTS(SELECT 1 FROM Far_Inventario_Producto 
		WHERE IdInventario = @IdInventario
		AND CantidadSobrante > 0)
BEGIN
	INSERT INTO Far_Movimiento(IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, IdAlmacenOrigen, IdAlmacenDestino, IdConcepto, IdTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, IdDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, IdProveedor, IdTipoCompra, IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdPeriodo, 'S', @NumeroMovimientoSalida, DATEADD(DD, 0, DATEDIFF(dd, 0, GETDATE())), @IdAlmacen, NULL, @IdConcepto, NULL, '', NULL, NULL, '', NULL, NULL, NULL, NULL,'', '', NULL, 1, @UsuarioModificacion, GETDATE())

	SELECT @IdMovimientoIngreso = SCOPE_IDENTITY()

	INSERT INTO Far_Movimiento_Producto(IdMovimiento, IdProducto, Cantidad, Precio, Total, Lote, FechaVencimiento, Activo, UsuarioCreacion, FechaCreacion)
	SELECT @IdMovimientoIngreso, IdProducto, CantidadSobrante, Precio, Total, Lote, FechaVencimiento, 1, @UsuarioModificacion, GETDATE()
	FROM Far_Inventario_Producto
	WHERE IdInventario = @IdInventario
	AND CantidadSobrante > 0
END

IF EXISTS(SELECT 1 FROM Far_Inventario_Producto 
		WHERE IdInventario = @IdInventario
		AND CantidadFaltante > 0)
BEGIN 
	INSERT INTO Far_Movimiento(IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, IdAlmacenOrigen, IdAlmacenDestino, IdConcepto, IdTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, IdDocumentoOrigen, NumeroDocumentoOrigen, FechaDocumentoOrigen, IdProveedor, IdTipoCompra, IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, Activo, UsuarioCreacion, FechaCreacion)
	VALUES (@IdPeriodo, 'S', @NumeroMovimientoSalida, DATEADD(DD, 0, DATEDIFF(dd, 0, GETDATE())), @IdAlmacen, NULL, @IdConcepto, NULL, '', NULL, NULL, '', NULL, NULL, NULL, NULL,'', '', NULL, 1, @UsuarioModificacion, GETDATE())

	SELECT @IdMovimientoSalida = SCOPE_IDENTITY()

	INSERT INTO Far_Movimiento_Producto(IdMovimiento, IdProducto, Cantidad, Precio, Total, Lote, FechaVencimiento, Activo, UsuarioCreacion, FechaCreacion)
	SELECT @IdMovimientoSalida, IdProducto, CantidadFaltante, Precio, Total, Lote, FechaVencimiento, 1, @UsuarioModificacion, GETDATE()
	FROM Far_Inventario_Producto
	WHERE IdInventario = @IdInventario
	AND CantidadFaltante > 0
END
GO

CREATE PROCEDURE Far_Producto_Stock_Fecha
@Fecha DATETIME,
@IdAlmacen INT
AS

DECLARE @IdPeriodo INT
SELECT @IdPeriodo = CAST(YEAR(@Fecha) AS VARCHAR(4)) +  RIGHT('0' + RTRIM(MONTH(@Fecha)), 2);

SELECT PRO.IdProducto ,PRO_SIS.CodigoSismed, PRO.Descripcion, TP.NombreTipoProducto, FF.NombreFormaFarmaceutica, Cantidad
FROM Far_Producto PRO
INNER JOIN (SELECT PRO.IdProducto, SUM(CASE
						WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
						THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
						ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
						END) Cantidad
			FROM Far_Producto PRO
			LEFT JOIN 
			(SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto
				FROM Far_Movimiento MOV
				INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
				AND IdPeriodo = @IdPeriodo
				AND ((IdAlmacenOrigen = @IdAlmacen AND TipoMovimiento = 'S')
							OR (IdAlmacenDestino = @IdAlmacen AND TipoMovimiento = 'I'))
				WHERE CONVERT(DATE, FechaRegistro) <= @Fecha) MOV_PRO_TEMP ON PRO.IdProducto = MOV_PRO_TEMP.IdProducto				
			GROUP BY PRO.IdProducto) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto	
INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
LEFT JOIN Far_Producto_Sismed PRO_SIS ON PRO.IdProductoSismed = PRO_SIS.IdProductoSismed
GO

CREATE PROCEDURE Far_Producto_Precio_Insertar 
@TipoPrecio CHAR(1),
@PrecioAdquisicion DECIMAL(12, 8),
@PrecioDistribucion DECIMAL(12, 8),
@PrecioOperacion DECIMAL(12, 8),
@IdProducto INT,
@FechaRegistro DATETIME,
@FechaVigencia DATETIME,
@UsuarioCreacion INT
AS
	INSERT INTO Far_Producto_Precio(TipoPrecio, PrecioAdquisicion, PrecioDistribucion, PrecioOperacion, IdProducto, FechaRegistro, FechaVigencia, UsuarioCreacion, FechaCreacion)
	VALUES (@TipoPrecio, @PrecioAdquisicion, @PrecioDistribucion, @PrecioOperacion, @IdProducto, @FechaRegistro, @FechaVigencia, @UsuarioCreacion, GETDATE())
GO

CREATE PROCEDURE Far_Producto_Precio_Ultimo
@Descripcion VARCHAR(100)
AS
SELECT IdProducto, NombreProducto, CodigoSismed, PrecioAdquisicion, PrecioDistribucion, PrecioOperacion
FROM (SELECT PRO.IdProducto, PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.NombreFormaFarmaceutica + ' - ' + PRO.Presentacion NombreProducto, PS.CodigoSismed, PrecioAdquisicion, PrecioDistribucion, PrecioOperacion
FROM Far_Producto PRO
INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
LEFT JOIN Far_Producto_Sismed PS ON PRO.IdProductoSismed = PS.IdProductoSismed
LEFT JOIN (SELECT IdProductoPrecio, TipoPrecio, PrecioAdquisicion, PrecioDistribucion, PrecioOperacion, PRO_PRE.IdProducto, PRO_PRE.FechaRegistro, FechaVigencia
			FROM Far_Producto_Precio PRO_PRE 
			INNER JOIN (SELECT IdProducto, MAX(FechaRegistro) FechaRegistro
				FROM Far_Producto_Precio
				GROUP BY IdProducto) UltimoPrecio 
				ON UltimoPrecio.IdProducto = PRO_PRE.IdProducto
				AND UltimoPrecio.FechaRegistro = PRO_PRE.FechaRegistro) PRO_PRE_ULTIMO
ON PRO_PRE_ULTIMO.IdProducto = PRO.IdProducto) TMP
WHERE NombreProducto LIKE '%' + @Descripcion + '%'
OR CodigoSismed LIKE '%' + @Descripcion + '%'
GO

CREATE PROCEDURE Far_Producto_Precio_PorProducto
@IdProducto INT
AS
	SELECT IdProductoPrecio, TipoPrecio, PrecioAdquisicion, PrecioDistribucion, PrecioOperacion, IdProducto, FechaRegistro, FechaVigencia
	FROM Far_Producto_Precio
	WHERE @IdProducto = IdProducto
	ORDER BY FechaRegistro DESC
GO

CREATE PROCEDURE Far_Producto_AlertaVencimiento
AS
DECLARE @IdPeriodo INT
DECLARE @DiasVencimiento INT

	SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()
	SELECT @DiasVencimiento = Valor FROM Far_Parametro WHERE NombreParametro = 'PROVEN'

	SELECT AP.Descripcion + ' - ' + AL.Descripcion Almacen,
			PS.CodigoSismed, 
			PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.NombreFormaFarmaceutica + ' - ' + PRO.Presentacion NombreProducto, 
			TP.NombreTipoProducto, 
			PRO_STOCK.Cantidad, 
			PRO_STOCK.Lote, 
			PRO_STOCK.FechaVencimiento, 
			CASE 
				WHEN DATEDIFF(DAY, GETDATE(), FechaVencimiento) <= @DiasVencimiento 
					 AND DATEDIFF(DAY, GETDATE(), FechaVencimiento) >= 0 THEN 'P'				
				ELSE 'V'
			END Estado
	FROM Far_Producto PRO
	LEFT JOIN Far_Producto_Sismed PS ON PRO.IdProductoSismed = PS.IdProductoSismed
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN (SELECT P.IdProducto, SUM(CASE
									WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
									THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
									ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
									END) Cantidad, Lote, FechaVencimiento, IdAlmacenDestino IdAlmacen
				FROM Far_Producto P
				LEFT JOIN 
				(SELECT CASE 
						WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
						ELSE IdAlmacenDestino 
						END AS IdAlmacenDestino, TipoMovimiento, Cantidad, IdProducto, Lote, FechaVencimiento
					FROM Far_Movimiento MOV
					INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
					AND IdPeriodo = @IdPeriodo) MOV_PRO_TEMP ON P.IdProducto = MOV_PRO_TEMP.IdProducto	
				GROUP BY P.IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto
	INNER JOIN Far_Almacen AL ON AL.IdAlmacen = PRO_STOCK.IdAlmacen
	INNER JOIN Far_Almacen AP ON AL.IdAlmacenPadre = AP.IdAlmacen
				WHERE Cantidad > 0
	AND FechaVencimiento BETWEEN DATEADD(DAY, -1 * @DiasVencimiento, GETDATE()) AND DATEADD(DAY, @DiasVencimiento, GETDATE())
GO

CREATE PROCEDURE Far_Producto_Vencimiento
@IdAlmacen INT,
@FechaDesde DATETIME,
@FechaHasta DATETIME,
@IdProducto INT
AS
DECLARE @IdPeriodo INT
SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()

	SELECT AP.Descripcion + ' - ' + AL.Descripcion Almacen,
			PS.CodigoSismed, 
			PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.NombreFormaFarmaceutica + ' - ' + PRO.Presentacion NombreProducto, 
			TP.NombreTipoProducto, 
			PRO_STOCK.Cantidad, 
			PRO_STOCK.Lote, 
			PRO_STOCK.FechaVencimiento,
			CASE 
				WHEN DATEDIFF(DAY, GETDATE(), FechaVencimiento) >= 0 THEN 'P'				
				ELSE 'V'
			END Estado
	FROM Far_Producto PRO
	LEFT JOIN Far_Producto_Sismed PS ON PRO.IdProductoSismed = PS.IdProductoSismed
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN (SELECT P.IdProducto, SUM(CASE
									WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
									THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
									ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
									END) Cantidad, Lote, FechaVencimiento, IdAlmacenDestino IdAlmacen
				FROM Far_Producto P
				LEFT JOIN 
				(SELECT CASE 
						WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
						ELSE IdAlmacenDestino 
						END AS IdAlmacenDestino, TipoMovimiento, Cantidad, IdProducto, Lote, FechaVencimiento
					FROM Far_Movimiento MOV
					INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
					AND IdPeriodo = @IdPeriodo) MOV_PRO_TEMP ON P.IdProducto = MOV_PRO_TEMP.IdProducto	
				GROUP BY P.IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto
	INNER JOIN Far_Almacen AL ON AL.IdAlmacen = PRO_STOCK.IdAlmacen
	INNER JOIN Far_Almacen AP ON AL.IdAlmacenPadre = AP.IdAlmacen
	WHERE Cantidad > 0
	AND (AL.IdAlmacen = @IdAlmacen OR @IdAlmacen = 0)
	AND (PRO.IdProducto = @IdProducto OR @IdAlmacen = 0)
	AND FechaVencimiento BETWEEN ISNULL(@FechaDesde, CAST(-53690 AS DATETIME)) AND ISNULL(@FechaHasta, CAST(+2900000 AS DATETIME))
GO

CREATE PROCEDURE Far_Producto_StockMinimo
AS
DECLARE @IdPeriodo INT
DECLARE @CantidadMinima INT

SELECT @IdPeriodo = IdPeriodo FROM Func_PeriodoActivo()
SELECT @CantidadMinima = Valor FROM Far_Parametro WHERE NombreParametro = 'PROMIN'

	SELECT AP.Descripcion + ' - ' + AL.Descripcion Almacen,
			PS.CodigoSismed, 
			PRO.Descripcion + ' - ' + PRO.Concentracion + ' - ' + FF.NombreFormaFarmaceutica + ' - ' + PRO.Presentacion NombreProducto, 
			TP.NombreTipoProducto, 
			PRO_STOCK.Cantidad, 
			PRO_STOCK.Lote, 
			PRO_STOCK.FechaVencimiento,
			PRO.StockMin
	FROM Far_Producto PRO
	LEFT JOIN Far_Producto_Sismed PS ON PRO.IdProductoSismed = PS.IdProductoSismed
	INNER JOIN Far_Forma_Farmaceutica FF ON PRO.IdFormaFarmaceutica = FF.IdFormaFarmaceutica
	INNER JOIN Far_Tipo_Producto TP ON PRO.IdTipoProducto = TP.IdTipoProducto
	INNER JOIN (SELECT P.IdProducto, SUM(CASE
									WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
									THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
									ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
									END) Cantidad, Lote, FechaVencimiento, IdAlmacenDestino IdAlmacen
				FROM Far_Producto P
				LEFT JOIN 
				(SELECT CASE 
						WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
						ELSE IdAlmacenDestino 
						END AS IdAlmacenDestino, TipoMovimiento, Cantidad, IdProducto, Lote, FechaVencimiento
					FROM Far_Movimiento MOV
					INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
					AND IdPeriodo = @IdPeriodo) MOV_PRO_TEMP ON P.IdProducto = MOV_PRO_TEMP.IdProducto	
				GROUP BY P.IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) PRO_STOCK ON PRO.IdProducto = PRO_STOCK.IdProducto
	INNER JOIN Far_Almacen AL ON AL.IdAlmacen = PRO_STOCK.IdAlmacen
	INNER JOIN Far_Almacen AP ON AL.IdAlmacenPadre = AP.IdAlmacen
	WHERE PRO.StockMin IS NOT NULL
	AND PRO_STOCK.Cantidad <= PRO.StockMin + @CantidadMinima
GO

CREATE PROCEDURE Far_Periodo_AperturarAlmacen
@IdPeriodoCerrar INT
AS

SELECT IdAlmacen 
FROM (SELECT SUM(CASE
				WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
				THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
				ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
				END) Cantidad, IdAlmacenDestino IdAlmacen
	FROM (SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto, Lote, FechaVencimiento, CASE 
																									WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
																									ELSE IdAlmacenDestino 
																									END AS IdAlmacenDestino
			FROM Far_Movimiento MOV
			INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
			AND IdPeriodo = @IdPeriodoCerrar) MOV_PRO_TEMP
	GROUP BY IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) ALMACEN_STOCK
WHERE Cantidad > 0
GROUP BY IdAlmacen
GO

CREATE PROCEDURE Far_Movimiento_IngresoInicial
@IdPeriodoAbrir INT,
@IdAlmacenDestino INT,
@UsuarioCreacion INT,
@IdMovimiento INT OUTPUT
AS

DECLARE @ConceptoInicial INT

SELECT @ConceptoInicial = Valor FROM Far_Parametro WHERE NombreParametro = 'INICIO'

INSERT INTO Far_Movimiento(IdPeriodo, TipoMovimiento, NumeroMovimiento, FechaRegistro, IdAlmacenOrigen, IdAlmacenDestino, IdConcepto, IdTipoDocumentoMov, NumeroDocumentoMov, FechaRecepcion, IdDocumentoOrigen, FechaDocumentoOrigen, IdProveedor, IdTipoCompra, IdTipoProceso, NumeroProceso, Referencia, IdMovimientoIngreso, Activo, UsuarioCreacion, FechaCreacion)
VALUES(@IdPeriodoAbrir, 'I', 1, GETDATE(), NULL, @IdAlmacenDestino/*AlmacenDestino*/, @ConceptoInicial, NULL /*IdTipoDocumentoMov*/, '' /*NumeroDocumentoMov*/, NULL/*FechaRecepcion*/, NULL /*IdDocumentoOrigen*/, NULL /*FechaDocumentoOrigen*/, NULL /*IdProveedor*/, NULL /*IdTipoCompra*/, NULL /*IdTipoProceso*/, '' /*NumeroProceso*/, '' /*Referencia*/, NULL /*IdMovimientoIngreso*/, 1 /*Activo*/, @UsuarioCreacion, GETDATE() /*FechaCreacion*/)

SELECT @IdMovimiento = SCOPE_IDENTITY()
GO

CREATE PROCEDURE Far_Movimiento_Producto_Inicial
@IdPeriodoCerrar INT,
@IdMovimiento INT,
@IdAlmacenDestino INT,
@UsuarioCreacion INT
AS

INSERT INTO Far_Movimiento_Producto(IdMovimiento, IdProducto, Cantidad, Precio, Total, Lote, FechaVencimiento, RegistroSanitario, Activo, UsuarioCreacion, FechaCreacion)

SELECT IdMovimiento, IdProducto, Cantidad, Precio, Total, Lote, FechaVencimiento, RegistroSanitario, Activo, UsuarioCreacion, FechaCreacion
FROM (SELECT @IdMovimiento AS IdMovimiento, 
		IdProducto, 
		SUM(CASE
			WHEN ISNULL(MOV_PRO_TEMP.TipoMovimiento, 'I') = 'I'
			THEN ISNULL(MOV_PRO_TEMP.Cantidad, 0)
			ELSE ISNULL(MOV_PRO_TEMP.Cantidad, 0) * -1
		END) Cantidad, 0 AS Precio, 0 AS Total, Lote, FechaVencimiento, '' AS RegistroSanitario, 1 AS Activo, @UsuarioCreacion AS UsuarioCreacion, GETDATE() AS FechaCreacion,
		IdAlmacenDestino
	FROM (SELECT MOV.IdMovimiento, TipoMovimiento, Cantidad, IdProducto, Lote, FechaVencimiento, CASE 
																								 WHEN TipoMovimiento = 'S' THEN IdAlmacenOrigen
																								 ELSE IdAlmacenDestino 
																								 END AS IdAlmacenDestino
		FROM Far_Movimiento MOV
		INNER JOIN Far_Movimiento_Producto MOV_PRO ON MOV.IdMovimiento = MOV_PRO.IdMovimiento
		AND IdPeriodo = @IdPeriodoCerrar) MOV_PRO_TEMP
	GROUP BY IdProducto, Lote, FechaVencimiento, IdAlmacenDestino) STOCK_TMP
WHERE IdAlmacenDestino = @IdAlmacenDestino
GO