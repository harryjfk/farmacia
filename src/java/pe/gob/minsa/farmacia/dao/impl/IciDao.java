package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.minsa.farmacia.domain.Ici;

public class IciDao {

    @Autowired
    DataSource dataSource;

    public Ici obtenerPorPeriodo(int idPeriodo, int idAlmacen, int idTipoSuministro) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Ici ici = jdbcTemplate.queryForObject("{call Far_ICI_ListarPorPeriodo(?,?,?)}", new Object[]{idPeriodo,idAlmacen,idTipoSuministro}, new IciRowMapper());
            return ici;
        } catch (EmptyResultDataAccessException ex) {
            return new Ici();
        }
    }

    public class IciRowMapper implements RowMapper<Ici> {

        @Override
        public Ici mapRow(ResultSet rs, int i) throws SQLException {
            Ici ici = new Ici();
            ici.setIdICI(rs.getInt("IdICI"));
            ici.setIdPeriodo(rs.getInt("IdPeriodo"));            
            ici.setIdTipoSuministro(rs.getInt("IdTipoSuministro"));
            return ici;
        }

    }
}
