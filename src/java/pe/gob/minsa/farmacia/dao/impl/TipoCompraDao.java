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
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.domain.TipoCompra;

public class TipoCompraDao implements DaoManager<TipoCompra> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoCompra> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoCompra> tiposCompras = jdbcTemplate.query("{call Far_Tipo_Compra_Listar}", new TipoCompraRowMapper());

        return tiposCompras;
    }

    @Override
    public TipoCompra obtenerPorId(int id) {
        List<TipoCompra> tiposCompras = listar();
        Integer indice = null;

        for (int i = 0; i <= tiposCompras.size() - 1; ++i) {
            if (tiposCompras.get(i).getIdTipoCompra() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return tiposCompras.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(TipoCompra tipoCompra) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Compra_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoCompra", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoCompra", tipoCompra.getNombreTipoCompra());
        maps.addValue("@Activo", tipoCompra.getActivo());
        maps.addValue("@UsuarioCreacion", tipoCompra.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoCompra tipoCompra) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Compra_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoCompra", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoCompra", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoCompra", tipoCompra.getIdTipoCompra());
        maps.addValue("@NombreTipoCompra", tipoCompra.getNombreTipoCompra());
        maps.addValue("@Activo", tipoCompra.getActivo());
        maps.addValue("@UsuarioModificacion", tipoCompra.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Compra_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoCompra", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoCompra", id);

        jdbcCall.execute(maps);
    }

    public class TipoCompraRowMapper implements RowMapper<TipoCompra> {

        @Override
        public TipoCompra mapRow(ResultSet rs, int i) throws SQLException {

            TipoCompra tipoCompra = new TipoCompra();

            tipoCompra.setIdTipoCompra(rs.getInt("IdTipoCompra"));
            tipoCompra.setNombreTipoCompra(rs.getString("NombreTipoCompra"));
            tipoCompra.setActivo(rs.getInt("Activo"));

            return tipoCompra;
        }
    }

}
