/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

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
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author stark
 */
@Entity
@Table(name = "Far_Turno")
public class Turno extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idTurno")
    private long idTurno;
    @Column(name = "Descripcion")
    private String descripcion;
    @Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "horaInicio")
    private Date horaInicio;
    @Temporal(javax.persistence.TemporalType.TIME)
    @Column(name = "horaFinal")
    private Date horaFinal;
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private List<Vendedor> vendedores;
    @Column(name = "farmacia")
    private long farmacia;
    @Column(name = "Codigo")
    private String codigo;

    @Column(name = "idModule")
    private long IdModulo;

    public Turno() {
    }

    public long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(long idTurno) {
        this.idTurno = idTurno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public long getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(long farmacia) {
        this.farmacia = farmacia;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    public String getCodigo() {
        return String.valueOf(this.getIdTurno());
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.idTurno ^ (this.idTurno >>> 32));
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
        final Turno other = (Turno) obj;
        return this.idTurno == other.idTurno;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno[ id=" + idTurno + " ]";
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
