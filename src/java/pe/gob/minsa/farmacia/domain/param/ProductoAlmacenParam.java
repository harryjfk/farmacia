package pe.gob.minsa.farmacia.domain.param;

public class ProductoAlmacenParam {
    
    private String descripcion;
    private int idFormaFarmaceutica;
    private int idTipoProducto;
    private int idUnidadMedida;
    private int idAlmacen;

    public ProductoAlmacenParam(){
        this.descripcion = "";
        this.idFormaFarmaceutica = 0;
        this.idTipoProducto = 0;
        this.idUnidadMedida = 0;
        this.idAlmacen = 0;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }    
}
