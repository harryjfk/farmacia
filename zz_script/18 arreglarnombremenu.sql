GO
USE Farmacia
GO
--cambiar nombre de consulta por catalogo en el submdulo DispensacionMedicamentosDosisUnitarias
UPDATE Far_Menu SET NombreMenu = 'Proceso' WHERE IdMenu = 18
UPDATE Far_Menu SET NombreMenu = 'Consulta' WHERE IdMenu = 19 
UPDATE Far_Menu SET Orden = 2 WHERE IdMenu = 18
UPDATE Far_Menu SET Orden = 3 WHERE IdMenu = 19 