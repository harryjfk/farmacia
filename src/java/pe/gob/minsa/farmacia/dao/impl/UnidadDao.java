package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.minsa.farmacia.domain.Unidad;

public class UnidadDao {
    @Autowired
    DataSource dataSource;

    
    public List<Unidad> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Unidad> unidades = jdbcTemplate.query("{call Far_Unidad_Listar}", new UnidadRowMapper());

        return unidades;
    }
    
    public class UnidadRowMapper implements RowMapper<Unidad> {

        @Override
        public Unidad mapRow(ResultSet rs, int i) throws SQLException {

            Unidad unidad = new Unidad();

            unidad.setIdUnidad(rs.getString("IdUnidad"));
            unidad.setNombreUnidad(rs.getString("NombreUnidad"));
            
            return unidad;
        }
    }
}
