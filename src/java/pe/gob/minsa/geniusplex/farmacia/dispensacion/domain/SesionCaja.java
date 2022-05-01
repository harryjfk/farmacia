/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * Representa una Sesion de Caja
 */
@Entity
@Table(name = "Far_Sesion_Caja")
public class SesionCaja extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdSesionCaja")
    private long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "InicioSesionCaja")
    private Date inicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "CierreSesionCaja")
    private Date cierre;
    @Column(name = "MontoInicial")
    private double montoInicial;
    @Column(name = "IdModulo")
    private long IdModulo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the puntoDeVenta
     */
//    public PtoDeVenta getPuntoDeVenta() {
//        return puntoDeVenta;
//    }

    /**
     * @param puntoDeVenta the puntoDeVenta to set
     */
//    public void setPuntoDeVenta(PtoDeVenta puntoDeVenta) {
//        this.puntoDeVenta = puntoDeVenta;
//    }

    /**
     * @return the vendedor
     */
//    public Vendedor getVendedor() {
//        return vendedor;
//    }

    /**
     * @param vendedor the vendedor to set
     */
//    public void setVendedor(Vendedor vendedor) {
//        this.vendedor = vendedor;
//    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the cierre
     */
    public Date getCierre() {
        return cierre;
    }

    /**
     * @param cierre the cierre to set
     */
    public void setCierre(Date cierre) {
        this.cierre = cierre;
    }

    /**
     * @return the montoInicial
     */
    public double getMontoInicial() {
        return montoInicial;
    }

    /**
     * @param montoInicial the montoInicial to set
     */
    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
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
        if (!(object instanceof SesionCaja)) {
            return false;
        }
        SesionCaja other = (SesionCaja) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SesionCaja[ id=" + id + " ]";
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
