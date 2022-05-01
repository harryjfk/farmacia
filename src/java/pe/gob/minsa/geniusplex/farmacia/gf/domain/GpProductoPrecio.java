/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Producto_Precio")
@NamedQueries({
    @NamedQuery(name = "GpProductoPrecio.findAll", query = "SELECT g FROM GpProductoPrecio g")})
public class GpProductoPrecio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdProductoPrecio")
    private Integer idProductoPrecio;
    @Column(name = "TipoPrecio")
    private Character tipoPrecio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PrecioAdquisicion")
    private BigDecimal precioAdquisicion;
    @Basic(optional = false)
    @Column(name = "PrecioDistribucion")
    private BigDecimal precioDistribucion;
    @Basic(optional = false)
    @Column(name = "PrecioOperacion")
    private BigDecimal precioOperacion;
    @Basic(optional = false)
    @Column(name = "FechaRegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "FechaVigencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVigencia;
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
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne(optional = false)
    private GpProducto producto;

    public GpProductoPrecio() {
    }

    public GpProductoPrecio(Integer idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public GpProductoPrecio(Integer idProductoPrecio, BigDecimal precioAdquisicion, BigDecimal precioDistribucion, BigDecimal precioOperacion, Date fechaRegistro, Date fechaVigencia, int usuarioCreacion, Date fechaCreacion) {
        this.idProductoPrecio = idProductoPrecio;
        this.precioAdquisicion = precioAdquisicion;
        this.precioDistribucion = precioDistribucion;
        this.precioOperacion = precioOperacion;
        this.fechaRegistro = fechaRegistro;
        this.fechaVigencia = fechaVigencia;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdProductoPrecio() {
        return idProductoPrecio;
    }

    public void setIdProductoPrecio(Integer idProductoPrecio) {
        this.idProductoPrecio = idProductoPrecio;
    }

    public Character getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(Character tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public BigDecimal getPrecioAdquisicion() {
        return precioAdquisicion;
    }

    public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
        this.precioAdquisicion = precioAdquisicion;
    }

    public BigDecimal getPrecioDistribucion() {
        return precioDistribucion;
    }

    public void setPrecioDistribucion(BigDecimal precioDistribucion) {
        this.precioDistribucion = precioDistribucion;
    }

    public BigDecimal getPrecioOperacion() {
        return precioOperacion;
    }

    public void setPrecioOperacion(BigDecimal precioOperacion) {
        this.precioOperacion = precioOperacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
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
        hash += (idProductoPrecio != null ? idProductoPrecio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpProductoPrecio)) {
            return false;
        }
        GpProductoPrecio other = (GpProductoPrecio) object;
        if ((this.idProductoPrecio == null && other.idProductoPrecio != null) || (this.idProductoPrecio != null && !this.idProductoPrecio.equals(other.idProductoPrecio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoPrecio[ idProductoPrecio=" + idProductoPrecio + " ]";
    }

    public GpProducto getProducto() {
        return producto;
    }

    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }
    
}
