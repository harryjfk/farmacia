package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.PersonalDao;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.util.BusinessException;

public class PersonalService {

    @Autowired
    PersonalDao personalDao;

    public List<Personal> listar() {
        return personalDao.listar();
    }
    
    public List<Personal> listarMedico() {
        return personalDao.listarMedico();
    }

    public List<Personal> listarSinUsuario() {
        return personalDao.listarSinUsuario();
    }
    
    public List<Personal> listarPorUnidad(String idUnidad) {
        return personalDao.listarPorUnidad(idUnidad);
    }
    
    public List<Personal> listarMedico(String id) {
        List<Personal> medicos = new ArrayList<Personal>();

        for (Personal t : personalDao.listarMedico()) {
            if (t.getIdPersonal().equals(id)) {
                medicos.add(t);
            }
        }

        return medicos;
    }
    
     public Personal obtenerPorId(String id) throws BusinessException {
        
        Personal medico = personalDao.obtenerPorId(id);

        if (medico == null) {
            throw new BusinessException(Arrays.asList("No se encontró el personal"));
        }

        return medico;
    }
}
