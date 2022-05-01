package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;

public class TipoAlmacen extends BaseDomain implements Serializable {

    private int idTipoAlmacen;
    private String nombreTipoAlmacen;

    public int getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    public void setIdTipoAlmacen(int idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }

    public String getNombreTipoAlmacen() {
        return nombreTipoAlmacen;
    }

    public void setNombreTipoAlmacen(String nombreTipoAlmacen) {
        this.nombreTipoAlmacen = nombreTipoAlmacen;
    }
    
}
