package pe.gob.minsa.farmacia.domain.lazyload;

public interface IAlmacen extends IBaseDomain {
    
    public int getIdAlmacen();

    public void setIdAlmacen(int idAlmacen);    

    public Integer getIdAlmacenPadre();

    public void setIdAlmacenPadre(Integer idAlmacenPadre);

    public int getCantidadHijos();

    public void setCantidadHijos(int cantidadHijos);

    public int getIdTipoAlmacen();

    public void setIdTipoAlmacen(int idTipoAlmacen);    

    public String getDescripcion();

    public void setDescripcion(String descripcion);

    public String getAbreviatura();    

    public void setAbreviatura(String abreviatura);    

    public String getDireccion();

    public void setDireccion(String direccion);

    public String getFax();

    public void setFax(String fax);

    public String getTelefono();

    public void setTelefono(String telefono);
    
    public String getRuc();

    public void setRuc(String ruc);

    public String getIdUbigeo();    

    public void setIdUbigeo(String idUbigeo);    

    public String getResponsable();

    public void setResponsable(String responsable);
    
}