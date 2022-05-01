package pe.gob.minsa.farmacia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public class Personal implements Serializable {
    
    private String idPersonal;
    @JsonIgnore
    private String nombre;
    @JsonIgnore
    private String apellidoPaterno;
    @JsonIgnore
    private String apellidoMaterno;
    private String tipoDocumento;
    private String nroDocumento;    
    private String unidad;    
    private String cargo;
    
    private String colegiatura;

    public String getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(String idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNombreCompleto(){
        return this.nombre + " " + this.apellidoPaterno + " " + this.apellidoMaterno;        
    }
    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }    

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }    

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public String getColegiatura() {
        return colegiatura;
    }

    public void setColegiatura(String colegiatura) {
        this.colegiatura = colegiatura;
    }      
    
}
