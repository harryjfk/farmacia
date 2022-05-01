package pe.gob.minsa.farmacia.dao;

import java.util.List;

public interface DaoManager<T> {

    public List<T> listar();
    
    public T obtenerPorId(int id);
    
    public void insertar(T t);

    public void actualizar(T t);    

    public void eliminar(int id);

}