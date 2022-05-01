package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.TipoProducto;

public class TipoProductoDao implements DaoManager<TipoProducto> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoProducto> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoProducto> tiposProducto = jdbcTemplate.query("{call Far_Tipo_Producto_Listar}", new TipoProductoRowMapper());

        return tiposProducto;
    }

    @Override
    public TipoProducto obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TipoProducto tipoProducto = jdbcTemplate.queryForObject("{call Far_Tipo_Producto_ListarPorId(?)}", new Object[]{id}, new TipoProductoRowMapper());

        return tipoProducto;
    }

    @Override
    public void insertar(TipoProducto tipoProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Producto_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoProducto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoProducto", tipoProducto.getNombreTipoProducto());
        maps.addValue("@Activo", tipoProducto.getActivo());
        maps.addValue("@UsuarioCreacion", tipoProducto.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoProducto tipoProducto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Producto_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoProducto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProducto", tipoProducto.getIdTipoProducto());
        maps.addValue("@NombreTipoProducto", tipoProducto.getNombreTipoProducto());
        maps.addValue("@Activo", tipoProducto.getActivo());
        maps.addValue("@UsuarioModificacion", tipoProducto.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Producto_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProducto", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProducto", id);

        jdbcCall.execute(maps);
    }

    public class TipoProductoRowMapper implements RowMapper<TipoProducto> {

        @Override
        public TipoProducto mapRow(ResultSet rs, int i) throws SQLException {
            TipoProducto tipoProducto = new TipoProducto();

            tipoProducto.setIdTipoProducto(rs.getInt("IdTipoProducto"));
            tipoProducto.setNombreTipoProducto(rs.getString("NombreTipoProducto"));
            tipoProducto.setActivo(rs.getInt("Activo"));

            return tipoProducto;
        }
    }
}
