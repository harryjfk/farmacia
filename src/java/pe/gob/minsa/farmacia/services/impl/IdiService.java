package pe.gob.minsa.farmacia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.IdiDao;
import pe.gob.minsa.farmacia.domain.Idi;

public class IdiService {
    @Autowired
    IdiDao idiDao;
    
    public Idi obtenerPorPeriodo(int idPeriodo, int idTipoSuministro, int idTipoProceso){
        return idiDao.obtenerPorPeriodo(idPeriodo, idTipoSuministro, idTipoProceso);
    }
}
