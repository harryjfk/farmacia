package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ProductoSismedDao;
import pe.gob.minsa.farmacia.domain.ProductoSismed;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ProductoSismedService implements ServiceManager<ProductoSismed> {

    @Autowired
    ProductoSismedDao productoSismedDao;

    @Override
    public List<ProductoSismed> listar() {
        return productoSismedDao.listar();
    }

    public List<ProductoSismed> listarActivos() {

        List<ProductoSismed> listado = new ArrayList<ProductoSismed>();

        for (ProductoSismed p : productoSismedDao.listar()) {
            if (p.getActivo() == 1) {
                listado.add(p);
            }
        }

        return listado;
    }

    public List<ProductoSismed> listarActivos(int idProductoSismed) {

        List<ProductoSismed> listado = new ArrayList<ProductoSismed>();

        for (ProductoSismed p : productoSismedDao.listar()) {
            if (p.getActivo() == 1 || p.getIdProductoSismed() == idProductoSismed) {
                listado.add(p);
            }
        }

        return listado;
    }

    @Override
    public ProductoSismed obtenerPorId(int id) throws BusinessException {
        return productoSismedDao.obtenerPorId(id);
    }

    private void validateLocal(ProductoSismed productoSismed, List<String> errores) {
        if (productoSismed.getNombreProductoSismed().isEmpty()) {
            errores.add("El nombre de producto sismed es un campo requerido");
        }
    }

    @Override
    public void insertar(ProductoSismed productoSismed) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validateLocal(productoSismed, errores);

        productoSismed.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSismedDao.insertar(productoSismed);
        }
    }

    @Override
    public void actualizar(ProductoSismed productoSismed) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validateLocal(productoSismed, errores);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSismedDao.actualizar(productoSismed);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        ProductoSismed productoSismed = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (productoSismed.getActivo() == 1) {
                productoSismed.setActivo(0);
            } else {
                productoSismed.setActivo(1);
            }

            productoSismedDao.actualizar(productoSismed);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (productoSismedDao.esUsado(id)) {
            errores.add("No puede eliminar el producto sismed, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSismedDao.eliminar(id);
        }
    }
}
