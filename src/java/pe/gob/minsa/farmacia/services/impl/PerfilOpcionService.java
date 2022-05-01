package pe.gob.minsa.farmacia.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.PerfilOpcionDao;
import pe.gob.minsa.farmacia.domain.PerfilOpcion;
import pe.gob.minsa.farmacia.domain.dto.PerfilOpcionConfiguracion;
import pe.gob.minsa.farmacia.services.ServiceManager;

public class PerfilOpcionService implements ServiceManager<PerfilOpcion> {
        
    @Autowired
    PerfilOpcionDao perfilOpcionDao;
    
    public List<PerfilOpcionConfiguracion> listarParaConfiguracion(int idSubmenu, int idPerfil){
        return perfilOpcionDao.listarParaConfiguracion(idSubmenu, idPerfil);
    }
    
    @Override
    public List<PerfilOpcion> listar() {
        return perfilOpcionDao.listar();
    }

    @Override
    public PerfilOpcion obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(PerfilOpcion perfilOpcion) {
       perfilOpcionDao.insertar(perfilOpcion);
    }

    @Override
    public void actualizar(PerfilOpcion perfilOpcion) {
        perfilOpcionDao.actualizar(perfilOpcion);
    }

    @Override
    public void cambiarEstado(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
