package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoProcesoDao;
import pe.gob.minsa.farmacia.domain.TipoProceso;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoProcesoService implements ServiceManager<TipoProceso> {

    @Autowired
    TipoProcesoDao tipoProcesoDao;

    @Override
    public List<TipoProceso> listar() {
        return tipoProcesoDao.listar();
    }
    
    public List<TipoProceso> listarActivos() {
        List<TipoProceso> tiposProceso = new ArrayList<TipoProceso>();

        for (TipoProceso t : tipoProcesoDao.listar()) {
            if (t.getActivo() == 1) {
                tiposProceso.add(t);
            }
        }

        return tiposProceso;
    }

    @Override
    public TipoProceso obtenerPorId(int id) throws BusinessException {
        TipoProceso tipoProceso = tipoProcesoDao.obtenerPorId(id);

        if (tipoProceso == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de proceso"));
        }

        return tipoProceso;
    }

    private void validarLocal(TipoProceso tipoProceso, List<String> errores) {
        if (tipoProceso.getNombreTipoProceso().isEmpty()) {
            errores.add("El nombre del tipo de proceso es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoProceso tipoProceso) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoProceso, errores);

        if (tipoProcesoDao.existe(tipoProceso.getNombreTipoProceso())) {
            errores.add("Ya existe el tipo de proceso " + tipoProceso.getNombreTipoProceso());
        }

        tipoProceso.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProcesoDao.insertar(tipoProceso);
        }
    }

    @Override
    public void actualizar(TipoProceso tipoProceso) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoProceso, errores);

        if (tipoProcesoDao.existe(tipoProceso.getNombreTipoProceso(),
                tipoProceso.getIdTipoProceso())) {

            errores.add("Ya existe el tipo de proceso " + tipoProceso.getNombreTipoProceso());
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProcesoDao.actualizar(tipoProceso);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoProceso tipoProceso = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoProceso.getActivo() == 1) {
                tipoProceso.setActivo(0);
            } else {
                tipoProceso.setActivo(1);
            }

            tipoProcesoDao.actualizar(tipoProceso);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (tipoProcesoDao.esUsado(id)) {
            errores.add("No puede eliminar el tipo de proceso, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProcesoDao.eliminar(id);
        }
    }
}