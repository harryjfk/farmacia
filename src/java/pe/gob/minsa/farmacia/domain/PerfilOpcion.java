package pe.gob.minsa.farmacia.domain;

public class PerfilOpcion extends BaseDomain {

    private int idPerfilOpcion;
    private int idPerfil;
    private int idOpcion;

    public int getIdPerfilOpcion() {
        return idPerfilOpcion;
    }

    public void setIdPerfilOpcion(int idPerfilOpcion) {
        this.idPerfilOpcion = idPerfilOpcion;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }    
    
}
