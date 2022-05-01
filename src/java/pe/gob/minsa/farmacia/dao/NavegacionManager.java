package pe.gob.minsa.farmacia.dao;

import java.util.List;


public interface NavegacionManager<T> {
     
    public List<T> listar();
    
    public List<T> listarParaSession(int idUsuario);
    
    public void cambiarOrden(int id, boolean subida);
    
    public T obtenerPorId(int id);
     
    public void actualizar(T t);
    
}
