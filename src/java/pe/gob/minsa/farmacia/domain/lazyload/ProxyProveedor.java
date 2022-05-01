package pe.gob.minsa.farmacia.domain.lazyload;

import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.dao.impl.DocumentoOrigenDao;
import pe.gob.minsa.farmacia.dao.impl.ProveedorDao;
import pe.gob.minsa.farmacia.domain.BaseDomain;
import pe.gob.minsa.farmacia.domain.Proveedor;

public class ProxyProveedor extends BaseDomain implements IProveedor {

    private boolean esCargado;
    private final Integer idProveedor;
    private final String razonSocial;
    private IProveedor proveedor;

    public ProxyProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
        this.razonSocial = null;
    }

    public ProxyProveedor(Integer idProveedor, String razonSocial) {
        this.idProveedor = idProveedor;
        this.razonSocial = razonSocial;
    }

    private void Load() {

        if (idProveedor == null) {
            proveedor = new Proveedor();
        } else {
            proveedor = StaticContextAccessor.getBean(ProveedorDao.class).obtenerPorId(idProveedor);
        }

        esCargado = true;
    }

    @Override
    public int getIdProveedor() {
        return this.idProveedor;
    }

    @Override
    public void setIdProveedor(int idProveedor) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setIdProveedor(idProveedor);
    }

    @Override
    public String getRuc() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getRuc();
    }

    @Override
    public void setRuc(String ruc) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setRuc(ruc);
    }

    @Override
    public String getRazonSocial() {
        if (this.razonSocial == null) {
            if (esCargado == false) {
                Load();
            }

            return proveedor.getRazonSocial();
        }

        return razonSocial;
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setRazonSocial(razonSocial);
    }

    @Override
    public String getDireccion() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getDireccion();
    }

    @Override
    public void setDireccion(String direccion) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setDireccion(direccion);
    }

    @Override
    public String getTelefono() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getTelefono();
    }

    @Override
    public void setTelefono(String telefono) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setTelefono(telefono);
    }

    @Override
    public String getContacto() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getContacto();
    }

    @Override
    public void setContacto(String contacto) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setContacto(contacto);
    }

    @Override
    public String getTelefonoContacto() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getTelefonoContacto();
    }

    @Override
    public void setTelefonoContacto(String telefonoContacto) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setTelefonoContacto(telefonoContacto);
    }

    @Override
    public String getFax() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getFax();
    }

    @Override
    public void setFax(String fax) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setFax(fax);
    }

    @Override
    public String getCorreo() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getCorreo();
    }

    @Override
    public void setCorreo(String correo) {
        if (esCargado == false) {
            Load();
        }
        proveedor.setCorreo(correo);
    }

    @Override
    public int getActivo() {
        if (esCargado == false) {
            Load();
        }
        return proveedor.getActivo();
    }

    @Override
    public void setActivo(int activo) {
        if (esCargado == false) {
            Load();
        }

        proveedor.setActivo(activo);
    }
    
    @Override
    public String getTipoProveedor(){
        return proveedor.getTipoProveedor();
    }
    
    @Override
    public void setTipoProveedor(String tipoProveedor){
        proveedor.setTipoProveedor(tipoProveedor);
    }
}
