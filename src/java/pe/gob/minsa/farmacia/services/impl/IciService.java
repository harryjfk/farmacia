package pe.gob.minsa.farmacia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.IciDao;
import pe.gob.minsa.farmacia.domain.Ici;

public class IciService {
    
    @Autowired
    IciDao iciDao;
    
    public Ici obtenerPorPeriodo(int idPeriodo, int idAlmacen, int idTipoSuministro){
        return iciDao.obtenerPorPeriodo(idPeriodo, idAlmacen, idTipoSuministro);
    }
}
