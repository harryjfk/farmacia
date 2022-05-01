/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Receta_Producto")
public class RecetaProducto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IdReceta")
    private long idReceta;
    
    @Column(name = "IdProducto")
    private int idProducto;
    
    
    @Column(name = "IdLote")
    private String idLote;
    
    @Column(name = "IdAlmacen")
    private long idAlmacen;

    @Transient
    private ProductoLote productoLote;

    
    @Column(name = "Cantidad")
    private int cantidad;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name="Paciente")
    private Paciente paciente;
    @Column(name = "subModulo")
    private long IdModulo;
    public RecetaProducto() {
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.idProducto ^ (this.idProducto >>> 32));
        hash = 83 * hash + (this.idLote != null ? this.idLote.hashCode() : 0);
        hash = 83 * hash + (int) (this.idAlmacen ^ (this.idAlmacen >>> 32));
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
        final RecetaProducto other = (RecetaProducto) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
       
        if ((this.idLote == null) ? (other.idLote != null) : !this.idLote.equals(other.idLote)) {
            return false;
        }
        return this.idAlmacen == other.idAlmacen;
    }

    

   
    

    public ProductoLote getProductoLote() {
        return productoLote;
    }

    public int getCantidad() {
        return cantidad;
    }

   

    public void setProductoLote(ProductoLote productoLote) {
        this.productoLote = productoLote;
        this.idAlmacen = productoLote.getIdAlmacen();
        this.idLote = productoLote.getLote().getDescripcion();
        this.idProducto = productoLote.getProducto().getIdProducto();
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getIdLote() {
        return idLote;
    }

    public long getIdAlmacen() {
        return idAlmacen;
    }

    @Override
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }
      
}
