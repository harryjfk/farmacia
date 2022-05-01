
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.List;

/**
 *
 * @author stark
 * @param <T>
 */
public interface GpPaginable<T> {
    
    public List<T> listarRango(Object idModulo, int[] range, String sSearch, Object[] sort);
    public int contarPaginado(Object idModulo, String sSearch);
    
}
