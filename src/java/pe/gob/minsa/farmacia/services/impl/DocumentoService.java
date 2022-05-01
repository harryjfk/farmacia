package pe.gob.minsa.farmacia.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.DocumentoDao;
import pe.gob.minsa.farmacia.domain.Documento;
import pe.gob.minsa.farmacia.domain.dto.DocumentoComp;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class DocumentoService implements ServiceManager<Documento> {

    @Autowired
    DocumentoDao documentoDao;

    @Override
    public List<Documento> listar() {
        return documentoDao.listar();
    }

    public List<DocumentoComp> listarComp(int idUsuario) {
        return documentoDao.listarComp(idUsuario);
    }

    @Override
    public Documento obtenerPorId(int id) throws BusinessException {
        Documento documento = documentoDao.obtenerPorId(id);

        if (documento == null) {
            throw new BusinessException(Arrays.asList("No se encontró el documento"));
        }

        return documento;
    }

    @Override
    public void insertar(Documento documento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        documento.setDespacho(0);
        documento.setFechaDespacho(null);
        documento.setActivo(1);
        documento.setFechaCreacion(
                new Timestamp(new GregorianCalendar().getTimeInMillis())
        );

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            documentoDao.insertar(documento);
        }
    }

    @Override
    public void actualizar(Documento documento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        documento.setFechaModificacion(
                new Timestamp(new GregorianCalendar().getTimeInMillis())
        );
        
        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            documentoDao.actualizar(documento);
        }
    }

    public void actualizarDespacho(int id, int despachado) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        Documento documento = obtenerPorId(id);
        
        if(despachado == 1){
            documento.setDespacho(1);            
            documento.setFechaDespacho(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        }else{
            documento.setDespacho(0);
            documento.setFechaDespacho(null);
        }

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            documentoDao.actualizar(documento);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Documento documento = obtenerPorId(id);

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {

            if (documento.getActivo() == 1) {
                documento.setActivo(0);
            } else {
                documento.setActivo(1);
            }

            documentoDao.actualizar(documento);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.size() > 0) {
            throw new BusinessException(errores);
        } else {
            documentoDao.eliminar(id);
        }
    }

}
