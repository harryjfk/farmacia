package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoDocumentoMovDao;
import pe.gob.minsa.farmacia.domain.TipoDocumentoMov;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoDocumentoMovService implements ServiceManager<TipoDocumentoMov> {

    @Autowired
    TipoDocumentoMovDao tipoDocumentoMovDao;

    @Override
    public List<TipoDocumentoMov> listar() {
        return tipoDocumentoMovDao.listar();
    }

    public List<TipoDocumentoMov> listarActivos() {
        List<TipoDocumentoMov> tiposDocumentoMov = new ArrayList<TipoDocumentoMov>();

        for (TipoDocumentoMov t : tipoDocumentoMovDao.listar()) {
            if (t.getActivo() == 1) {
                tiposDocumentoMov.add(t);
            }
        }

        return tiposDocumentoMov;
    }

    public List<TipoDocumentoMov> listarActivos(int id) {
        List<TipoDocumentoMov> tiposDocumentoMov = new ArrayList<TipoDocumentoMov>();

        for (TipoDocumentoMov t : tipoDocumentoMovDao.listar()) {
            if (t.getActivo() == 1 || t.getIdTipoDocumentoMov() == id) {
                tiposDocumentoMov.add(t);
            }
        }

        return tiposDocumentoMov;
    }

    @Override
    public TipoDocumentoMov obtenerPorId(int id) throws BusinessException {
        TipoDocumentoMov tipoDocumentoMov = tipoDocumentoMovDao.obtenerPorId(id);

        if (tipoDocumentoMov == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de documento de movimiento"));
        }

        return tipoDocumentoMov;
    }

    private void validarLocal(TipoDocumentoMov tipoDocumentoMov, List<String> errores) {
        if (tipoDocumentoMov.getNombreTipoDocumentoMov().isEmpty()) {
            errores.add("El nombre del tipo de documento es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoDocumentoMov tipoDocumentoMov) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoDocumentoMov, errores);

        for (TipoDocumentoMov t : tipoDocumentoMovDao.listar()) {
            if (tipoDocumentoMov.getNombreTipoDocumentoMov().equalsIgnoreCase(t.getNombreTipoDocumentoMov())) {
                errores.add("Ya existe el tipo de documento " + tipoDocumentoMov.getNombreTipoDocumentoMov());
                break;
            }
        }

        tipoDocumentoMov.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoMovDao.insertar(tipoDocumentoMov);
        }
    }

    @Override
    public void actualizar(TipoDocumentoMov tipoDocumentoMov) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoDocumentoMov, errores);

        for (TipoDocumentoMov t : tipoDocumentoMovDao.listar()) {
            if (tipoDocumentoMov.getNombreTipoDocumentoMov().equalsIgnoreCase(t.getNombreTipoDocumentoMov())
                    && tipoDocumentoMov.getIdTipoDocumentoMov() != t.getIdTipoDocumentoMov()) {
                errores.add("Ya existe el tipo de documento " + tipoDocumentoMov.getNombreTipoDocumentoMov());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoMovDao.actualizar(tipoDocumentoMov);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoDocumentoMov tipoDocumentoMov = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoDocumentoMov.getActivo() == 1) {
                tipoDocumentoMov.setActivo(0);
            } else {
                tipoDocumentoMov.setActivo(1);
            }

            tipoDocumentoMovDao.actualizar(tipoDocumentoMov);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoMovDao.eliminar(id);
        }
    }

}
