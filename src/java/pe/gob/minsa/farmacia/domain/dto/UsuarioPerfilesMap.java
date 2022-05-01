package pe.gob.minsa.farmacia.domain.dto;

import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.Usuario;

public class UsuarioPerfilesMap extends Usuario {

    private Perfil perfil;

    public UsuarioPerfilesMap() {
        perfil = new Perfil();
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
