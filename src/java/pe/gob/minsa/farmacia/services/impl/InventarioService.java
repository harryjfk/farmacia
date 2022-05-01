package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.InventarioDao;
import pe.gob.minsa.farmacia.domain.Inventario;
import pe.gob.minsa.farmacia.util.BusinessException;

public class InventarioService {

    @Autowired
    InventarioDao inventarioDao;

    public Inventario obtener(int idPeriodo, int idAlmacen, Timestamp fechaProceso, int idTipoProceso){
        return inventarioDao.obtener(idPeriodo, idAlmacen, fechaProceso, idTipoProceso);
    }
    
    public void insertar(Inventario inventario) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (inventarioDao.existe(inventario.getIdPeriodo(), inventario.getIdAlmacen(), inventario.getFechaProceso(),inventario.getIdTipoProceso())) {
            errores.add("Ya existe el inventario del almacén");
        }
        
        inventario.setNumeroInventario(
                inventarioDao.obtenerCorrelativoNumeroInventario(inventario.getIdPeriodo())
        );
        inventario.setFechaCierre(null);
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            inventario.setActivo(1);
            inventarioDao.insertar(inventario);
        }
    }

    public boolean existe(int idPeriodo, int idAlmacen, Timestamp fechaProceso, int idTipoProceso) {
        return inventarioDao.existe(idPeriodo, idAlmacen, fechaProceso, idTipoProceso);
    }
}
