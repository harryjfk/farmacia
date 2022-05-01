package pe.gob.minsa.farmacia.domain.lazyload;

import pe.gob.minsa.farmacia.dao.impl.ConceptoDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.TipoMovimientoConcepto;

public class ProxyConcepto extends BaseDomain implements IConcepto {

    private boolean esCargado;
    private final int idConcepto;
    private final String nombreConcepto;
    private IConcepto concepto;

    public ProxyConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
        this.nombreConcepto = null;
    }
    
    public ProxyConcepto(int idConcepto, String nombreConcepto) {
        this.idConcepto = idConcepto;
        this.nombreConcepto = nombreConcepto;
    }

    private void Load() {
        concepto = StaticContextAccessor.getBean(ConceptoDao.class).obtenerPorId(idConcepto);
        esCargado = true;
    }

    @Override
    public int getIdConcepto() {
        return this.idConcepto;
    }

    @Override
    public void setIdConcepto(int idConcepto) {
        if (esCargado == false) {
            Load();
        }
        concepto.setIdConcepto(idConcepto);
    }

    @Override
    public String getNombreConcepto() {
        
        if(this.nombreConcepto == null){
            if (esCargado == false) {
                Load();
            }
            
            return concepto.getNombreConcepto();
        }
        
        return this.nombreConcepto;
    }

    @Override
    public void setNombreConcepto(String nombreConcepto) {
        if (esCargado == false) {
            Load();
        }

        concepto.setNombreConcepto(nombreConcepto);
    }
    
    @Override
    public TipoMovimientoConcepto getTipoMovimientoConcepto() {
        if (esCargado == false) {
            Load();
        }
        return concepto.getTipoMovimientoConcepto();
    }

    @Override
    public void setTipoMovimientoConcepto(TipoMovimientoConcepto tipoMovimientoConcepto) {
        if (esCargado == false) {
            Load();
        }
        concepto.setTipoMovimientoConcepto(tipoMovimientoConcepto);
    }
    
    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return concepto.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }
        
        concepto.setActivo(activo);
    }
}