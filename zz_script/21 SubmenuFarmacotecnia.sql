GO
USE Farmacia
GO

SET IDENTITY_INSERT Far_Submenu ON;
--Catalogo Farmacotecnia
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (215, 'Turnos', '/dispensacion/10/turno/listar', 26, 5, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (216, 'Clientes', '/dispensacion/10/cliente/listar', 26, 6, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (217, 'Prescriptores', '/dispensacion/10/prescriptor/listar', 26, 7, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (218, 'Vendedores/cajeros', '/dispensacion/10/vendedor/listar', 26, 8, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (220, 'Puntos de Venta', '/dispensacion/10/ptoventa/listar', 26, 10, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion)
 VALUES (223, 'Diagnósticos CIE', '/dispensacion/10/diagnosticoCie/listar', 26, 13, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
 VALUES (274, 'Servicios', '/dispensacion/10/servicio/listar', 26, 14, 1, 1, GETDATE(), NULL, NULL);
SET IDENTITY_INSERT Far_Submenu OFF;

--
SET IDENTITY_INSERT Far_Opcion ON;
--Catalogo FarmClini_DispenMedicaDosisUnitarias
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (906, 'registrar', 'Nuevo', 215, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (907, 'modificar', 'Editar', 215, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (908, 'estado', 'Cambiar Estado', 215, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (909, 'eliminar', 'Eliminar', 215, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--catalogo servicio
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (995, 'registrar', 'Nuevo', 274, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (996, 'modificar', 'Editar', 274, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (997, 'estado', 'Cambiar Estado', 274, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (998, 'eliminar', 'Eliminar', 274, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (910, 'registrar', 'Nuevo', 216, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (911, 'modificar', 'Editar', 216, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (912, 'estado', 'Cambiar Estado', 216, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (913, 'eliminar', 'Eliminar', 216, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (914, 'registrar', 'Nuevo', 217, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (915, 'modificar', 'Editar', 217, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (916, 'estado', 'Cambiar Estado', 217, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (917, 'eliminar', 'Eliminar', 217, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (918, 'registrar', 'Nuevo', 218, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (919, 'modificar', 'Editar', 218, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (920, 'estado', 'Cambiar Estado', 218, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (921, 'eliminar', 'Eliminar', 218, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	

INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (926, 'registrar', 'Nuevo', 220, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (927, 'modificar', 'Editar', 220, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (928, 'estado', 'Cambiar Estado', 220, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (929, 'eliminar', 'Eliminar', 220, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);



INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (938, 'registrar', 'Nuevo', 223, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (939, 'modificar', 'Editar', 223, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (940, 'estado', 'Cambiar Estado', 223, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (941, 'eliminar', 'Eliminar', 223, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
--Consulta Consumo Promedio Mensual
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (949, 'modificar', 'Editar', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (950, 'estado', 'Cambiar Estado', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (951, 'eliminar', 'Eliminar', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
--AgregarExcel y PDF
--Consulta TarjetaControlVisible
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (952, 'consulta', 'Consultar', 150, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (953, 'pdf', 'Exportar PDF', 150, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (954, 'excel', 'Exportar Excel', 150, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Consulta ConsumoPromedioMensual
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (955, 'consulta', 'Consultar', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (956, 'pdf', 'Exportar PDF', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (957, 'excel', 'Exportar Excel', 151, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Consulta SituaciónStocks
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (958, 'consulta', 'Consultar', 152, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (959, 'pdf', 'Exportar PDF', 152, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (960, 'excel', 'Exportar Excel', 152, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Opcion OFF;
--
SET IDENTITY_INSERT Far_Perfil_Opcion ON;
--Catalogo Farmcotecnia
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (995, 1, 995, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (996, 1, 996, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (997, 1, 997, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (998, 1, 998, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (906, 1, 906, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (907, 1, 907, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (908, 1, 908, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (909, 1, 909, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (910, 1, 910, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (911, 1, 911, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (912, 1, 912, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (913, 1, 913, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (914, 1, 914, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (915, 1, 915, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (916, 1, 916, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (917, 1, 917, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (918, 1, 918, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (919, 1, 919, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (920, 1, 920, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (921, 1, 921, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (926, 1, 926, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (927, 1, 927, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (928, 1, 928, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (929, 1, 929, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (938, 1, 938, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (939, 1, 939, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (940, 1, 940, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (941, 1, 941, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

--Consulta Consumo Promedio Mensual
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (949, 1, 949, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (950, 1, 950, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (951, 1, 951, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);	
--AgregarExcel y PDF
--Consulta TarjetaControlVisible
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (952, 1, 952, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (953, 1, 953, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (954, 1, 954, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);	
--Consulta ConsumoPromedioMensual
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (955, 1, 955, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (956, 1, 956, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (957, 1, 957, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);	
--Consulta SituaciónStocks
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (958, 1, 958, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (959, 1, 959, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (960, 1, 960, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);	

SET IDENTITY_INSERT Far_Perfil_Opcion OFF;