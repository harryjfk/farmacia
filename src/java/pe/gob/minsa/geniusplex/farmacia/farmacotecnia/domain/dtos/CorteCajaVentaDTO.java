package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos;

import java.math.BigDecimal;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.FVenta;

/**
 *
 * @author armando
 */
public class CorteCajaVentaDTO {
    private final FVenta venta;

    public CorteCajaVentaDTO(FVenta venta) {
        this.venta = venta;
    }
    
    public BigDecimal getTotalVenta() {
        return  venta.getSubTotalPreventa().add(venta.getRedondeoPreventa());
    }
    
    public FVenta getVenta() {
        return venta;
    }
}
