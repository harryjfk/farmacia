package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ModuloDao;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.services.NavegacionServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ModuloService implements NavegacionServiceManager<Modulo> {

    @Autowired
    ModuloDao moduloDao;

    @Override
    public List<Modulo> listar() {
        return moduloDao.listar();
    }
    
    @Override
    public List<Modulo> listarParaSession(int idUsuario) {
        return moduloDao.listarParaSession(idUsuario);
    }

    @Override
    public List<Modulo> listarActivos() {
        List<Modulo> modulos = new ArrayList<Modulo>();

        for (Modulo m : moduloDao.listar()) {
            if (m.getActivo() == 1) {
                modulos.add(m);
            }
        }

        return modulos;
    }

    @Override
    public Modulo obtenerPorId(int id) throws BusinessException {        
        Modulo modulo = moduloDao.obtenerPorId(id);
        
        if(modulo == null){
            throw new BusinessException(Arrays.asList("No se encontró el módulo"));
        }
        
        return modulo;
    }
    
    public void cambiarOrden(int idModulo, boolean subida) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
        
        if(idModulo <= 0){
            errores.add("El código del módulo debe ser mayor a cero");
        }
        
        Modulo modulo = this.obtenerPorId(idModulo);
                
        if(subida){
            
            int ordenMaximo = 0;

            for (Modulo m : moduloDao.listar()) {
                if (ordenMaximo < m.getOrden()) {
                    ordenMaximo = m.getOrden();
                }
            }
            
            if(modulo.getOrden() == ordenMaximo){
                errores.add("No puede aumentar más el orden");
            }
        }else{
            if(modulo.getOrden() == 1){
                errores.add("No puede disminuir más el orden");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            moduloDao.cambiarOrden(idModulo, subida);
        }
    }

    @Override
    public void actualizar(Modulo modulo) throws BusinessException {

        List<String> errores = new ArrayList<String>();
        
        if (modulo.getNombreModulo().isEmpty()) {
            errores.add("El nombre del módulo es un campo requerido");
        }

        for (Modulo m : moduloDao.listar()) {
            if (modulo.getNombreModulo().equalsIgnoreCase(m.getNombreModulo())
                    && modulo.getIdModulo() != m.getIdModulo()) {
                errores.add("Ya existe el nombre del módulo " + modulo.getNombreModulo());
                break;
            }
        }
        
        Modulo moduloTemp = this.obtenerPorId(modulo.getIdModulo());        
        modulo.setOrden(moduloTemp.getOrden());

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            moduloDao.actualizar(modulo);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {        

        List<String> errores = new ArrayList<String>();
                
        Modulo modulo = this.obtenerPorId(id);

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {

            if (modulo.getActivo() == 1) {
                modulo.setActivo(0);
            } else {
                modulo.setActivo(1);
            }

            moduloDao.actualizar(modulo);
        }
    }

}
