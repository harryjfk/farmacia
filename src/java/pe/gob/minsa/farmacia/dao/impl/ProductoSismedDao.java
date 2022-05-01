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
import pe.gob.minsa.farmacia.domain.ProductoSiga;
import pe.gob.minsa.farmacia.domain.ProductoSismed;

public class ProductoSismedDao implements DaoManager<ProductoSismed> {
    
    @Autowired
    DataSource dataSource;
    
    public List<ProductoSismed> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ProductoSismed> productosSismed = jdbcTemplate.query("{call Far_Producto_Sismed_Listar}", new ProductoSismedRowMapper());

        return productosSismed;
    }

    @Override
    public ProductoSismed obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            ProductoSismed productoSismed = jdbcTemplate.queryForObject("{call Far_Producto_Sismed_PorId(?)}", new Object[]{id}, new ProductoSismedRowMapper());
            return productoSismed;
        } catch (EmptyResultDataAccessException ex) {
            return new ProductoSismed();
        }
    }

    @Override
    public void insertar(ProductoSismed productoSismed) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Sismed_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoSismed", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreProductoSismed", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@CodigoSismed", productoSismed.getCodigoSismed());
        maps.addValue("@NombreProductoSismed", productoSismed.getNombreProductoSismed());
        maps.addValue("@Activo", productoSismed.getActivo());
        maps.addValue("@UsuarioCreacion", productoSismed.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(ProductoSismed productoSismed) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Sismed_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSismed", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@CodigoSismed", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreProductoSismed", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSismed", productoSismed.getIdProductoSismed());
        maps.addValue("@CodigoSismed", productoSismed.getCodigoSismed());
        maps.addValue("@NombreProductoSismed", productoSismed.getNombreProductoSismed());
        maps.addValue("@Activo", productoSismed.getActivo());
        maps.addValue("@UsuarioModificacion", productoSismed.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Sismed_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSismed", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSismed", id);

        jdbcCall.execute(maps);
    }
    
    public boolean esUsado(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Producto_Sismed_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdProductoSismed", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdProductoSismed", id);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }
    
    public class ProductoSismedRowMapper implements RowMapper<ProductoSismed> {

        @Override
        public ProductoSismed mapRow(ResultSet rs, int i) throws SQLException {

            ProductoSismed productoSismed = new ProductoSismed();

            productoSismed.setIdProductoSismed(rs.getInt("IdProductoSismed"));
            productoSismed.setCodigoSismed(rs.getString("CodigoSismed"));
            productoSismed.setNombreProductoSismed(rs.getString("NombreProductoSismed"));
            productoSismed.setActivo(rs.getInt("Activo"));

            return productoSismed;
        }
    }
}
