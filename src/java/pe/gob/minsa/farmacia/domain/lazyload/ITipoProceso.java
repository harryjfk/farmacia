package pe.gob.minsa.farmacia.domain.lazyload;

public interface ITipoProceso extends IBaseDomain {
   
    public int getIdTipoProceso();

    public void setIdTipoProceso(int idTipoProceso);

    public String getNombreTipoProceso();

    public void setNombreTipoProceso(String nombreTipoProceso);
}
