/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author armando
 */
@Entity
@Table(name = "PACIENTE",schema = "HVitarteBD.dbo")
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT g FROM Paciente g")})
public class Paciente extends RemoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PACIENTE")
    private String paciente;
    @Basic(optional = false)
    @Column(name = "HISTORIA")
    private String historia;
    @Basic(optional = false)
    @Column(name = "PATERNO")
    private String paterno;
    @Basic(optional = false)
    @Column(name = "MATERNO")
    private String materno;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "NOMBRES")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "SEXO")
    private Character sexo;
    @Basic(optional = false)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Column(name = "FECHA_APERTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Basic(optional = false)
    @Column(name = "HORA_APERTURA")
    private String horaApertura;
    @Basic(optional = false)
    @Column(name = "PADRE")
    private String padre;
    @Basic(optional = false)
    @Column(name = "MADRE")
    private String madre;
    @Basic(optional = false)
    @Column(name = "HIJOS")
    private int hijos;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "DISTRITO")
    private String distrito;
    @Basic(optional = false)
    @Column(name = "LOCALIDAD")
    private String localidad;
    @Basic(optional = false)
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Basic(optional = false)
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Basic(optional = false)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "LUGAR_NACIMIENTO")
    private String lugarNacimiento;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "EDAD")
    private String edad;
    @Basic(optional = false)
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Basic(optional = false)
    @Column(name = "DOCUMENTO")
    private String documento;
    @Basic(optional = false)
    @Column(name = "PAIS")
    private String pais;
    @Basic(optional = false)
    @Column(name = "OCUPACION")
    private String ocupacion;
    @Basic(optional = false)
    @Column(name = "GRADO_INSTRUCCION")
    private String gradoInstruccion;
    @Basic(optional = false)
    @Column(name = "CONYUGE_NOMBRE")
    private String conyugeNombre;
    @Basic(optional = false)
    @Column(name = "CONYUGE_OCUPACION")
    private String conyugeOcupacion;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_NOMBRE")
    private String responsableNombre;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_DIRECCION")
    private String responsableDireccion;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_PARENTESCO")
    private String responsableParentesco;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_TRABAJO")
    private String responsableTrabajo;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_OCUPACION")
    private String responsableOcupacion;
    @Basic(optional = false)
    @Column(name = "RESPONSABLE_TELEFONO")
    private String responsableTelefono;
    @Basic(optional = false)
    @Column(name = "SEGURO")
    private String seguro;
    @Basic(optional = false)
    @Column(name = "MEDICO")
    private String medico;
    @Basic(optional = false)
    @Column(name = "CONSULTORIO")
    private String consultorio;
    @Basic(optional = false)
    @Column(name = "CONSULTORIO_OLD")
    private String consultorioOld;
    @Basic(optional = false)
    @Column(name = "ENTIDAD")
    private String entidad;
    @Basic(optional = false)
    @Column(name = "CIEX")
    private String ciex;
    @Basic(optional = false)
    @Column(name = "CIEX_DESCRIPCION")
    private String ciexDescripcion;
    @Basic(optional = false)
    @Column(name = "RH")
    private Character rh;
    @Basic(optional = false)
    @Column(name = "GRUPO_SANGUINEO")
    private String grupoSanguineo;
    @Column(name = "ANIO")
    private String anio;
    @Basic(optional = false)
    @Column(name = "SYSINSERT")
    private String sysinsert;
    @Basic(optional = false)
    @Column(name = "SYSUPDATE")
    private String sysupdate;
    @Basic(optional = false)
    @Column(name = "SYSDELETE")
    private String sysdelete;
    @Basic(optional = false)
    @Column(name = "TURNO_CONSULTA")
    private String turnoConsulta;
    @Column(name = "FECHA_CONSULTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsulta;
    @Column(name = "TIPO_REGISTRO")
    private Character tipoRegistro;
    @Column(name = "NRO_RAYOSX")
    private String nroRayosx;
    @Column(name = "MODULO")
    private String modulo;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "EDAD2")
    private Integer edad2;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "SYSAUDITORIA")
    private String sysauditoria;
    @Column(name = "HOSPITAL_NACIMIENTO")
    private String hospitalNacimiento;
    @Column(name = "HC_PADRE")
    private String hcPadre;
    @Column(name = "HC_MADRE")
    private String hcMadre;
    @Column(name = "TIPO_NACIMIENTO")
    private String tipoNacimiento;
    @Basic(optional = false)
    @Column(name = "NUEVO")
    private boolean nuevo;
    @Basic(optional = false)
    @Column(name = "EMERGENCIA")
    private boolean emergencia;
    @Basic(optional = false)
    @Lob
    @Column(name = "MOTIVO_ANULACION")
    private String motivoAnulacion;
    @Column(name = "FEC_ANULACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecAnulacion;
    @Basic(optional = false)
    @Column(name = "USUARIO_ANULACION")
    private String usuarioAnulacion;

    public Paciente() {
    }

    public Paciente(String paciente) {
        this.paciente = paciente;
    }

    public Paciente(String paciente, String historia, String paterno, String materno, String nombre, String nombres, Character sexo, String estadoCivil, String horaApertura, String padre, String madre, int hijos, String direccion, String distrito, String localidad, String telefono1, String telefono2, String email, String lugarNacimiento, String edad, String tipoDocumento, String documento, String pais, String ocupacion, String gradoInstruccion, String conyugeNombre, String conyugeOcupacion, String responsableNombre, String responsableDireccion, String responsableParentesco, String responsableTrabajo, String responsableOcupacion, String responsableTelefono, String seguro, String medico, String consultorio, String consultorioOld, String entidad, String ciex, String ciexDescripcion, Character rh, String grupoSanguineo, String sysinsert, String sysupdate, String sysdelete, String turnoConsulta, boolean nuevo, boolean emergencia, String motivoAnulacion, String usuarioAnulacion) {
        this.paciente = paciente;
        this.historia = historia;
        this.paterno = paterno;
        this.materno = materno;
        this.nombre = nombre;
        this.nombres = nombres;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.horaApertura = horaApertura;
        this.padre = padre;
        this.madre = madre;
        this.hijos = hijos;
        this.direccion = direccion;
        this.distrito = distrito;
        this.localidad = localidad;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
        this.lugarNacimiento = lugarNacimiento;
        this.edad = edad;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.pais = pais;
        this.ocupacion = ocupacion;
        this.gradoInstruccion = gradoInstruccion;
        this.conyugeNombre = conyugeNombre;
        this.conyugeOcupacion = conyugeOcupacion;
        this.responsableNombre = responsableNombre;
        this.responsableDireccion = responsableDireccion;
        this.responsableParentesco = responsableParentesco;
        this.responsableTrabajo = responsableTrabajo;
        this.responsableOcupacion = responsableOcupacion;
        this.responsableTelefono = responsableTelefono;
        this.seguro = seguro;
        this.medico = medico;
        this.consultorio = consultorio;
        this.consultorioOld = consultorioOld;
        this.entidad = entidad;
        this.ciex = ciex;
        this.ciexDescripcion = ciexDescripcion;
        this.rh = rh;
        this.grupoSanguineo = grupoSanguineo;
        this.sysinsert = sysinsert;
        this.sysupdate = sysupdate;
        this.sysdelete = sysdelete;
        this.turnoConsulta = turnoConsulta;
        this.nuevo = nuevo;
        this.emergencia = emergencia;
        this.motivoAnulacion = motivoAnulacion;
        this.usuarioAnulacion = usuarioAnulacion;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getMadre() {
        return madre;
    }

    public void setMadre(String madre) {
        this.madre = madre;
    }

    public int getHijos() {
        return hijos;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getGradoInstruccion() {
        return gradoInstruccion;
    }

    public void setGradoInstruccion(String gradoInstruccion) {
        this.gradoInstruccion = gradoInstruccion;
    }

    public String getConyugeNombre() {
        return conyugeNombre;
    }

    public void setConyugeNombre(String conyugeNombre) {
        this.conyugeNombre = conyugeNombre;
    }

    public String getConyugeOcupacion() {
        return conyugeOcupacion;
    }

    public void setConyugeOcupacion(String conyugeOcupacion) {
        this.conyugeOcupacion = conyugeOcupacion;
    }

    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    public String getResponsableDireccion() {
        return responsableDireccion;
    }

    public void setResponsableDireccion(String responsableDireccion) {
        this.responsableDireccion = responsableDireccion;
    }

    public String getResponsableParentesco() {
        return responsableParentesco;
    }

    public void setResponsableParentesco(String responsableParentesco) {
        this.responsableParentesco = responsableParentesco;
    }

    public String getResponsableTrabajo() {
        return responsableTrabajo;
    }

    public void setResponsableTrabajo(String responsableTrabajo) {
        this.responsableTrabajo = responsableTrabajo;
    }

    public String getResponsableOcupacion() {
        return responsableOcupacion;
    }

    public void setResponsableOcupacion(String responsableOcupacion) {
        this.responsableOcupacion = responsableOcupacion;
    }

    public String getResponsableTelefono() {
        return responsableTelefono;
    }

    public void setResponsableTelefono(String responsableTelefono) {
        this.responsableTelefono = responsableTelefono;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getConsultorioOld() {
        return consultorioOld;
    }

    public void setConsultorioOld(String consultorioOld) {
        this.consultorioOld = consultorioOld;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getCiex() {
        return ciex;
    }

    public void setCiex(String ciex) {
        this.ciex = ciex;
    }

    public String getCiexDescripcion() {
        return ciexDescripcion;
    }

    public void setCiexDescripcion(String ciexDescripcion) {
        this.ciexDescripcion = ciexDescripcion;
    }

    public Character getRh() {
        return rh;
    }

    public void setRh(Character rh) {
        this.rh = rh;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getSysinsert() {
        return sysinsert;
    }

    public void setSysinsert(String sysinsert) {
        this.sysinsert = sysinsert;
    }

    public String getSysupdate() {
        return sysupdate;
    }

    public void setSysupdate(String sysupdate) {
        this.sysupdate = sysupdate;
    }

    public String getSysdelete() {
        return sysdelete;
    }

    public void setSysdelete(String sysdelete) {
        this.sysdelete = sysdelete;
    }

    public String getTurnoConsulta() {
        return turnoConsulta;
    }

    public void setTurnoConsulta(String turnoConsulta) {
        this.turnoConsulta = turnoConsulta;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Character getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Character tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNroRayosx() {
        return nroRayosx;
    }

    public void setNroRayosx(String nroRayosx) {
        this.nroRayosx = nroRayosx;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getEdad2() {
        return edad2;
    }

    public void setEdad2(Integer edad2) {
        this.edad2 = edad2;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public String getSysauditoria() {
        return sysauditoria;
    }

    public void setSysauditoria(String sysauditoria) {
        this.sysauditoria = sysauditoria;
    }

    public String getHospitalNacimiento() {
        return hospitalNacimiento;
    }

    public void setHospitalNacimiento(String hospitalNacimiento) {
        this.hospitalNacimiento = hospitalNacimiento;
    }

    public String getHcPadre() {
        return hcPadre;
    }

    public void setHcPadre(String hcPadre) {
        this.hcPadre = hcPadre;
    }

    public String getHcMadre() {
        return hcMadre;
    }

    public void setHcMadre(String hcMadre) {
        this.hcMadre = hcMadre;
    }

    public String getTipoNacimiento() {
        return tipoNacimiento;
    }

    public void setTipoNacimiento(String tipoNacimiento) {
        this.tipoNacimiento = tipoNacimiento;
    }

    public boolean getNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(boolean emergencia) {
        this.emergencia = emergencia;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public Date getFecAnulacion() {
        return fecAnulacion;
    }

    public void setFecAnulacion(Date fecAnulacion) {
        this.fecAnulacion = fecAnulacion;
    }

    public String getUsuarioAnulacion() {
        return usuarioAnulacion;
    }

    public void setUsuarioAnulacion(String usuarioAnulacion) {
        this.usuarioAnulacion = usuarioAnulacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paciente != null ? paciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.paciente == null && other.paciente != null) || (this.paciente != null && !this.paciente.equals(other.paciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente[ paciente=" + paciente + " ]";
    }    
}
