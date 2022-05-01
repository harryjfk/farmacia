/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "Far_Kit_Atencion")
public class KitAtencion extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "Periodo")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy")
    private Date periodo;

    @Column(name = "Descripcion")
    private String descripcion;
    
    @Column(name = "IdModulo")
    private long IdModulo;

    @Column(name = "TipoKit")
    private String tipoKit;
    
    @ManyToOne
    @JoinColumn(name = "IdComponente")
    private Componente componente;

    @ManyToOne
    @JoinColumn(name = "IdSubComponente")
    private SubComponente subComponente;

    @ManyToOne
    @JoinColumn(name = "IdProceso")
    private Proceso proceso;
    
    @OneToMany(mappedBy = "kitAtencion", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore //se van a listar los productos asociados a un kit, no los kits en si
    private List<KitAtencionProducto> productos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the componente
     */
    public Componente getComponente() {
        return componente;
    }

    /**
     * @param componente the componente to set
     */
    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    /**
     * @return the subComponente
     */
    public SubComponente getSubComponente() {
        return subComponente;
    }

    /**
     * @param subComponente the subComponente to set
     */
    public void setSubComponente(SubComponente subComponente) {
        this.subComponente = subComponente;
    }

    /**
     * @return the proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the periodo
     */
    public Date getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
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
     * @return the IdModulo
     */
    public long getIdModulo() {
        return IdModulo;
    }

    /**
     * @param IdModulo the IdModulo to set
     */
    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    /**
     * @return the tipoKit
     */
    public String getTipoKit() {
        return tipoKit;
    }

    /**
     * @param tipoKit the tipoKit to set
     */
    public void setTipoKit(String tipoKit) {
        this.tipoKit = tipoKit;
    }

    /**
     * @return the productos
     */
    public List<KitAtencionProducto> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<KitAtencionProducto> productos) {
        this.productos = productos;
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
        if (!(object instanceof KitAtencion)) {
            return false;
        }
        KitAtencion other = (KitAtencion) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @Override 
    @PrePersist 
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }
}
