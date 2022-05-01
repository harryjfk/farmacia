package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.IDocumentoOrigen;

public class DocumentoOrigen extends BaseDomain implements Serializable, IDocumentoOrigen {

    private int idDocumentoOrigen;
    private String nombreDocumentoOrigen;

    @Override
    public int getIdDocumentoOrigen() {
        return idDocumentoOrigen;
    }

    @Override
    public void setIdDocumentoOrigen(int idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }

    @Override
    public String getNombreDocumentoOrigen() {
        return nombreDocumentoOrigen;
    }

    @Override
    public void setNombreDocumentoOrigen(String nombreDocumentoOrigen) {
        this.nombreDocumentoOrigen = nombreDocumentoOrigen;
    }
}
