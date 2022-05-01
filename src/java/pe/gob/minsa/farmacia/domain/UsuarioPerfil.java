package pe.gob.minsa.farmacia.domain;

public class UsuarioPerfil extends BaseDomain {
    
    private int idUsuarioPerfil;
    private int idUsuario;
    private int idPerfil;

    public int getIdUsuarioPerfil() {
        return idUsuarioPerfil;
    }

    public void setIdUsuarioPerfil(int idUsuarioPerfil) {
        this.idUsuarioPerfil = idUsuarioPerfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }    
}
