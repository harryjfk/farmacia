GO
USE Farmacia;
GO

SET IDENTITY_INSERT Far_Submenu ON;
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (180, 'Puntos de Venta', '/dispensacion/4/ptoventa/listar', 8, 11, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (181, 'Puntos de Venta', '/dispensacion/5/ptoventa/listar', 11, 11, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (182, 'Puntos de Venta', '/dispensacion/6/ptoventa/listar', 14, 11, 1, 1, GETDATE(), NULL, NULL);
SET IDENTITY_INSERT Far_Submenu OFF;

SET IDENTITY_INSERT Far_Opcion ON;
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (800, 'registrar', 'Nuevo', 180, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (801, 'modificar', 'Editar', 180, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (802, 'estado', 'Cambiar Estado', 180, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (803, 'eliminar', 'Eliminar', 180, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
-- 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (804, 'registrar', 'Nuevo', 181, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (805, 'modificar', 'Editar', 181, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (806, 'estado', 'Cambiar Estado', 181, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (807, 'eliminar', 'Eliminar', 181, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
-- 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (808, 'registrar', 'Nuevo', 182, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (809, 'modificar', 'Editar', 182, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (810, 'estado', 'Cambiar Estado', 182, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (811, 'eliminar', 'Eliminar', 182, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Opcion OFF;

SET IDENTITY_INSERT Far_Perfil_Opcion ON;
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (800, 1, 800, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (801, 1, 801, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (802, 1, 802, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (803, 1, 803, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (804, 1, 804, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (805, 1, 805, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (806, 1, 806, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (807, 1, 807, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (808, 1, 808, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (809, 1, 809, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (810, 1, 810, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (811, 1, 811, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Perfil_Opcion OFF;


