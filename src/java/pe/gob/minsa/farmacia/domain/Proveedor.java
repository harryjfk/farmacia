package pe.gob.minsa.farmacia.domain;

import java.io.Serializable;
import pe.gob.minsa.farmacia.domain.lazyload.IProveedor;

public class Proveedor  extends BaseDomain implements Serializable, IProveedor {

    private int idProveedor;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String contacto;
    private String telefonoContacto;
    private String fax;
    private String correo;
    private String tipoProveedor;

    @Override
    public String getTipoProveedor() {
        return tipoProveedor;
    }
    
    @Override
    public void setTipoProveedor(String tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    @Override
    public int getIdProveedor() {
        return idProveedor;
    }

    @Override
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
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
    public String getRazonSocial() {
        return razonSocial;
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getContacto() {
        return contacto;
    }

    @Override
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Override
    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    @Override
    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
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
    public String getCorreo() {
        return correo;
    }

    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
