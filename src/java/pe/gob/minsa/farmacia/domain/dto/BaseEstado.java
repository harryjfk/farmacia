package pe.gob.minsa.farmacia.domain.dto;

public abstract class BaseEstado {

    private int activo;

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getActivoTexto() {
        if (this.activo == 1) {
            return "Activo";
        } else {
            return "Inactivo";
        }
    }
}
