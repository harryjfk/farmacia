package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.UnidadMedidaDao;
import pe.gob.minsa.farmacia.domain.UnidadMedida;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class UnidadMedidaService implements ServiceManager<UnidadMedida> {

    @Autowired
    UnidadMedidaDao unidadMedidaDao;

    @Override
    public List<UnidadMedida> listar() {
        return unidadMedidaDao.listar();
    }

    public List<UnidadMedida> listarActivos() {
        List<UnidadMedida> unidadesMedida = unidadMedidaDao.listar();

        for (int i = 0; i <= unidadesMedida.size() - 1; ++i) {
            if (unidadesMedida.get(i).getActivo() == 0) {
                unidadesMedida.remove(i);
                i = i - 1;
            }
        }

        return unidadesMedida;
    }
    
    public List<UnidadMedida> listarActivos(int idUnidadMedida) {
        List<UnidadMedida> unidadesMedida = unidadMedidaDao.listar();

        for (int i = 0; i <= unidadesMedida.size() - 1; ++i) {
            UnidadMedida unidadMedida = unidadesMedida.get(i);
            if (unidadMedida.getActivo() == 0
                    && unidadMedida.getIdUnidadMedida() != idUnidadMedida) {
                unidadesMedida.remove(i);
                i = i - 1;
            }
        }

        return unidadesMedida;
    }

    @Override
    public UnidadMedida obtenerPorId(int id) throws BusinessException {
         UnidadMedida unidadMedida = unidadMedidaDao.obtenerPorId(id);

        if (unidadMedida == null) {
            throw new BusinessException(Arrays.asList("No se encontró la unidad de medida"));
        }

        return unidadMedidaDao.obtenerPorId(id);
    }

    private void validarLocal(UnidadMedida unidadMedida, List<String> errores) {
        if (unidadMedida.getNombreUnidadMedida().isEmpty()) {
            errores.add("El nombre de la unidad de medida es un campo requerido");
        }
    }

    @Override
    public void insertar(UnidadMedida unidadMedida) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(unidadMedida, errores);

        for (UnidadMedida t : unidadMedidaDao.listar()) {
            if (unidadMedida.getNombreUnidadMedida().equalsIgnoreCase(t.getNombreUnidadMedida())) {
                errores.add("Ya existe la unidad de medida " + unidadMedida.getNombreUnidadMedida());
                break;
            }
        }

        unidadMedida.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            unidadMedidaDao.insertar(unidadMedida);
        }
    }

    @Override
    public void actualizar(UnidadMedida unidadMedida) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(unidadMedida, errores);

        for (UnidadMedida t : unidadMedidaDao.listar()) {
            if (unidadMedida.getNombreUnidadMedida().equalsIgnoreCase(t.getNombreUnidadMedida())
                    && unidadMedida.getIdUnidadMedida() != t.getIdUnidadMedida()) {
                errores.add("Ya existe la unidad de medida " + unidadMedida.getNombreUnidadMedida());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            unidadMedidaDao.actualizar(unidadMedida);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        UnidadMedida unidadMedida = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (unidadMedida.getActivo() == 1) {
                unidadMedida.setActivo(0);
            } else {
                unidadMedida.setActivo(1);
            }

            unidadMedidaDao.actualizar(unidadMedida);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            unidadMedidaDao.eliminar(id);
        }
    }

}
