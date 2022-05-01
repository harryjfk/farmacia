package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.UsuarioPerfilDao;
import pe.gob.minsa.farmacia.domain.UsuarioPerfil;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class UsuarioPerfilService implements ServiceManager<UsuarioPerfil> {

    @Autowired
    UsuarioPerfilDao usuarioPerfilDao;

    @Override
    public List<UsuarioPerfil> listar() {
        return usuarioPerfilDao.listar();
    }

    public List<UsuarioPerfil> listarPorUsuario(int idUsuario) {
        List<UsuarioPerfil> usuarioPerfiles = new ArrayList<UsuarioPerfil>();

        for (UsuarioPerfil usuarioPerfil : listar()) {
            if (usuarioPerfil.getIdUsuario() == idUsuario) {
                usuarioPerfiles.add(usuarioPerfil);
            }
        }

        return usuarioPerfiles;
    }

    @Override
    public UsuarioPerfil obtenerPorId(int id) throws BusinessException {

        UsuarioPerfil usuarioPerfil = usuarioPerfilDao.obtenerPorId(id);

        if (usuarioPerfil == null) {
            throw new BusinessException(Arrays.asList("No se encontró el perfil del usuario"));
        }

        return usuarioPerfil;
    }

    @Override
    public void insertar(UsuarioPerfil usuarioPerfil) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            usuarioPerfil.setActivo(1);
            usuarioPerfilDao.insertar(usuarioPerfil);
        }
    }

    @Override
    public void actualizar(UsuarioPerfil usuarioPerfil) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            usuarioPerfilDao.actualizar(usuarioPerfil);
        }
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
