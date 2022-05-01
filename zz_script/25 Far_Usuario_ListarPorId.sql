USE [Farmacia]
GO
/****** Object:  StoredProcedure [dbo].[Far_Usuario_ListarPorId]    Script Date: 03/26/2015 17:58:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[Far_Usuario_ListarPorId]
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
	WHERE Far_Usuario.IdUsuario = @IdUsuario
