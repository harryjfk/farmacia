package pe.gob.minsa.farmacia.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.ConceptoDao;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.TipoMovimientoConcepto;
import pe.gob.minsa.farmacia.services.ServiceManager;
import pe.gob.minsa.farmacia.util.BusinessException;

public class ConceptoService implements ServiceManager<Concepto> {

    @Autowired
    ConceptoDao conceptoDao;
    
    @Override
    public List<Concepto> listar() {
        return conceptoDao.listar();
    }
  
    public List<Concepto> listarActivos() {
        List<Concepto> conceptos = new ArrayList<Concepto>();

        for (Concepto t : conceptoDao.listar()) {
            if (t.getActivo() == 1) {
                conceptos.add(t);
            }
        }

        return conceptos;
    }
    
    public List<Concepto> listarActivos(TipoMovimientoConcepto tipoMovimientoConcepto) {
        List<Concepto> conceptos = new ArrayList<Concepto>();

        for (Concepto t : conceptoDao.listar()) {
            if (t.getActivo() == 1 &&
                    (t.getTipoMovimientoConcepto() == tipoMovimientoConcepto
                    || t.getTipoMovimientoConcepto() ==TipoMovimientoConcepto.TODOS)) {
                conceptos.add(t);
            }
        }

        return conceptos;
    }

    public List<Concepto> listarActivos(int id) {
        List<Concepto> conceptos = new ArrayList<Concepto>();

        for (Concepto t : conceptoDao.listar()) {
            if (t.getActivo() == 1 || t.getIdConcepto() == id) {
                conceptos.add(t);
            }
        }

        return conceptos;
    }

    @Override
    public Concepto obtenerPorId(int id) throws BusinessException {
        Concepto concepto = conceptoDao.obtenerPorId(id);

        if (concepto == null) {
            throw new BusinessException(Arrays.asList("No se encontró el concepto"));
        }

        return concepto;
    }

    private void validarLocal(Concepto concepto, List<String> errores) {
        if (concepto.getNombreConcepto().isEmpty()) {
            errores.add("El nombre del tipo de concepto es un campo requerido");
        }
        if(concepto.getTipoMovimientoConcepto() != TipoMovimientoConcepto.INGRESO){
            if(concepto.getTipoPrecio() == null){
                errores.add("El tipo de precio es un campo requerido para notas de salida");
            }
        }
        if(concepto.getTipoMovimientoConcepto() == TipoMovimientoConcepto.INGRESO){
            concepto.setTipoPrecio(null);
        }
    }

    @Override
    public void insertar(Concepto concepto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(concepto, errores);

        for (Concepto t : conceptoDao.listar()) {
            if (concepto.getNombreConcepto().equalsIgnoreCase(t.getNombreConcepto())) {
                errores.add("Ya existe el tipo de concepto " + concepto.getNombreConcepto());
                break;
            }
        }        

        concepto.setActivo(1);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            conceptoDao.insertar(concepto);
        }
    }

    @Override
    public void actualizar(Concepto concepto) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        validarLocal(concepto, errores);

        for (Concepto t : conceptoDao.listar()) {
            if (concepto.getNombreConcepto().equalsIgnoreCase(t.getNombreConcepto())
                    && concepto.getIdConcepto() != t.getIdConcepto()) {
                errores.add("Ya existe el tipo de concepto " + concepto.getNombreConcepto());
                break;
            }
        }

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            conceptoDao.actualizar(concepto);
        }
    }

    @Override
    public void cambiarEstado(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        Concepto concepto = obtenerPorId(id);

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {

            if (concepto.getActivo() == 1) {
                concepto.setActivo(0);
            } else {
                concepto.setActivo(1);
            }

            conceptoDao.actualizar(concepto);
        }
    }

    @Override
    public void eliminar(int id) throws BusinessException {
        List<String> errores = new ArrayList<String>();

        if (errores.isEmpty() == false) {
            throw new BusinessException(errores);
        } else {
            conceptoDao.eliminar(id);
        }
    }
}
