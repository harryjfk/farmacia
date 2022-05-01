package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ConceptoDao;
import pe.gob.minsa.farmacia.dao.impl.TipoCompraDao;
import pe.gob.minsa.farmacia.domain.TipoCompra;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoCompraService implements ServiceManager<TipoCompra> {

    @Autowired
    TipoCompraDao tipoCompraDao;

    @Override
    public List<TipoCompra> listar() {
        return tipoCompraDao.listar();
    }

    public List<TipoCompra> listarActivos() {
        List<TipoCompra> tiposCompras = new ArrayList<TipoCompra>();

        for (TipoCompra t : tipoCompraDao.listar()) {
            if (t.getActivo() == 1) {
                tiposCompras.add(t);
            }
        }

        return tiposCompras;
    }

    public List<TipoCompra> listarActivos(int id) {
        List<TipoCompra> tiposCompras = new ArrayList<TipoCompra>();

        for (TipoCompra t : tipoCompraDao.listar()) {
            if (t.getActivo() == 1 || t.getIdTipoCompra() == id) {
                tiposCompras.add(t);
            }
        }

        return tiposCompras;
    }

    @Override
    public TipoCompra obtenerPorId(int id) throws BusinessException {
        TipoCompra tipoCompra = tipoCompraDao.obtenerPorId(id);

        if (tipoCompra == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de compra"));
        }

        return tipoCompra;
    }

    private void validarLocal(TipoCompra tipoCompra, List<String> errores) {
        if (tipoCompra.getNombreTipoCompra().isEmpty()) {
            errores.add("El nombre del tipo de compra es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoCompra tipoCompra) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoCompra, errores);

        for (TipoCompra t : tipoCompraDao.listar()) {
            if (tipoCompra.getNombreTipoCompra().equalsIgnoreCase(t.getNombreTipoCompra())) {
                errores.add("Ya existe el tipo de compra " + tipoCompra.getNombreTipoCompra());
                break;
            }
        }

        tipoCompra.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoCompraDao.insertar(tipoCompra);
        }
    }

    @Override
    public void actualizar(TipoCompra tipoCompra) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoCompra, errores);

        for (TipoCompra t : tipoCompraDao.listar()) {
            if (tipoCompra.getNombreTipoCompra().equalsIgnoreCase(t.getNombreTipoCompra())
                    && tipoCompra.getIdTipoCompra() != t.getIdTipoCompra()) {
                errores.add("Ya existe el tipo de compra " + tipoCompra.getNombreTipoCompra());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoCompraDao.actualizar(tipoCompra);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoCompra tipoCompra = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoCompra.getActivo() == 1) {
                tipoCompra.setActivo(0);
            } else {
                tipoCompra.setActivo(1);
            }

            tipoCompraDao.actualizar(tipoCompra);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoCompraDao.eliminar(id);
        }
    }
}
