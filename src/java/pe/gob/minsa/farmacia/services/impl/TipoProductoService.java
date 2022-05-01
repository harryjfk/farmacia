package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoProductoDao;
import pe.gob.minsa.farmacia.domain.TipoProducto;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoProductoService implements ServiceManager<TipoProducto> {

    @Autowired
    TipoProductoDao tipoProductoDao;

    @Override
    public List<TipoProducto> listar() {
        return tipoProductoDao.listar();
    }

    public List<TipoProducto> listarActivos() {
        List<TipoProducto> tiposProducto = tipoProductoDao.listar();

        for (int i = 0; i <= tiposProducto.size() - 1; ++i) {
            if (tiposProducto.get(i).getActivo() == 0) {
                tiposProducto.remove(i);
                i = i - 1;
            }
        }

        return tiposProducto;
    }
    
    public List<TipoProducto> listarActivos(int idTipoProducto) {
        List<TipoProducto> tiposProducto = tipoProductoDao.listar();

        for (int i = 0; i <= tiposProducto.size() - 1; ++i) {
            TipoProducto tipoProducto = tiposProducto.get(i);
            
            if (tipoProducto.getActivo() == 0
                    && tipoProducto.getIdTipoProducto() != idTipoProducto) {
                tiposProducto.remove(i);
                i = i - 1;
            }
        }

        return tiposProducto;
    }

    @Override
    public TipoProducto obtenerPorId(int id) throws BusinessException {
        TipoProducto tipoProducto = tipoProductoDao.obtenerPorId(id);

        if (tipoProducto == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de producto"));
        }

        return tipoProducto;
    }

    private void validarLocal(TipoProducto tipoProducto, List<String> errores) {
        if (tipoProducto.getNombreTipoProducto().isEmpty()) {
            errores.add("El nombre del tipo de producto es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoProducto tipoProducto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoProducto, errores);

        for (TipoProducto t : tipoProductoDao.listar()) {
            if (tipoProducto.getNombreTipoProducto().equalsIgnoreCase(t.getNombreTipoProducto())) {
                errores.add("Ya existe el tipo de producto " + tipoProducto.getNombreTipoProducto());
                break;
            }
        }

        tipoProducto.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProductoDao.insertar(tipoProducto);
        }
    }

    @Override
    public void actualizar(TipoProducto tipoProducto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoProducto, errores);

        for (TipoProducto t : tipoProductoDao.listar()) {
            if (tipoProducto.getNombreTipoProducto().equalsIgnoreCase(t.getNombreTipoProducto())
                    && tipoProducto.getIdTipoProducto() != t.getIdTipoProducto()) {
                errores.add("Ya existe  el tipo de producto " + tipoProducto.getNombreTipoProducto());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProductoDao.actualizar(tipoProducto);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoProducto tipoProducto = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoProducto.getActivo() == 1) {
                tipoProducto.setActivo(0);
            } else {
                tipoProducto.setActivo(1);
            }

            tipoProductoDao.actualizar(tipoProducto);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoProductoDao.eliminar(id);
        }
    }
}
