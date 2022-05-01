package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class TipoAccion extends BaseDomain implements Serializable {

    private int idTipoAccion;
    private String nombreTipoAccion;

    public int getIdTipoAccion() {
        return idTipoAccion;
    }

    public void setIdTipoAccion(int idTipoAccion) {
        this.idTipoAccion = idTipoAccion;
    }

    public String getNombreTipoAccion() {
        return nombreTipoAccion;
    }

    public void setNombreTipoAccion(String nombreTipoAccion) {
        this.nombreTipoAccion = nombreTipoAccion;
    }    
}
