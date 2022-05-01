package pe.gob.minsa.farmacia.services.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.InventarioProductoDao;
import pe.gob.minsa.farmacia.domain.InventarioProducto;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;
import pe.gob.minsa.farmacia.util.BusinessException;

public class InventarioProductoService {

    @Autowired
    InventarioProductoDao inventarioProductoDao;
    
    public List<InventarioProducto> listar(int idProducto, int idInventario){
        return inventarioProductoDao.listar(idProducto, idInventario);
    }
    
    public InventarioProducto obtenerPorId(int idInventarioProducto){
        return inventarioProductoDao.obtenerPorId(idInventarioProducto);
    }
    
    public void preparar(int idInventario, int usuarioCreacion){
        inventarioProductoDao.preparar(idInventario, usuarioCreacion);
    }
    
    public void procesar(int idInventario, int usuarioModificacion) throws BusinessException{
        inventarioProductoDao.procesar(idInventario, usuarioModificacion);
    }
    
    public List<InventarioProductoTotalDto> listarTotales(int idInventario){
        return inventarioProductoDao.listarTotales(idInventario);
    }
    
    public void insertar(InventarioProducto inventarioProducto) throws BusinessException{
        
        if(inventarioProducto.getConteo() > inventarioProducto.getCantidad()){
            inventarioProducto.setCantidadFaltante(0);
            inventarioProducto.setCantidadSobrante(inventarioProducto.getConteo() - inventarioProducto.getCantidad());
        }else if(inventarioProducto.getCantidad() > inventarioProducto.getConteo()){
            inventarioProducto.setCantidadFaltante(inventarioProducto.getCantidad() - inventarioProducto.getConteo());
            inventarioProducto.setCantidadSobrante(0);
        }else{
            inventarioProducto.setCantidadFaltante(0);
            inventarioProducto.setCantidadSobrante(0);
        }
        
        inventarioProducto.setTotal(
                inventarioProducto.getPrecio().multiply(new BigDecimal(inventarioProducto.getCantidad()))
        );
        inventarioProductoDao.insertar(inventarioProducto);
    }
    
    public void modificarConteo(int idInventarioProducto, int conteo, int alterado, int usuarioModificacion)  throws BusinessException{
        InventarioProducto inventarioProducto = inventarioProductoDao.obtenerPorId(idInventarioProducto);
                
        if(conteo > inventarioProducto.getCantidad()){
            inventarioProducto.setCantidadFaltante(0);
            inventarioProducto.setCantidadSobrante(conteo - inventarioProducto.getCantidad());
        }else if(inventarioProducto.getCantidad() > conteo){
            inventarioProducto.setCantidadFaltante(inventarioProducto.getCantidad() - conteo);
            inventarioProducto.setCantidadSobrante(0);
        }else{
            inventarioProducto.setCantidadFaltante(0);
            inventarioProducto.setCantidadSobrante(0);
        }
        
        inventarioProducto.setConteo(conteo);
        inventarioProducto.setCantidadAlterado(alterado);
        inventarioProducto.setUsuarioModificacion(usuarioModificacion);
        inventarioProductoDao.actualizar(inventarioProducto);
    }
    
    public void actualizar(InventarioProducto inventarioProducto){
        inventarioProductoDao.actualizar(inventarioProducto);
    }
}
