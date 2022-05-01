package pe.gob.minsa.farmacia.domain.dto;

import java.math.BigDecimal;

public class InventarioProductoTotalDto {

    private int idProducto;
    private String descripcion;
    private String formaFarmaceutica;
    private int cantidad;
    private BigDecimal precio;
    private int conteo;
    private int cantidadFaltante;
    private int cantidadSobrante;
    private int cantidadAlterado;
    
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

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getConteo() {
        return conteo;
    }

    public void setConteo(int conteo) {
        this.conteo = conteo;
    }

    public int getCantidadFaltante() {
        return cantidadFaltante;
    }

    public void setCantidadFaltante(int cantidadFaltante) {
        this.cantidadFaltante = cantidadFaltante;
    }

    public int getCantidadSobrante() {
        return cantidadSobrante;
    }

    public void setCantidadSobrante(int cantidadSobrante) {
        this.cantidadSobrante = cantidadSobrante;
    }

    public int getCantidadAlterado() {
        return cantidadAlterado;
    }

    public void setCantidadAlterado(int cantidadAlterado) {
        this.cantidadAlterado = cantidadAlterado;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getTotal() {
        return precio.multiply(new BigDecimal(cantidad));
    }
    
    public BigDecimal getTotalFaltante() {
        return precio.multiply(new BigDecimal(cantidadFaltante));
    }

    public BigDecimal getTotalSobrante() {
        return precio.multiply(new BigDecimal(cantidadSobrante));
    }
    
    public BigDecimal getTotalFisico() {
        return precio.multiply(new BigDecimal(conteo));
    }
}
