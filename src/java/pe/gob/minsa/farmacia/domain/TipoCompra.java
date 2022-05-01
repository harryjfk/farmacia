package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoCompra;

public class TipoCompra extends BaseDomain implements Serializable, ITipoCompra {

    private int idTipoCompra;
    private String nombreTipoCompra;

    @Override
    public int getIdTipoCompra() {
        return idTipoCompra;
    }

    @Override
    public void setIdTipoCompra(int idTipoCompra) {
        this.idTipoCompra = idTipoCompra;
    }

    @Override
    public String getNombreTipoCompra() {
        return nombreTipoCompra;
    }

    @Override
    public void setNombreTipoCompra(String nombreTipoCompra) {
        this.nombreTipoCompra = nombreTipoCompra;
    }
    
}
