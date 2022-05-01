package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author stark
 */
@Table(name = "Far_Receta")
@Entity
public class Receta extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="IdReceta")
    private long idReceta;
    @Column(name = "subModulo")
    private long IdModulo;
    @Column(name = "Numero")
    private long numero;
    @Column(name = "Atendida")
    private int atendida;
    @ManyToOne
    @JoinColumn(name="Paciente",referencedColumnName = "Paciente")
    private Paciente paciente;
   
    

    public long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(long idReceta) {
        this.idReceta = idReceta;
    }

   

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long idModulo) {
        this.IdModulo = idModulo;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public int getAtendida() {
        return atendida;
    }

    public void setAtendida(int atendida) {
        this.atendida = atendida;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (this.idReceta ^ (this.idReceta >>> 32));
        hash = 41 * hash + (int) (this.IdModulo ^ (this.IdModulo >>> 32));
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
        final Receta other = (Receta) obj;
        if (this.idReceta != other.idReceta) {
            return false;
        }
        if (this.IdModulo != other.IdModulo) {
            return false;
        }
        return true;
    }

    

    

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Receta[ id=" + idReceta + " ]";
    }

    @Override
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }

}
