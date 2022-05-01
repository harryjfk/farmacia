USE Farmacia;

SET IDENTITY_INSERT Far_Submenu ON;
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (190, 'Diagnósticos CIE', '/dispensacion/4/diagnosticoCie/listar', 8, 11, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (191, 'Diagnósticos CIE', '/dispensacion/5/diagnosticoCie/listar', 11, 11, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (192, 'Diagnósticos CIE', '/dispensacion/6/diagnosticoCie/listar', 14, 11, 1, 1, GETDATE(), NULL, NULL);
SET IDENTITY_INSERT Far_Submenu OFF;

SET IDENTITY_INSERT Far_Opcion ON;
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (550, 'registrar', 'Nuevo', 190, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (551, 'modificar', 'Editar', 190, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (552, 'estado', 'Cambiar Estado', 190, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (553, 'eliminar', 'Eliminar', 190, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
-- 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (554, 'registrar', 'Nuevo', 191, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (555, 'modificar', 'Editar', 191, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (556, 'estado', 'Cambiar Estado', 191, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (557, 'eliminar', 'Eliminar', 191, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
-- 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (558, 'registrar', 'Nuevo', 192, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (559, 'modificar', 'Editar', 192, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (560, 'estado', 'Cambiar Estado', 192, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (561, 'eliminar', 'Eliminar', 192, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Opcion OFF;

SET IDENTITY_INSERT Far_Perfil_Opcion ON;
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (550, 1, 550, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (551, 1, 551, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (552, 1, 552, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (553, 1, 553, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (554, 1, 554, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (555, 1, 555, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (556, 1, 556, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (557, 1, 557, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (558, 1, 558, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (559, 1, 559, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (560, 1, 560, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (561, 1, 561, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Perfil_Opcion OFF;


INSERT INTO Far_Diagnostico_CIE (CODIGO, Activo, Descripcion, FechaCreacion, FechaModificacion, UsuarioCreacion, UsuarioModificacion) 
	VALUES ('M15.0  ', 1, '(OSTEO)ARTROSIS PRIMARIA GENERALIZADA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, NULL);
INSERT INTO Far_Diagnostico_CIE (CODIGO, Activo, Descripcion, FechaCreacion, FechaModificacion, UsuarioCreacion, UsuarioModificacion) 
	VALUES ('Z38.2  ', 1, 'A TERMINO (PRODUCTO UNICO EN LUGAR NO ESPECIFICADO)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, NULL);
INSERT INTO Far_Diagnostico_CIE (CODIGO, Activo, Descripcion, FechaCreacion, FechaModificacion, UsuarioCreacion, UsuarioModificacion) 
	VALUES ('Z38.0  ', 1, 'A TERMINO (PRODUCTO UNICO NACIDO EN HOSPITAL)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, NULL);
INSERT INTO Far_Diagnostico_CIE (CODIGO, Activo, Descripcion, FechaCreacion, FechaModificacion, UsuarioCreacion, UsuarioModificacion) 
	VALUES ('Z62.4  ', 1, 'ABANDONO EMOCIONAL DEL NIÑO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, NULL);
INSERT INTO Far_Diagnostico_CIE (CODIGO, Activo, Descripcion, FechaCreacion, FechaModificacion, UsuarioCreacion, UsuarioModificacion) 
	VALUES ('U32.4  ', 1, 'ABANDONO RECUPERADO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, NULL);
