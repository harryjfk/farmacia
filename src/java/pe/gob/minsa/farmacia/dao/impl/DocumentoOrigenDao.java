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
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;

public class DocumentoOrigenDao implements DaoManager<DocumentoOrigen> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<DocumentoOrigen> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<DocumentoOrigen> documentosOrigen = jdbcTemplate.query("{call Far_Documento_Origen_Listar}", new DocumentoOrigenRowMapper());

        return documentosOrigen;
    }

    @Override
    public DocumentoOrigen obtenerPorId(int id) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            DocumentoOrigen documentoOrigen = jdbcTemplate.queryForObject("{call Far_Documento_Origen_ListarPorId(?)}", new Object[]{id}, new DocumentoOrigenRowMapper());
            return documentoOrigen;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public void insertar(DocumentoOrigen documentoOrigen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Origen_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreDocumentoOrigen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreDocumentoOrigen", documentoOrigen.getNombreDocumentoOrigen());
        maps.addValue("@Activo", documentoOrigen.getActivo());
        maps.addValue("@UsuarioCreacion", documentoOrigen.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(DocumentoOrigen documentoOrigen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Origen_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreDocumentoOrigen", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumentoOrigen", documentoOrigen.getIdDocumentoOrigen());
        maps.addValue("@NombreDocumentoOrigen", documentoOrigen.getNombreDocumentoOrigen());
        maps.addValue("@Activo", documentoOrigen.getActivo());
        maps.addValue("@UsuarioModificacion", documentoOrigen.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Origen_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumentoOrigen", id);

        jdbcCall.execute(maps);
    }

    public boolean existe(String nombreDocumentoOrigen) {
        return this.existe(nombreDocumentoOrigen, 0);
    }

    public boolean existe(String nombreDocumentoOrigen, int idDocumentoOrigenPertenece) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Origen_Existe");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreDocumentoOrigen", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumentoOrigen", idDocumentoOrigenPertenece);
        maps.addValue("@NombreDocumentoOrigen", nombreDocumentoOrigen);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    public boolean esUsado(int idDocumentoOrigen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Documento_Origen_EsUsado");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER));
        jdbcCall.declareParameters(new SqlOutParameter("@EsUsado", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdDocumentoOrigen", idDocumentoOrigen);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@EsUsado"));
    }

    public class DocumentoOrigenRowMapper implements RowMapper<DocumentoOrigen> {

        @Override
        public DocumentoOrigen mapRow(ResultSet rs, int i) throws SQLException {

            DocumentoOrigen documentoOrigen = new DocumentoOrigen();

            documentoOrigen.setIdDocumentoOrigen(rs.getInt("IdDocumentoOrigen"));
            documentoOrigen.setNombreDocumentoOrigen(rs.getString("NombreDocumentoOrigen"));
            documentoOrigen.setActivo(rs.getInt("Activo"));

            return documentoOrigen;
        }
    }
}