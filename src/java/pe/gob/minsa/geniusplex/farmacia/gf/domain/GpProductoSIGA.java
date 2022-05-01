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
@Table(name = "Far_Producto_SIGA")
@NamedQueries({
    @NamedQuery(name = "GpProductoSIGA.findAll", query = "SELECT g FROM GpProductoSIGA g")})
public class GpProductoSIGA implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdProductoSiga")
    private Integer idProductoSiga;
    @Basic(optional = false)
    @Column(name = "NombreProductoSiga")
    private String nombreProductoSiga;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProductoSiga")
    private List<GpProducto> gpProductoList;

    public GpProductoSIGA() {
    }

    public GpProductoSIGA(Integer idProductoSiga) {
        this.idProductoSiga = idProductoSiga;
    }

    public GpProductoSIGA(Integer idProductoSiga, String nombreProductoSiga, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idProductoSiga = idProductoSiga;
        this.nombreProductoSiga = nombreProductoSiga;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdProductoSiga() {
        return idProductoSiga;
    }

    public void setIdProductoSiga(Integer idProductoSiga) {
        this.idProductoSiga = idProductoSiga;
    }

    public String getNombreProductoSiga() {
        return nombreProductoSiga;
    }

    public void setNombreProductoSiga(String nombreProductoSiga) {
        this.nombreProductoSiga = nombreProductoSiga;
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
        hash += (idProductoSiga != null ? idProductoSiga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpProductoSIGA)) {
            return false;
        }
        GpProductoSIGA other = (GpProductoSIGA) object;
        if ((this.idProductoSiga == null && other.idProductoSiga != null) || (this.idProductoSiga != null && !this.idProductoSiga.equals(other.idProductoSiga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoSIGA[ idProductoSiga=" + idProductoSiga + " ]";
    }
    
}
