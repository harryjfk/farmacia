USE Farmacia;
GO
	-- Turnos
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/turno/listar' WHERE IdMenu = 8 AND NombreSubmenu = 'Turnos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/turno/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Turnos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/turno/listar' WHERE IdMenu = 14 AND NombreSubmenu = 'Turnos';

-- Clientes
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/cliente/listar' WHERE IdMenu = 8 AND NombreSubmenu = 'Clientes';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/cliente/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Clientes';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/cliente/listar' WHERE IdMenu = 14 AND NombreSubmenu = 'Clientes';

-- Prescriptores
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/prescriptor/listar' WHERE IdMenu = 8 AND NombreSubmenu = 'Prescriptores';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/prescriptor/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Prescriptores';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/prescriptor/listar' WHERE IdMenu = 14 AND NombreSubmenu = 'Prescriptores';

-- Vendedores/Cajeros
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/vendedor/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'vendedores/cajeros' OR NombreSubmenu = 'Vendedores/cajeros');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/vendedor/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'vendedores/cajeros' OR NombreSubmenu = 'Vendedores/cajeros');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/vendedor/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'vendedores/cajeros' OR NombreSubmenu = 'Vendedores/cajeros');

-- Kits de Atencion
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/kitatencion/listar' WHERE IdMenu = 8 AND NombreSubmenu = 'Kits de Atención';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/kitatencion/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Kits de Atención';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/kitatencion/listar' WHERE IdMenu = 14 AND NombreSubmenu = 'Kits de Atención';

-- Recetas atendidas y no atendidas
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/recetas/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'Recetas Atendidos y no Atendidos' OR NombreSubmenu = 'Recetas Atendidas y no Atendidas');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/recetas/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'Recetas Atendidos y no Atendidos' OR NombreSubmenu = 'Recetas Atendidas y no Atendidas');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/recetas/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'Recetas Atendidos y no Atendidos' OR NombreSubmenu = 'Recetas Atendidas y no Atendidas');
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/recetas/consulta' WHERE IdMenu = 10 AND (NombreSubmenu = 'Recetas Atendidas y no Atendidas');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/recetas/consulta' WHERE IdMenu = 16 AND (NombreSubmenu = 'Recetas Atendidas y no Atendidas');

-- Inventario Rutinario
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/inventarioRutinario/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/inventarioRutinario/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/inventarioRutinario/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/inventarioRutinario/consultar' WHERE IdMenu = 10 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/inventarioRutinario/consultar' WHERE IdMenu = 13 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/inventarioRutinario/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');


--Demanda Insatisfecha
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/demandaInsatisfecha/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/demandaInsatisfecha/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/demandaInsatisfecha/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/demandaInsatisfecha/consultar' WHERE IdMenu = 10 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/demandaInsatisfecha/consultar' WHERE IdMenu = 13 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/demandaInsatisfecha/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');

-- Buenas Practicas de Dispensacion
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/practicasDispensacion/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/practicasDispensacion/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/practicasDispensacion/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'Buenas practicas de Dispensación' OR NombreSubmenu = 'Buenas practicas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/practicasDispensacion/consultar' WHERE IdMenu = 10 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/practicasDispensacion/consultar' WHERE IdMenu = 13 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/practicasDispensacion/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');

-- Buenas Practicas de Prescripcion
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/practicasPrescripcion/listar' WHERE IdMenu = 8 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/practicasPrescripcion/listar' WHERE IdMenu = 11 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/practicasPrescripcion/listar' WHERE IdMenu = 14 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas practicas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/practicasPrescripcion/reporte' WHERE IdMenu = 10 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/practicasPrescripcion/reporte' WHERE IdMenu = 13 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/practicasPrescripcion/reporte' WHERE IdMenu = 16 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas practicas de Prescripción');


-- Intervenciones
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/especialidad/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Intervenciones por Especialidades';
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/atendida/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Intervenciones Atendidas y no Atendidas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/programada/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Intervenciones Programadas y no Programadas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/especialidad/consulta' WHERE IdMenu = 13 AND NombreSubmenu = 'Intervenciones por Especialidades';
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/atendida/consulta' WHERE IdMenu = 13 AND NombreSubmenu = 'Intervenciones Atendidas y no Atendidas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/intervencion/programada/consulta' WHERE IdMenu = 13 AND NombreSubmenu = 'Intervenciones Programadas y no Programadas';

-- Compras de urgencia
UPDATE Far_Submenu SET Enlace = '/dispensacion/compraDeUrgencia/listar' WHERE IdMenu = 11 AND NombreSubmenu = 'Compras de Urgencias';

--Ventas
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/venta/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/venta/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/venta/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/venta/reportar' WHERE IdMenu = 10 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/venta/reportar' WHERE IdMenu = 13 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/venta/reportar' WHERE IdMenu = 16 AND NombreSubmenu = 'Ventas';

--PreVentas
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/preventa/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Pre ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/preventa/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Pre ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/preventa/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Pre ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/preventa/reportePreventa' WHERE IdMenu = 10 AND NombreSubmenu = 'Pre venta';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/preventa/reportePreventa' WHERE IdMenu = 13 AND NombreSubmenu = 'Pre ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/preventa/reportePreventa' WHERE IdMenu = 16 AND NombreSubmenu = 'Pre venta';

--Corte de Caja
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/cortecaja/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Corte de Caja';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/cortecaja/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Corte de Caja';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/cortecaja/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Corte de Caja';

--Salida por Estrategias Sanitarias
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/intervencionsanitaria/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Salida por Estrategias Sanitarias';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/intervencionsanitaria/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Salida por Estrategias Sanitarias';

--Consulta consumo por tipo Salida
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/consumoSalida/consultar' WHERE IdMenu = 10 AND (NombreSubmenu = 'Consumo por tipo de salida' OR NombreSubmenu = 'Consumo por tipo de salida');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/consumoSalida/consultar' WHERE IdMenu = 13 AND (NombreSubmenu = 'Consumo por tipo de salida' OR NombreSubmenu = 'Consumo por tipo de salida');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/consumoSalida/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Consumo por tipo de salida' OR NombreSubmenu = 'Consumo por tipo de salida');
--Consulta  consumo por Paciente
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/consumoPaciente/consultar' WHERE IdMenu = 10 AND (NombreSubmenu = 'Consumo por pacientes' OR NombreSubmenu = 'Consumo por pacientes');
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/consumoPaciente/consultar' WHERE IdMenu = 13 AND (NombreSubmenu = 'Consumo por pacientes' OR NombreSubmenu = 'Consumo por pacientes');
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/consumoPaciente/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Consumo por pacientes' OR NombreSubmenu = 'Consumo por pacientes');
--Notas de Abono
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/notaabono/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Nota de Abono';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/notaabono/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Nota de Abono';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/notaabono/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Nota de Abono';

--Cuentas Corrientes
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/cuentacorriente/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Cuenta Corrientes';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/cuentacorriente/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Cuenta Corrientes';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/cuentacorriente/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Cuenta Corrientes';

UPDATE Far_Submenu SET Enlace = '/dispensacion/6/consumoPaciente/consultar' WHERE IdMenu = 16 AND (NombreSubmenu = 'Consumo por pacientes' OR NombreSubmenu = 'Consumo por pacientes');

-- Reposicion de Stock o pedidos
UPDATE Far_Submenu SET Enlace = '/dispensacion/4/repstockpedido/procesar' WHERE IdMenu = 9 AND NombreSubmenu = 'Reposición de Stock o Pedidos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/5/repstockpedido/procesar' WHERE IdMenu = 12 AND NombreSubmenu = 'Reposición de Stock o Pedidos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/6/repstockpedido/procesar' WHERE IdMenu = 15 AND NombreSubmenu = 'Reposición de Stock o Pedidos';


---------Modulo de Farmacia Clinica -------------------------------------------
--Ventas
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/venta/procesar' WHERE IdMenu = 18 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/venta/reportar' WHERE IdMenu = 19 AND NombreSubmenu = 'Ventas';

-- Buenas Practicas de Dispensacion
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/practicasDispensacion/listar' WHERE IdMenu = 17 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/practicasDispensacion/consultar' WHERE IdMenu = 19 AND (NombreSubmenu = 'Buenas Prácticas de Dispensación' OR NombreSubmenu = 'Buenas Prácticas de Dispensación');

-- Buenas Practicas de Prescripcion
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/practicasPrescripcion/listar' WHERE IdMenu = 17 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/practicasPrescripcion/reporte' WHERE IdMenu = 19 AND (NombreSubmenu = 'Buenas Prácticas de Prescripción' OR NombreSubmenu = 'Buenas Prácticas de Prescripción');

--Demanda Insatisfecha
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/demandaInsatisfecha/listar' WHERE IdMenu = 17 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/demandaInsatisfecha/consultar' WHERE IdMenu = 19 AND (NombreSubmenu = 'Demanda Insatisfecha' OR NombreSubmenu = 'Demanda Insatisfecha');
--Devoluciones
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/devoluciones/listar' WHERE IdMenu = 18 AND (NombreSubmenu = 'Devoluciones' OR NombreSubmenu = 'Devoluciones');

-- Inventario Rutinario
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/inventarioRutinario/listar' WHERE IdMenu = 17 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/inventarioRutinario/consultar' WHERE IdMenu = 19 AND (NombreSubmenu = 'Inventario Rutinario' OR NombreSubmenu = 'Inventario Rutinario');
--Medidas educativas--
UPDATE Far_Submenu SET Enlace = '/dispensacion/8/prm/listar' WHERE IdMenu = 21 AND (NombreSubmenu = 'Registro PRM' OR NombreSubmenu = 'Registro PRM');
UPDATE Far_Submenu SET Enlace = '/dispensacion/8/prm/consultar' WHERE IdMenu = 22 AND (NombreSubmenu = 'PRM' OR NombreSubmenu = 'PRM');


--Formato de Devoluciones
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/documentos/listarFormatoDevolucion' WHERE IdMenu = 17 AND (NombreSubmenu = 'Formato de Devolución' OR NombreSubmenu = 'Formato de Devolución');
--Formato RAM Hoja Amarilla
UPDATE Far_Submenu SET Enlace = '/dispensacion/9/documentos/listarFormatoRAM' WHERE IdMenu = 23 AND (NombreSubmenu = 'Formato RAM Hoja Amarilla' OR NombreSubmenu = 'Formato RAM Hoja Amarilla');
UPDATE Far_Submenu SET Enlace = '/dispensacion/9/ram/procesar' WHERE IdMenu = 24 AND (NombreSubmenu = 'Registro RAM' OR NombreSubmenu = 'Registro RAM');
UPDATE Far_Submenu SET Enlace = '/dispensacion/9/ram/consultar' WHERE IdMenu = 25 AND (NombreSubmenu = 'Reporte RAM' OR NombreSubmenu = 'Reporte RAM');

-- Recetas atendidas y no atendidas
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/recetas/listar' WHERE IdMenu = 17 AND (NombreSubmenu = 'Recetas Atendidos y no Atendidos' OR NombreSubmenu = 'Recetas Atendidas y no Atendidas');

-- Reposicion de Stock o pedidos
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/repstockpedido/procesar' WHERE IdMenu = 18 AND NombreSubmenu = 'Reposición de Stock o Pedidos';

--Notas de Abono
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/notaabono/procesar' WHERE IdMenu = 18 AND NombreSubmenu = 'Nota de Abono';

--Cuentas Corrientes
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/cuentacorriente/procesar' WHERE IdMenu = 18 AND NombreSubmenu = 'Cuenta Corrientes';

--Consumos
UPDATE Far_Submenu SET Enlace = '/dispensacion/7/consumoSalida/consultar' WHERE IdMenu = 19 AND NombreSubmenu = 'Consumos';

--Formato de seguimiento farmaco terapeutico
UPDATE Far_Submenu SET Enlace = '/dispensacion/8/documentos/listarFormatoHFT' WHERE IdMenu = 20 AND NombreSubmenu = 'Formato de Seguimiento Farmaco terapéutico';

--Farmacotecnia
--Equipos
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/equipos/listar' WHERE IdMenu = 26 AND NombreSubmenu = 'Equipos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/materias/listarPrecios' WHERE IdMenu = 26 AND NombreSubmenu = 'Precios';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/materias/listarMaterias' WHERE IdMenu = 26 AND NombreSubmenu = 'Materia Prima y empaque';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/matriz/listar' WHERE IdMenu = 26 AND NombreSubmenu = 'Matriz de fórmula Magistral y Oficiales';
-- Procesos
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/notapedido/procesar' WHERE IdMenu = 27 AND NombreSubmenu = 'Nota de Pedidos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/preventa/procesar' WHERE IdMenu = 27 AND NombreSubmenu = 'Pre venta';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/venta/procesar' WHERE IdMenu = 27 AND NombreSubmenu = 'Venta';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/demandaInsatisfecha/listar' WHERE IdMenu = 27 AND NombreSubmenu = 'Registro demanda insatisfecha';

-- Consultas
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/venta/reportar' WHERE IdMenu = 28 AND NombreSubmenu = 'Ventas';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/cortecaja/procesar' WHERE IdMenu = 28 AND NombreSubmenu = 'Corte de Caja';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/ingresos/consultar' WHERE IdMenu = 28 AND NombreSubmenu = 'Ingresos';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/tarjetacontrolvisible/consultar' WHERE IdMenu = 28 AND NombreSubmenu = 'Tarjeta de Control Visibe';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/consumopromedomen/consultar' WHERE IdMenu = 28 AND NombreSubmenu = 'Consumo Promedio Mensual';
UPDATE Far_Submenu SET Enlace = '/dispensacion/10/situacionstock/consultar' WHERE IdMenu = 28 AND NombreSubmenu = 'Situación Stock';

-- Ventas
update Far_Submenu SET Enlace = '/dispensacion/10/fpreventa/procesar'
where Enlace like '%/dispensacion/10/preventa/procesar%';
update Far_Submenu SET Enlace = '/dispensacion/10/fventa/procesar'
where Enlace like '%/dispensacion/10/venta/procesar%';