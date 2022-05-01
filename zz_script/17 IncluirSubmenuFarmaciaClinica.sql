GO
USE Farmacia
GO

SET IDENTITY_INSERT Far_Submenu ON;
--Catalogo FarmClini_DispenMedicaDosisUnitarias
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (200, 'Turnos', '/dispensacion/7/turno/listar', 17, 7, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (201, 'Clientes', '/dispensacion/7/cliente/listar', 17, 8, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (202, 'Prescriptores', '/dispensacion/7/prescriptor/listar', 17, 9, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (203, 'Vendedores/cajeros', '/dispensacion/7/vendedor/listar', 17, 10, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (204, 'Kits de Atención', '/dispensacion/7/kitatencion/listar', 17, 11, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (205, 'Puntos de Venta', '/dispensacion/7/ptoventa/listar', 17, 12, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (212, 'Sub Componentes', '/dispensacion/7/subcomponente/listar', 17, 13, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (213, 'Componentes', '/dispensacion/7/componente/listar', 17, 14, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (214, 'Diagnósticos CIE', '/dispensacion/7/diagnosticoCie/listar', 17, 15, 1, 1, GETDATE(), NULL, NULL);

-- procesos FarmClini_DispenMedicaDosisUnitarias
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (211, 'Pre ventas', '/dispensacion/7/preventa/procesar', 18, 6, 1, 1, GETDATE(), NULL, NULL);

--catalogo de Seguimientofarmavoterapeutico
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (206, 'Gestionar Acciones Educativas', '/dispensacion/8/documentos/listarMedidasEducativas', 20, 2, 1, 1, GETDATE(), NULL, NULL);

--Procesos de Seguimientofarmavoterapeutico
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (207, 'Procesar Acciones Educativas', '/dispensacion/8/medidas/listar', 21, 2, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (208, 'Procesar Hoja Farmacoterapeutica', '/dispensacion/8/hft/procesar', 21, 3, 1, 1, GETDATE(), NULL, NULL);

--Consultas de Seguimientofarmavoterapeutico
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (209, 'Hoja Farmacoterapeutica', '/dispensacion/8/hft/consultar', 22, 2, 1, 1, GETDATE(), NULL, NULL);
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (210, 'Acciones Educativas', '/dispensacion/8/medidas/consultar', 22, 3, 1, 1, GETDATE(), NULL, NULL);

--Catalogo de servicio
INSERT INTO Far_Submenu (IdSubmenu, NombreSubmenu, Enlace, IdMenu, Orden, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) VALUES (273, 'Servicios', '/dispensacion/7/servicio/listar', 17, 16, 1, 1, GETDATE(), NULL, NULL);


SET IDENTITY_INSERT Far_Submenu OFF;

--
SET IDENTITY_INSERT Far_Opcion ON;
--Catalogo de servicio
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (991, 'registrar', 'Nuevo', 273, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (992, 'modificar', 'Editar', 273, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (993, 'estado', 'Cambiar Estado', 273, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (994, 'eliminar', 'Eliminar', 273, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--Catalogo FarmClini_DispenMedicaDosisUnitarias
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (812, 'registrar', 'Nuevo', 200, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (813, 'modificar', 'Editar', 200, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (814, 'estado', 'Cambiar Estado', 200, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (815, 'eliminar', 'Eliminar', 200, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (816, 'registrar', 'Nuevo', 201, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (817, 'modificar', 'Editar', 201, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (818, 'estado', 'Cambiar Estado', 201, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (819, 'eliminar', 'Eliminar', 201, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (820, 'registrar', 'Nuevo', 202, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (821, 'modificar', 'Editar', 202, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (822, 'estado', 'Cambiar Estado', 202, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (823, 'eliminar', 'Eliminar', 202, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (824, 'registrar', 'Nuevo', 203, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (825, 'modificar', 'Editar', 203, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (826, 'estado', 'Cambiar Estado', 203, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (827, 'eliminar', 'Eliminar', 203, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (828, 'registrar', 'Nuevo', 204, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (829, 'modificar', 'Editar', 204, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (830, 'estado', 'Cambiar Estado', 204, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (831, 'eliminar', 'Eliminar', 204, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (832, 'registrar', 'Nuevo', 205, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (833, 'modificar', 'Editar', 205, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (834, 'estado', 'Cambiar Estado', 205, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (835, 'eliminar', 'Eliminar', 205, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- procesos FarmClini_DispenMedicaDosisUnitarias	
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (887, 'registrar', 'Nuevo', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (888, 'modificar', 'Editar', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (889, 'estado', 'Cambiar Estado', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (890, 'eliminar', 'Eliminar', 211, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
--catalogo de Seguimientofarmavoterapeutico
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (836, 'registrar', 'Nuevo', 206, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (837, 'modificar', 'Editar', 206, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (838, 'estado', 'Cambiar Estado', 206, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (839, 'eliminar', 'Eliminar', 206, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

--Procesos de Seguimientofarmavoterapeutico
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (840, 'registrar', 'Nuevo', 207, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (841, 'modificar', 'Editar', 207, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (842, 'estado', 'Cambiar Estado', 207, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (843, 'eliminar', 'Eliminar', 207, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (844, 'registrar', 'Nuevo', 208, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (845, 'modificar', 'Editar', 208, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (846, 'estado', 'Cambiar Estado', 208, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (847, 'eliminar', 'Eliminar', 208, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

--Consultas de Seguimientofarmavoterapeutico
-- INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
-- 	VALUES (848, 'registrar', 'Nuevo', 209, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (849, 'modificar', 'Editar', 209, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (850, 'estado', 'Cambiar Estado', 209, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (851, 'eliminar', 'Eliminar', 209, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
-- 	VALUES (879, 'registrar', 'Nuevo', 210, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (880, 'modificar', 'Editar', 210, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (881, 'estado', 'Cambiar Estado', 210, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (882, 'eliminar', 'Eliminar', 210, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Sub Componente
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (891, 'registrar', 'Nuevo', 212, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (892, 'modificar', 'Editar', 212, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (893, 'estado', 'Cambiar Estado', 212, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (894, 'eliminar', 'Eliminar', 212, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Componente
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (895, 'registrar', 'Nuevo', 213, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (896, 'modificar', 'Editar', 213, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (897, 'estado', 'Cambiar Estado', 213, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (898, 'eliminar', 'Eliminar', 213, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Diagnostico
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (899, 'registrar', 'Nuevo', 214, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (900, 'modificar', 'Editar', 214, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (901, 'estado', 'Cambiar Estado', 214, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Opcion (IdOpcion, AppOpcion, NombreOpcion, IdSubmenu, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (902, 'eliminar', 'Eliminar', 214, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
SET IDENTITY_INSERT Far_Opcion OFF;
--
SET IDENTITY_INSERT Far_Perfil_Opcion ON;
--Catalogo FarmClini_DispenMedicaDosisUnitarias
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (991, 1, 991, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (992, 1, 992, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (993, 1, 993, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (994, 1, 994, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (812, 1, 812, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (813, 1, 813, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (814, 1, 814, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (815, 1, 815, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (816, 1, 816, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (817, 1, 817, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (818, 1, 818, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (819, 1, 819, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (820, 1, 820, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (821, 1, 821, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (822, 1, 822, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (823, 1, 823, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (824, 1, 824, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (825, 1, 825, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (826, 1, 826, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (827, 1, 827, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (828, 1, 828, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (829, 1, 829, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (830, 1, 830, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (831, 1, 831, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (832, 1, 832, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (833, 1, 833, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (834, 1, 834, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (835, 1, 835, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- procesos FarmClini_DispenMedicaDosisUnitarias	
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (887, 1, 887, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (888, 1, 888, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (889, 1, 889, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (890, 1, 890, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
--catalogo de Seguimientofarmavoterapeutico
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (836, 1, 836, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (837, 1, 837, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (838, 1, 838, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (839, 1, 839, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
	
--Procesos de Seguimientofarmavoterapeutico
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (840, 1, 840, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (841, 1, 841, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (842, 1, 842, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (843, 1, 843, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (844, 1, 844, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (845, 1, 845, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (846, 1, 846, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (847, 1, 847, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

--Consultas de Seguimientofarmavoterapeutico	
-- INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
-- 	VALUES (848, 1, 848, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (849, 1, 849, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (850, 1, 850, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (851, 1, 851, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
-- 	VALUES (879, 1, 879, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (880, 1, 880, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (881, 1, 881, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (882, 1, 882, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Sub Componente
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (891, 1, 891, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (892, 1, 892, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (893, 1, 893, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (894, 1, 894, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Sub Componente
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (895, 1, 895, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (896, 1, 896, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (897, 1, 897, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (898, 1, 898, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

-- Diagnostico
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (899, 1, 899, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (900, 1, 900, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (901, 1, 901, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO dbo.Far_Perfil_Opcion (IdPerfilOpcion, IdPerfil, IdOpcion, Activo, UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion) 
	VALUES (902, 1, 902, 1, 1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);

	
SET IDENTITY_INSERT Far_Perfil_Opcion OFF;

