package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.IConcepto;

public class Concepto extends BaseDomain implements Serializable, IConcepto {

    private int idConcepto;
    private String nombreConcepto;
    private TipoMovimientoConcepto tipoMovimientoConcepto;
    private TipoPrecioConcepto tipoPrecio;

    @Override
    public int getIdConcepto() {
        return idConcepto;
    }

    @Override
    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    @Override
    public String getNombreConcepto() {
        return nombreConcepto;
    }

    @Override
    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    @Override
    public TipoMovimientoConcepto getTipoMovimientoConcepto() {
        return tipoMovimientoConcepto;
    }

    @Override
    public void setTipoMovimientoConcepto(TipoMovimientoConcepto tipoMovimientoConcepto) {
        this.tipoMovimientoConcepto = tipoMovimientoConcepto;
    }

    public TipoPrecioConcepto getTipoPrecio() {        
        return tipoPrecio;
    }

    public void setTipoPrecio(TipoPrecioConcepto tipoPrecio) {        
        this.tipoPrecio = tipoPrecio;
    }   
}