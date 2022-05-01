package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.domain.param.MovimientoParam;
import pe.gob.minsa.farmacia.dao.impl.MovimientoDao;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.Parametro;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.domain.dto.IngresoAlmacenDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoDto;
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.domain.param.ConsultaMovimientoParam;
import pe.gob.minsa.farmacia.domain.param.GuiasRemisionParam;
import pe.gob.minsa.farmacia.domain.param.IngresoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.TarjetaControlParam;
import pe.gob.minsa.farmacia.domain.TipoMovimiento;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class MovimientoService implements ServiceManager<Movimiento> {

    @Autowired
    MovimientoDao movimientoDao;
    
    @Autowired
    MovimientoProductoService movimientoProductoService;
    
    @Autowired
    ParametroService parametroService;
    
    @Autowired
    PeriodoService periodoService;
    
    @Autowired
    ProveedorService proveedorService;
    
    @Autowired
    AlmacenService almacenService;
    
    private final String nombreParametroGuia = "GUIARE";
    private final String nombreParametroInicial = "INICIO";

    @Override
    public List<Movimiento> listar() {
        return movimientoDao.listar();
    }
    
    public List<Movimiento> listarPorTipo(MovimientoParam movimentoParam) {
        return movimientoDao.listarPorTipo(movimentoParam);
    }
    
    public int ingresoInicial(int idAlmacen, int idPeriodoAbrir, int usuarioCreacion){
        return movimientoDao.ingresoInicial(idAlmacen, idPeriodoAbrir, usuarioCreacion);
    }
    
    public List<Movimiento> listarGuiasRemision(GuiasRemisionParam guiasRemisionParam){
        
        int idTipoGuiaRemision = Integer.parseInt(parametroService.obtenerPorNombre(nombreParametroGuia).getValor());
        
        MovimientoParam movimientoParam = new MovimientoParam();        
        movimientoParam.setTipoMovimento(TipoMovimiento.INGRESO);
        movimientoParam.setFechaDesde(guiasRemisionParam.getFechaDesde());
        movimientoParam.setFechaHasta(guiasRemisionParam.getFechaHasta());        
        
        List<Movimiento> guiasRemision = new ArrayList<Movimiento>();
        
        for(Movimiento mov : this.listarPorTipo(movimientoParam)){                    
            if(mov.getTipoDocumentoMov().getIdTipoDocumentoMov() == idTipoGuiaRemision){
                guiasRemision.add(mov);
            }
        }
        
        return guiasRemision;
    }
    
    public List<MovimientoDto> consultaMovimiento(ConsultaMovimientoParam consultaMovimientoParam){
        return movimientoDao.consultaMovimiento(consultaMovimientoParam);
    }
    
    public List<TarjetaControlDto> tarjetaControl(TarjetaControlParam tarjetaControlParam){
        return movimientoDao.tarjetaControl(tarjetaControlParam);
    }
    
    public List<IngresoAlmacenDto> listarIngresoPorAlmacen(IngresoAlmacenParam ingresoAlmacenParam){
        return movimientoDao.listarIngresoPorAlmacen(ingresoAlmacenParam);
    }
    
    public Movimiento obtenerPorIdIngreso(int idIngreso) throws BusinessException {
        Movimiento movimiento = movimientoDao.obtenerPorIdIngreso(idIngreso);
        return movimiento;
    }

    @Override
    public Movimiento obtenerPorId(int id) throws BusinessException {
        Movimiento movimiento = movimientoDao.obtenerPorId(id);

        if (movimiento == null) {
            throw new BusinessException(Arrays.asList("No se encontró el movimiento"));
        }

        return movimiento;
    }
    
    public int obtenerNumeroCorrelativoIngreso(int idPeriodo, int idAlmacenDestino){
        return movimientoDao.obtenerNumeroCorrelativoIngreso(idPeriodo, idAlmacenDestino);
    }
    
    public int obtenerNumeroCorrelativoSalida(int idPeriodo, int idAlmacenOrigen){
        return movimientoDao.obtenerNumeroCorrelativoSalida(idPeriodo, idAlmacenOrigen);
    }

    private void validarLocal(Movimiento movimiento, List<String> errores) {
        if (movimiento.getIdPeriodo() == 0) {
            errores.add("El periodo es un campo requerido");
        }

        if (movimiento.getTipoMovimiento() == null) {
            errores.add("El tipo de movimiento es un campo requerido");
        }        

        if (movimiento.getIdConcepto() == 0) {
            errores.add("El concepto es un campo requerido");
        }
    }

    @Override
    public void insertar(Movimiento movimiento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(movimiento, errores);

        movimiento.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            movimientoDao.insertar(movimiento);
        }
    }
    
    @Override
    public void actualizar(Movimiento movimiento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(movimiento, errores);

        movimiento.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            movimientoDao.actualizar(movimiento);
        }
    }
    
    public void insertarSalidaConDetalle(Movimiento movimiento, List<MovimientoProducto> movimientoProductos) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        //Almacen almDestino = almacenService.obtenerPorId(movimiento.getIdAlmacenDestino());
        
        if(movimientoProductos.isEmpty()){
            errores.add("Debe agregar como mínimo un producto");
        }
        
        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            
            if (movimiento.getIdAlmacenDestino() != null) {
                Almacen almDestino = almacenService.obtenerPorId(movimiento.getIdAlmacenDestino());

                if (movimiento.getIdAlmacenDestino() != null && almacenService.obtenerIdAlmacenLogistica() != almDestino.getIdAlmacenPadre()) {
                    this.insertarIngresoConDetalle(movimiento, movimientoProductos);
                    movimiento.setIdMovimientoIngreso(movimiento.getIdMovimiento());
                }
            }
            
            //if(movimiento.getIdAlmacenDestino() != null && almacenService.obtenerIdAlmacenLogistica()!=almDestino.getIdAlmacenPadre()){                
            //    this.insertarIngresoConDetalle(movimiento, movimientoProductos);
            //    movimiento.setIdMovimientoIngreso(movimiento.getIdMovimiento());
            //}  
            
            movimiento.setIdPeriodo(periodoService.obtenerPeriodoActivo().getIdPeriodo());
            movimiento.setTipoMovimiento(TipoMovimiento.SALIDA);
            //movimiento.setIdAlmacenDestino(null);
            //movimiento.setIdTipoDocumentoMov(null);
            //movimiento.setNumeroDocumentoMov("");
            movimiento.setFechaRecepcion(null);
            movimiento.setIdDocumentoOrigen(null);
            movimiento.setNumeroDocumentoOrigen("");
            movimiento.setFechaDocumentoOrigen(null);
            movimiento.setIdProveedor(null);
            movimiento.setIdTipoCompra(null);
            movimiento.setIdTipoProceso(null);
            movimiento.setNumeroProceso("");
            movimiento.setNumeroMovimiento(
                this.obtenerNumeroCorrelativoSalida(movimiento.getIdPeriodo(), movimiento.getIdAlmacenOrigen())
            );
            this.insertar(movimiento);
            
            for(int i =0; i <= movimientoProductos.size() - 1; ++i){
                movimientoProductos.get(i).setIdMovimiento(movimiento.getIdMovimiento());
                movimientoProductoService.insertar(movimientoProductos.get(i));
            }
        }
    }    
    
    public void modificarSalidaConDetalle(Movimiento movimiento, List<MovimientoProducto> movimientoProductos) throws BusinessException {
        List<String> errores = new ArrayList<String>();
       
        if(movimientoProductos.isEmpty()){
            errores.add("Debe agregar como mínimo un producto");
        }
        
        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            
            movimiento.setTipoMovimiento(TipoMovimiento.SALIDA);
            movimiento.setFechaRecepcion(null);
            movimiento.setIdDocumentoOrigen(null);
            movimiento.setNumeroDocumentoOrigen("");
            movimiento.setFechaDocumentoOrigen(null);
            movimiento.setIdProveedor(null);
            movimiento.setIdTipoCompra(null);
            movimiento.setIdTipoProceso(null);
            movimiento.setNumeroProceso("");
            
            //Obtener Movimiento de BD
            Movimiento movimientoTemp = this.obtenerPorId(movimiento.getIdMovimiento());
            movimiento.setIdPeriodo(movimientoTemp.getIdPeriodo());
            movimiento.setNumeroMovimiento(
                  movimientoTemp.getNumeroMovimiento()
            );
            
            movimiento.setActivo(
                  movimientoTemp.getActivo()
            );
            
            this.actualizar(movimiento);
            
            movimientoProductoService.eliminarPorMovimiento(movimiento.getIdMovimiento());
            
            for(int i =0; i <= movimientoProductos.size() - 1; ++i){
                movimientoProductos.get(i).setIdMovimiento(movimiento.getIdMovimiento());
                movimientoProductoService.insertar(movimientoProductos.get(i));
            }
            
            if(movimiento.getIdMovimientoIngreso()!=null){
                movimiento.setIdMovimiento(movimiento.getIdMovimientoIngreso());
                movimiento.setIdMovimientoIngreso(null);
                
                this.modificarIngresoConDetalle(movimiento, movimientoProductos);
            }
        }
    }
    
    public void insertarIngresoConDetalle(Movimiento movimiento, List<MovimientoProducto> movimientoProductos) throws BusinessException {
        List<String> errores = new ArrayList<String>();
       
        if(movimiento.getFechaRegistro() == null){
            errores.add("La fecha de registro es una campo requerido");
        }
        
        if(movimientoProductos.isEmpty()){
            errores.add("Debe agregar como mínimo un producto");
        }
        
        if (movimiento.getIdAlmacenDestino() == null) {
            errores.add("El almacén destino es un campo requerido");
        }
        
        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }
        
        if (movimiento.getIdAlmacenOrigen() != null &&
                movimiento.getIdAlmacenDestino() != null) {
            if(movimiento.getIdAlmacenOrigen() == movimiento.getIdAlmacenDestino()){
                errores.add("No puede seleccionar el mismo almacén de origen/destino");
            }
        }
        
        Parametro inicial = parametroService.obtenerPorNombre(nombreParametroInicial);
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        
        if(movimiento.getIdConcepto() == Integer.parseInt(inicial.getValor())){
            boolean existeSaldoInicial = movimientoDao.ExisteSaldoInicial(periodo.getIdPeriodo(), Integer.parseInt(inicial.getValor()));
            
            if(existeSaldoInicial){
                 errores.add("No puede registrar un movimiento saldo inicial porque ya existe en el periodo");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            Proveedor proveedor = (Proveedor) movimiento.getProveedor();
            if(proveedor.getIdProveedor() == 0){
                if(proveedor.getRuc() != null &&
                        proveedor.getRuc().isEmpty() == false && 
                        proveedor.getRazonSocial() != null &&
                        proveedor.getRazonSocial().isEmpty() == false){
                    proveedorService.insertar(proveedor);
                    movimiento.setIdProveedor(proveedor.getIdProveedor());
                }
            }
            
            movimiento.setIdPeriodo(periodo.getIdPeriodo());
            movimiento.setTipoMovimiento(TipoMovimiento.INGRESO);
            movimiento.setNumeroMovimiento(
                this.obtenerNumeroCorrelativoIngreso(movimiento.getIdPeriodo(), movimiento.getIdAlmacenDestino())
            );
            
            this.insertar(movimiento);
            
            for(int i =0; i <= movimientoProductos.size() - 1; ++i){
                movimientoProductos.get(i).setIdMovimiento(movimiento.getIdMovimiento());
                movimientoProductoService.insertar(movimientoProductos.get(i));
            }
            
            
        }
    }

    public void modificarIngresoConDetalle(Movimiento movimiento, List<MovimientoProducto> movimientoProductos) throws BusinessException{
        List<String> errores = new ArrayList<String>(); 
        if(movimiento.getFechaRegistro() == null){
            errores.add("La fecha de registro es una campo requerido");
        }
        
        if(movimientoProductos.isEmpty()){
            errores.add("Debe agregar como mínimo un producto");
        }
        
        if (movimiento.getIdAlmacenDestino() == null) {
            errores.add("El almacén destino es un campo requerido");
        }
        
        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }
        
        if (movimiento.getIdAlmacenOrigen() != null &&
                movimiento.getIdAlmacenDestino() != null) {
            if(movimiento.getIdAlmacenOrigen() == movimiento.getIdAlmacenDestino()){
                errores.add("No puede seleccionar el mismo almacén de origen/destino");
            }
        }
        
        Parametro inicial = parametroService.obtenerPorNombre(nombreParametroInicial);
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        
        if(movimiento.getIdConcepto() == Integer.parseInt(inicial.getValor())){
            boolean existeSaldoInicial = movimientoDao.ExisteSaldoInicial(periodo.getIdPeriodo(), Integer.parseInt(inicial.getValor()));
            
            if(existeSaldoInicial){
                 errores.add("No puede registrar un movimiento saldo inicial porque ya existe en el periodo");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            Proveedor proveedor = (Proveedor) movimiento.getProveedor();
            if(proveedor.getIdProveedor() == 0){
                if(proveedor.getRuc() != null &&
                        proveedor.getRuc().isEmpty() == false && 
                        proveedor.getRazonSocial() != null &&
                        proveedor.getRazonSocial().isEmpty() == false){
                    proveedorService.insertar(proveedor);
                    movimiento.setIdProveedor(proveedor.getIdProveedor());
                }
            }
            
            movimiento.setIdPeriodo(periodo.getIdPeriodo());
            movimiento.setTipoMovimiento(TipoMovimiento.INGRESO);
            
            this.actualizar(movimiento);
            movimientoProductoService.eliminarPorMovimiento(movimiento.getIdMovimiento());
            
            for(int i =0; i <= movimientoProductos.size() - 1; ++i){
                movimientoProductos.get(i).setIdMovimiento(movimiento.getIdMovimiento());
                movimientoProductoService.insertar(movimientoProductos.get(i));
            }
        }
    }
    
    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Movimiento movimiento = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (movimiento.getActivo() == 1) {
                movimiento.setActivo(0);
            } else {
                movimiento.setActivo(1);
            }

            movimientoDao.actualizar(movimiento);
        }
    }
    
    public void anularGuia(int idMovimiento) throws BusinessException {
        Movimiento movimiento = this.obtenerPorId(idMovimiento);
        Parametro parametro = parametroService.obtenerPorNombre(nombreParametroGuia);
                
        if(movimiento.getTipoDocumentoMov().getIdTipoDocumentoMov() != Integer.parseInt(parametro.getValor())){
            throw new BusinessException(Arrays.asList("No se puede anular, este movimiento no es una guía"));
        }else{
            movimiento.setActivo(0);
            movimientoDao.actualizar(movimiento);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            movimientoDao.eliminar(id);
        }
    }    
}