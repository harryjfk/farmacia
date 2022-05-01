package pe.gob.minsa.farmacia.domain.dto;

/**
 * Clase abstracta para campos básicos de ayuda en movimientos
 *
 * @author Bryan Chauca
 */
public abstract class BaseAyudaMovimientoDto {

    private int idProducto;
    private String codigoSismed;
    private String producto;
    private String formaFarmaceutica;

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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }
}
