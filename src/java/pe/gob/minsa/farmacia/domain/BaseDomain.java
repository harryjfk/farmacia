package pe.gob.minsa.farmacia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import pe.gob.minsa.farmacia.domain.lazyload.IBaseDomain;

public abstract class BaseDomain implements IBaseDomain{

    @JsonIgnore
    private int usuarioCreacion;
    @JsonIgnore
    private Timestamp fechaCreacion;
    @JsonIgnore
    private int usuarioModificacion;
    @JsonIgnore
    private Timestamp fechaModificacion;
    
    private int activo;

    @Override
    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    @Override
    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    @Override
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public int getUsuarioModificacion() {
        return usuarioModificacion;
    }

    @Override
    public void setUsuarioModificacion(int usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    @Override
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    @Override
    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Override
    public int getActivo() {
        return activo;
    }

    @Override
    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String getActivoTexto() {
        if (this.activo == 1) {
            return "Activo";
        } else {
            return "Inactivo";
        }
    }
}
