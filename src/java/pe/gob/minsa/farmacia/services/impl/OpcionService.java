package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.OpcionDao;
import pe.gob.minsa.farmacia.domain.Opcion;
import pe.gob.minsa.farmacia.services.NavegacionServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class OpcionService implements NavegacionServiceManager<Opcion> {

    @Autowired
    OpcionDao opcionDao;
    
    @Override
    public List<Opcion> listar() {
        return opcionDao.listar();
    }
    
    @Override
    public List<Opcion> listarParaSession(int idUsuario) {
        return opcionDao.listarParaSession(idUsuario);
    }
    
    public List<Opcion> listarParaSession(int idUsuario, int idSubmenu) {
        return opcionDao.listarParaSession(idUsuario, idSubmenu);
    }
    
    public List<Opcion> listarPorSubmenu(int idSubmenu){
        return opcionDao.listarPorSubmenu(idSubmenu);
    }
    
    @Override
    public List<Opcion> listarActivos() {
        List<Opcion> opciones = new ArrayList<Opcion>();

        for (Opcion o : opcionDao.listar()) {
            if(o.getActivo() == 1){
                opciones.add(o);
            }
        }

        return opciones;
    }
    
    @Override
    public Opcion obtenerPorId(int id) throws BusinessException {
        
        Opcion opcion = opcionDao.obtenerPorId(id);
        
        if(opcion == null){
            throw new BusinessException(Arrays.asList("No se encontró la opción"));
        }
        
        return opcion;
    }

    @Override
    public void actualizar(Opcion opcion) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
        
        if (opcion.getNombreOpcion().isEmpty()) {
            errores.add("El nombre de la opción es un campo requerido");
        }
        
        if (opcion.getAppOpcion().isEmpty()) {
            errores.add("El enlace es un campo requerido");
        }

        for (Opcion m : opcionDao.listar()) {
            if (opcion.getNombreOpcion().equalsIgnoreCase(m.getNombreOpcion()) 
                    && opcion.getIdSubmenu() == m.getIdSubmenu()
                    && opcion.getIdOpcion() != m.getIdOpcion()) {
                errores.add("Ya existe la opción " + opcion.getNombreOpcion()+ " en el submenu seleccionado");
                break;
            }
        }
        
        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            opcionDao.actualizar(opcion);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
         
        List<String> errores = new ArrayList<String>();

        Opcion opcion = obtenerPorId(id);
       
        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            
            if(opcion.getActivo() == 1){
                opcion.setActivo(0);
            }else{
                opcion.setActivo(1);
            }
            
            opcionDao.actualizar(opcion);
        }
    }
    
}
