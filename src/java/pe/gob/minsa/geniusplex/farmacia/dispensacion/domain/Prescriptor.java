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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * Representa un Prescriptor
 */
@Entity
@Table(name = "Far_Medico")
public class Prescriptor extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Medico")
    private long idMedico;
    @Column(name = "Nombres")
    private String nombre;
    @Column(name = "Colegio")
    private String colegio;
    @Column(name = "Paterno")
    private String paterno;
    @Column(name = "Materno")
    private String materno;
    @Column(name = "Profesion")
    private String profesion;
    @Column(name = "Especialidad")
    private String especialidad;
    @Column(name = "Cargo")
    private String cargo;
    @Column(name = "Plaza")
    private String plaza;
    @Column(name = "Consultorio")
    private String consultorio;
    @Column(name = "Dni")
    private String dni;
    @Column(name = "PlazaHv")
    private int plazaHv;
    @Column(name = "ProfesionalSalud")
    private String profesionalSalud;
    @Column(name = "Personal")
    private String personal;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Telefono1")
    private String telefonoUno;
    @Column(name = "IdModulo")
    private long IdModulo;

    public Prescriptor() {
    }

    /**
     * @return the idMedico
     */
    public long getIdMedico() {
        return idMedico;
    }

    /**
     * @param idMedico the idMedico to set
     */
    public void setIdMedico(long idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the colegio
     */
    public String getColegio() {
        return colegio;
    }

    /**
     * @param colegio the colegio to set
     */
    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    /**
     * @return the paterno
     */
    public String getPaterno() {
        return paterno;
    }

    /**
     * @param paterno the paterno to set
     */
    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    /**
     * @return the materno
     */
    public String getMaterno() {
        return materno;
    }

    /**
     * @param materno the materno to set
     */
    public void setMaterno(String materno) {
        this.materno = materno;
    }

    /**
     * @return the profesion
     */
    public String getProfesion() {
        return profesion;
    }

    /**
     * @param profesion the profesion to set
     */
    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    /**
     * @return the especialidad
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * @param especialidad the especialidad to set
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * @return the plaza
     */
    public String getPlaza() {
        return plaza;
    }

    /**
     * @param plaza the plaza to set
     */
    public void setPlaza(String plaza) {
        this.plaza = plaza;
    }

    /**
     * @return the consultorio
     */
    public String getConsultorio() {
        return consultorio;
    }

    /**
     * @param consultorio the consultorio to set
     */
    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the plazaHv
     */
    public int getPlazaHv() {
        return plazaHv;
    }

    /**
     * @param plazaHv the plazaHv to set
     */
    public void setPlazaHv(int plazaHv) {
        this.plazaHv = plazaHv;
    }

    /**
     * @return the profesionalSalud
     */
    public String getProfesionalSalud() {
        return profesionalSalud;
    }

    /**
     * @param profesionalSalud the profesionalSalud to set
     */
    public void setProfesionalSalud(String profesionalSalud) {
        this.profesionalSalud = profesionalSalud;
    }

    /**
     * @return the personal
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefonoUno
     */
    public String getTelefonoUno() {
        return telefonoUno;
    }

    /**
     * @param telefonoUno the telefonoUno to set
     */
    public void setTelefonoUno(String telefonoUno) {
        this.telefonoUno = telefonoUno;
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

    public String getCodigo() {
        return String.valueOf(this.getIdMedico());
    }

    public void setCodigo(String codigo) {
        //do nothing, it is just to avoid json parsing conflicts
    }

    /**
     * Obtiene el nombre del prescriptor
     *
     * @return String el nombre del prescriptor en la forma Apellido Paterno
     * Apellido Materno Nombres
     */
    public String getNombrePrescriptor() {
        return String.format("%s %s %s", paterno, materno, nombre);

    }
    
    public void setNombrePrescriptor(String nombre) {
        //do nothing, it is just to avoid json parsing conflicts
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (int) (this.idMedico ^ (this.idMedico >>> 32));
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
        final Prescriptor other = (Prescriptor) obj;
        return this.idMedico == other.idMedico;
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
