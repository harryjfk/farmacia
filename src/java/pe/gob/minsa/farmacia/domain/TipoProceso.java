package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.ITipoProceso;

public class TipoProceso extends BaseDomain implements Serializable, ITipoProceso {

    private int idTipoProceso;
    private String nombreTipoProceso;

    @Override
    public int getIdTipoProceso() {
        return idTipoProceso;
    }

    @Override
    public void setIdTipoProceso(int idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    @Override
    public String getNombreTipoProceso() {
        return nombreTipoProceso;
    }

    @Override
    public void setNombreTipoProceso(String nombreTipoProceso) {
        this.nombreTipoProceso = nombreTipoProceso;
    }
}
