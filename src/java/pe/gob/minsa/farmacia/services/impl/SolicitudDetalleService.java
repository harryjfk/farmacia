package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.SolicitudDetalleDao;
import pe.gob.minsa.farmacia.domain.BalanceSemestral;
import pe.gob.minsa.farmacia.domain.Historico;
import pe.gob.minsa.farmacia.domain.SolicitudDetalle;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class SolicitudDetalleService implements ServiceManager<SolicitudDetalle> {

    @Autowired
    SolicitudDetalleDao solicitudDetalleDao;

    public List<SolicitudDetalle> listar(int idSolicitud, int tipoMedicamento) {
        return solicitudDetalleDao.listar(idSolicitud,tipoMedicamento);
    }
    
    public List<BalanceSemestral> listarBalance(String fecha) {
        return solicitudDetalleDao.listarBalance(fecha);
    }
    
    public List<BalanceSemestral> listarConsultaMedicamentos() {
        return solicitudDetalleDao.listarConsultaMedicamentos();
    }
    
    public List<BalanceSemestral> listarConsultaNoAprobados() {
        return solicitudDetalleDao.listarConsultaNoAprobados();
    }
    
    public List<Historico> listarConsultaHistoricos(String fecIni, String fecFin) {
        return solicitudDetalleDao.listarConsultaHistoricos(fecIni,fecFin);
    }

    public SolicitudDetalle obtenerPorId(int id, int idSolicitud, int tipoMedicamento) throws BusinessException {

        SolicitudDetalle solicitud = solicitudDetalleDao.obtenerPorId(id,idSolicitud,tipoMedicamento);

        if (solicitud == null) {
            throw new BusinessException(Arrays.asList("No se encontró el detalle"));
        }

        return solicitud;
    }
    
    private void validarProducto(SolicitudDetalle solicitud, List<String> errores){
        if (solicitud.getIdProducto()==0) {
            errores.add("El producto es un campo requerido");
        }
    }

    public Integer insertarSolicitudDetalle(SolicitudDetalle solicitud) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarProducto(solicitud, errores);

        solicitud.setActivo(1);
        
        Integer id = 0;
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            id = solicitudDetalleDao.insertarSolicitudDetalle(solicitud);
        }
        
        return id;
    }

    @Override
    public void actualizar(SolicitudDetalle solicitud) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        //validarProducto(solicitud, errores);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            solicitudDetalleDao.actualizar(solicitud);
        }
    }
    
    public void aprobar(SolicitudDetalle solicitud) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        //validarProducto(solicitud, errores);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            solicitudDetalleDao.aprobar(solicitud);
        }
    }

    public void cambiarEstado(int id, int idSolicitud, int tipoMedicamento) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        SolicitudDetalle solicitud = obtenerPorId(id,idSolicitud,tipoMedicamento);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (solicitud.getActivo() == 1) {
                solicitud.setActivo(0);
            } else {
                solicitud.setActivo(1);
            }

            solicitudDetalleDao.actualizar(solicitud);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            solicitudDetalleDao.eliminar(id);
        }
    }

    @Override
    public List<SolicitudDetalle> listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SolicitudDetalle obtenerPorId(int id) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void insertar(SolicitudDetalle t) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
