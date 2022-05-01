package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.minsa.farmacia.dao.impl.UsuarioDao;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.domain.UsuarioPerfil;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.UtilMail;

@Service
public class UsuarioService implements ServiceManager<Usuario> {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    UsuarioPerfilService usuarioPerfilService;

    @Override
    public List<Usuario> listar() {
        return usuarioDao.listar();
    }

    @Override
    public Usuario obtenerPorId(int id) throws BusinessException {

        Usuario usuario = usuarioDao.obtenerPorId(id);

        if (usuario == null) {
            throw new BusinessException(Arrays.asList("No se encontró el usuario"));
        }

        return usuario;
    }
    
    private void validarLocal(Usuario usuario, List<String> errores){
        
        if (usuario.getNombreUsuario().isEmpty()) {
            errores.add("El nombre de usuario es un campo requerido");
        }

        if (usuario.getClave().isEmpty()) {
            errores.add("La clave es un campo requerido");
        }

        if (usuario.getPersonal().getIdPersonal().isEmpty()) {
            errores.add("El personal es un campo requerido");
        }

        if (usuario.getPerfiles().isEmpty()) {
            errores.add("Debe asignar como mínimo un perfil");
        }
        
        if(usuario.getCorreo().isEmpty()){
            errores.add("El correo es un campo requerido");            
        }else{
            if (UtilMail.isMailValid(usuario.getCorreo()) == false) {
                errores.add("Debe ingresar un correo válido");
            }
        }
    }

    @Override
    public void insertar(Usuario usuario) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(usuario, errores);
        
        if (usuarioDao.existeUsuario(usuario.getNombreUsuario())) {
            errores.add("Ya existe este nombre de usuario");
        }

        if (usuarioDao.existeCorreo(usuario.getCorreo())) {
            errores.add("Ya existe este correo");
        }
                
        usuario.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            usuarioDao.insertar(usuario);

            for (int i = 0; i <= usuario.getPerfiles().size() - 1; ++i) {
                UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
                usuarioPerfil.setIdPerfil(usuario.getPerfiles().get(i).getIdPerfil());
                usuarioPerfil.setIdUsuario(usuario.getIdUsuario());
                usuarioPerfilService.insertar(usuarioPerfil);
            }
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws BusinessException {

        List<String> errores = new ArrayList<String>();
        
        validarLocal(usuario, errores);

         if (usuarioDao.existeUsuario(usuario.getNombreUsuario(), usuario.getIdUsuario())) {
            errores.add("Ya existe este nombre de usuario");
        }

        if (usuarioDao.existeCorreo(usuario.getCorreo(), usuario.getIdUsuario())) {
            errores.add("Ya existe este correo");
        }

        if (usuario.getIdUsuario() == 1 && usuario.getActivo() == 0) {
            errores.add("No puede desactivar el usuario administrador");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {            
            usuarioDao.actualizar(usuario);

            List<UsuarioPerfil> usuarioPerfiles = usuarioPerfilService.listarPorUsuario(usuario.getIdUsuario());

            for (UsuarioPerfil usuarioPerfil : usuarioPerfiles) {
                usuarioPerfil.setActivo(0);
                usuarioPerfilService.actualizar(usuarioPerfil);
            }

            for (int i = 0; i <= usuario.getPerfiles().size() - 1; ++i) {

                boolean encontrado = false;
                for (UsuarioPerfil usuarioPerfil : usuarioPerfiles) {
                    if (usuario.getPerfiles().get(i).getIdPerfil() == usuarioPerfil.getIdPerfil()) {
                        usuarioPerfil.setActivo(1);
                        usuarioPerfilService.actualizar(usuarioPerfil);
                        encontrado = true;
                    }
                }

                if (encontrado == false) {
                    UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
                    usuarioPerfil.setIdPerfil(usuario.getPerfiles().get(i).getIdPerfil());
                    usuarioPerfil.setIdUsuario(usuario.getIdUsuario());
                    usuarioPerfilService.insertar(usuarioPerfil);
                }
            }
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        
        List<String> errores = new ArrayList<String>();
        
        Usuario usuario = this.obtenerPorId(id);        
        
        if (usuario.getIdUsuario() == 1) {
            errores.add("No puede desactivar el usuario administrador");
        }

        if (errores.isEmpty()) {
            throw new BusinessException(errores);
        } else {

            if (usuario.getActivo() == 1) {
                usuario.setActivo(0);
            } else {
                usuario.setActivo(1);
            }
            
            usuarioDao.actualizar(usuario);            
        }
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Usuario iniciarSesion(String usuario, String clave) {
        return usuarioDao.iniciarSesion(usuario, clave);
    }

}
