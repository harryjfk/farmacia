package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ProductoDao;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoIngresoDto;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoSalidaDto;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockEnAlmacen;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockPorAlmacen;
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockFechaDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockMinimo;
import pe.gob.minsa.farmacia.domain.dto.StockGeneralProductoDto;
import pe.gob.minsa.farmacia.domain.param.ProductoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.ProductoParam;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ProductoService implements ServiceManager<Producto> {

    @Autowired
    ProductoDao productoDao;

    @Override
    public List<Producto> listar() {
        return productoDao.listar();
    }

    public List<ProductoComp> listar(ProductoParam productoParam) {
        return productoDao.listar(productoParam);
    }

    public List<ProductoComp> listarPorAlmacen(ProductoAlmacenParam productoAlmacenParam) {
        return productoDao.listarPorAlmacen(productoAlmacenParam);
    }

    public List<ProductoComp> listarMedicamento() {
        return productoDao.listarMedicamento();
    }

    public List<StockGeneralProductoDto> listarStockGeneral(String descripcion) {
        return productoDao.listarStockGeneral(descripcion);
    }
    
    public List<DetalleStockPorAlmacen> listarStockPorAlmacenes(int idProducto){
        return productoDao.listarStockPorAlmacenes(idProducto);
    }
    
    public List<DetalleStockEnAlmacen> listarStockPorAlmacen(int idProducto, int idAlmacen, Timestamp fecha){
        return productoDao.listarStockPorAlmacen(idProducto, idAlmacen, fecha);
    }
    
    public List<ProductoStockFechaDto> listarProductoAFecha(Timestamp fecha, int idAlmacen){
        return productoDao.listarProductoAFecha(fecha, idAlmacen);
    }
        
    public List<AyudaProductoIngresoDto> listarAyudaProductoIngreso(String criterio){
        return productoDao.listarAyudaProductoIngreso(criterio);
    }
    
    public List<AyudaProductoSalidaDto> listarAyudaProductoSalida(String criterio, int idAlmacen){
        return productoDao.listarAyudaProductoSalida(criterio, idAlmacen);
    }
    
    public List<ProductoAlertaVencimientoDto> listarAlertaVencimiento(){
        return productoDao.listarAlertaVencimiento();
    }
    
    public List<ProductoAlertaVencimientoDto> listarProductosVencimiento(int idAlmacen, int idProducto, Timestamp fechaVenDesde, Timestamp fechaVenHasta){
        return productoDao.listarProductosVencimiento(idAlmacen, idProducto, fechaVenDesde, fechaVenHasta);
    }
    
    public List<ProductoStockMinimo> listarConStockMinimo(){
        return productoDao.listarConStockMinimo();
    }

    @Override
    public Producto obtenerPorId(int id) throws BusinessException {
        Producto producto = productoDao.obtenerPorId(id);

        if (producto == null) {
            throw new BusinessException(Arrays.asList("No se encontró el producto"));
        }

        return producto;
    }

    public ProductoComp obtenerProductoCompPorId(int id) throws BusinessException {
        ProductoComp producto = productoDao.obtenerProductoCompPorId(id);

        if (producto == null) {
            throw new BusinessException(Arrays.asList("No se encontró el producto"));
        }

        return producto;
    }

    public ProductoComp obtenerProductoCompPorIdPorSolicitud(int id, int idSolicitud) throws BusinessException {
        ProductoComp producto = productoDao.obtenerProductoCompPorIdPorSolicitud(id, idSolicitud);

        if (producto == null) {
            throw new BusinessException(Arrays.asList("No se encontró el producto"));
        }

        return producto;
    }
    
    private void validarLocal(Producto producto, List<String> errores) {

    }

    @Override
    public void insertar(Producto producto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(producto, errores);

        producto.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoDao.insertar(producto);
        }
    }

    @Override
    public void actualizar(Producto producto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(producto, errores);
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoDao.actualizar(producto);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Producto producto = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (producto.getActivo() == 1) {
                producto.setActivo(0);
            } else {
                producto.setActivo(1);
            }

            productoDao.actualizar(producto);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoDao.eliminar(id);
        }
    }
}
