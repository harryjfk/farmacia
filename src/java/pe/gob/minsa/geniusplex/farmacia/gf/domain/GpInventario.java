/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RemoteEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Inventario")
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM GpInventario i")})
public class GpInventario extends RemoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdInventario")
    private Integer idInventario;
    @Basic(optional = false)
    @Column(name = "NumeroInventario")
    private int numeroInventario;
    @Basic(optional = false)
    @Column(name = "FechaProceso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "FechaCierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInventario")
    private List<GpInventarioProducto> inventarioProductoList;
    @JoinColumn(name = "IdPeriodo", referencedColumnName = "IdPeriodo")
    @ManyToOne
    private GpPeriodo periodo;
    @JoinColumn(name = "IdAlmacen", referencedColumnName = "IdAlmacen")
    @ManyToOne
    private GpAlmacen almacen;

    public GpInventario() {
    }

    public GpInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public GpInventario(Integer idInventario, int numeroInventario, Date fechaProceso, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idInventario = idInventario;
        this.numeroInventario = numeroInventario;
        this.fechaProceso = fechaProceso;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Integer idInventario) {
        this.idInventario = idInventario;
    }

    public int getNumeroInventario() {
        return numeroInventario;
    }

    public void setNumeroInventario(int numeroInventario) {
        this.numeroInventario = numeroInventario;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
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

    public List<GpInventarioProducto> getInventarioProductoList() {
        return inventarioProductoList;
    }

    public void setInventarioProductoList(List<GpInventarioProducto> inventarioProductoList) {
        this.inventarioProductoList = inventarioProductoList;
    }

    /**
     * @return the periodo
     */
    public GpPeriodo getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(GpPeriodo periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventario != null ? idInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpInventario)) {
            return false;
        }
        GpInventario other = (GpInventario) object;
        if ((this.idInventario == null && other.idInventario != null) || (this.idInventario != null && !this.idInventario.equals(other.idInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.Inventario[ idInventario=" + idInventario + " ]";
    }
    
}
