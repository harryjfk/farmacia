package pe.gob.minsa.farmacia.services;

import java.util.List;
import pe.gob.minsa.farmacia.util.BusinessException;

public interface NavegacionServiceManager<T> {

    public List<T> listar();
    
    public List<T> listarParaSession(int idUsuario);

    public List<T> listarActivos();

    public T obtenerPorId(int id) throws BusinessException;

    public void actualizar(T t) throws BusinessException;

    public void cambiarEstado(int id) throws BusinessException;

}
