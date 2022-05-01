/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.math.BigDecimal;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;

/**
 *
 * @author armando
 */
public class CorteCajaVentaDTO {
    private final Venta venta;

    public CorteCajaVentaDTO(Venta venta) {
        this.venta = venta;
    }
    
    public BigDecimal getTotalVenta() {
        return  venta.getSubTotalPreventa().add(venta.getRedondeoPreventa());
    }
    
    public Venta getVenta() {
        return venta;
    }
}
