package pe.gob.minsa.farmacia.domain.lazyload;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoProcesoDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.TipoProceso;

public class ProxyTipoProceso extends BaseDomain implements ITipoProceso {

    private boolean esCargado;
    private final Integer idTipoProceso;
    private ITipoProceso tipoProceso;

    public ProxyTipoProceso(Integer idTipoProceso) {
        this.idTipoProceso = idTipoProceso;
    }

    private void Load() {
        if (idTipoProceso == null) {
            tipoProceso = new TipoProceso();
        } else {
            tipoProceso = StaticContextAccessor.getBean(TipoProcesoDao.class).obtenerPorId(idTipoProceso);
        }

        esCargado = true;
    }

    @Override
    public int getIdTipoProceso() {
        return this.idTipoProceso;
    }

    @Override
    public void setIdTipoProceso(int idTipoProceso) {
        if (esCargado == false) {
            Load();
        }
        tipoProceso.setIdTipoProceso(idTipoProceso);
    }

    @Override
    public String getNombreTipoProceso() {
        if (esCargado == false) {
            Load();
        }
        return tipoProceso.getNombreTipoProceso();
    }

    @Override
    public void setNombreTipoProceso(String nombreTipoProceso) {
        if (esCargado == false) {
            Load();
        }
        tipoProceso.setNombreTipoProceso(nombreTipoProceso);
    }

    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return tipoProceso.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }

        tipoProceso.setActivo(activo);
    }
}
