package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.internal.helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.TipoMovimiento;
import pe.gob.minsa.farmacia.services.impl.MovimientoService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.GpServiceManager;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.PedidoPg;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpConcepto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPeriodo;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoDocumentoMov;

@Service("pedidoService")
public class PedidoService extends GpServiceManager<GpMovimiento> {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory emf;
    private PedidoPg paginador;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private MovimientoService movimientoService;

    public PedidoService() {
        super(GpMovimiento.class);
    }

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
        setEntityManager(em);
        paginador = new PedidoPg();
        paginador.setColumns(new String[]{"idAlmacenOrigen", "idPeriodo", "concepto", "idTipoDocumentoMov", "numeroDocumentoMov", "idMovimiento"});
        paginador.setEntityManager(em);
    }

    public List<GpMovimiento> listarRango(int[] range, String sSearch, Object[] sort, String periodo, long idAlmacen, long idConcepto) {
        paginador.setIdPeriodo(periodo);
        paginador.setIdAlmacenOrigen(idAlmacen);
        paginador.setIdConcepto(idConcepto);
        return paginador.listarRango(0, range, sSearch, sort);
    }

    public int contarPaginado(String sSearch, String periodo, long idAlmacen, long idConcepto) {
        paginador.setIdPeriodo(periodo);
        paginador.setIdAlmacenOrigen(idAlmacen);
        paginador.setIdConcepto(idConcepto);
        return paginador.contarPaginado(0, sSearch);
    }

    public boolean insertar(GpMovimiento movimiento, List<GpMovimientoProducto> productos) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (productos.isEmpty()) {
            errores.add("Debe agregar como mínimo un producto");
        }

        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }

        boolean ok = true;
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            GpPeriodo gpp = obtenerPeriodoActivo();
            Calendar calendar = Calendar.getInstance();
            movimiento.setIdPeriodo(gpp);
            movimiento.setTipoMovimiento('S');//Se inserta como salida hasta nuevo aviso
            movimiento.setIdTipoDocumentoMov(null);
            movimiento.setIdTipoCompra(null);
            movimiento.setIdTipoProceso(null);
            movimiento.setNumeroMovimiento(
                    movimientoService.obtenerNumeroCorrelativoSalida(movimiento.getIdPeriodo().getIdPeriodo(), movimiento.getIdAlmacenOrigen().getIdAlmacen())
            );
            for (GpMovimientoProducto mp : productos) {
                GpProducto producto = mp.getIdProducto();
                producto = em.getReference(GpProducto.class, producto.getIdProducto());
                mp.setIdProducto(producto);
                mp.setIdMovimiento(movimiento);
            }
            movimiento.setGpMovimientoProductoList(productos);
            GpAlmacen almacenOrigen = movimiento.getIdAlmacenOrigen();
            almacenOrigen = em.getReference(GpAlmacen.class, almacenOrigen.getIdAlmacen());
            movimiento.setIdAlmacenOrigen(almacenOrigen);
            GpConcepto concepto = movimiento.getConcepto();
            if (concepto != null) {
                concepto = em.getReference(GpConcepto.class, concepto.getIdConcepto());
                movimiento.setConcepto(concepto);
            }
            GpTipoDocumentoMov tipoDocumentoMov = movimiento.getIdTipoDocumentoMov();
            if (tipoDocumentoMov != null) {
                tipoDocumentoMov = em.getReference(GpTipoDocumentoMov.class, tipoDocumentoMov.getIdTipoDocumentoMov());
                movimiento.setIdTipoDocumentoMov(tipoDocumentoMov);
            }
            movimiento.setFechaRegistro(calendar.getTime());
            movimiento.setFechaCreacion(calendar.getTime());
            ok = this.insertar(movimiento);
        }
        return ok;
    }

    public boolean actualizar(GpMovimiento movimiento, List<GpMovimientoProducto> productos) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (productos.isEmpty()) {
            errores.add("Debe agregar como mínimo un producto");
        }

        if (movimiento.getIdAlmacenOrigen() == null) {
            errores.add("El almacén origen es un campo requerido");
        }

        boolean ok = true;
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            Calendar calendar = Calendar.getInstance();

            GpAlmacen almacenOrigen = movimiento.getIdAlmacenOrigen();
            almacenOrigen = em.getReference(GpAlmacen.class, almacenOrigen.getIdAlmacen());
            GpConcepto concepto = movimiento.getConcepto();
            GpTipoDocumentoMov tipoDocumentoMov = movimiento.getIdTipoDocumentoMov();
            String referencia = movimiento.getReferencia();

            movimiento = this.obtenerPorId(movimiento.getIdMovimiento());
            List<GpMovimientoProducto> oldMovProds = movimiento.getGpMovimientoProductoList();
            for (GpMovimientoProducto mp : productos) {
                GpProducto producto = mp.getIdProducto();
                producto = em.getReference(GpProducto.class, producto.getIdProducto());
                mp.setIdProducto(producto);
                mp.setIdMovimiento(movimiento);
            }
            movimiento.setGpMovimientoProductoList(productos);
            movimiento.setIdAlmacenOrigen(almacenOrigen);
            movimiento.setReferencia(referencia);
            if (concepto != null) {
                concepto = em.getReference(GpConcepto.class, concepto.getIdConcepto());
                movimiento.setConcepto(concepto);
            }
            if (tipoDocumentoMov != null) {
                tipoDocumentoMov = em.getReference(GpTipoDocumentoMov.class, tipoDocumentoMov.getIdTipoDocumentoMov());
                movimiento.setIdTipoDocumentoMov(tipoDocumentoMov);
            }
            movimiento.setFechaModificacion(calendar.getTime());
            ok = this.actualizar(movimiento);
            //Borro los viejos
            //TODO Debuguear para ver que esta pasando.
            for (GpMovimientoProducto oldMovProd : oldMovProds) {
                boolean esta = false;
                for (GpMovimientoProducto producto : productos) {
                    if (oldMovProd.getIdProducto().equals(producto.getIdProducto())
                            && oldMovProd.getLote().equals(producto.getLote())
                            && oldMovProd.getPrecio().equals(producto.getPrecio())) {
                        //es el mismo movimiento.
                        esta = true;
                        if (!oldMovProd.getCantidad().equals(producto.getCantidad())) {
                            //la cantidad se actualizo, por lo tanto elimino el viejo
                            em.getTransaction().begin();
                            em.remove(oldMovProd);
                            em.getTransaction().commit();
                            break;
                        }
                    }
                }
                if (!esta) {
                    em.getTransaction().begin();
                    em.remove(oldMovProd);
                    em.getTransaction().commit();
                }
            }
        }
        return ok;
    }

    private GpPeriodo obtenerPeriodoActivo() {
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        GpPeriodo gpp = new GpPeriodo(periodo.getIdPeriodo());
        gpp.setActivo(1);
        gpp.setFechaCreacion(periodo.getFechaCreacion());
        gpp.setFechaModificacion(periodo.getFechaModificacion());
        gpp.setUsuarioCreacion(periodo.getUsuarioCreacion());
        gpp.setUsuarioModificacion(periodo.getUsuarioModificacion());
        return gpp;
    }

    private Movimiento crearMovimiento(GpMovimiento movimiento) {
        GpPeriodo gpp = obtenerPeriodoActivo();
        Movimiento mov = new Movimiento();
        mov.setIdAlmacenOrigen(movimiento.getIdAlmacenOrigen().getIdAlmacen());
        mov.setIdPeriodo(gpp.getIdPeriodo());
        mov.setTipoMovimiento(TipoMovimiento.SALIDA);//Se inserta como salida
        mov.setIdAlmacenDestino(null);
        mov.setIdTipoDocumentoMov(null);
        mov.setNumeroDocumentoMov("");
        mov.setFechaRecepcion(null);
        mov.setIdDocumentoOrigen(null);
        mov.setNumeroDocumentoOrigen("");
        mov.setFechaDocumentoOrigen(null);
        mov.setIdProveedor(null);
        mov.setIdTipoCompra(null);
        mov.setIdTipoProceso(null);
        mov.setNumeroProceso("");
        mov.setNumeroMovimiento(
                movimientoService.obtenerNumeroCorrelativoSalida(mov.getIdPeriodo(), mov.getIdAlmacenOrigen())
        );
        return mov;
    }

    private List<MovimientoProducto> crearMovimientoProductos(List<GpMovimientoProducto> productos) {
        List<MovimientoProducto> mps = new ArrayList<MovimientoProducto>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (GpMovimientoProducto producto : productos) {
            MovimientoProducto mp = new MovimientoProducto();
            mp.setCantidad(producto.getCantidad());
            mp.setIdProducto(producto.getIdProducto().getIdProducto());
            if (producto.getIdMovimiento() != null) {
                mp.setIdMovimiento(producto.getIdMovimiento().getIdMovimiento());
            }
            mp.setLote(producto.getLote());
            mp.setPrecio(producto.getPrecio());
            mp.setRegistroSanitario("");
            mp.setTotal(producto.getTotal());
            Timestamp fv = Helper.timestampFromDate(producto.getFechaVencimiento());
            mp.setFechaVencimiento(fv);
        }
        return mps;
    }
}
