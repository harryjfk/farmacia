package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoAlmacenDao;
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoAlmacenService implements ServiceManager<TipoAlmacen>{
    
    
    @Autowired
    TipoAlmacenDao tipoAlmacenDao;

    @Override
    public List<TipoAlmacen> listar() {
        return tipoAlmacenDao.listar();
    }
    
    public List<TipoAlmacen> listarActivos() {
        List<TipoAlmacen> tiposAlmacenes = new ArrayList<TipoAlmacen>();

        for (TipoAlmacen t : tipoAlmacenDao.listar()) {
            if (t.getActivo() == 1) {
                tiposAlmacenes.add(t);
            }
        }

        return tiposAlmacenes;
    }
    
    public List<TipoAlmacen> listarActivos(int id) {
        List<TipoAlmacen> tiposAlmacenes = new ArrayList<TipoAlmacen>();

        for (TipoAlmacen t : tipoAlmacenDao.listar()) {
            if (t.getActivo() == 1 || t.getIdTipoAlmacen()== id) {
                tiposAlmacenes.add(t);
            }
        }

        return tiposAlmacenes;
    }

    @Override
    public TipoAlmacen obtenerPorId(int id) throws BusinessException {
        TipoAlmacen tipoAlmacen = tipoAlmacenDao.obtenerPorId(id);

        if (tipoAlmacen == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de almacén"));
        }

        return tipoAlmacen;
    }
    
    private void validarLocal(TipoAlmacen tipoAlmacen, List<String> errores){        
        if (tipoAlmacen.getNombreTipoAlmacen().isEmpty()) {
            errores.add("El nombre del tipo de almacen es un campo requerido");
        }        
    }

    @Override
    public void insertar(TipoAlmacen tipoAlmacen) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoAlmacen, errores);

        for (TipoAlmacen t : tipoAlmacenDao.listar()) {
            if (tipoAlmacen.getNombreTipoAlmacen().equalsIgnoreCase(t.getNombreTipoAlmacen())) {
                errores.add("Ya existe el tipo de almacen " + tipoAlmacen.getNombreTipoAlmacen());
                break;
            }
        }

        tipoAlmacen.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAlmacenDao.insertar(tipoAlmacen);
        }
    }

    @Override
    public void actualizar(TipoAlmacen tipoAlmacen) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoAlmacen, errores);
        
        if(tipoAlmacen.getIdTipoAlmacen() == 10 && tipoAlmacen.getActivo() == 0) {
            errores.add("No puede desactivar el tipo de almacén virtual, es usado por el sistema");
        }

        for (TipoAlmacen t : tipoAlmacenDao.listar()) {
            if (tipoAlmacen.getNombreTipoAlmacen().equalsIgnoreCase(t.getNombreTipoAlmacen())
                    && tipoAlmacen.getIdTipoAlmacen()!= t.getIdTipoAlmacen()) {
                errores.add("Ya existe el tipo de almacen " + tipoAlmacen.getNombreTipoAlmacen());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAlmacenDao.actualizar(tipoAlmacen);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoAlmacen tipoAlmacen = obtenerPorId(id);

        if(id == 10) {
            errores.add("No puede desactivar el tipo de almacén virtual, es usado por el sistema");
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoAlmacen.getActivo() == 1) {
                tipoAlmacen.setActivo(0);
            } else {
                tipoAlmacen.setActivo(1);
            }

            tipoAlmacenDao.actualizar(tipoAlmacen);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        if(id == 10) {
            errores.add("No puede desactivar el tipo de almacén virtual, es usado por el sistema");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoAlmacenDao.eliminar(id);
        }
    }
    
}
