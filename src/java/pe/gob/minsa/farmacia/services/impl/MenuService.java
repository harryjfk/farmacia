package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.MenuDao;
import pe.gob.minsa.farmacia.domain.Menu;
import pe.gob.minsa.farmacia.services.NavegacionServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class MenuService implements NavegacionServiceManager<Menu> {

    @Autowired
    MenuDao menuDao;

    @Override
    public List<Menu> listar() {
        return menuDao.listar();
    }

    @Override
    public List<Menu> listarParaSession(int idUsuario) {
        return menuDao.listarParaSession(idUsuario);
    }

    @Override
    public List<Menu> listarActivos() {
        List<Menu> menus = new ArrayList<Menu>();

        for (Menu m : menuDao.listar()) {
            if (m.getActivo() == 1) {
                menus.add(m);
            }
        }

        return menus;
    }

    public List<Menu> listarPorIdSubmodulo(int idSubmodulo) {

        List<Menu> menus = new ArrayList<Menu>();

        for (Menu m : menuDao.listar()) {
            if (idSubmodulo == m.getIdSubmodulo()) {
                menus.add(m);
            }
        }

        return menus;
    }

    @Override
    public Menu obtenerPorId(int id) throws BusinessException {
        Menu menu = menuDao.obtenerPorId(id);

        if (menu == null) {
            throw new BusinessException(Arrays.asList("No se encontró el menú"));
        }

        return menu;
    }
    
    public void cambiarOrden(int idMenu, boolean subida) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
                
        if(idMenu <= 0){
            errores.add("El código del menú debe ser mayor a cero");
        }
        
        Menu menu = this.obtenerPorId(idMenu);
                
        if(subida){            
            int ordenMaximo = 0;

            for (Menu m : this.listarPorIdSubmodulo(menu.getIdSubmodulo())) {
                if (ordenMaximo < m.getOrden()) {
                    ordenMaximo = m.getOrden();
                }
            }
            
            if(menu.getOrden() == ordenMaximo){
                errores.add("No puede aumentar más el orden");
            }
        }else{
            if(menu.getOrden() == 1){
                errores.add("No puede disminuir más el orden");
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            menuDao.cambiarOrden(idMenu, subida);
        }
    }

    @Override
    public void actualizar(Menu menu) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (menu.getNombreMenu().isEmpty()) {
            errores.add("El nombre del menú es un campo requerido");
        }

        for (Menu m : menuDao.listar()) {
            if (menu.getNombreMenu().equalsIgnoreCase(m.getNombreMenu())
                    && menu.getIdSubmodulo() == m.getIdSubmodulo()
                    && menu.getIdMenu() != m.getIdMenu()) {
                errores.add("Ya existe el menú " + menu.getNombreMenu() + " en el submódulo seleccionado");
                break;
            }
        }

        Menu menuTemp = this.obtenerPorId(menu.getIdMenu());
        menu.setOrden(menuTemp.getOrden());

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            menuDao.actualizar(menu);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        Menu menu = this.obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            if (menu.getActivo() == 1) {
                menu.setActivo(0);
            } else {
                menu.setActivo(1);
            }
            menuDao.actualizar(menu);
        }
    }
}
