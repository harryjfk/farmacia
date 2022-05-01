package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.SubmoduloDao;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.services.NavegacionServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class SubmoduloService implements NavegacionServiceManager<Submodulo> {

    @Autowired
    SubmoduloDao submoduloDao;

    @Override
    public List<Submodulo> listar() {
        return submoduloDao.listar();
    }

    @Override
    public List<Submodulo> listarParaSession(int idUsuario) {
        return submoduloDao.listarParaSession(idUsuario);
    }

    @Override
    public List<Submodulo> listarActivos() {
        List<Submodulo> submodulos = new ArrayList<Submodulo>();

        for (Submodulo s : submoduloDao.listar()) {
            if (s.getActivo() == 1) {
                submodulos.add(s);
            }
        }

        return submodulos;
    }

    public List<Submodulo> listarPorIdModulo(int idModulo) {

        List<Submodulo> submodulos = new ArrayList<Submodulo>();

        for (Submodulo s : submoduloDao.listar()) {
            if (idModulo == s.getIdModulo()) {
                submodulos.add(s);
            }
        }

        return submodulos;
    }

    @Override
    public Submodulo obtenerPorId(int id) throws BusinessException {
        Submodulo submodulo = submoduloDao.obtenerPorId(id);

        if (submodulo == null) {
            throw new BusinessException(Arrays.asList("No se encontró el submódulo"));
        }

        return submodulo;
    }
    
    public void cambiarOrden(int idSubmodulo, boolean subida) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
                
        if(idSubmodulo <= 0){
            errores.add("El código del submódulo debe ser mayor a cero");
        }
        
        Submodulo submodulo = this.obtenerPorId(idSubmodulo);
                
        if(subida){            
            int ordenMaximo = 0;

            for (Submodulo s : this.listarPorIdModulo(submodulo.getIdModulo())) {
                if (ordenMaximo < s.getOrden()) {
                    ordenMaximo = s.getOrden();
                }
            }
            
            if(submodulo.getOrden() == ordenMaximo){
                errores.add("No puede aumentar más el orden");
            }
        }else{
            if(submodulo.getOrden() == 1){
                errores.add("No puede disminuir más el orden");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            submoduloDao.cambiarOrden(idSubmodulo, subida);
        }
    }

    @Override
    public void actualizar(Submodulo submodulo) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (submodulo.getNombreSubmodulo().isEmpty()) {
            errores.add("El nombre del submódulo es un campo requerido");
        }

        for (Submodulo s : submoduloDao.listar()) {
            if (submodulo.getNombreSubmodulo().equalsIgnoreCase(s.getNombreSubmodulo())
                    && submodulo.getIdModulo() == s.getIdModulo()
                    && submodulo.getIdSubmodulo() != s.getIdSubmodulo()) {
                errores.add("Ya existe el nombre del submódulo " + submodulo.getNombreSubmodulo() + " en el módulo seleccionado");
                break;
            }
        }

        Submodulo submoduloTemp = this.obtenerPorId(submodulo.getIdSubmodulo());
        submodulo.setOrden(submoduloTemp.getOrden());

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            submoduloDao.actualizar(submodulo);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        Submodulo submodulo = this.obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {            
            if (submodulo.getActivo() == 1) {
                submodulo.setActivo(0);
            } else {
                submodulo.setActivo(1);
            }
            submoduloDao.actualizar(submodulo);
        }
    }
}
