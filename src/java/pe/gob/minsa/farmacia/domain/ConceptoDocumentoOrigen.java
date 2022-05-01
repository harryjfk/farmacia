package pe.gob.minsa.farmacia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;

public class ConceptoDocumentoOrigen {
    
    private int idConceptoDocumentoOrigen;
    private int idConcepto;    
    private int idDocumentoOrigen;
    
    private DocumentoOrigen documentoOrigen;
    
    @JsonIgnore
    private int usuarioCreacion;
    @JsonIgnore
    private Timestamp fechaCreacion;

    public int getIdConceptoDocumentoOrigen() {
        return idConceptoDocumentoOrigen;
    }

    public void setIdConceptoDocumentoOrigen(int idConceptoDocumentoOrigen) {
        this.idConceptoDocumentoOrigen = idConceptoDocumentoOrigen;
    }
    
    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public int getIdDocumentoOrigen() {
        return idDocumentoOrigen;
    }

    public void setIdDocumentoOrigen(int idDocumentoOrigen) {
        this.idDocumentoOrigen = idDocumentoOrigen;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public DocumentoOrigen getDocumentoOrigen() {
        return documentoOrigen;
    }

    public void setDocumentoOrigen(DocumentoOrigen documentoOrigen) {
        this.documentoOrigen = documentoOrigen;
    }
}
