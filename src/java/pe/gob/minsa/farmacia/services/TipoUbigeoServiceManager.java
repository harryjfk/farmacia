package pe.gob.minsa.farmacia.services;

import java.util.List;
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.util.BusinessException;

public interface TipoUbigeoServiceManager {

    List<Ubigeo> listar();

    List<Ubigeo> listarActivos();

    List<Ubigeo> listarActivos(String id);

    List<Ubigeo> listarDepartamentos();

    List<Ubigeo> listarPronvincias(String idDepartamento);

    List<Ubigeo> listarDistritos(String idProvincia);

    Ubigeo obtenerPorId(String id) throws BusinessException;

    void insertar(Ubigeo tipoUbigeo) throws BusinessException;

    void actualizar(Ubigeo tipoUbigeo) throws BusinessException;

    void cambiarEstado(String id) throws BusinessException;

    void eliminar(String id) throws BusinessException;
}
