package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.MovimientoProductoDao;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.dto.DetallePorLoteDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoProductoStock;
import pe.gob.minsa.farmacia.util.BusinessException;

public class MovimientoProductoService{

    @Autowired
    private MovimientoProductoDao movimientoProductoDao;
    
    public List<MovimientoProducto> listarPorMovimiento(int idMovimiento) {        
        return movimientoProductoDao.listarPorMovimiento(idMovimiento);
    }
    
    public MovimientoProducto obtenerPorLote(int idProducto, String lote){
        return movimientoProductoDao.obtenerPorLote(idProducto, lote);
    }    
    public List<String> obtenerLotes(int idProducto, int idAlmacen){
        return movimientoProductoDao.obtenerLotes(idProducto, idAlmacen);
    }
    
     public List<MovimientoProductoStock> obtenerMovimientos(int idAlmacen, int idProducto){
         return movimientoProductoDao.obtenerMovimientos(idAlmacen, idProducto);
     }
    
    public DetallePorLoteDto obtenerDetallePorLote(int idProducto, String lote){
        return movimientoProductoDao.obtenerDetallePorLote(idProducto, lote);
    }
    
    public void ingresoInicial(int idMovimiento, int idPeriodoCerrar, int idAlmacenDestino, int usuarioCreacion) {
        movimientoProductoDao.ingresoInicial(idMovimiento, idPeriodoCerrar, idAlmacenDestino, usuarioCreacion);
    }
   
    public void insertar(MovimientoProducto movimientoProducto) throws BusinessException {
        movimientoProducto.setActivo(1);
        movimientoProductoDao.insertar(movimientoProducto);
    }
    
    public void eliminarPorMovimiento(int idMovimiento){
        movimientoProductoDao.eliminarPorMovimiento(idMovimiento);
    }
    
    public int stockPorProductoYAlmacen(int idProducto, int idAlmacen, String lote){
        return movimientoProductoDao.stockPorIdProducto(idProducto, idAlmacen, lote);
    }
}
