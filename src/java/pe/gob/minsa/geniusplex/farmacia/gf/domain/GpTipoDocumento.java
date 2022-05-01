/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Tipo_Documento")
@NamedQueries({
    @NamedQuery(name = "GpTipoDocumento.findAll", query = "SELECT g FROM GpTipoDocumento g")})
public class GpTipoDocumento extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdTipoDocumento")
    private Integer idTipoDocumento;
    @Basic(optional = false)
    @Column(name = "NombreTipoDocumento")
    private String nombreTipoDocumento;
    
    public GpTipoDocumento() {
    }

    public GpTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public GpTipoDocumento(Integer idTipoDocumento, String nombreTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoDocumento != null ? idTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpTipoDocumento)) {
            return false;
        }
        GpTipoDocumento other = (GpTipoDocumento) object;
        if ((this.idTipoDocumento == null && other.idTipoDocumento != null) || (this.idTipoDocumento != null && !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoDocumento[ idTipoDocumento=" + idTipoDocumento + " ]";
    }

    @Override
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }

}
