package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ProveedorDao;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.HelperValidate;
import pe.gob.minsa.farmacia.util.UtilMail;

public class ProveedorService implements ServiceManager<Proveedor> {

    @Autowired
    ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> listar() {
        return proveedorDao.listar();
    }
    
    public List<Proveedor> listarPorTipo(String tipo){
        return proveedorDao.listarPorTipo(tipo);
    }

    @Override
    public Proveedor obtenerPorId(int id) throws BusinessException {
        Proveedor proveedor = proveedorDao.obtenerPorId(id);

        if (proveedor == null) {
            throw new BusinessException(Arrays.asList("No se encontró el proveedor"));
        }

        return proveedor;
    }
    
    public Proveedor obtenerPorRuc(String ruc) {
        return proveedorDao.obtenerPorRuc(ruc);
    }

    private void validarLocal(Proveedor proveedor, List<String> errores) {
        if (proveedor.getRazonSocial().isEmpty()) {
            errores.add("La razón social es un campo requerido");
        }

        if (proveedor.getRuc().isEmpty()) {
            errores.add("El ruc es un campo requerido");
        } else {
            if(HelperValidate.IsRucValidate(proveedor.getRuc()) == false){
                errores.add("El ruc debe contener sólo 11 dígitos numéricos");
            }
        }

        if (proveedor.getCorreo() != null && proveedor.getCorreo().isEmpty() == false) {
            if (UtilMail.isMailValid(proveedor.getCorreo()) == false) {
                errores.add("Debe ingresar un correo válido");
            }
        }
    }

    @Override
    public void insertar(Proveedor proveedor) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(proveedor, errores);

        for (Proveedor t : proveedorDao.listar()) {
            if (proveedor.getRuc().equalsIgnoreCase(t.getRuc())) {
                errores.add("Ya está registrado el ruc " + proveedor.getRuc());
            }

            if (proveedor.getRazonSocial().trim().equalsIgnoreCase(t.getRazonSocial().trim())) {
                errores.add("Ya está registrado la razón social " + proveedor.getRazonSocial());
            }
        }

        proveedor.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            proveedorDao.insertar(proveedor);
        }
    }

    @Override
    public void actualizar(Proveedor proveedor) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(proveedor, errores);

        for (Proveedor t : proveedorDao.listar()) {
            if (proveedor.getRuc().equalsIgnoreCase(t.getRuc())
                    && proveedor.getIdProveedor() != t.getIdProveedor()) {
                errores.add("Ya está registrado el ruc " + proveedor.getRuc());
            }

            if (proveedor.getRazonSocial().trim().equalsIgnoreCase(t.getRazonSocial().trim())
                    && proveedor.getIdProveedor() != t.getIdProveedor()) {
                errores.add("Ya está registrado la razón social " + proveedor.getRazonSocial());
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            proveedorDao.actualizar(proveedor);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Proveedor proveedor = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (proveedor.getActivo() == 1) {
                proveedor.setActivo(0);
            } else {
                proveedor.setActivo(1);
            }

            proveedorDao.actualizar(proveedor);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            proveedorDao.eliminar(id);
        }
    }
}
