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
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.TipoMovimientoConcepto;
import pe.gob.minsa.farmacia.domain.TipoPrecioConcepto;

public class ConceptoDao implements DaoManager<Concepto> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Concepto> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Concepto> conceptos = jdbcTemplate.query("{call Far_Concepto_Listar}", new ConceptoRowMapper());

        return conceptos;
    }

    @Override
    public Concepto obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Concepto concepto = jdbcTemplate.queryForObject("{call Far_Concepto_ListarPorId(?)}", new Object[]{id}, new ConceptoRowMapper());

        return concepto;
    }

    @Override
    public void insertar(Concepto concepto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreConcepto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimientoConcepto", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoPrecio", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreConcepto", concepto.getNombreConcepto());
        maps.addValue("@TipoMovimientoConcepto", concepto.getTipoMovimientoConcepto().toString());
        maps.addValue("@TipoPrecio", TipoPrecioConcepto.toValue(concepto.getTipoPrecio()));
        maps.addValue("@Activo", concepto.getActivo());
        maps.addValue("@UsuarioCreacion", concepto.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(Concepto concepto) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreConcepto", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoMovimientoConcepto", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@TipoPrecio", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConcepto", concepto.getIdConcepto());
        maps.addValue("@NombreConcepto", concepto.getNombreConcepto());
        maps.addValue("@TipoMovimientoConcepto", concepto.getTipoMovimientoConcepto().toString());
        maps.addValue("@TipoPrecio", TipoPrecioConcepto.toValue(concepto.getTipoPrecio()));
        maps.addValue("@Activo", concepto.getActivo());
        maps.addValue("@UsuarioModificacion", concepto.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConcepto", id);

        jdbcCall.execute(maps);
    }

    public class ConceptoRowMapper implements RowMapper<Concepto> {

        @Override
        public Concepto mapRow(ResultSet rs, int i) throws SQLException {

            Concepto concepto = new Concepto();

            concepto.setIdConcepto(rs.getInt("IdConcepto"));
            concepto.setNombreConcepto(rs.getString("NombreConcepto"));
            concepto.setTipoMovimientoConcepto(TipoMovimientoConcepto.fromString(rs.getString("TipoMovimientoConcepto")));
            concepto.setTipoPrecio(TipoPrecioConcepto.fromString(rs.getString("TipoPrecio")));
            concepto.setActivo(rs.getInt("Activo"));

            return concepto;
        }
    }
}
