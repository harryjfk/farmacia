package pe.gob.minsa.farmacia.services;

import java.util.List;
import pe.gob.minsa.farmacia.util.BusinessException;

public interface ServiceManager<T> {

    public List<T> listar();
    
    public T obtenerPorId(int id) throws BusinessException;
    
    public void insertar(T t) throws BusinessException;

    public void actualizar(T t) throws BusinessException;
    
    public void cambiarEstado(int id) throws BusinessException;

    public void eliminar(int id) throws BusinessException;

}
