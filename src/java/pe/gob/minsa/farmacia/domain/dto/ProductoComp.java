package pe.gob.minsa.farmacia.domain.dto;

/**
 * Clase que permite la transferencia de datos para el listado de productos en el mantimiento de productos.
 * @author Bryan Chauca
 */

public class ProductoComp extends BaseEstado {

    private int idProducto;
    private String descripcion;
    private String presentacion;
    private String concentracion;
    private String formaFarmaceutica;
    private String tipoProducto;
    private String unidadMedida;
    
    /**/
    private String aprobado;
    private String condicionAprobado;
    private String motivoAprobado;
    private String cantidadAprobada;
    
    public String getAprobado() {
        return aprobado;
    }

    public void setAprobado(String aprobado) {
        this.aprobado = aprobado;
    }
    
    public String getCondicionAprobado() {
        return condicionAprobado;
    }

    public void setCondicionAprobado(String condicionAprobado) {
        this.condicionAprobado = condicionAprobado;
    }

    public String getMotivoAprobado() {
        return motivoAprobado;
    }

    public void setMotivoAprobado(String motivoAprobado) {
        this.motivoAprobado = motivoAprobado;
    }

    public String getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(String cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }
    /**/
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;        
    }    
}
