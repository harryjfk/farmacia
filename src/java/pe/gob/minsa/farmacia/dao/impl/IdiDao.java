package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.minsa.farmacia.domain.Idi;

public class IdiDao {
    
    @Autowired
    DataSource dataSource;

    public Idi obtenerPorPeriodo(int idPeriodo,int idTipoSuministro, int idTipoProceso) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Idi idi = jdbcTemplate.queryForObject("{call Far_IDI_ListarPorPeriodo(?,?,?)}", new Object[]{idPeriodo, idTipoSuministro, idTipoProceso}, new IdiRowMapper());
            return idi;
        } catch (EmptyResultDataAccessException ex) {
            return new Idi();
        }
    }

    public class IdiRowMapper implements RowMapper<Idi> {

        @Override
        public Idi mapRow(ResultSet rs, int i) throws SQLException {
            Idi idi = new Idi();
            idi.setIdIDI(rs.getInt("IdIDI"));
            idi.setIdPeriodo(rs.getInt("IdPeriodo"));            
            idi.setIdTipoSuministro(rs.getInt("IdTipoSuministro"));
            idi.setIdTipoProceso(rs.getInt("IdTipoProceso"));
            return idi;
        }
    }
}
