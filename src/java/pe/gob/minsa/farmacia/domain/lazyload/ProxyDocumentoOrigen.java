package pe.gob.minsa.farmacia.domain.lazyload;

import pe.gob.minsa.farmacia.dao.impl.DocumentoOrigenDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;

public class ProxyDocumentoOrigen extends BaseDomain implements IDocumentoOrigen {

    private boolean esCargado;
    private final Integer idDocumentoOrigen;
    private final String nombreDocumentoOrigen;
    private IDocumentoOrigen documentoOrigen;

    public ProxyDocumentoOrigen(Integer idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
        this.nombreDocumentoOrigen = null;
    }

    public ProxyDocumentoOrigen(Integer idDocumentoOrigen, String nombreDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
        this.nombreDocumentoOrigen = nombreDocumentoOrigen;
    }

    private void Load() {

        if (idDocumentoOrigen == null) {
            documentoOrigen = new DocumentoOrigen();
        } else {
            documentoOrigen = StaticContextAccessor.getBean(DocumentoOrigenDao.class).obtenerPorId(idDocumentoOrigen);
        }

        esCargado = true;
    }

    @Override
    public int getIdDocumentoOrigen() {
        return this.idDocumentoOrigen;
    }

    @Override
    public void setIdDocumentoOrigen(int idDocumentoOrigen) {
        if (esCargado == false) {
            Load();
        }
        documentoOrigen.setIdDocumentoOrigen(idDocumentoOrigen);
    }

    @Override
    public String getNombreDocumentoOrigen() {
        if (nombreDocumentoOrigen == null) {
            if (esCargado == false) {
                Load();
            }
            return documentoOrigen.getNombreDocumentoOrigen();
        }
        return this.nombreDocumentoOrigen;
    }

    @Override
    public void setNombreDocumentoOrigen(String nombreDocumentoOrigen) {
        if (esCargado == false) {
            Load();
        }
        documentoOrigen.setNombreDocumentoOrigen(nombreDocumentoOrigen);
    }

    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return documentoOrigen.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }
        documentoOrigen.setActivo(activo);
    }
}
