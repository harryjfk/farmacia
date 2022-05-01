package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.AlmacenDao;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.dto.AlmacenTree;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.HelperValidate;

public class AlmacenService implements ServiceManager<Almacen> {

    @Autowired
    AlmacenDao almacenDao;

    @Override
    public List<Almacen> listar() {
        return almacenDao.listar();
    }
    
    public List<Almacen> listarPadres() {
        return almacenDao.listarPadres();
    }
    
    public int obtenerIdAlmacenLogistica(){
        return almacenDao.obtenerIdAlmacenLogistica();
    }
    
    public int obtenerIdAlmacenEspecializado(){
        return almacenDao.obtenerIdAlmacenEspecializado();
    }
    
    public List<Almacen> listarPadresActivos() {
        List<Almacen> almacenes = new ArrayList<Almacen>();
        
        for(Almacen a : almacenDao.listarPadres()){
            if(a.getActivo() == 1){
                almacenes.add(a);
            }
        }
   
        return almacenes;
    }
    
    public List<Almacen> listarPorPadre(int idAlmacenPadre) {
        return almacenDao.listarPorPadre(idAlmacenPadre);
    }

    @Override
    public Almacen obtenerPorId(int id) throws BusinessException {
        Almacen almacen = almacenDao.obtenerPorId(id);
        
        if (almacen == null) {
            throw new BusinessException(Arrays.asList("No se encontró el almacén"));
        }
        
        return almacen;
    }
    
    public List<AlmacenTree> cargarTree(){
        return almacenDao.cargarTree();
    }

    private void validarLocal(Almacen almacen, List<String> errores) {

        if (almacen.getIdTipoAlmacen() == 0) {
            errores.add("El tipo de almacén es un campo requerido");
        }

        if (almacen.getDescripcion().isEmpty()) {
            errores.add("La descripción de almacén es un campo requerido");
        }
        
        if(almacen.getRuc().isEmpty() == false){
            if(HelperValidate.IsRucValidate(almacen.getRuc()) == false){
                errores.add("El ruc debe contener 11 dígitos numéricos");
            }
        }else{
            almacen.setRuc(null);
        }
        
        if(almacen.getCodigoAlmacen().isEmpty()){
            errores.add("El código de almacén es un campo requerido");
        }
    }

    @Override
    public void insertar(Almacen almacen) throws BusinessException {

        List<String> errores = new ArrayList<String>();

        validarLocal(almacen, errores);

        for (Almacen t : almacenDao.listar()) {
            if (almacen.getDescripcion().equalsIgnoreCase(t.getDescripcion())) {
                errores.add("Ya existe el almacén " + almacen.getDescripcion());
                break;
            }
        }
        
        if(almacen.getCodigoAlmacen().isEmpty() == false){
             if(almacenDao.existeCodigo(almacen.getCodigoAlmacen(), 0)){
                errores.add("Ya existe el código del almacén " + almacen.getCodigoAlmacen());
            }
        }
        
        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            
            Almacen almacenSismed = new Almacen();
            almacenSismed.setCodigoAlmacen(almacen.getCodigoAlmacen() + "01");
            almacenSismed.setDescripcion("ALM SISMED");
            almacenSismed.setAbreviatura("SISMED");
            almacenSismed.setIdTipoAlmacen(10);            
            almacenSismed.setIdUbigeo(null);
            almacenSismed.setUsuarioCreacion(almacen.getUsuarioCreacion());
            almacenSismed.setActivo(1);

            Almacen almacenDonaciones = new Almacen();
            almacenDonaciones.setCodigoAlmacen(almacen.getCodigoAlmacen() + "02");
            almacenDonaciones.setDescripcion("ALM DONACIONES");
            almacenDonaciones.setAbreviatura("DONACIONES");
            almacenDonaciones.setIdTipoAlmacen(10);            
            almacenDonaciones.setIdUbigeo(null);
            almacenDonaciones.setUsuarioCreacion(almacen.getUsuarioCreacion());
            almacenDonaciones.setActivo(1);
            
            almacen.setActivo(1);
            almacenDao.insertar(almacen);
            almacenSismed.setIdAlmacenPadre(almacen.getIdAlmacen());
            almacenDonaciones.setIdAlmacenPadre(almacen.getIdAlmacen());
            
            almacenDao.insertar(almacenSismed);
            almacenDao.insertar(almacenDonaciones);
        }
    }

    @Override
    public void actualizar(Almacen almacen) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        validarLocal(almacen, errores);

        for (Almacen t : almacenDao.listar()) {
            if (almacen.getDescripcion().equalsIgnoreCase(t.getDescripcion())
                    && almacen.getIdAlmacen() != t.getIdAlmacen()) {
                errores.add("Ya existe el almacén " + almacen.getDescripcion());
                break;
            }
        }
        
        if(almacen.getCodigoAlmacen().isEmpty() == false){
            if(almacenDao.existeCodigo(almacen.getCodigoAlmacen(), almacen.getIdAlmacen())){
                errores.add("Ya existe el código del almacén " + almacen.getCodigoAlmacen());
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            almacenDao.actualizar(almacen);
            almacenDao.actualizarVirtualEstado(almacen.getIdAlmacen(), almacen.getActivo(), almacen.getUsuarioModificacion());
        }
    }

    @Override    
    public void cambiarEstado(int id) throws BusinessException {
        
    }
    
    public void cambiarEstado(int id, int usuarioModificacion) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Almacen almacen = obtenerPorId(id);
        almacen.setUsuarioModificacion(usuarioModificacion);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (almacen.getActivo() == 1) {
                almacen.setActivo(0);
            } else {
                almacen.setActivo(1);
            }

            almacenDao.actualizar(almacen);
            almacenDao.actualizarVirtualEstado(almacen.getIdAlmacen(), almacen.getActivo(), almacen.getUsuarioModificacion());                 
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();
        
        if(almacenDao.esUsado(id)){
            errores.add("No puede eliminar el almacén, se encuentra en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            almacenDao.eliminar(id);
        }
    }
}
