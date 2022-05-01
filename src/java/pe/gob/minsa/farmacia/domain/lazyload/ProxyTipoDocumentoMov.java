package pe.gob.minsa.farmacia.domain.lazyload;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoCompraDao;
import pe.gob.minsa.farmacia.dao.impl.TipoDocumentoMovDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.TipoDocumentoMov;

public class ProxyTipoDocumentoMov extends BaseDomain implements ITipoDocumentoMov {

    private boolean esCargado;
    private final Integer idTipoDocumentoMov;
    private final String nombreTipoDocumentoMov;
    private ITipoDocumentoMov tipoDocumentoMov;

    public ProxyTipoDocumentoMov(Integer idTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
        this.nombreTipoDocumentoMov = null;
    }

    public ProxyTipoDocumentoMov(Integer idTipoDocumentoMov, String nombreTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
        this.nombreTipoDocumentoMov = nombreTipoDocumentoMov;
    }

    private void Load() {

        if (idTipoDocumentoMov == null) {
            tipoDocumentoMov = new TipoDocumentoMov();
        } else {
            tipoDocumentoMov = StaticContextAccessor.getBean(TipoDocumentoMovDao.class).obtenerPorId(idTipoDocumentoMov);
        }

        esCargado = true;
    }

    @Override
    public int getIdTipoDocumentoMov() {
        return this.idTipoDocumentoMov;
    }

    @Override
    public void setIdTipoDocumentoMov(int idTipoDocumentoMov) {
        if (esCargado == false) {
            Load();
        }
        tipoDocumentoMov.setIdTipoDocumentoMov(idTipoDocumentoMov);
    }

    @Override
    public String getNombreTipoDocumentoMov() {
        if (nombreTipoDocumentoMov == null) {
            if (esCargado == false) {
                Load();
            }
            return tipoDocumentoMov.getNombreTipoDocumentoMov();
        }
        return this.nombreTipoDocumentoMov;
    }

    @Override
    public void setNombreTipoDocumentoMov(String nombreTipoDocumentoMov) {
        if (esCargado == false) {
            Load();
        }
        tipoDocumentoMov.setNombreTipoDocumentoMov(nombreTipoDocumentoMov);
    }

    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return tipoDocumentoMov.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }

        tipoDocumentoMov.setActivo(activo);
    }
}
