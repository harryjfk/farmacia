/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 *
 * Representa un Punto de Venta
 */
@Entity
@Table(name = "Far_Caja_Punto_Venta")
public class PuntoDeVenta extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdCajaPuntoVenta")
    private long id;
    @Column(name = "TipoPuntoVenta")
    private String tipo;
    @Column(name = "IdModulo")
    private long IdModulo;
    @Column(name = "NombrePC")
    private String nombrePc;
    @Column(name = "SerieBoleta")
    private String serieBoleta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * @return the nombrePc
     */
    public String getNombrePc() {
        return nombrePc;
    }

    /**
     * @param nombrePc the nombrePc to set
     */
    public void setNombrePc(String nombrePc) {
        this.nombrePc = nombrePc;
    }

    /**
     * @return the serieBoleta
     */
    public String getSerieBoleta() {
        return serieBoleta;
    }

    /**
     * @param serieBoleta the serieBoleta to set
     */
    public void setSerieBoleta(String serieBoleta) {
        this.serieBoleta = serieBoleta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PuntoDeVenta)) {
            return false;
        }
        PuntoDeVenta other = (PuntoDeVenta) object;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PtoDeVenta[ id=" + id + " ]";
    }

    @Override
    @PrePersist
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }
    
}
