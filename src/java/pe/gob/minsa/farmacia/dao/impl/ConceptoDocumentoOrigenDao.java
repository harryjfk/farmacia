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
import pe.gob.minsa.farmacia.domain.ConceptoDocumentoOrigen;
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;

public class ConceptoDocumentoOrigenDao {

    @Autowired
    DataSource dataSource;

    public List<ConceptoDocumentoOrigen> listar(int idConcepto) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<ConceptoDocumentoOrigen> conceptoDocumentosOrigen = jdbcTemplate.query("{call Far_Concepto_Documento_Origen_Listar(?)}", new Object[]{idConcepto}, new ConceptoDocumentoOrigenRowMapper());

        return conceptoDocumentosOrigen;
    }

    public void insertar(ConceptoDocumentoOrigen conceptoDocumentoOrigen){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Documento_Origen_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConcepto", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdDocumentoOrigen", java.sql.Types.INTEGER)); 
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        
        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdConceptoDocumentoOrigen", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConcepto", conceptoDocumentoOrigen.getIdConcepto());
        maps.addValue("@IdDocumentoOrigen", conceptoDocumentoOrigen.getIdDocumentoOrigen());      
        maps.addValue("@UsuarioCreacion", conceptoDocumentoOrigen.getUsuarioCreacion());

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        conceptoDocumentoOrigen.setIdConceptoDocumentoOrigen((Integer)resultMap.get("@IdConceptoDocumentoOrigen"));
    }
    
    public void eliminar(int idConceptoDocumentoOrigen){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Concepto_Documento_Origen_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdConceptoDocumentoOrigen", java.sql.Types.INTEGER));
        
        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdConceptoDocumentoOrigen", idConceptoDocumentoOrigen);
        jdbcCall.execute(maps);
    }
    
    public class ConceptoDocumentoOrigenRowMapper implements RowMapper<ConceptoDocumentoOrigen> {

        @Override
        public ConceptoDocumentoOrigen mapRow(ResultSet rs, int i) throws SQLException {
            ConceptoDocumentoOrigen conceptoDocumentoOrigen = new ConceptoDocumentoOrigen();

            conceptoDocumentoOrigen.setIdConceptoDocumentoOrigen(rs.getInt("IdConceptoDocumentoOrigen"));
            conceptoDocumentoOrigen.setIdConcepto(rs.getInt("IdConcepto"));
            conceptoDocumentoOrigen.setIdDocumentoOrigen(rs.getInt("IdDocumentoOrigen"));
            
            conceptoDocumentoOrigen.setDocumentoOrigen(new DocumentoOrigen());
            conceptoDocumentoOrigen.getDocumentoOrigen().setIdDocumentoOrigen(rs.getInt("IdDocumentoOrigen"));
            conceptoDocumentoOrigen.getDocumentoOrigen().setNombreDocumentoOrigen(rs.getString("NombreDocumentoOrigen"));
            conceptoDocumentoOrigen.getDocumentoOrigen().setActivo(rs.getInt("ActivoDocumentoOrigen"));

            return conceptoDocumentoOrigen;
        }
    }
}
