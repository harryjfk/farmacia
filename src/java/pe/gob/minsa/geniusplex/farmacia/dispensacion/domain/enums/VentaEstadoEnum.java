/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums;

/**
 *
 * @author armando
 */
public enum VentaEstadoEnum {

    /**
     * Todavia no ha sida pagada
     */
    X_COBRAR,
    /**
     * Ya ha sido pagada
     */
    CANCELADA,
    /**
     * Al contado, pagada en caja
     */
    PAGADA_EN_CAJA;

    @Override
    public String toString() {
        if (this.name().equals("X_COBRAR")) {
            return "X COBRAR";
        }
        if (this.name().equals("PAGADA_EN_CAJA")) {
            return "PAGADA EN CAJA";
        }
        return this.name();
    }
}
