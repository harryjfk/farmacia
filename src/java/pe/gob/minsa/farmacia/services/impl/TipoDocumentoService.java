package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.TipoDocumentoDao;
import pe.gob.minsa.farmacia.domain.TipoDocumento;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class TipoDocumentoService implements ServiceManager<TipoDocumento> {

    @Autowired
    TipoDocumentoDao tipoDocumentoDao;

    @Override
    public List<TipoDocumento> listar() {
        return tipoDocumentoDao.listar();
    }

    public List<TipoDocumento> listarActivos() {
        List<TipoDocumento> tiposDocumentos = new ArrayList<TipoDocumento>();

        for (TipoDocumento t : tipoDocumentoDao.listar()) {
            if (t.getActivo() == 1) {
                tiposDocumentos.add(t);
            }
        }

        return tiposDocumentos;
    }

    public List<TipoDocumento> listarActivos(int id) {
        List<TipoDocumento> tiposDocumentos = new ArrayList<TipoDocumento>();

        for (TipoDocumento t : tipoDocumentoDao.listar()) {
            if (t.getActivo() == 1 || t.getIdTipoDocumento() == id) {
                tiposDocumentos.add(t);
            }
        }

        return tiposDocumentos;
    }

    @Override
    public TipoDocumento obtenerPorId(int id) throws BusinessException {

        TipoDocumento tipoDocumento = tipoDocumentoDao.obtenerPorId(id);

        if (tipoDocumento == null) {
            throw new BusinessException(Arrays.asList("No se encontró el tipo de documento"));
        }

        return tipoDocumento;
    }
    
    private void validarLocal(TipoDocumento tipoDocumento, List<String> errores){
        if (tipoDocumento.getNombreTipoDocumento().isEmpty()) {
            errores.add("El nombre del tipo de documento es un campo requerido");
        }
    }

    @Override
    public void insertar(TipoDocumento tipoDocumento) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(tipoDocumento, errores);

        for (TipoDocumento t : tipoDocumentoDao.listar()) {
            if (tipoDocumento.getNombreTipoDocumento().equalsIgnoreCase(t.getNombreTipoDocumento())) {
                errores.add("Ya existe el tipo de documento " + tipoDocumento.getNombreTipoDocumento());
                break;
            }
        }

        tipoDocumento.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoDao.insertar(tipoDocumento);
        }
    }

    @Override
    public void actualizar(TipoDocumento tipoDocumento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(tipoDocumento, errores);

        for (TipoDocumento t : tipoDocumentoDao.listar()) {
            if (tipoDocumento.getNombreTipoDocumento().equalsIgnoreCase(t.getNombreTipoDocumento())
                    && tipoDocumento.getIdTipoDocumento() != t.getIdTipoDocumento()) {
                errores.add("Ya existe el tipo de documento " + tipoDocumento.getNombreTipoDocumento());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoDao.actualizar(tipoDocumento);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        TipoDocumento tipoDocumento = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (tipoDocumento.getActivo() == 1) {
                tipoDocumento.setActivo(0);
            } else {
                tipoDocumento.setActivo(1);
            }

            tipoDocumentoDao.actualizar(tipoDocumento);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        if(tipoDocumentoDao.esUsado(id)){
            errores.add("No puede eliminar el tipo de documento, está en uso");
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            tipoDocumentoDao.eliminar(id);
        }
    }

}
