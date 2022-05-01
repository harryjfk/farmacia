package pe.gob.minsa.farmacia.domain.lazyload;

public interface IProveedor extends IBaseDomain {

    public int getIdProveedor();

    public void setIdProveedor(int idProveedor);

    public String getRuc();

    public void setRuc(String ruc);

    public String getRazonSocial();

    public void setRazonSocial(String razonSocial);

    public String getDireccion();

    public void setDireccion(String direccion);

    public String getTelefono();

    public void setTelefono(String telefono);

    public String getContacto();

    public void setContacto(String contacto);

    public String getTelefonoContacto();

    public void setTelefonoContacto(String telefonoContacto);

    public String getFax();

    public void setFax(String fax);

    public String getCorreo();

    public void setCorreo(String correo);
    
    public String getTipoProveedor();
    
    public void setTipoProveedor(String tipoProveedor);
}
