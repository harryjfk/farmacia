GO
 USE Farmacia
GO

SET IDENTITY_INSERT Far_Perfil ON;
INSERT INTO dbo.Far_Perfil (IdPerfil, NombrePerfil, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (12,'Cajero', 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
SET IDENTITY_INSERT Far_Perfil OFF;

SET IDENTITY_INSERT Far_Opcion ON;
--Modulo Farmacia Emergencia y Seguro idMenu=9
--Preventa 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (961, 'pdf', 'Exportar PDF', 53, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (962, 'excel', 'Exportar Excel', 53, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (963, 'registrar', 'Nuevo', 53, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (964, 'pdf', 'Exportar PDF', 54, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (965, 'excel', 'Exportar Excel', 54, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (966, 'registrar', 'Nuevo', 54, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacia Centro Quirurgico	 idMenu=12
--PreVenta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (967, 'pdf', 'Exportar PDF', 79, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (968, 'excel', 'Exportar Excel', 79, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (969, 'registrar', 'Nuevo', 79, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (970, 'pdf', 'Exportar PDF', 80, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (971, 'excel', 'Exportar Excel', 80, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (972, 'registrar', 'Nuevo', 80, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacia Ambulatoria	idMenu=15
--PreVenta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (973, 'pdf', 'Exportar PDF', 103, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (974, 'excel', 'Exportar Excel', 103, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (975, 'registrar', 'Nuevo', 103, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (976, 'pdf', 'Exportar PDF', 104, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (977, 'excel', 'Exportar Excel', 104, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (978, 'registrar', 'Nuevo', 104, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
--Modulo Dispensacion de Medicamentos Dosis Unitaria	idMenu=18
--PreVenta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (979, 'pdf', 'Exportar PDF', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (980, 'excel', 'Exportar Excel', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (981, 'registrar', 'Nuevo', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (982, 'pdf', 'Exportar PDF', 125, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (983, 'excel', 'Exportar Excel', 125, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (984, 'registrar', 'Nuevo', 125, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacotecnia idMenu=27
--PreVenta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (985, 'pdf', 'Exportar PDF', 144, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (986, 'excel', 'Exportar Excel', 144, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (987, 'registrar', 'Nuevo', 144, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (988, 'pdf', 'Exportar PDF', 145, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (989, 'excel', 'Exportar Excel', 145, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (990, 'registrar', 'Nuevo', 145, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

SET IDENTITY_INSERT Far_Opcion OFF;


SET IDENTITY_INSERT Far_Perfil_Opcion ON;
--Modulo Farmacia Emergencia y Seguro
--Preventa 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (961, 12, 961, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (962, 12, 962, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (963, 12, 963, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (964, 12, 964, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (965, 12, 965, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (966, 12, 966, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacia Centro Quirurgico	 idMenu=12
--PreVenta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (967, 12, 967, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (968, 12, 968, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (969, 12, 969, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (970, 12, 970, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (971, 12, 971, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (972, 12, 972, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacia Ambulatoria	idMenu=15
--PreVenta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (973, 12, 973, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (974, 12, 974, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (975, 12, 975, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (976, 12, 976, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (977, 12, 977, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (978, 12, 978, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Dispensacion de Medicamentos Dosis Unitaria	idMenu=18
--PreVenta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (979, 12, 979, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (980, 12, 980, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (981, 12, 981, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (982, 12, 982, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (983, 12, 983, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (984, 12, 984, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Modulo Farmacia Ambulatoria	idMenu=15
--PreVenta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (985, 12, 985, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (986, 12, 986, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (987, 12, 987, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Venta 
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (988, 12, 988, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (989, 12, 989, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (990, 12, 990, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
SET IDENTITY_INSERT Far_Perfil_Opcion OFF;