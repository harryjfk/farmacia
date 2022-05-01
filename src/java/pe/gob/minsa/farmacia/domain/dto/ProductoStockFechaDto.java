package pe.gob.minsa.farmacia.domain.dto;

public class ProductoStockFechaDto {
    
    private int idProducto;
    private String codigoSismed;
    private String descripcion;
    private String tipoProducto;
    private String formaFarmaceutica;
    private int stock;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoSismed() {        
        return codigoSismed;
    }

    public void setCodigoSismed(String codigoSismed) {
        this.codigoSismed = codigoSismed;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

   
}
