package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.PerfilDao;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class PerfilService implements ServiceManager<Perfil> {

    @Autowired
    PerfilDao perfilDao;

    @Override
    public List<Perfil> listar() {
        return perfilDao.listar();
    }

    @Override
    public Perfil obtenerPorId(int id) throws BusinessException {

        Perfil perfil = perfilDao.obtenerPorId(id);

        if (perfil == null) {
            throw new BusinessException(Arrays.asList("No se encontró el perfil"));
        }

        return perfil;
    }

    @Override
    public void insertar(Perfil perfil) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (perfil.getNombrePerfil().isEmpty()) {
            errores.add("El nombre de perfil es un campo requerido");
        }

        for (Perfil p : perfilDao.listar()) {
            if (perfil.getNombrePerfil().equalsIgnoreCase(p.getNombrePerfil())) {
                errores.add("Ya existe el nombre de perfil " + perfil.getNombrePerfil());
                break;
            }
        }

        perfil.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            perfilDao.insertar(perfil);
        }
    }

    @Override
    public void actualizar(Perfil perfil) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (perfil.getActivo() == 0 && perfil.getIdPerfil() == 1) {
            errores.add("No puede desactivar el perfil administrador");
        }

        if (perfil.getNombrePerfil().isEmpty()) {
            errores.add("El nombre de perfil es un campo requerido");
        }

        for (Perfil p : perfilDao.listar()) {
            if (perfil.getNombrePerfil().equalsIgnoreCase(p.getNombrePerfil())
                    && perfil.getIdPerfil() != p.getIdPerfil()) {
                errores.add("Ya existe el nombre de perfil " + perfil.getNombrePerfil());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            perfilDao.actualizar(perfil);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        Perfil perfil = obtenerPorId(id);

        if (perfil.getIdPerfil() == 1) {
            errores.add("No puede desactivar el perfil administrador");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (perfil.getActivo() == 1) {
                perfil.setActivo(0);
            } else {
                perfil.setActivo(1);
            }

            perfilDao.actualizar(perfil);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();

        if (id == 1) {
            errores.add("No puede eliminar el perfil administrador");
        }else{
            if(perfilDao.esUsado(id)){
                errores.add("No puede eliminar el perfil, está en uso");
            }
        }               

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            perfilDao.eliminar(id);
        }
    }
}
