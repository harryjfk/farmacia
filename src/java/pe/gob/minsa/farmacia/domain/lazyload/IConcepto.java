package pe.gob.minsa.farmacia.domain.lazyload;

import pe.gob.minsa.farmacia.domain.TipoMovimientoConcepto;

public interface IConcepto extends IBaseDomain {

    public int getIdConcepto();

    public void setIdConcepto(int idConcepto);

    public String getNombreConcepto();

    public void setNombreConcepto(String nombreConcepto);

    public TipoMovimientoConcepto getTipoMovimientoConcepto();

    public void setTipoMovimientoConcepto(TipoMovimientoConcepto tipoMovimientoConcepto);

}
