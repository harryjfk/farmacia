/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Sub_Componente")
public class SubComponente extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long id;
    
    @Column(name = "Codigo")
    private String codigo;
    
    @Column(name = "Descripcion")
    private String descripcion;
    
    @JoinTable(name = "Far_Sub_Componente_Producto", joinColumns = {
        @JoinColumn(name = "SubComponente_Id", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "Producto_Id", referencedColumnName = "IdProducto")})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<GpProducto> productos;
    
    @JoinColumn(name = "DiagnosticoCIE")
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private DiagnosticoCIE diagnostico;
    
    @JsonIgnore
    @JoinColumn(name = "KitsAtencion")
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<KitAtencion> kits;
    
    @JoinColumn(name = "Coordinador")
    @OneToOne
    private GpPersonal coordinador; 

    public SubComponente() {
    }

    public SubComponente(String desc) {
        descripcion = desc;
    }
    public SubComponente(long id, String desc) {
        this.id = id;
        descripcion = desc;
    }
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the productos
     */
    public List<GpProducto> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<GpProducto> productos) {
        this.productos = productos;
    }

    /**
     * @return the diagnostico
     */
    public DiagnosticoCIE getDiagnostico() {
        return diagnostico;
    }

    /**
     * @param diagnostico the diagnostico to set
     */
    public void setDiagnostico(DiagnosticoCIE diagnostico) {
        this.diagnostico = diagnostico;
    }

    /**
     * @return the coordinador
     */
    public GpPersonal getCoordinador() {
        return coordinador;
    }

    /**
     * @param coordinador the coordinador to set
     */
    public void setCoordinador(GpPersonal coordinador) {
        this.coordinador = coordinador;
    }

    /**
     * @return the kits
     */
    public List<KitAtencion> getKits() {
        return kits;
    }

    /**
     * @param kits the kits to set
     */
    public void setKits(List<KitAtencion> kits) {
        this.kits = kits;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubComponente)) {
            return false;
        }
        SubComponente other = (SubComponente) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return this.getDescripcion();
    }    
}
