package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoDocumentoMov;

public class TipoDocumentoMov extends BaseDomain implements Serializable, ITipoDocumentoMov{

    private int idTipoDocumentoMov;
    private String nombreTipoDocumentoMov;

    @Override
    public int getIdTipoDocumentoMov() {
        return idTipoDocumentoMov;
    }

    @Override
    public void setIdTipoDocumentoMov(int idTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
    }

    @Override
    public String getNombreTipoDocumentoMov() {
        return nombreTipoDocumentoMov;
    }

    @Override
    public void setNombreTipoDocumentoMov(String nombreTipoDocumentoMov) {
        this.nombreTipoDocumentoMov = nombreTipoDocumentoMov;
    }
}
