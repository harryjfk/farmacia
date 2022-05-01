package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.FormaFarmaceuticaDao;
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class FormaFarmaceuticaService implements ServiceManager<FormaFarmaceutica> {

    @Autowired
    FormaFarmaceuticaDao formaFarmaceuticaDao;

    @Override
    public List<FormaFarmaceutica> listar() {
        return formaFarmaceuticaDao.listar();
    }

    public List<FormaFarmaceutica> listarActivos() {
        List<FormaFarmaceutica> formasFarmaceuticas = formaFarmaceuticaDao.listar();

        for (int i = 0; i <= formasFarmaceuticas.size() - 1; ++i) {
            if (formasFarmaceuticas.get(i).getActivo() == 0) {
                formasFarmaceuticas.remove(i);
                i = i - 1;
            }
        }

        return formasFarmaceuticas;
    }
    
    public List<FormaFarmaceutica> listarActivos(int idFormaFarmaceutica) {
        List<FormaFarmaceutica> formasFarmaceuticas = formaFarmaceuticaDao.listar();

        for (int i = 0; i <= formasFarmaceuticas.size() - 1; ++i) {
            
            FormaFarmaceutica formaFarmaceutica = formasFarmaceuticas.get(i);
            
            if (formaFarmaceutica.getActivo() == 0 
                    && formaFarmaceutica.getIdFormaFarmaceutica() != idFormaFarmaceutica) {
                formasFarmaceuticas.remove(i);
                i = i - 1;
            }
        }

        return formasFarmaceuticas;
    }

    @Override
    public FormaFarmaceutica obtenerPorId(int id) throws BusinessException {
        FormaFarmaceutica formaFarmaceutica = formaFarmaceuticaDao.obtenerPorId(id);

        if (formaFarmaceutica == null) {
            throw new BusinessException(Arrays.asList("No se encontró la forma farmaceutica"));
        }

        return formaFarmaceutica;
    }

    private void validarLocal(FormaFarmaceutica formaFarmaceutica, List<String> errores) {
        if (formaFarmaceutica.getNombreFormaFarmaceutica().isEmpty()) {
            errores.add("El nombre de la forma farmaceutica es un campo requerido");
        }
    }

    @Override
    public void insertar(FormaFarmaceutica formaFarmaceutica) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(formaFarmaceutica, errores);

        for (FormaFarmaceutica t : formaFarmaceuticaDao.listar()) {
            if (formaFarmaceutica.getNombreFormaFarmaceutica().equalsIgnoreCase(t.getNombreFormaFarmaceutica())) {
                errores.add("Ya existe la forma farmaceutica " + formaFarmaceutica.getNombreFormaFarmaceutica());
                break;
            }
        }

        formaFarmaceutica.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            formaFarmaceuticaDao.insertar(formaFarmaceutica);
        }
    }

    @Override
    public void actualizar(FormaFarmaceutica formaFarmaceutica) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(formaFarmaceutica, errores);

        for (FormaFarmaceutica t : formaFarmaceuticaDao.listar()) {
            if (formaFarmaceutica.getNombreFormaFarmaceutica().equalsIgnoreCase(t.getNombreFormaFarmaceutica())
                    && formaFarmaceutica.getIdFormaFarmaceutica() != t.getIdFormaFarmaceutica()) {
                errores.add("Ya existe la forma farmaceutica " + formaFarmaceutica.getNombreFormaFarmaceutica());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            formaFarmaceuticaDao.actualizar(formaFarmaceutica);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        FormaFarmaceutica formaFarmaceutica = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (formaFarmaceutica.getActivo() == 1) {
                formaFarmaceutica.setActivo(0);
            } else {
                formaFarmaceutica.setActivo(1);
            }

            formaFarmaceuticaDao.actualizar(formaFarmaceutica);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            formaFarmaceuticaDao.eliminar(id);
        }
    }
}
