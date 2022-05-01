package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.SolicitudDao;
import pe.gob.minsa.farmacia.domain.Solicitud;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class SolicitudService implements ServiceManager<Solicitud> {

    @Autowired
    SolicitudDao solicitudDao;

    @Override
    public List<Solicitud> listar() {
        return solicitudDao.listar();
    }

    public List<Solicitud> listarActivos() {
        List<Solicitud> solicitudes = new ArrayList<Solicitud>();

        for (Solicitud t : solicitudDao.listar()) {
            if (t.getActivo() == 1) {
                solicitudes.add(t);
            }
        }

        return solicitudes;
    }

    public List<Solicitud> listarActivos(int id) {
        List<Solicitud> solicitudes = new ArrayList<Solicitud>();

        for (Solicitud t : solicitudDao.listar()) {
            if (t.getActivo() == 1 || t.getIdSolicitud() == id) {
                solicitudes.add(t);
            }
        }

        return solicitudes;
    }

    @Override
    public Solicitud obtenerPorId(int id) throws BusinessException {

        Solicitud solicitud = solicitudDao.obtenerPorId(id);

        if (solicitud == null) {
            throw new BusinessException(Arrays.asList("No se encontró la solicitud"));
        }

        return solicitud;
    }
    
    private void validarLocal(Solicitud solicitud, List<String> errores){
        if (solicitud.getIdMedico().isEmpty()) {
            errores.add("El médico es un campo requerido");
        }
        if (solicitud.getInstitucion().isEmpty()) {
            errores.add("La institución es un campo requerido");
        }
        if (solicitud.getEstablecimiento().isEmpty()) {
            errores.add("El establecimiento es un campo requerido");
        }
    }

    public Integer insertarSolicitud(Solicitud solicitud) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(solicitud, errores);

        /*for (Solicitud t : solicitudDao.listar()) {
            if (solicitud.getNombreTipoDocumento().equalsIgnoreCase(t.getNombreTipoDocumento())) {
                errores.add("Ya existe el tipo de documento " + tipoDocumento.getNombreTipoDocumento());
                break;
            }
        }*/

        solicitud.setActivo(1);
        
        Integer id = 0;
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            id = solicitudDao.insertarSolicitud(solicitud);
        }
        
        return id;
    }

    @Override
    public void actualizar(Solicitud solicitud) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(solicitud, errores);

        /*for (Solicitud t : solicitudDao.listar()) {
            if (solicitud.getNombreTipoDocumento().equalsIgnoreCase(t.getNombreTipoDocumento())
                    && solicitud.getIdTipoDocumento() != t.getIdTipoDocumento()) {
                errores.add("Ya existe el tipo de documento " + solicitud.getNombreTipoDocumento());
                break;
            }
        }*/

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            solicitudDao.actualizar(solicitud);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Solicitud solicitud = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (solicitud.getActivo() == 1) {
                solicitud.setActivo(0);
            } else {
                solicitud.setActivo(1);
            }

            solicitudDao.actualizar(solicitud);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();
                
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            solicitudDao.eliminar(id);
        }
    }

    @Override
    public void insertar(Solicitud t) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
