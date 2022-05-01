
CREATE DATABASE Farmacia
GO
USE Farmacia
GO

CREATE TABLE Far_Modulo(
	IdModulo INT PRIMARY KEY IDENTITY(1,1),
	NombreModulo VARCHAR(70) NOT NULL,
	Orden INT NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Submodulo(
	IdSubmodulo INT PRIMARY KEY IDENTITY(1,1),
	NombreSubmodulo VARCHAR(70) NOT NULL,
	IdModulo INT FOREIGN KEY REFERENCES Far_Modulo(IdModulo) NOT NULL,
	Orden INT NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Menu(
	IdMenu INT PRIMARY KEY IDENTITY(1,1),
	NombreMenu VARCHAR(70) NOT NULL,
	IdSubmodulo INT FOREIGN KEY REFERENCES Far_Submodulo(IdSubmodulo) NOT NULL,
	Orden INT NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Submenu(
	IdSubmenu INT PRIMARY KEY IDENTITY(1,1),
	NombreSubmenu VARCHAR(70) NOT NULL,
	Enlace VARCHAR(100) NOT NULL,
	IdMenu INT FOREIGN KEY REFERENCES Far_Menu(IdMenu) NOT NULL,
	Orden INT NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Opcion(
	IdOpcion INT PRIMARY KEY IDENTITY(1,1),
	AppOpcion VARCHAR(30) NOT NULL,
	NombreOpcion VARCHAR(30) NOT NULL,
	IdSubmenu INT FOREIGN KEY REFERENCES Far_Submenu(IdSubmenu) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Perfil(
	IdPerfil INT PRIMARY KEY IDENTITY(1,1),
	NombrePerfil VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Usuario(
	IdUsuario INT PRIMARY KEY IDENTITY(1,1),
	IdPersonal CHAR(6) NOT NULL,
	NombreUsuario VARCHAR(70) NOT NULL,
	Clave VARCHAR(30) NOT NULL,	
	Correo VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Usuario_Perfil(
	IdUsuarioPerfil INT PRIMARY KEY IDENTITY(1,1),
	IdUsuario INT NOT NULL,	
	IdPerfil INT FOREIGN KEY REFERENCES Far_Perfil(IdPerfil) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Perfil_Opcion (
	IdPerfilOpcion INT  PRIMARY KEY IDENTITY(1,1),
	IdPerfil INT FOREIGN KEY REFERENCES Far_Perfil(IdPerfil),
	IdOpcion INT FOREIGN KEY REFERENCES Far_Opcion(IdOpcion),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Parametro(
	IdParametro INT PRIMARY KEY IDENTITY(1,1),
	NombreParametro VARCHAR(70) NOT NULL,
	DescripcionParametro VARCHAR(120) NOT NULL,
	Valor VARCHAR(250) NOT NULL
)

CREATE TABLE Far_Tipo_Accion(
	IdTipoAccion INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoAccion VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Tipo_Documento(
	IdTipoDocumento INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoDocumento VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Documento(
	IdDocumento INT PRIMARY KEY IDENTITY(1,1),
	NumeracionInterna VARCHAR(20),
	IdUsuario INT NOT NULL,
	FechaDocumento DATETIME,
	FechaSalida DATETIME,
	IdTipoAccion INT FOREIGN KEY REFERENCES Far_Tipo_Accion(IdTipoAccion),
	IdTipoDocumento INT FOREIGN KEY REFERENCES Far_Tipo_Documento(IdTipoDocumento),
	NroDocumento VARCHAR(50),
	Asunto VARCHAR(200),
	Remitente VARCHAR(200),
	Destino VARCHAR(200),
	NumeracionDireccion VARCHAR(100),
	Observacion VARCHAR(200),
	Despacho INT,
	FechaDespacho DATETIME,
	Extension VARCHAR(10),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Tipo_Almacen(
	IdTipoAlmacen INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoAlmacen VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Ubigeo(
	IdUbigeo VARCHAR(6) PRIMARY KEY,
	NombreUbigeo VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Concepto(
	IdConcepto INT PRIMARY KEY IDENTITY(1,1),
	NombreConcepto VARCHAR(70) NOT NULL,
	TipoMovimientoConcepto CHAR(1) NOT NULL,
	TipoPrecio CHAR(1) NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Tipo_Compra(
	IdTipoCompra INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoCompra VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Tipo_Proceso(
	IdTipoProceso INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoProceso VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Almacen(
	IdAlmacen INT PRIMARY KEY IDENTITY(1,1),
	IdAlmacenPadre INT FOREIGN KEY REFERENCES Far_Almacen(IdAlmacen) NULL,
	IdTipoAlmacen INT FOREIGN KEY REFERENCES Far_Tipo_Almacen(IdTipoAlmacen) NOT NULL,
	Descripcion VARCHAR(50) NOT NULL,
	Abreviatura VARCHAR(14) NOT NULL,
	Direccion VARCHAR(50),
	Fax VARCHAR(20),
	Telefono VARCHAR(20),
	Ruc CHAR(11),
	IdUbigeo VARCHAR(6) FOREIGN KEY REFERENCES Far_Ubigeo(IdUbigeo) NULL,
	Responsable VARCHAR(70),
	Farmacia INT NOT NULL,
	CodigoAlmacen VARCHAR(10) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Forma_Farmaceutica(
	IdFormaFarmaceutica INT PRIMARY KEY IDENTITY(1,1),
	NombreFormaFarmaceutica VARCHAR(70) NOT NULL,
	Abreviatura VARCHAR(20) NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Unidad_Medida(
	IdUnidadMedida INT PRIMARY KEY IDENTITY(1,1),
	NombreUnidadMedida VARCHAR(70) NOT NULL,
	Abreviatura VARCHAR(20) NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Tipo_Producto(
	IdTipoProducto INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoProducto VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Proveedor(
	IdProveedor INT PRIMARY KEY IDENTITY(1,1),
	Ruc CHAR(11) NOT NULL,
	RazonSocial VARCHAR(100) NOT NULL,
	Direccion VARCHAR(100),
	Telefono VARCHAR(20),
	Contacto VARCHAR(70),
	TelefonoContacto VARCHAR(20),
	Fax VARCHAR(20),
	Correo VARCHAR(100),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Producto_Sismed(
	IdProductoSismed INT PRIMARY KEY IDENTITY(1,1),
	CodigoSismed VARCHAR(20) NOT NULL,
	NombreProductoSismed VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Producto_Siga(
	IdProductoSiga INT PRIMARY KEY IDENTITY(1,1),
	CodigoSiga VARCHAR(20) NOT NULL,
	NombreProductoSiga VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Producto(
	IdProducto INTEGER PRIMARY KEY IDENTITY(1,1),
	Descripcion VARCHAR(100) NOT NULL,
    IdProductoSismed INT FOREIGN KEY REFERENCES Far_Producto_SISMED(IdProductoSismed) NULL,
	IdProductoSiga INT FOREIGN KEY REFERENCES Far_Producto_SIGA(IdProductoSiga) NULL,	
	Abreviatura VARCHAR(20),
	IdFormaFarmaceutica INT FOREIGN KEY REFERENCES Far_Forma_Farmaceutica(IdFormaFarmaceutica) NOT NULL,
	IdTipoProducto INT FOREIGN KEY REFERENCES Far_Tipo_Producto(IdTipoProducto) NOT NULL,
	IdUnidadMedida INT FOREIGN KEY REFERENCES  Far_Unidad_Medida(IdUnidadMedida) NOT NULL,
	Presentacion VARCHAR(100),
	Concentracion VARCHAR(100),	
	Petitorio INT,
	EstrSop INT,
	EstrVta INT,
	TraNac INT,
	TraLoc INT,
	Narcotico INT,
	StockMin DECIMAL(10),
	StockMax DECIMAL(10),
	Requerimiento DECIMAL(10),
	Adscrito INT,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Producto_Precio(
	IdProductoPrecio INT PRIMARY KEY IDENTITY(1,1),
	TipoPrecio CHAR(1),
	PrecioAdquisicion DECIMAL(12, 8) NOT NULL,
	PrecioDistribucion DECIMAL(12, 8) NOT NULL,
	PrecioOperacion DECIMAL(12, 8) NOT NULL,
	IdProducto INT FOREIGN KEY REFERENCES Far_Producto(IdProducto) NOT NULL,
	FechaRegistro DATETIME NOT NULL,
	FechaVigencia DATETIME NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Solicitud(
	IdSolicitud INT PRIMARY KEY IDENTITY(1,1),
	IdMedico VARCHAR(6) NOT NULL ,	
	Fecha DATETIME NOT NULL,
	Establecimiento VARCHAR(100) NOT NULL,
	Institucion VARCHAR(100) NOT NULL,
	Justificacion VARCHAR(100) NOT NULL,
	Motivo VARCHAR(100) NOT NULL,
	ExisteMedicamento INT NOT NULL ,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME,
	Extension VARCHAR(10) NULL
)

CREATE TABLE Far_Detalle_Solicitud_Producto(
	IdSolicitudDetalleProducto INT PRIMARY KEY IDENTITY(1,1),
	IdSolicitud INT FOREIGN KEY REFERENCES Far_Solicitud(IdSolicitud) NOT NULL,
	IdProducto INT FOREIGN KEY REFERENCES  Far_Producto(IdProducto) NOT NULL,
	DescripcionMedicamento VARCHAR(100) NOT NULL,
	TipoMedicamento INT NOT NULL,	
	Aprobado INT NULL,
	CantidadAprobada INT NULL ,
	MotivoAprobado VARCHAR(100) NULL ,
	CondicionAprobado VARCHAR(100) NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME,
	FechaAprobacion DATETIME,
	Extension VARCHAR(10) NULL
)

CREATE TABLE Far_Tipo_Documento_Mov(
	IdTipoDocumentoMov INT PRIMARY KEY IDENTITY(1,1),
	NombreTipoDocumentoMov VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Documento_Origen(
	IdDocumentoOrigen INT PRIMARY KEY IDENTITY(1,1),
	NombreDocumentoOrigen VARCHAR(70) NOT NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Concepto_Documento_Origen(
	IdConceptoDocumentoOrigen INT PRIMARY KEY IDENTITY(1,1),
	IdConcepto INT FOREIGN KEY REFERENCES Far_Concepto(IdConcepto) NOT NULL,
	IdDocumentoOrigen INT FOREIGN KEY REFERENCES Far_Documento_Origen(IdDocumentoOrigen) NOT NULL,	
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL
)

CREATE TABLE Far_Concepto_Tipo_Documento_Mov(
	IdConceptoTipoDocumentoMov INT PRIMARY KEY IDENTITY(1,1),
	IdConcepto INT FOREIGN KEY REFERENCES Far_Concepto(IdConcepto) NOT NULL,
	IdTipoDocumentoMov INT FOREIGN KEY REFERENCES Far_Tipo_Documento_Mov(IdTipoDocumentoMov),
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL
)

CREATE TABLE Far_Periodo(
	IdPeriodo INT PRIMARY KEY,	
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Movimiento(
	IdMovimiento INT PRIMARY KEY IDENTITY(1,1),
	IdPeriodo INT FOREIGN KEY REFERENCES Far_Periodo(IdPeriodo) NOT NULL,
	TipoMovimiento CHAR(1) NOT NULL,
	NumeroMovimiento INT NOT NULL,
	FechaRegistro DATETIME NOT NULL,
	IdAlmacenOrigen INT FOREIGN KEY REFERENCES Far_Almacen(IdAlmacen),
	IdAlmacenDestino INT FOREIGN KEY REFERENCES Far_Almacen(IdAlmacen),
	IdConcepto INT FOREIGN KEY REFERENCES Far_Concepto(IdConcepto) NOT NULL,
	IdTipoDocumentoMov INT FOREIGN KEY REFERENCES Far_Tipo_Documento_Mov(IdTipoDocumentoMov),
	NumeroDocumentoMov VARCHAR(20),
	FechaRecepcion DATETIME,
	IdDocumentoOrigen INT FOREIGN KEY REFERENCES Far_Documento_Origen(IdDocumentoOrigen),
	NumeroDocumentoOrigen VARCHAR(20),
	FechaDocumentoOrigen DATETIME,
	IdProveedor INT FOREIGN KEY REFERENCES Far_Proveedor(IdProveedor),
	IdTipoCompra INT FOREIGN KEY REFERENCES Far_Tipo_Compra(IdTipoCompra),
	IdTipoProceso INT FOREIGN KEY REFERENCES Far_Tipo_Proceso(IdTipoProceso),
	NumeroProceso VARCHAR(20),
	Referencia VARCHAR(20),
	IdMovimientoIngreso INT FOREIGN KEY REFERENCES Far_Movimiento(IdMovimiento),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Movimiento_Producto(
	IdMovimientoProducto INT PRIMARY KEY IDENTITY(1,1),
	IdMovimiento INT FOREIGN KEY REFERENCES Far_Movimiento(IdMovimiento),
	IdProducto INT FOREIGN KEY REFERENCES Far_Producto(IdProducto),
	Cantidad INT,
	Precio DECIMAL(14,8),
	Total DECIMAL(14,8),
	Lote VARCHAR(20),
	FechaVencimiento DATETIME,
	RegistroSanitario VARCHAR(20),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)


CREATE TABLE Far_Tipo_Suministro(
	IdTipoSuministro INT PRIMARY KEY IDENTITY(1,1),
	Descripcion VARCHAR(100),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_IDI(
	IdIDI INT PRIMARY KEY IDENTITY(1,1),
	IdTipoSuministro INT FOREIGN KEY REFERENCES  Far_Tipo_Suministro(IdTipoSuministro) NULL,
	IdPeriodo INT FOREIGN KEY REFERENCES  Far_Periodo(IdPeriodo) NOT NULL
)

CREATE TABLE Far_IDI_Detalle(
	IdIDI_Detalle INT PRIMARY KEY IDENTITY(1,1),    
	IdIDI INT FOREIGN KEY REFERENCES  Far_IDI(IdIDI) NOT NULL,
	IdProducto INT FOREIGN KEY REFERENCES Far_Producto(IdProducto) NOT NULL,	
	PrecioOperacion DECIMAL(10,4),
	SaldoAnterior DECIMAL(10,0),
	Ingresos DECIMAL(10,0),
	Reingresos DECIMAL(10,0),
	Distribucion DECIMAL(10,0),
	Transferecia  DECIMAL(10,0),
	Vencido DECIMAL(10,0),
	QuiebreStock DECIMAL(10,0),
	Merma  DECIMAL(10,0),
	Venta DECIMAL(10,0),
	Exoneracion DECIMAL(10,0),
	OtrasSal DECIMAL(10,0),
	Vencimiento DATETIME,
	Activo VARCHAR(1)
)

CREATE TABLE Far_ICI(
	IdICI INT PRIMARY KEY IDENTITY(1,1),
	IdTipoSuministro INT FOREIGN KEY REFERENCES Far_Tipo_Suministro(IdTipoSuministro) NULL,
	IdPeriodo INT FOREIGN KEY REFERENCES  Far_Periodo(IdPeriodo) NOT NULL
)

CREATE TABLE Far_ICI_Detalle(
	IdICI_Detalle INT PRIMARY KEY IDENTITY(1,1),    
	IdICI INT FOREIGN KEY REFERENCES  Far_ICI(IdICI) NOT NULL,
	IdProducto INT FOREIGN KEY REFERENCES Far_Producto(IdProducto) NOT NULL,	
	PrecioOperacion DECIMAL(10,4),
	SaldoAnterior DECIMAL(10,0),
	Ingresos DECIMAL(10,0),
	Ventas DECIMAL(10,0),
	SIS  DECIMAL(10,0),
	IntervSanit DECIMAL(10,0),
	FactPerd  DECIMAL(10,0),
	DefensaNacional DECIMAL(10,0),
	Exoneracion DECIMAL(10,0),
	SOAT DECIMAL(10,0),
	CreditoHospitalario DECIMAL(10,0),
    OtrosConvenios DECIMAL(10,0),
    Devolucion DECIMAL(10,0),
    Vencido DECIMAL(10,0),
	QuiebreStock DECIMAL(10,0),
	Merma DECIMAL(10,0),
	Otras_Sal DECIMAL(10,0),
	Vencimiento DATETIME,
	Requerimiento DECIMAL(10,0),
	Activo VARCHAR(1)
)

CREATE TABLE Far_IME(
    IdIME INT PRIMARY KEY IDENTITY(1,1),
    IdPeriodo INT FOREIGN KEY REFERENCES  Far_Periodo(IdPeriodo) NOT NULL,    
	AVenta DECIMAL(10,4),
	ACredito DECIMAL(10,4),
	ASoat DECIMAL(10,4),
	AOtros DECIMAL(10,4),
	ASis DECIMAL(10,4),
	AIntsan DECIMAL(10,4),
    Adn DECIMAL(10,4),
    BVenta DECIMAL(10,4),
	BCredito DECIMAL(10,4),
	BSoat DECIMAL(10,4),
	BOtros DECIMAL(10,4),
	BSis DECIMAL(10,4),
	BIntsan DECIMAL(10,4),
	Bdn DECIMAL(10,4),
    DCredito DECIMAL(10,4),
    DSoat DECIMAL(10,4),
	DOtros DECIMAL(10,4),
	DSis DECIMAL(10,4),
	DIntsan DECIMAL(10,4),
	Ddn DECIMAL(10,4),
	GSaldoAntMed DECIMAL(10,4),
	ExonPositivo DECIMAL(10,4),
    MesAntGadm DECIMAL(10,4),
    ExoNegativo DECIMAL(10,4),
	H10Positivo DECIMAL(10,4),
	TotalAbastMesNeg DECIMAL(10,4),
	Sadma DECIMAL(10,4),
	FortNegativo DECIMAL(10,4),
	TotalGadm DECIMAL(10,4),
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_IME_II(
	IdFarImeII INT PRIMARY KEY IDENTITY(1,1),
	IdIME INT FOREIGN KEY REFERENCES  Far_IME(IdIME) NOT NULL,
	Fecha DATETIME,
	Partida VARCHAR(10),
	Importe DECIMAL(10,4)
)

CREATE TABLE Far_IME_III(
	IdFarImeIII INT PRIMARY KEY IDENTITY(1,1),
	IdIME INT FOREIGN KEY REFERENCES  Far_IME(IdIME) NOT NULL,
	Fecha DATETIME,
	Partida VARCHAR(10),
	Detalle VARCHAR(20),
	DocFuente VARCHAR(12),
	Importe DECIMAL(10,4)
)


CREATE TABLE Far_Proceso_Inventario (
    IdProcesoInventario INT PRIMARY KEY IDENTITY(1,1),
    Descripcion VARCHAR(70) NOT NULL,
    Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Inventario (
    IdInventario INT PRIMARY KEY IDENTITY(1,1),
	IdPeriodo INT FOREIGN KEY REFERENCES  Far_Periodo(IdPeriodo) NOT NULL,
	NumeroInventario INT NOT NULL,
	IdAlmacen INT FOREIGN KEY REFERENCES  Far_Almacen(IdAlmacen) NOT NULL,
	FechaProceso DATETIME NOT NULL,
	FechaCierre DATETIME NULL,
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)

CREATE TABLE Far_Inventario_Producto(
	IdInventarioProducto INT PRIMARY KEY IDENTITY(1,1),
	IdProducto INT FOREIGN KEY REFERENCES Far_Producto(IdProducto) NOT NULL,
	IdInventario INT FOREIGN KEY REFERENCES  Far_Inventario(IdInventario) NOT NULL,
	Lote VARCHAR(20) NOT NULL,
	FechaVencimiento DATETIME NOT NULL,
	Cantidad INT NOT NULL,
	Precio DECIMAL(10,4) NOT NULL,
	Total DECIMAL(10,4) NOT NULL,
	Conteo INT NULL,
	CantidadFaltante INT NULL,
	CantidadSobrante INT NULL,
	CantidadAlterado INT NULL,	
	Activo INT NOT NULL,
	UsuarioCreacion INT NOT NULL,
	FechaCreacion DATETIME NOT NULL,
	UsuarioModificacion INT,
	FechaModificacion DATETIME
)