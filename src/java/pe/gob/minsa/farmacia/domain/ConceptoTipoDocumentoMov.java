package pe.gob.minsa.farmacia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;

public class ConceptoTipoDocumentoMov {
    
    private int idConceptoTipoDocumentoMov;
    private int idConcepto;
    private int idTipoDocumentoMov;
    
    private TipoDocumentoMov tipoDocumentoMov;    
    
    @JsonIgnore
    private int usuarioCreacion;
    @JsonIgnore
    private Timestamp fechaCreacion;

    public int getIdConceptoTipoDocumentoMov() {
        return idConceptoTipoDocumentoMov;
    }

    public void setIdConceptoTipoDocumentoMov(int idConceptoTipoDocumentoMov) {
        this.idConceptoTipoDocumentoMov = idConceptoTipoDocumentoMov;
    }
    
    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public int getIdTipoDocumentoMov() {
        return idTipoDocumentoMov;
    }

    public void setIdTipoDocumentoMov(int idTipoDocumentoMov) {
        this.idTipoDocumentoMov = idTipoDocumentoMov;
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

    public TipoDocumentoMov getTipoDocumentoMov() {
        return tipoDocumentoMov;
    }

    public void setTipoDocumentoMov(TipoDocumentoMov tipoDocumentoMov) {
        this.tipoDocumentoMov = tipoDocumentoMov;
    }
}