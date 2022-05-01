package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.UnidadDao;
import pe.gob.minsa.farmacia.domain.Unidad;

public class UnidadService {
    
    @Autowired
    UnidadDao unidadDao;
    
    public List<Unidad> listar(){
        return unidadDao.listar();
    }
}
