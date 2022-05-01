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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoDocumento;

/**
 *
 * @author GeniusPlex
 *
 * Entidad Cliente
 *
 */
@Entity
@Table(name = "Far_Cliente")
public class Cliente extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long idCliente;
    @Column(name = "Codigo")
    private String Codigo;
    @Column(name = "CodPersonal")
    private String CodPersonal;
    @Column(name = "NoAfiliacion")
    private String NoAfiliacion;
    @Column(name = "Nombre")
    private String Nombre;
    @Column(name = "ApellidoPaterno")
    private String ApellidoPaterno;
    @Column(name = "ApellidoMaterno")
    private String ApellidoMaterno;
    @Column(name = "Direccion")
    private String Direccion;
    @JoinColumn(name = "TipoDocumento")
    @ManyToOne
    private GpTipoDocumento TipoDocumento;
    @Column(name = "NoDocumento")
    private int NoDocumento;
    @Column(name = "Telefono")
    private String Telefono;
    @Column(name = "Dni")
    private String Dni;
    @Column(name = "IdModulo")
    private long IdModulo;

    /**
     * Entidad Cliente
     */
    public Cliente() {
    }

    public Cliente(String CodPersonal, String NoAfiliacion, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Direccion, GpTipoDocumento TipoDocumento, int NoDocumento, String Telefono, String Dni) {
        this.CodPersonal = CodPersonal;
        this.NoAfiliacion = NoAfiliacion;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Direccion = Direccion;
        this.TipoDocumento = TipoDocumento;
        this.NoDocumento = NoDocumento;
        this.Telefono = Telefono;
        this.Dni = Dni;
    }

    /**
     * Obtiene el Identificador de Cliente
     *
     * @return Identificador de Cliente
     */
    public long getIdCliente() {
        return idCliente;
    }

    /**
     * Asigna el Identificador de Cliente
     *
     * @param idCliente Identificador de Cliente
     */
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el codigo del Cliente
     *
     * @return Codigo del Cliente
     */
    public String getCodigo() {
        return String.valueOf(getIdCliente());
//        return Codigo;
    }

    /**
     * Asigna el codigo del Cliente
     *
     * @param Codigo
     */
    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * Obtiene el Codigo Personal del Cliente
     *
     * @return Codigo Personal del Cliente
     */
    public String getCodPersonal() {
        return CodPersonal;
    }

    /**
     * Asigna el Codigo Personal del Cliente
     *
     * @param CodPersonal Codigo Personal del Cliente
     */
    public void setCodPersonal(String CodPersonal) {
        this.CodPersonal = CodPersonal;
    }

    /**
     * Asigna el Numero de Afiliacion del Cliente
     *
     * @return Numero de Afiliacion del Cliente
     */
    public String getNoAfiliacion() {
        return NoAfiliacion;
    }

    /**
     * Asigna el Numero de Afiliacion del Cliente
     *
     * @param NoAfiliacion Numero de Afiliacion del Cliente
     */
    public void setNoAfiliacion(String NoAfiliacion) {
        this.NoAfiliacion = NoAfiliacion;
    }

    /**
     * Obtiene el Nombre de Cliente
     *
     * @return Nombre de Cliente
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Asigna el Nombre de Cliente
     *
     * @param Nombre Nombre de Cliente
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * Obtiene la Direccion del Cliente
     *
     * @return Direccion del Cliente
     */
    public String getDireccion() {
        return Direccion;
    }

    /**
     * Asigna la Direccion del Cliente
     *
     * @param Direccion Direccion del Cliente
     */
    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    /**
     * Obtiene el Tipo de Documento del Cliente
     *
     * @return Tipo de Documento del Cliente
     */
    public GpTipoDocumento getTipoDocumento() {
        return TipoDocumento;
    }

    /**
     * Asigna el Tipo de Documento del Cliente
     *
     * @param TipoDocumento Tipo de Documento del Cliente
     */
    public void setTipoDocumento(GpTipoDocumento TipoDocumento) {
        this.TipoDocumento = TipoDocumento;
    }

    /**
     * Obtiene el Numero de Documento del Cliente
     *
     * @return
     */
    public int getNoDocumento() {
        return NoDocumento;
    }

    /**
     * Asigna el Numero de Documento del Cliente
     *
     * @param NoDocumento Numero de Documento del Cliente
     */
    public void setNoDocumento(int NoDocumento) {
        this.NoDocumento = NoDocumento;
    }

    /**
     * Obtiene el Telefono del Cliente
     *
     * @return Telefono del Cliente
     */
    public String getTelefono() {
        return Telefono;
    }

    /**
     * Asigna el Telefono del Cliente
     *
     * @param Telefono Telefono del Cliente
     */
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    /**
     * Obtiene el Apellido Paterno del Cliente
     *
     * @return Apellido Paterno del Cliente
     */
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    /**
     * Asigna el Apellido Paterno del Cliente
     *
     * @param ApellidoPaterno Apellido Paterno del Cliente
     */
    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    /**
     * Obtiene el Apellido Materno del Cliente
     *
     * @return Apellido Materno del Cliente
     */
    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    /**
     * Asigna el Apellido Materno del Cliente
     *
     * @param ApellidoMaterno Apellido Materno del Cliente
     */
    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    /**
     * Obtiene el DNI del Cliente
     *
     * @return DNI del Cliente
     */
    public String getDni() {
        return Dni;
    }

    /**
     * Asigna el el DNI del Cliente
     *
     * @param Dni del Cliente
     */
    public void setDni(String Dni) {
        this.Dni = Dni;
    }

    public long getIdModulo() {
        return IdModulo;
    }

    public void setIdModulo(long IdModulo) {
        this.IdModulo = IdModulo;
    }

    @Override
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.getFechaCreacion() == null) {
            this.setFechaCreacion(GregorianCalendar.getInstance().getTime());
            this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
            this.setActivo(1);
        }
        this.setFechaModificacion(GregorianCalendar.getInstance().getTime());
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s", getNombre(), getApellidoPaterno(), getApellidoMaterno());
    }
    
    public String getNombreCliente() {
        return this.toString();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.Codigo != null ? this.Codigo.hashCode() : 0);
        hash = 53 * hash + (this.CodPersonal != null ? this.CodPersonal.hashCode() : 0);
        hash = 53 * hash + (this.NoAfiliacion != null ? this.NoAfiliacion.hashCode() : 0);
        hash = 53 * hash + (this.Nombre != null ? this.Nombre.hashCode() : 0);
        hash = 53 * hash + (this.ApellidoPaterno != null ? this.ApellidoPaterno.hashCode() : 0);
        hash = 53 * hash + (this.ApellidoMaterno != null ? this.ApellidoMaterno.hashCode() : 0);
        hash = 53 * hash + (this.Dni != null ? this.Dni.hashCode() : 0);
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
        final Cliente other = (Cliente) obj;
        if ((this.Nombre == null) ? (other.Nombre != null) : !this.Nombre.equals(other.Nombre)) {
            return false;
        }
        if ((this.ApellidoPaterno == null) ? (other.ApellidoPaterno != null) : !this.ApellidoPaterno.equals(other.ApellidoPaterno)) {
            return false;
        }
        if ((this.ApellidoMaterno == null) ? (other.ApellidoMaterno != null) : !this.ApellidoMaterno.equals(other.ApellidoMaterno)) {
            return false;
        }
        return this.IdModulo == other.IdModulo;
    }
    
}
