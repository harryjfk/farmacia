package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoAccionDao;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoAccionService implements ServiceManager<TipoAccion> {

    @Autowired
    TipoAccionDao tipoAccionDao;

    @Override
    public List<TipoAccion> listar() {
        return tipoAccionDao.listar();
    }

    public List<TipoAccion> listarActivos() {
        List<TipoAccion> tiposAcciones = new ArrayList<TipoAccion>();

        for (TipoAccion t : tipoAccionDao.listar()) {
            if (t.getActivo() == 1) {
                tiposAcciones.add(t);
            }
        }

        return tiposAcciones;
    }

    public List<TipoAccion> listarActivos(int id) {
        List<TipoAccion> tiposAcciones = new ArrayList<TipoAccion>();

        for (TipoAccion t : tipoAccionDao.listar()) {
            if (t.getActivo() == 1 || t.getIdTipoAccion() == id) {
                tiposAcciones.add(t);
            }
        }

        return tiposAcciones;
    }

    @Override
    public TipoAccion obtenerPorId(int id) throws BusinessException {

        TipoAccion tipoAccion = tipoAccionDao.obtenerPorId(id);

        if (tipoAccion == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de acción"));
        }

        return tipoAccion;
    }

    private void validarLocal(TipoAccion tipoAccion, List<String> errores) {
        if (tipoAccion.getNombreTipoAccion().isEmpty()) {
            errores.add("El nombre del tipo de acción es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoAccion tipoAccion) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(tipoAccion, errores);
        
        for (TipoAccion t : tipoAccionDao.listar()) {
            if (tipoAccion.getNombreTipoAccion().equalsIgnoreCase(t.getNombreTipoAccion())) {
                errores.add("Ya existe el tipo de acción " + tipoAccion.getNombreTipoAccion());
                break;
            }
        }

        tipoAccion.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAccionDao.insertar(tipoAccion);
        }
    }

    @Override
    public void actualizar(TipoAccion tipoAccion) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(tipoAccion, errores);
        
        for (TipoAccion t : tipoAccionDao.listar()) {
            if (tipoAccion.getNombreTipoAccion().equalsIgnoreCase(t.getNombreTipoAccion())
                    && tipoAccion.getIdTipoAccion() != t.getIdTipoAccion()) {
                errores.add("Ya existe el tipo de acción " + tipoAccion.getNombreTipoAccion());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAccionDao.actualizar(tipoAccion);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        TipoAccion tipoAccion = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoAccion.getActivo() == 1) {
                tipoAccion.setActivo(0);
            } else {
                tipoAccion.setActivo(1);
            }

            tipoAccionDao.actualizar(tipoAccion);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        if (tipoAccionDao.esUsado(id)) {
            errores.add("No puede eliminar el tipo de acción, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAccionDao.eliminar(id);
        }
    }

}
