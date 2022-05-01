package pe.gob.minsa.farmacia.dao;

import java.util.List;
import pe.gob.minsa.farmacia.domain.Ubigeo;

public interface TipoUbigeoDaoManager {
    List<Ubigeo> listar();
    
    List<Ubigeo> listarDepartamentos();
    
    List<Ubigeo> listarPronvincias(String idDepartamento);
    
    List<Ubigeo> listarDistritos(String idProvincia);
    
    Ubigeo obtenerPorId(String id);
    
    void insertar(Ubigeo tipoUbigeo);

    void actualizar(Ubigeo tipoUbigeo);    

    void eliminar(String id);
}
