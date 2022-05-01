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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Movimiento_Producto")
@NamedQueries({
    @NamedQuery(name = "GpMovimientoProducto.findAll", query = "SELECT g FROM GpMovimientoProducto g")})
public class GpMovimientoProducto extends BaseEntity implements Serializable {
    @Column(name = "FechaVencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "RegistroSanitario")
    private String registroSanitario;
    
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    @ManyToOne
    private GpProducto idProducto;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMovimientoProducto")
    private Integer idMovimientoProducto;
    @Column(name = "Cantidad")
    private Integer cantidad;
    @Column(name = "Precio")
    private BigDecimal precio;
    @Column(name = "Total")
    private BigDecimal total;
    @Column(name = "Lote")
    private String lote;
    @JsonIgnore
    @JoinColumn(name = "IdMovimiento", referencedColumnName = "IdMovimiento")
    @ManyToOne
    private GpMovimiento idMovimiento;

    public GpMovimientoProducto() {
    }

    public GpMovimientoProducto(Integer idMovimientoProducto) {
        this.idMovimientoProducto = idMovimientoProducto;
    }


    public Integer getIdMovimientoProducto() {
        return idMovimientoProducto;
    }

    public void setIdMovimientoProducto(Integer idMovimientoProducto) {
        this.idMovimientoProducto = idMovimientoProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public GpMovimiento getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(GpMovimiento idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovimientoProducto != null ? idMovimientoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpMovimientoProducto)) {
            return false;
        }
        GpMovimientoProducto other = (GpMovimientoProducto) object;
        if ((this.idMovimientoProducto == null && other.idMovimientoProducto != null) || (this.idMovimientoProducto != null && !this.idMovimientoProducto.equals(other.idMovimientoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto[ idMovimientoProducto=" + idMovimientoProducto + " ]";
    }
    
    public GpProducto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(GpProducto idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }
}
