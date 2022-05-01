package pe.gob.minsa.farmacia.domain.lazyload;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.AlmacenDao;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.BaseDomain;

public class ProxyAlmacen extends BaseDomain implements IAlmacen {

    private boolean esCargado;
    private final Integer idAlmacen;
    private final String descripcion;
    private IAlmacen almacen;
    
    public ProxyAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
        this.descripcion = null;        
    }

    public ProxyAlmacen(Integer idAlmacen, String descripcion) {
        this.idAlmacen = idAlmacen;
        this.descripcion = descripcion;
               
    }
    
    private void Load() {        
        if (idAlmacen == null) {
            almacen = new Almacen();
        } else {            
            almacen = StaticContextAccessor.getBean(AlmacenDao.class).obtenerPorId(idAlmacen);            
        }
        esCargado = true;
    }

    @Override
    public int getIdAlmacen() {
        return this.idAlmacen;
    }

    @Override
    public void setIdAlmacen(int idAlmacen) {
        if (esCargado == false) {
            Load();
        }
        almacen.setIdAlmacen(idAlmacen);
    }

    @Override
    public Integer getIdAlmacenPadre() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getIdAlmacenPadre();
    }

    @Override
    public void setIdAlmacenPadre(Integer idAlmacenPadre) {
        if (esCargado == false) {
            Load();
        }
        almacen.setIdAlmacenPadre(idAlmacenPadre);
    }

    @Override
    public int getCantidadHijos() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getCantidadHijos();
    }

    @Override
    public void setCantidadHijos(int cantidadHijos) {
        if (esCargado == false) {
            Load();
        }
        almacen.setCantidadHijos(cantidadHijos);
    }

    @Override
    public int getIdTipoAlmacen() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getIdTipoAlmacen();
    }

    @Override
    public void setIdTipoAlmacen(int idTipoAlmacen) {
        if (esCargado == false) {
            Load();
        }
        almacen.setIdTipoAlmacen(idTipoAlmacen);
    }

    @Override
    public String getDescripcion() {
        if (this.descripcion == null) {
            if (esCargado == false) {
                Load();
            }

            return almacen.getDescripcion();
        }

        return this.descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        if (esCargado == false) {
            Load();
        }
        almacen.setDescripcion(descripcion);
    }

    @Override
    public String getAbreviatura() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getAbreviatura();
    }

    @Override
    public void setAbreviatura(String abreviatura) {
        if (esCargado == false) {
            Load();
        }
        almacen.setAbreviatura(abreviatura);
    }

    @Override
    public String getDireccion() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getDireccion();
    }

    @Override
    public void setDireccion(String direccion) {
        if (esCargado == false) {
            Load();
        }
        almacen.setDireccion(direccion);
    }

    @Override
    public String getFax() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getFax();
    }

    @Override
    public void setFax(String fax) {
        if (esCargado == false) {
            Load();
        }
        almacen.setFax(fax);
    }

    @Override
    public String getTelefono() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getTelefono();
    }

    @Override
    public void setTelefono(String telefono) {
        if (esCargado == false) {
            Load();
        }
        almacen.setTelefono(telefono);
    }

    @Override
    public String getRuc() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getRuc();
    }

    @Override
    public void setRuc(String ruc) {
        if (esCargado == false) {
            Load();
        }
        almacen.setRuc(ruc);
    }

    @Override
    public String getIdUbigeo() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getIdUbigeo();
    }

    @Override
    public void setIdUbigeo(String idUbigeo) {
        if (esCargado == false) {
            Load();
        }
        almacen.setIdUbigeo(idUbigeo);
    }

    @Override
    public String getResponsable() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getResponsable();
    }

    @Override
    public void setResponsable(String responsable) {
        if (esCargado == false) {
            Load();
        }
        almacen.setResponsable(responsable);
    }

    @Override    
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return almacen.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }

        almacen.setActivo(activo);
    }
}
