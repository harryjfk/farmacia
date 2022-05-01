/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * Representa un Vendedor
 */
@Entity
@Table(name = "Far_Vendedor")
public class Vendedor extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IdCajero")
    private long idVendedor;
    @Column(name = "Nombres")
    private String nombre;
    @Column(name = "TipoPersonal")
    private String tipoPersonal;
    @Column(name = "Paterno")
    private String paterno;
    @Column(name = "Materno")
    private String materno;
    @Column(name = "Personal")
    private String personal;
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "Telefono1")
    private String telefonoUno;
    @Column(name = "TipoCajero", length = 1)
    private String tipo;
    @Column(name = "IdModulo")
    private long IdModulo;
    @JsonIgnore
    @ManyToMany(mappedBy = "vendedores")
    private List<Turno> turnos;
    
    @Transient
    private String username;
    @Transient
    private String password;

    public Vendedor() {
    }

    /**
     * @return the idVendedor
     */
    public long getIdVendedor() {
        return idVendedor;
    }

    /**
     * @param idVendedor the idVendedor to set
     */
    public void setIdVendedor(long idVendedor) {
        this.idVendedor = idVendedor;
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
     * @return the tipoPersonal
     */
    public String getTipoPersonal() {
        return tipoPersonal;
    }

    /**
     * @param tipoPersonal the tipoPersonal to set
     */
    public void setTipoPersonal(String tipoPersonal) {
        this.tipoPersonal = tipoPersonal;
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
     * @return the turnos
     */
    public List<Turno> getTurnos() {
        return turnos;
    }

    /**
     * @param turnos the turnos to set
     */
    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
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
        return String.valueOf(this.getIdVendedor());
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (this.idVendedor ^ (this.idVendedor >>> 32));
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
        final Vendedor other = (Vendedor) obj;
        return this.idVendedor == other.idVendedor;
    }

    /**
     * Obtiene el nombre del prescriptor
     *
     * @return String el nombre del prescriptor en la forma Apellido Paterno
     * Apellido Materno Nombres
     */
    public String getNombreVendedor() {
        return String.format("%s %s %s", paterno, materno, nombre);

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
    
    @Override
    public String toString() {
        return String.format("%s %s %s", getNombre(), getPaterno(), getMaterno());
    }
    
}
