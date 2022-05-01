package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.domain.ConceptoTipoDocumentoMov;
import pe.gob.minsa.farmacia.domain.TipoDocumentoMov;

public class ConceptoTipoDocumentoMovDao {

    @Autowired
    DataSource dataSource;

    public List<ConceptoTipoDocumentoMov> listar(int idConcepto) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ConceptoTipoDocumentoMov> conceptoTiposDocumento = jdbcTemplate.query("{call Far_Concepto_Tipo_Documento_Mov_Listar(?)}", new Object[]{idConcepto}, new ConceptoTipoDocumentoMovRowMapper());

        return conceptoTiposDocumento;
    }

    public void insertar(ConceptoTipoDocumentoMov conceptoTipoDocumentoMov) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Tipo_Documento_Mov_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdTipoDocumentoMov", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdConceptoTipoDocumentoMov", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConcepto", conceptoTipoDocumentoMov.getIdConcepto());
        maps.addValue("@IdTipoDocumentoMov", conceptoTipoDocumentoMov.getIdTipoDocumentoMov());
        maps.addValue("@UsuarioCreacion", conceptoTipoDocumentoMov.getUsuarioCreacion());

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        conceptoTipoDocumentoMov.setIdConceptoTipoDocumentoMov((Integer) resultMap.get("@IdConceptoTipoDocumentoMov"));
    }

    public void eliminar(int idConceptoDocumentoOrigen) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Tipo_Documento_Mov_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConceptoTipoDocumentoMov", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConceptoTipoDocumentoMov", idConceptoDocumentoOrigen);
        jdbcCall.execute(maps);
    }

    public class ConceptoTipoDocumentoMovRowMapper implements RowMapper<ConceptoTipoDocumentoMov> {

        @Override
        public ConceptoTipoDocumentoMov mapRow(ResultSet rs, int i) throws SQLException {
            ConceptoTipoDocumentoMov conceptoTipoDocumento = new ConceptoTipoDocumentoMov();

            conceptoTipoDocumento.setIdConceptoTipoDocumentoMov(rs.getInt("IdConceptoTipoDocumentoMov"));
            conceptoTipoDocumento.setIdConcepto(rs.getInt("IdConcepto"));
            conceptoTipoDocumento.setIdTipoDocumentoMov(rs.getInt("IdTipoDocumentoMov"));
            
            conceptoTipoDocumento.setTipoDocumentoMov(new TipoDocumentoMov());
            conceptoTipoDocumento.getTipoDocumentoMov().setIdTipoDocumentoMov(rs.getInt("IdTipoDocumentoMov"));
            conceptoTipoDocumento.getTipoDocumentoMov().setActivo(rs.getInt("ActivoTipoDocumentoMov"));
            conceptoTipoDocumento.getTipoDocumentoMov().setNombreTipoDocumentoMov(rs.getString("NombreTipoDocumentoMov"));

            return conceptoTipoDocumento;
        }
    }
}
