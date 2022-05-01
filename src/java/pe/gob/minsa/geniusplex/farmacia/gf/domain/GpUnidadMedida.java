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
@Table(name = "Far_Unidad_Medida")
@NamedQueries({
    @NamedQuery(name = "GpUnidadMedida.findAll", query = "SELECT g FROM GpUnidadMedida g")})
public class GpUnidadMedida implements Serializable {
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadMedida")
    private List<GpProducto> gpProductoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdUnidadMedida")
    private Integer idUnidadMedida;
    @Basic(optional = false)
    @Column(name = "NombreUnidadMedida")
    private String nombreUnidadMedida;
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

    public GpUnidadMedida() {
    }

    public GpUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public GpUnidadMedida(Integer idUnidadMedida, String nombreUnidadMedida, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idUnidadMedida = idUnidadMedida;
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(Integer idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadMedida != null ? idUnidadMedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpUnidadMedida)) {
            return false;
        }
        GpUnidadMedida other = (GpUnidadMedida) object;
        if ((this.idUnidadMedida == null && other.idUnidadMedida != null) || (this.idUnidadMedida != null && !this.idUnidadMedida.equals(other.idUnidadMedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.newpackage.GpUnidadMedida[ idUnidadMedida=" + idUnidadMedida + " ]";
    }

    public List<GpProducto> getGpProductoList() {
        return gpProductoList;
    }

    public void setGpProductoList(List<GpProducto> gpProductoList) {
        this.gpProductoList = gpProductoList;
    }
    
}
