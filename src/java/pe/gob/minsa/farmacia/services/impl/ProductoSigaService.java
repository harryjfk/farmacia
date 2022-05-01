package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ProductoSigaDao;
import pe.gob.minsa.farmacia.domain.ProductoSiga;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ProductoSigaService implements ServiceManager<ProductoSiga> {

    @Autowired
    ProductoSigaDao productoSigaDao;

    @Override
    public List<ProductoSiga> listar() {
        return productoSigaDao.listar();
    }

    public List<ProductoSiga> listarActivos() {
        List<ProductoSiga> listado = productoSigaDao.listar();

        for (int i = 0; i < listado.size(); ++i) {
            if (listado.get(i).getActivo() == 0) {
                listado.remove(i);
                i = i - 1;
            }
        }

        return listado;
    }

    public List<ProductoSiga> listarActivos(int idProductoSiga) {
        List<ProductoSiga> listado = productoSigaDao.listar();

        for (int i = 0; i < listado.size(); ++i) {
            ProductoSiga productoSiga = listado.get(i);
            if (productoSiga.getActivo() == 0
                    && productoSiga.getIdProductoSiga() != idProductoSiga) {
                listado.remove(i);
                i = i - 1;
            }
        }

        return listado;
    }

    @Override
    public ProductoSiga obtenerPorId(int id) throws BusinessException {
        ProductoSiga productoSiga = productoSigaDao.obtenerPorId(id);

        if (productoSiga == null) {
            throw new BusinessException(Arrays.asList("No se encontró el perfil"));
        }

        return productoSiga;
    }

    private void validateLocal(ProductoSiga productoSiga, List<String> errores) {
        if (productoSiga.getNombreProductoSiga().isEmpty()) {
            errores.add("El nombre de producto siga es un campo requerido");
        }
    }

    @Override
    public void insertar(ProductoSiga productoSiga) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validateLocal(productoSiga, errores);

        productoSiga.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSigaDao.insertar(productoSiga);
        }
    }

    @Override
    public void actualizar(ProductoSiga productoSiga) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validateLocal(productoSiga, errores);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSigaDao.actualizar(productoSiga);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        ProductoSiga productoSiga = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (productoSiga.getActivo() == 1) {
                productoSiga.setActivo(0);
            } else {
                productoSiga.setActivo(1);
            }

            productoSigaDao.actualizar(productoSiga);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (productoSigaDao.esUsado(id)) {
            errores.add("No puede eliminar el producto siga, está en uso");
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            productoSigaDao.eliminar(id);
        }
    }

}
