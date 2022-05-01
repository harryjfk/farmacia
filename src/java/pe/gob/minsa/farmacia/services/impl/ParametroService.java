package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ParametroDao;
import pe.gob.minsa.farmacia.domain.Parametro;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ParametroService {

    @Autowired
    ParametroDao parametroDao;

    public List<Parametro> listar() {
        return parametroDao.listar();
    }

    public List<Parametro> listarSinDependencia() {
        return parametroDao.listarSinDependencia();
    }

    public List<Parametro> listarDependencia() {
        return parametroDao.listarDependencia();
    }

    public Parametro obtenerPorId(int id) throws BusinessException {

        Parametro parametro = parametroDao.obtenerPorId(id);

        if (parametro == null) {
            throw new BusinessException(Arrays.asList("No se encontró el parámetro"));
        }

        return parametro;
    }

    public Parametro obtenerPorNombre(String nombreParametro) {
        return parametroDao.obtenerPorNombre(nombreParametro);
    }

    public void validarLocal(Parametro parametro, List<String> errores) {
        if (parametro.getNombreParametro().isEmpty()) {
            errores.add("El nombre del parámetro es un campo requerido");
        }

        if (parametro.getValor().isEmpty()) {
            errores.add("El valor es un campo requerido");
        }
    }

    public void insertar(Parametro parametro) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(parametro, errores);

        Parametro parametroTemp = parametroDao.obtenerPorNombre(parametro.getNombreParametro());
        if (parametroTemp != null) {
            errores.add("Ya existe este nombre de parámetro");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            parametroDao.insertar(parametro);
        }
    }

    public void actualizar(Parametro parametro) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(parametro, errores);

        if (parametro.getIdParametro() == 0) {
            errores.add("El Código del parámetro es requerido");
        }

        Parametro parametroTemp = parametroDao.obtenerPorNombre(parametro.getNombreParametro());
        if (parametroTemp != null) {
            if (parametroTemp.getIdParametro() != parametro.getIdParametro()) {
                errores.add("Ya existe este nombre de parámetro");
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            parametroDao.actualizar(parametro);
        }
    }
}
