package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.ProductoSiga;

public class ProductoSigaDao implements DaoManager<ProductoSiga> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<ProductoSiga> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoSiga> productosSiga = jdbcTemplate.query("{call Far_Producto_Siga_Listar}", new ProductoSigaRowMapper());

        return productosSiga;
    }

    @Override
    public ProductoSiga obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            ProductoSiga productoSiga = jdbcTemplate.queryForObject("{call Far_Producto_Siga_PorId(?)}", new Object[]{id}, new ProductoSigaRowMapper());
            return productoSiga;
        } catch (EmptyResultDataAccessException ex) {
            return new ProductoSiga();
        }
    }

    @Override
    public void insertar(ProductoSiga productoSiga) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Siga_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoSiga", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreProductoSiga", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@CodigoSiga", productoSiga.getCodigoSiga());
        maps.addValue("@NombreProductoSiga", productoSiga.getNombreProductoSiga());
        maps.addValue("@Activo", productoSiga.getActivo());
        maps.addValue("@UsuarioCreacion", productoSiga.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(ProductoSiga productoSiga) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Siga_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSiga", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoSiga", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreProductoSiga", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSiga", productoSiga.getIdProductoSiga());
        maps.addValue("@CodigoSiga", productoSiga.getCodigoSiga());
        maps.addValue("@NombreProductoSiga", productoSiga.getNombreProductoSiga());
        maps.addValue("@Activo", productoSiga.getActivo());
        maps.addValue("@UsuarioModificacion", productoSiga.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Siga_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSiga", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSiga", id);

        jdbcCall.execute(maps);
    }
    
    public boolean esUsado(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Siga_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSiga", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSiga", id);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    public class ProductoSigaRowMapper implements RowMapper<ProductoSiga> {

        @Override
        public ProductoSiga mapRow(ResultSet rs, int i) throws SQLException {

            ProductoSiga productoSiga = new ProductoSiga();

            productoSiga.setIdProductoSiga(rs.getInt("IdProductoSiga"));
            productoSiga.setCodigoSiga(rs.getString("CodigoSiga"));
            productoSiga.setNombreProductoSiga(rs.getString("NombreProductoSiga"));
            productoSiga.setActivo(rs.getInt("Activo"));

            return productoSiga;
        }
    }
}
