package pe.gob.minsa.farmacia.domain.lazyload;

import pe.gob.minsa.farmacia.dao.impl.TipoCompraDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.TipoCompra;

public class ProxyTipoCompra extends BaseDomain implements ITipoCompra {

    private boolean esCargado;
    private final Integer idTipoCompra;
    private ITipoCompra tipoCompra;

    public ProxyTipoCompra(Integer idTipoCompra) {
        this.idTipoCompra = idTipoCompra;
    }

    private void Load() {
        
        if(idTipoCompra == null){
            tipoCompra = new TipoCompra();
        }else{
            tipoCompra = StaticContextAccessor.getBean(TipoCompraDao.class).obtenerPorId(idTipoCompra);
        }
        
        esCargado = true;
    }

    @Override
    public int getIdTipoCompra() {
        return this.idTipoCompra;
    }

    @Override
    public void setIdTipoCompra(int idTipoCompra) {
        if (esCargado == false) {
            Load();
        }
        tipoCompra.setIdTipoCompra(idTipoCompra);
    }

    @Override
    public String getNombreTipoCompra() {
        if (esCargado == false) {
            Load();
        }

        return tipoCompra.getNombreTipoCompra();
    }

    @Override
    public void setNombreTipoCompra(String nombreTipoCompra) {
        if (esCargado == false) {
            Load();
        }

        tipoCompra.setNombreTipoCompra(nombreTipoCompra);
    }
    
    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return tipoCompra.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }
        
        tipoCompra.setActivo(activo);
    }
}
