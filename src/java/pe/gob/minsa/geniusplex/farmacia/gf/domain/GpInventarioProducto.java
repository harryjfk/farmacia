/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RemoteEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Inventario_Producto")
@NamedQueries({
    @NamedQuery(name = "InventarioProducto.findAll", query = "SELECT i FROM GpInventarioProducto i")})
public class GpInventarioProducto extends RemoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdInventarioProducto")
    private Integer idInventarioProducto;
    @Basic(optional = false)
    @Column(name = "Lote")
    private String lote;
    @Basic(optional = false)
    @Column(name = "FechaVencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Basic(optional = false)
    @Column(name = "Cantidad")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "Precio")
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "Total")
    private BigDecimal total;
    @Column(name = "Conteo")
    private Integer conteo;
    @Column(name = "CantidadFaltante")
    private Integer cantidadFaltante;
    @Column(name = "CantidadSobrante")
    private Integer cantidadSobrante;
    @Column(name = "CantidadAlterado")
    private Integer cantidadAlterado;
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
    @JoinColumn(name = "IdInventario", referencedColumnName = "IdInventario")
    @ManyToOne(optional = false)
    private GpInventario idInventario;
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne
    private GpProducto producto;

    public GpInventarioProducto() {
    }

    public GpInventarioProducto(Integer idInventarioProducto) {
        this.idInventarioProducto = idInventarioProducto;
    }

    public GpInventarioProducto(Integer idInventarioProducto, String lote, Date fechaVencimiento, int cantidad, BigDecimal precio, BigDecimal total, int activo, int usuarioCreacion, Date fechaCreacion) {
        this.idInventarioProducto = idInventarioProducto;
        this.lote = lote;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
        this.activo = activo;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdInventarioProducto() {
        return idInventarioProducto;
    }

    public void setIdInventarioProducto(Integer idInventarioProducto) {
        this.idInventarioProducto = idInventarioProducto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getConteo() {
        return conteo;
    }

    public void setConteo(Integer conteo) {
        this.conteo = conteo;
    }

    public Integer getCantidadFaltante() {
        return cantidadFaltante;
    }

    public void setCantidadFaltante(Integer cantidadFaltante) {
        this.cantidadFaltante = cantidadFaltante;
    }

    public Integer getCantidadSobrante() {
        return cantidadSobrante;
    }

    public void setCantidadSobrante(Integer cantidadSobrante) {
        this.cantidadSobrante = cantidadSobrante;
    }

    public Integer getCantidadAlterado() {
        return cantidadAlterado;
    }

    public void setCantidadAlterado(Integer cantidadAlterado) {
        this.cantidadAlterado = cantidadAlterado;
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

    public GpInventario getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(GpInventario idInventario) {
        this.idInventario = idInventario;
    }

    /**
     * @return the producto
     */
    public GpProducto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventarioProducto != null ? idInventarioProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpInventarioProducto)) {
            return false;
        }
        GpInventarioProducto other = (GpInventarioProducto) object;
        if ((this.idInventarioProducto == null && other.idInventarioProducto != null) || (this.idInventarioProducto != null && !this.idInventarioProducto.equals(other.idInventarioProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.InventarioProducto[ idInventarioProducto=" + idInventarioProducto + " ]";
    }
    
}
