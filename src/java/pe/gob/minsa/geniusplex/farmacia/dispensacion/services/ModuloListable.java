
package pe.gob.minsa.geniusplex.farmacia.dispensacion.services;

import java.util.List;

/**
 *
 * @author stark
 * @param <T>
 */
public interface ModuloListable<T> {
    
    public List<T> listarPorModulo(Object idModulo);
    
}
