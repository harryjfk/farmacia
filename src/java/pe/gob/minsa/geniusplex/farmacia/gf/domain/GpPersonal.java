/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.gf.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RemoteEntity;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "PER_PERSONAL" ,schema = "HVitarteBD.dbo")
@NamedQueries({
    @NamedQuery(name = "GpPersonal.findAll", query = "SELECT p FROM GpPersonal p")})
public class GpPersonal extends RemoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PERSONAL")
    private String personal;
    @Basic(optional = false)
    @Column(name = "CARGO")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private Character sexo;
    @Basic(optional = false)
    @Column(name = "TIPO_DOCUMENTO")
    private Character tipoDocumento;
    @Basic(optional = false)
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    @Column(name = "FEC_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecNacimiento;
    @Basic(optional = false)
    @Column(name = "FEC_CREADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCreado;
    @Column(name = "FEC_MODIFICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecModificado;
    @Column(name = "FEC_ANULADO")
    private String fecAnulado;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private boolean activo;
    @Column(name = "NRO_COLEGIATURA")
    private String nroColegiatura;
    @Basic(optional = false)
    @Column(name = "CONDICION_LABORAL")
    private String condicionLaboral;
    @Basic(optional = false)
    @Column(name = "FOTO")
    private String foto;
    @Basic(optional = false)
    @Column(name = "TIPO_FUNCION")
    private String tipoFuncion;
    @Basic(optional = false)
    @Column(name = "UNIDAD")
    private String unidad;
    @Basic(optional = false)
    @Column(name = "PLAZA")
    private String plaza;

    public GpPersonal() {
    }

    public GpPersonal(String personal) {
        this.personal = personal;
    }

    public GpPersonal(String personal, String cargo, String apellidoPaterno, String apellidoMaterno, String nombres, Character sexo, Character tipoDocumento, String nroDocumento, Date fecCreado, boolean activo, String condicionLaboral, String foto, String tipoFuncion, String unidad, String plaza) {
        this.personal = personal;
        this.cargo = cargo;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombres = nombres;
        this.sexo = sexo;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.fecCreado = fecCreado;
        this.activo = activo;
        this.condicionLaboral = condicionLaboral;
        this.foto = foto;
        this.tipoFuncion = tipoFuncion;
        this.unidad = unidad;
        this.plaza = plaza;
    }
    
    public  GpPersonal(Personal gfPersonal) {
        this.personal = gfPersonal.getIdPersonal();
        this.cargo = gfPersonal.getCargo();
        this.apellidoPaterno = gfPersonal.getApellidoPaterno();
        this.apellidoMaterno = gfPersonal.getApellidoMaterno();
        this.nombres = gfPersonal.getNombreCompleto();
        this.tipoDocumento = gfPersonal.getTipoDocumento().charAt(0);
        this.nroDocumento = gfPersonal.getNroDocumento();
        this.unidad = gfPersonal.getUnidad();
    }
    

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getNombreCompleto() {
        return String.format("%s %s %s", nombres, apellidoPaterno, apellidoMaterno);
    }
    

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Character getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Character tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public Date getFecNacimiento() {
        return GregorianCalendar.getInstance().getTime();
    }

    public void setFecNacimiento(Date fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public Date getFecCreado() {
        return fecCreado;
    }

    public void setFecCreado(Date fecCreado) {
        this.fecCreado = fecCreado;
    }

    public Date getFecModificado() {
        return fecModificado;
    }

    public void setFecModificado(Date fecModificado) {
        this.fecModificado = fecModificado;
    }

    public String getFecAnulado() {
        return fecAnulado;
    }

    public void setFecAnulado(String fecAnulado) {
        this.fecAnulado = fecAnulado;
    }

    public boolean getActivo() {
        return true;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNroColegiatura() {
        return nroColegiatura;
    }

    public void setNroColegiatura(String nroColegiatura) {
        this.nroColegiatura = nroColegiatura;
    }

    public String getCondicionLaboral() {
        return condicionLaboral;
    }

    public void setCondicionLaboral(String condicionLaboral) {
        this.condicionLaboral = condicionLaboral;
    }

    public String getFoto() {
        return "";
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipoFuncion() {
        return "";
    }

    public void setTipoFuncion(String tipoFuncion) {
        this.tipoFuncion = tipoFuncion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPlaza() {
        return plaza;
    }

    public void setPlaza(String plaza) {
        this.plaza = plaza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personal != null ? personal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GpPersonal)) {
            return false;
        }
        GpPersonal other = (GpPersonal) object;
        return !((this.personal == null && other.personal != null) || (this.personal != null && !this.personal.equals(other.personal)));
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.gf.domain.Personal[ personal=" + personal + " ]";
    }
    
}
