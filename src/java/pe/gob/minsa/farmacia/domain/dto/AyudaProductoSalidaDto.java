package pe.gob.minsa.farmacia.domain.dto;

/**
 * Clase que permite la transferencia de datos para la ayuda de productos en las Nota de Ingreso con origen
 * y salidas.
 * @author Bryan Chauca
 */
public class AyudaProductoSalidaDto extends BaseAyudaMovimientoDto {
    
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }    
}
