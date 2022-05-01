package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.SubmenuDao;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.services.NavegacionServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class SubmenuService implements NavegacionServiceManager<Submenu> {

    @Autowired
    SubmenuDao submenuDao;

    @Override
    public List<Submenu> listar() {
        return submenuDao.listar();
    }
    
    @Override
    public List<Submenu> listarParaSession(int idUsuario) {
        return submenuDao.listarParaSession(idUsuario);
    }
    
    public Submenu obtenerPorEnlace(String enlace) throws BusinessException{
        Submenu submenu = submenuDao.obtenerPorEnlace(enlace);
        
        if(submenu == null){
            throw new BusinessException(Arrays.asList("No se encontró el submenú"));
        }
        
        return submenu;
    }       

    @Override
    public List<Submenu> listarActivos() {
        List<Submenu> submenus = new ArrayList<Submenu>();

        for (Submenu s : submenuDao.listar()) {
            if (s.getActivo() == 1) {
                submenus.add(s);
            }
        }

        return submenus;
    }

    public List<Submenu> listarPorIdMenu(int idMenu) {

        List<Submenu> submenus = new ArrayList<Submenu>();

        for (Submenu s : submenuDao.listar()) {
            if (idMenu == s.getIdMenu()) {
                submenus.add(s);
            }
        }

        return submenus;
    }

    @Override
    public Submenu obtenerPorId(int id) throws BusinessException {
        
        Submenu submenu = submenuDao.obtenerPorId(id);
        
        if(submenu == null){
            throw new BusinessException(Arrays.asList("No se encontró el submenú"));
        }
        
        return submenu;
    }
    
    public void cambiarOrden(int idSubmenu, boolean subida) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
                
        if(idSubmenu <= 0){
            errores.add("El código del submenú debe ser mayor a cero");
        }
        
        Submenu submenu = this.obtenerPorId(idSubmenu);
                
        if(subida){            
            int ordenMaximo = 0;

            for (Submenu s : this.listarPorIdMenu(submenu.getIdMenu())) {
                if (ordenMaximo < s.getOrden()) {
                    ordenMaximo = s.getOrden();
                }
            }
            
            if(submenu.getOrden() == ordenMaximo){
                errores.add("No puede aumentar más el orden");
            }
        }else{
            if(submenu.getOrden() == 1){
                errores.add("No puede disminuir más el orden");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            submenuDao.cambiarOrden(idSubmenu, subida);
        }
    }

    @Override
    public void actualizar(Submenu submenu) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();

        if (submenu.getNombreSubmenu().isEmpty()) {
            errores.add("El nombre del submenu es un campo requerido");
        }

        for (Submenu s : submenuDao.listar()) {
            if (submenu.getNombreSubmenu().equalsIgnoreCase(s.getNombreSubmenu())
                    && submenu.getIdMenu() == s.getIdMenu()
                    && submenu.getIdSubmenu() != s.getIdSubmenu()) {
                errores.add("Ya existe el nombre del submenu " + submenu.getNombreSubmenu());
                break;
            }
        }
        
        Submenu submenuTemp = this.obtenerPorId(submenu.getIdSubmenu());
        submenu.setOrden(submenuTemp.getOrden());
        submenu.setEnlace(submenuTemp.getEnlace());

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            submenuDao.actualizar(submenu);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();

        Submenu submenu = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (submenu.getActivo() == 1) {
                submenu.setActivo(0);
            } else {
                submenu.setActivo(1);
            }

            submenuDao.actualizar(submenu);
        }
    }

}