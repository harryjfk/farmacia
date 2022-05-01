package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Proveedor;

public class ProveedorDao implements DaoManager<Proveedor> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Proveedor> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Proveedor> proveedors = jdbcTemplate.query("{call Far_Proveedor_Listar}", new ProveedorRowMapper());

        return proveedors;
    }
    
    public List<Proveedor> listarPorTipo(String tipo) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Proveedor> proveedors = jdbcTemplate.query("{call Far_Proveedor_ListarPorTipo(?)}",new Object[]{tipo}, new ProveedorRowMapper());

        
        return proveedors;
    }

    @Override
    public Proveedor obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Proveedor proveedor = jdbcTemplate.queryForObject("{call Far_Proveedor_ListarPorId(?)}", new Object[]{id}, new ProveedorRowMapper());

        return proveedor;
    }
    
    public Proveedor obtenerPorRuc(String ruc) {
        Proveedor proveedor = null;
    
        try {
        
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);        
            proveedor = jdbcTemplate.queryForObject("{call Far_Proveedor_ListarPorRUC(?)}", new Object[]{ruc}, new ProveedorRowMapper());
        
        } catch (EmptyResultDataAccessException ex) {
        }
        return proveedor;
    }

    @Override
    public void insertar(Proveedor proveedor) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Proveedor_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@Ruc", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RazonSocial", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Direccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Telefono", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Contacto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TelefonoContacto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fax", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Correo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoProveedor", java.sql.Types.CHAR));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdProveedor", java.sql.Types.INTEGER));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@Ruc", proveedor.getRuc());
        maps.addValue("@RazonSocial", proveedor.getRazonSocial());
        maps.addValue("@Direccion", proveedor.getDireccion());
        maps.addValue("@Telefono", proveedor.getTelefono());
        maps.addValue("@Contacto", proveedor.getContacto());
        maps.addValue("@TelefonoContacto", proveedor.getTelefonoContacto());
        maps.addValue("@Fax", proveedor.getFax());
        maps.addValue("@Correo", proveedor.getCorreo());
        maps.addValue("@Activo", proveedor.getActivo());
        maps.addValue("@UsuarioCreacion", proveedor.getUsuarioCreacion());
        maps.addValue("@TipoProveedor", proveedor.getTipoProveedor());
        
        Map<String, Object> res = jdbcCall.execute(maps);
        int idProveedor = (Integer) res.get("@IdProveedor");
        proveedor.setIdProveedor(idProveedor);
    }

    @Override
    public void actualizar(Proveedor proveedor) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Proveedor_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProveedor", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Ruc", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@RazonSocial", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Direccion", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Telefono", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Contacto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TelefonoContacto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Fax", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Correo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoProveedor", java.sql.Types.CHAR));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProveedor", proveedor.getIdProveedor());
        maps.addValue("@Ruc", proveedor.getRuc());
        maps.addValue("@RazonSocial", proveedor.getRazonSocial());
        maps.addValue("@Direccion", proveedor.getDireccion());
        maps.addValue("@Telefono", proveedor.getTelefono());
        maps.addValue("@Contacto", proveedor.getContacto());
        maps.addValue("@TelefonoContacto", proveedor.getTelefonoContacto());        
        maps.addValue("@Fax", proveedor.getFax());
        maps.addValue("@Correo", proveedor.getCorreo());
        maps.addValue("@Activo", proveedor.getActivo());        
        maps.addValue("@UsuarioModificacion", proveedor.getUsuarioModificacion());    
        maps.addValue("@TipoProveedor", proveedor.getTipoProveedor());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Proveedor_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProveedor", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProveedor", id);

        jdbcCall.execute(maps);
    }

    public class ProveedorRowMapper implements RowMapper<Proveedor> {

        @Override
        public Proveedor mapRow(ResultSet rs, int i) throws SQLException {
            Proveedor proveedor = new Proveedor();

            proveedor.setIdProveedor(rs.getInt("IdProveedor"));
            proveedor.setRuc(rs.getString("Ruc"));
            proveedor.setRazonSocial(rs.getString("RazonSocial"));
            proveedor.setDireccion(rs.getString("Direccion"));
            proveedor.setTelefono(rs.getString("Telefono"));
            proveedor.setContacto(rs.getString("Contacto"));
            proveedor.setTelefonoContacto(rs.getString("TelefonoContacto"));
            proveedor.setFax(rs.getString("Fax"));
            proveedor.setCorreo(rs.getString("Correo"));
            proveedor.setActivo(rs.getInt("Activo"));
            proveedor.setTipoProveedor(rs.getString("TipoProveedor"));

            return proveedor;
        }
    }
}
