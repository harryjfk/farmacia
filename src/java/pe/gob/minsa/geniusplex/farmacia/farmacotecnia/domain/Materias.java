package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.BaseEntity;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_MateriasPrimas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Materias extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Nombre")
    private String nombre;
    
    @Column(name = "Precio", precision = 38, scale = 2)
    private BigDecimal precio;

    @Column(name = "IdModulo")
    private long IdModulo;
    
    @Column(name = "Cantidad")
    private int cantidad;

    @JoinColumn(name = "UnidadMedida")
    @ManyToOne(cascade = {})
    private GpUnidadMedida unidad;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "Almacen")
    private GpAlmacen almacen;
    
    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MatrizMateria> insumos;
    
    @ManyToOne
    private GpTipoProducto tipoProducto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GpUnidadMedida getUnidad() {
        return unidad;
    }

    public void setUnidad(GpUnidadMedida unidad) {
        this.unidad = unidad;
    }

    public BigDecimal getPrecio() {
        return precio.setScale(2);
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public List<MatrizMateria> getInsumos() {
        if(this.insumos == null) {
            this.insumos = new ArrayList<MatrizMateria>();
        }
        return this.insumos;
    }
    
    public void setInsumos(List<MatrizMateria> insumos) {
        this.insumos = insumos;
    }

    /**
     * @return the tipoProducto
     */
    public GpTipoProducto getTipoProducto() {
        return tipoProducto;
    }

    /**
     * @param tipoProducto the tipoProducto to set
     */
    public void setTipoProducto(GpTipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    
    @Override
    public String toString() {
        return "Materias{" + "nombre=" + nombre + ", unidad=" + unidad + ", precio=" + precio + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 53 * hash + (this.unidad != null ? this.unidad.hashCode() : 0);
        hash = 53 * hash + (this.precio != null ? this.precio.hashCode() : 0);
        hash = 53 * hash + (int) (this.IdModulo ^ (this.IdModulo >>> 32));
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
        final Materias other = (Materias) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if ((this.unidad == null) ? (other.unidad != null) : !this.unidad.equals(other.unidad)) {
            return false;
        }
        if (this.precio != other.precio && (this.precio == null || !this.precio.equals(other.precio))) {
            return false;
        }
        return this.IdModulo == other.IdModulo;
    }

    public GpAlmacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(GpAlmacen almacen) {
        this.almacen = almacen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
