package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class TipoDocumento extends BaseDomain implements Serializable {

    private int idTipoDocumento;
    private String nombreTipoDocumento;

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

}
