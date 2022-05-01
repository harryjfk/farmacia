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

public class TipoAlmacenDao implements DaoManager<TipoAlmacen> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoAlmacen> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoAlmacen> tiposAlmacenes = jdbcTemplate.query("{call Far_Tipo_Almacen_Listar}", new TipoAlmacenRowMapper());

        return tiposAlmacenes;
    }

    @Override
    public TipoAlmacen obtenerPorId(int id) {
        List<TipoAlmacen> tiposAlmacenes = listar();
        Integer indice = null;

        for (int i = 0; i <= tiposAlmacenes.size() - 1; ++i) {
            if (tiposAlmacenes.get(i).getIdTipoAlmacen() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return tiposAlmacenes.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(TipoAlmacen tipoAlmacen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Almacen_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoAlmacen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoAlmacen", tipoAlmacen.getNombreTipoAlmacen());
        maps.addValue("@Activo", tipoAlmacen.getActivo());
        maps.addValue("@UsuarioCreacion", tipoAlmacen.getUsuarioCreacion());

        jdbcCall.execute(maps);

    }

    @Override
    public void actualizar(TipoAlmacen tipoAlmacen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Almacen_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAlmacen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoAlmacen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoAlmacen", tipoAlmacen.getIdTipoAlmacen());
        maps.addValue("@NombreTipoAlmacen", tipoAlmacen.getNombreTipoAlmacen());
        maps.addValue("@Activo", tipoAlmacen.getActivo());
        maps.addValue("@UsuarioModificacion", tipoAlmacen.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Almacen_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoAlmacen", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoAlmacen", id);

        jdbcCall.execute(maps);
    }

    public class TipoAlmacenRowMapper implements RowMapper<TipoAlmacen> {

        @Override
        public TipoAlmacen mapRow(ResultSet rs, int i) throws SQLException {

            TipoAlmacen tipoAlmacen = new TipoAlmacen();

            tipoAlmacen.setIdTipoAlmacen(rs.getInt("IdTipoAlmacen"));
            tipoAlmacen.setNombreTipoAlmacen(rs.getString("NombreTipoAlmacen"));
            tipoAlmacen.setActivo(rs.getInt("Activo"));

            return tipoAlmacen;
        }
    }

}
