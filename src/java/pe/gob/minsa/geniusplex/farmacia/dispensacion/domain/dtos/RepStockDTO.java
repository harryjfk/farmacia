/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos;

import java.util.List;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;

/**
 *
 * @author armando
 */
public class RepStockDTO {
    private List<InventarioProductoTotalDto> invs;

    public RepStockDTO(List<InventarioProductoTotalDto> invs) {
        this.invs = invs;
    }

    /**
     * @return the invs
     */
    public List<InventarioProductoTotalDto> getInvs() {
        return invs;
    }

    /**
     * @param invs the invs to set
     */
    public void setInvs(List<InventarioProductoTotalDto> invs) {
        this.invs = invs;
    }
    
    
}
