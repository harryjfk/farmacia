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

import pe.gob.minsa.farmacia.domain.TipoProceso;

public class TipoProcesoDao implements DaoManager<TipoProceso> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<TipoProceso> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<TipoProceso> tiposProceso = jdbcTemplate.query("{call Far_Tipo_Proceso_Listar}", new TipoProcesoRowMapper());

        return tiposProceso;
    }

    @Override
    public TipoProceso obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            TipoProceso tipoProceso = jdbcTemplate.queryForObject("{call Far_Tipo_Proceso_ListarPorId(?)}", new Object[]{id}, new TipoProcesoRowMapper());
            return tipoProceso;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void insertar(TipoProceso tipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Proceso_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoProceso", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreTipoProceso", tipoProceso.getNombreTipoProceso());
        maps.addValue("@Activo", tipoProceso.getActivo());
        maps.addValue("@UsuarioCreacion", tipoProceso.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(TipoProceso tipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Proceso_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoProceso", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProceso", tipoProceso.getIdTipoProceso());
        maps.addValue("@NombreTipoProceso", tipoProceso.getNombreTipoProceso());
        maps.addValue("@Activo", tipoProceso.getActivo());
        maps.addValue("@UsuarioModificacion", tipoProceso.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Proceso_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProceso", id);

        jdbcCall.execute(maps);
    }

    public boolean existe(String nombreTipoProceso) {
        return this.existe(nombreTipoProceso, 0);
    }

    public boolean existe(String nombreTipoProceso, int idTipoProcesoPertenece) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Proceso_Existe");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreTipoProceso", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProceso", idTipoProcesoPertenece);
        maps.addValue("@NombreTipoProceso", nombreTipoProceso);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    public boolean esUsado(int IdTipoProceso) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Tipo_Proceso_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoProceso", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdTipoProceso", IdTipoProceso);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    public class TipoProcesoRowMapper implements RowMapper<TipoProceso> {

        @Override
        public TipoProceso mapRow(ResultSet rs, int i) throws SQLException {

            TipoProceso tipoProceso = new TipoProceso();

            tipoProceso.setIdTipoProceso(rs.getInt("IdTipoProceso"));
            tipoProceso.setNombreTipoProceso(rs.getString("NombreTipoProceso"));
            tipoProceso.setActivo(rs.getInt("Activo"));

            return tipoProceso;
        }
    }
}
