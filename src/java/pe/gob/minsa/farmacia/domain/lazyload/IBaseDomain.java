package pe.gob.minsa.farmacia.domain.lazyload;

import java.sql.Timestamp;

public interface IBaseDomain {

    public int getUsuarioCreacion();

    public void setUsuarioCreacion(int usuarioCreacion);

    public Timestamp getFechaCreacion();

    public void setFechaCreacion(Timestamp fechaCreacion);

    public int getUsuarioModificacion();

    public void setUsuarioModificacion(int usuarioModificacion);

    public Timestamp getFechaModificacion();

    public void setFechaModificacion(Timestamp fechaModificacion);

    public int getActivo();

    public void setActivo(int activo);

    public String getActivoTexto();
}
