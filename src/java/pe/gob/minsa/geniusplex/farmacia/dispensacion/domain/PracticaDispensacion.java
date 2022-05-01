/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Buenas_Practicas_Dispensacion")
public class PracticaDispensacion extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto")
    private GpProducto producto;

    @ManyToOne
    @JoinColumn(name = "producto_corregido")
    private GpProducto productoCorregido;

    @ManyToOne
    @JoinColumn(name = "vendedor_corrige")
    private Vendedor vendedor;

    @Lob
    @Column(name = "obervacion")
    private String Observacion;

    @Column(name = "IdModulo")
    private long IdModulo;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "Fecha")
    private Date fecha;

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public GpProducto getProducto() {
        return producto;
    }

    public void setProducto(GpProducto producto) {
        this.producto = producto;
    }

    public GpProducto getProductoCorregido() {
        return productoCorregido;
    }

    public void setProductoCorregido(GpProducto productoCorregido) {
        this.productoCorregido = productoCorregido;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + (this.venta != null ? this.venta.hashCode() : 0);
        hash = 97 * hash + (this.producto != null ? this.producto.hashCode() : 0);
        hash = 97 * hash + (this.productoCorregido != null ? this.productoCorregido.hashCode() : 0);
        hash = 97 * hash + (this.vendedor != null ? this.vendedor.hashCode() : 0);
        hash = 97 * hash + (this.Observacion != null ? this.Observacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PracticaDispensacion other = (PracticaDispensacion) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.venta != other.venta && (this.venta == null || !this.venta.equals(other.venta))) {
            return false;
        }
        if (this.producto != other.producto && (this.producto == null || !this.producto.equals(other.producto))) {
            return false;
        }
        if (this.productoCorregido != other.productoCorregido && (this.productoCorregido == null || !this.productoCorregido.equals(other.productoCorregido))) {
            return false;
        }
        if (this.vendedor != other.vendedor && (this.vendedor == null || !this.vendedor.equals(other.vendedor))) {
            return false;
        }
        if ((this.Observacion == null) ? (other.Observacion != null) : !this.Observacion.equals(other.Observacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PracticaDispensacion[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
