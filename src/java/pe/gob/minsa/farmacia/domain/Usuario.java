package pe.gob.minsa.farmacia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends BaseDomain implements Serializable {
    
    private int idUsuario;
    private Personal personal;
    private String nombreUsuario;    
    private String clave;
    private String correo;
    private List<Perfil> perfiles;
    
    public Usuario(){        
        personal = new Personal();
        perfiles = new ArrayList<Perfil>();
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }    
    
    @JsonIgnore
    public String getClave() {
        return clave;
    }

    @JsonProperty("clave")
    public void setClave(String clave) {
        this.clave = clave;
    }   

    public List<Perfil> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }   

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
