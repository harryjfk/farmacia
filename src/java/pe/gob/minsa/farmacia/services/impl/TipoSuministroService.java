package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoSuministroDao;
import pe.gob.minsa.farmacia.domain.TipoSuministro;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoSuministroService implements ServiceManager<TipoSuministro>  {

    @Autowired
    TipoSuministroDao tipoSuministroDao;
    
    @Override
    public List<TipoSuministro> listar() {
        return tipoSuministroDao.listar();
    }

    @Override
    public TipoSuministro obtenerPorId(int id) throws BusinessException {
        TipoSuministro tipoSuministro = tipoSuministroDao.obtenerPorId(id);
        
        if(tipoSuministro == null){
            throw new BusinessException(Arrays.asList("No encontro el tipo de suministro."));
        }
        return tipoSuministro;
    }

    private void validarLocal(TipoSuministro tipoSuministro, List<String> errores) {
        if (tipoSuministro.getDescripcion().isEmpty()) {
            errores.add("El nombre del tipo de suministro es un campo requerido");
        }
    }
    @Override
    public void insertar(TipoSuministro tipoSuministro) throws BusinessException {
        List<String> errores = new ArrayList<String>() ;
        
        validarLocal(tipoSuministro, errores);
        if(tipoSuministroDao.existe(tipoSuministro.getDescripcion())){
            errores.add("Ya existe el tipo de suministro " + tipoSuministro.getDescripcion());
        }
        
        tipoSuministro.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoSuministroDao.insertar(tipoSuministro);
        }
    }

    @Override
    public void actualizar(TipoSuministro tipoSuministro) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoSuministro, errores);

        if (tipoSuministroDao.existe(tipoSuministro.getDescripcion(),
                tipoSuministro.getIdTipoSuministro())) {

            errores.add("Ya existe el tipo de suministro " + tipoSuministro.getDescripcion());
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoSuministroDao.actualizar(tipoSuministro);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoSuministro tipoSuministro = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoSuministro.getActivo() == 1) {
                tipoSuministro.setActivo(0);
            } else {
                tipoSuministro.setActivo(1);
            }

            tipoSuministroDao.actualizar(tipoSuministro);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (tipoSuministroDao.esUsado(id)) {
            errores.add("No puede eliminar el tipo de suministro, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoSuministroDao.eliminar(id);
        }
    }
    
}
