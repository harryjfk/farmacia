/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Producto_SISMED")
@NamedQueries({
    @NamedQuery(name = "GpProductoSISMED.findAll", query = "SELECT g FROM GpProductoSISMED g")})
public class GpProductoSISMED implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdProductoSismed")
    private Integer idProductoSismed;
    @Basic(optional = false)
    @Column(name = "NombreProductoSismed")
    private String nombreProductoSismed;
    @Basic(optional = false)
    @Column(name = "Activo")
    private int activo;
    @Basic(optional = false)
    @Column(name = "UsuarioCreacion")
    private int usuarioCreacion;
    @Basic(optional = false)
    @Column(name = "FechaCreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "UsuarioModificacion")
    private Integer usuarioModificacion;
    @Column(name = "FechaModificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProductoSismed")
    private List<GpProducto> gpProductoList;

    public GpProductoSISMED() {
    }

    public GpProductoSISMED(Integer idProductoSismed) {
        this.idProductoSismed = idProductoSismed;
    }

    public GpProductoSISMED(Integer idProductoSismed, String nombreProductoSismed, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idProductoSismed = idProductoSismed;
        this.nombreProductoSismed = nombreProductoSismed;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdProductoSismed() {
        return idProductoSismed;
    }

    public void setIdProductoSismed(Integer idProductoSismed) {
        this.idProductoSismed = idProductoSismed;
    }

    public String getNombreProductoSismed() {
        return nombreProductoSismed;
    }

    public void setNombreProductoSismed(String nombreProductoSismed) {
        this.nombreProductoSismed = nombreProductoSismed;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<GpProducto> getGpProductoList() {
        return gpProductoList;
    }

    public void setGpProductoList(List<GpProducto> gpProductoList) {
        this.gpProductoList = gpProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProductoSismed != null ? idProductoSismed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpProductoSISMED)) {
            return false;
        }
        GpProductoSISMED other = (GpProductoSISMED) object;
        if ((this.idProductoSismed == null && other.idProductoSismed != null) || (this.idProductoSismed != null && !this.idProductoSismed.equals(other.idProductoSismed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoSISMED[ idProductoSismed=" + idProductoSismed + " ]";
    }
    
}
