package pe.gob.minsa.farmacia.domain.dto;

public class PerfilOpcionConfiguracion {

    private int idPerfilOpcion;
    private int idOpcion;
    private String nombreOpcion;
    private int activo;

    public int getIdPerfilOpcion() {
        return idPerfilOpcion;
    }

    public void setIdPerfilOpcion(int idPerfilOpcion) {
        this.idPerfilOpcion = idPerfilOpcion;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }      

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
    
}
