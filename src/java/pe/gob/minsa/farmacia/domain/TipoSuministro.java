package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class TipoSuministro  extends BaseDomain implements Serializable{
    private int idTipoSuministro;
    private String descripcion;

    public int getIdTipoSuministro() {
        return idTipoSuministro;
    }

    public void setIdTipoSuministro(int idTipoSuministro) {
        this.idTipoSuministro = idTipoSuministro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
