/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Concepto")
public class GpConcepto extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdConcepto")
    private Integer idConcepto;
    @Basic(optional = false)
    @Column(name = "NombreConcepto")
    private String nombreConcepto;
    @Basic(optional = false)
    @Column(name = "TipoMovimientoConcepto")
    private Character tipoMovimientoConcepto;
    @Column(name = "TipoPrecio")
    private Character tipoPrecio;

    public GpConcepto() {
    }

    public GpConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public GpConcepto(Integer idConcepto, String nombreConcepto, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idConcepto = idConcepto;
        this.nombreConcepto = nombreConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto = nombreConcepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConcepto != null ? idConcepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpConcepto)) {
            return false;
        }
        GpConcepto other = (GpConcepto) object;
        return !((this.idConcepto == null && other.idConcepto != null) || (this.idConcepto != null && !this.idConcepto.equals(other.idConcepto)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.farmacia.controlUI.FarConcepto[ idConcepto=" + idConcepto + " ]";
    }

    public Character getTipoMovimientoConcepto() {
        return tipoMovimientoConcepto;
    }

    public void setTipoMovimientoConcepto(Character tipoMovimientoConcepto) {
        this.tipoMovimientoConcepto = tipoMovimientoConcepto;
    }

    public Character getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(Character tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }
}
