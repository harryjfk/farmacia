package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.IAlmacen;

public class Almacen extends BaseDomain implements Serializable, IAlmacen {

    private int idAlmacen;
    private Integer idAlmacenPadre;
    private int cantidadHijos;
    private int idTipoAlmacen;
    private String descripcion;
    private String abreviatura;
    private String direccion;
    private String fax;
    private String telefono;
    private String ruc;
    private String idUbigeo;
    private String responsable;
    private String codigoAlmacen;
    private int farmacia;

    @Override
    public int getIdAlmacen() {
        return idAlmacen;
    }

    @Override
    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    @Override
    public Integer getIdAlmacenPadre() {
        return idAlmacenPadre;
    }

    @Override
    public void setIdAlmacenPadre(Integer idAlmacenPadre) {
        this.idAlmacenPadre = idAlmacenPadre;
    }

    @Override
    public int getCantidadHijos() {
        return cantidadHijos;
    }

    @Override
    public void setCantidadHijos(int cantidadHijos) {
        this.cantidadHijos = cantidadHijos;
    }

    @Override
    public int getIdTipoAlmacen() {
        return idTipoAlmacen;
    }

    @Override
    public void setIdTipoAlmacen(int idTipoAlmacen) {
        this.idTipoAlmacen = idTipoAlmacen;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getAbreviatura() {
        return abreviatura;
    }

    @Override
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @Override
    public String getDireccion() {
        return direccion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String getFax() {
        return fax;
    }

    @Override
    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getRuc() {
        return ruc;
    }

    @Override
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public String getIdUbigeo() {
        return idUbigeo;
    }

    @Override
    public void setIdUbigeo(String idUbigeo) {
        this.idUbigeo = idUbigeo;
    }

    @Override
    public String getResponsable() {
        return responsable;
    }

    @Override
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(int farmacia) {
        this.farmacia = farmacia;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }
}
