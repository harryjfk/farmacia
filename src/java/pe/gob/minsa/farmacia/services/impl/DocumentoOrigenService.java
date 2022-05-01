package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.DocumentoOrigenDao;
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class DocumentoOrigenService implements ServiceManager<DocumentoOrigen> {

    @Autowired
    DocumentoOrigenDao documentoOrigenDao;

    @Override
    public List<DocumentoOrigen> listar() {
        return documentoOrigenDao.listar();
    }
    
    public List<DocumentoOrigen> listarActivos() {
        List<DocumentoOrigen> documentosOrigen = new ArrayList<DocumentoOrigen>();

        for (DocumentoOrigen t : documentoOrigenDao.listar()) {
            if (t.getActivo() == 1) {
                documentosOrigen.add(t);
            }
        }

        return documentosOrigen;
    }

    @Override
    public DocumentoOrigen obtenerPorId(int id) throws BusinessException {
        DocumentoOrigen documentoOrigen = documentoOrigenDao.obtenerPorId(id);
        
        if (documentoOrigen == null) {
            throw new BusinessException(Arrays.asList("No se encontró el documento origen"));
        }

        return documentoOrigen;
    }

    private void validarLocal(DocumentoOrigen documentoOrigen, List<String> errores) {
        if (documentoOrigen.getNombreDocumentoOrigen().isEmpty()) {
            errores.add("El nombre del documento origen es un campo requerido");
        }
    }

    @Override
    public void insertar(DocumentoOrigen documentoOrigen) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(documentoOrigen, errores);

        if (documentoOrigenDao.existe(documentoOrigen.getNombreDocumentoOrigen())) {
            errores.add("Ya existe el documento origen " + documentoOrigen.getNombreDocumentoOrigen());
        }

        documentoOrigen.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            documentoOrigenDao.insertar(documentoOrigen);
        }
    }

    @Override
    public void actualizar(DocumentoOrigen documentoOrigen) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(documentoOrigen, errores);

        if (documentoOrigenDao.existe(documentoOrigen.getNombreDocumentoOrigen(),
                documentoOrigen.getIdDocumentoOrigen())) {

            errores.add("Ya existe el documento origen " + documentoOrigen.getNombreDocumentoOrigen());
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            documentoOrigenDao.actualizar(documentoOrigen);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        DocumentoOrigen documentoOrigen = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (documentoOrigen.getActivo() == 1) {
                documentoOrigen.setActivo(0);
            } else {
                documentoOrigen.setActivo(1);
            }

            documentoOrigenDao.actualizar(documentoOrigen);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (documentoOrigenDao.esUsado(id)) {
            errores.add("No puede eliminar el documento origen, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            documentoOrigenDao.eliminar(id);
        }
    }
}
