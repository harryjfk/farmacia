package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class Perfil extends BaseDomain implements Serializable {
    
    private int idPerfil;
    private String nombrePerfil;    

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }   
    
}
