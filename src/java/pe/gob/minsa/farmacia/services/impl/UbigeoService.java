package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.UbigeoDao;
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.services.TipoUbigeoServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class UbigeoService implements TipoUbigeoServiceManager {

    @Autowired
    UbigeoDao ubigeoDao;

    @Override
    public List<Ubigeo> listar() {
        return ubigeoDao.listar();
    }

    @Override
    public List<Ubigeo> listarActivos() {
        List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();

        for (Ubigeo t : ubigeoDao.listar()) {
            if (t.getActivo() == 1) {
                ubigeos.add(t);
            }
        }

        return ubigeos;
    }

    @Override
    public List<Ubigeo> listarActivos(String id) {
        List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();

        for (Ubigeo t : ubigeoDao.listar()) {
            if (t.getActivo() == 1 || t.getIdUbigeo().equalsIgnoreCase(id)) {
                ubigeos.add(t);
            }
        }

        return ubigeos;
    }

    @Override
    public List<Ubigeo> listarDepartamentos() {
        return ubigeoDao.listarDepartamentos();
    }

    public List<Ubigeo> listarDepartamentosActivos() {

        List<Ubigeo> departamentos = ubigeoDao.listarDepartamentos();

        for (int i = 0; i <= departamentos.size() - 1; ++i) {
            if(departamentos.get(i).getActivo() == 0){
                departamentos.remove(i);
                i=i-1;
            }
        }

        return departamentos;
    }
    
    public List<Ubigeo> listarDepartamentosActivos(String id) {

        List<Ubigeo> departamentos = ubigeoDao.listarDepartamentos();

        for (int i = 0; i <= departamentos.size() - 1; ++i) {
            if(departamentos.get(i).getActivo() == 0 
                    && departamentos.get(i).getIdUbigeo().equalsIgnoreCase(id) == false){
                departamentos.remove(i);
                i=i-1;
            }
        }

        return departamentos;
    }

    @Override
    public List<Ubigeo> listarPronvincias(String idDepartamento) {
        return ubigeoDao.listarPronvincias(idDepartamento);
    }
    
    public List<Ubigeo> listarProvinciasActivos(String idDepartamento) {
        
        List<Ubigeo> provincias = ubigeoDao.listarPronvincias(idDepartamento);
        
        for (int i = 0; i <= provincias.size() - 1; ++i) {
            if(provincias.get(i).getActivo() == 0){
                provincias.remove(i);
                i=i-1;
            }
        }
        
        return provincias;
    }
    
    public List<Ubigeo> listarProvinciasActivos(String idDepartamento, String idProvincia) {
        
        List<Ubigeo> provincias = ubigeoDao.listarPronvincias(idDepartamento);
        
        for (int i = 0; i <= provincias.size() - 1; ++i) {            
            if(provincias.get(i).getActivo() == 0 
                    && provincias.get(i).getIdUbigeo().equalsIgnoreCase(idProvincia) == false){                
                provincias.remove(i);
                i=i-1;
            }
        }
        
        return provincias;
    }

    @Override
    public List<Ubigeo> listarDistritos(String idProvincia) {
        return ubigeoDao.listarDistritos(idProvincia);
    }
    
    public List<Ubigeo> listarDistritosActivos(String idProvincia) {
        
        List<Ubigeo> distritos = ubigeoDao.listarDistritos(idProvincia);
        
         for (int i = 0; i <= distritos.size() - 1; ++i) {
            if(distritos.get(i).getActivo() == 0){
                distritos.remove(i);
                i=i-1;
            }
        }
        
        return distritos;
    }
    
    public List<Ubigeo> listarDistritosActivos(String idProvincia, String idDistrito) {
        
        List<Ubigeo> distritos = ubigeoDao.listarDistritos(idProvincia);
        
         for (int i = 0; i <= distritos.size() - 1; ++i) {
            if(distritos.get(i).getActivo() == 0 
                    && distritos.get(i).getIdUbigeo().equalsIgnoreCase(idDistrito) == false){
                distritos.remove(i);
                i=i-1;
            }
        }
        
        return distritos;
    }

    @Override
    public Ubigeo obtenerPorId(String id) throws BusinessException {
        Ubigeo ubigeo = ubigeoDao.obtenerPorId(id);

        if (ubigeo == null) {
            throw new BusinessException(Arrays.asList("No se encontró el ubigeo"));
        }

        return ubigeo;
    }

    private void validarLocal(Ubigeo ubigeo, List<String> errores) {
        if (ubigeo.getIdUbigeo().isEmpty()) {
            errores.add("El código del ubigeo es un campo requerido");
        }

        if (ubigeo.getNombreUbigeo().isEmpty()) {
            errores.add("El nombre del ubigeo es un campo requerido");
        }
    }

    @Override
    public void insertar(Ubigeo ubigeo) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(ubigeo, errores);

        for (Ubigeo t : ubigeoDao.listar()) {
            if (ubigeo.getIdUbigeo().equalsIgnoreCase(t.getIdUbigeo())) {
                errores.add("Ya existe el código del ubigeo " + ubigeo.getIdUbigeo());
            }

            if (ubigeo.getNombreUbigeo().equalsIgnoreCase(t.getNombreUbigeo())) {
                errores.add("Ya existe el ubigeo " + ubigeo.getNombreUbigeo());
            }
        }

        ubigeo.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            ubigeoDao.insertar(ubigeo);
        }
    }

    @Override
    public void actualizar(Ubigeo ubigeo) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(ubigeo, errores);

        for (Ubigeo t : ubigeoDao.listar()) {
            if (ubigeo.getNombreUbigeo().equalsIgnoreCase(t.getNombreUbigeo())
                    && (ubigeo.getIdUbigeo().equalsIgnoreCase(t.getIdUbigeo()) == false)) {
                errores.add("Ya existe el ubigeo " + ubigeo.getNombreUbigeo());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            ubigeoDao.actualizar(ubigeo);
        }
    }

    @Override
    public void cambiarEstado(String id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Ubigeo ubigeo = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (ubigeo.getActivo() == 1) {
                ubigeo.setActivo(0);
            } else {
                ubigeo.setActivo(1);
            }

            ubigeoDao.actualizar(ubigeo);
        }
    }

    @Override
    public void eliminar(String id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            ubigeoDao.eliminar(id);
        }
    }

}
