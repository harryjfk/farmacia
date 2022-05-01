package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.minsa.farmacia.domain.Personal;

public class PersonalDao {
    
    @Autowired
    DataSource dataSource;

    public List<Personal> listar() {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Personal> personas = jdbcTemplate.query("{call Far_Personal_Listar}", new PersonaRowMapper());

        return personas;
    }
    
    public List<Personal> listarSinUsuario() {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Personal> personas = jdbcTemplate.query("{call Far_Personal_ListarSinUsuario}", new PersonaRowMapper());

        return personas;
    }
    
    public List<Personal> listarPorUnidad(String idUnidad) {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Personal> personas = jdbcTemplate.query("{call Far_Personal_ListarPorUnidad(?)}", new Object[]{idUnidad}, new PersonaRowMapper());

        return personas;
    }
    
     public List<Personal> listarMedico() {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Personal> personas = jdbcTemplate.query("{call Far_Personal_ListarMedico}", new PersonaRowMapper());

        return personas;
    }

    public Personal obtenerPorId(String id) {
        List<Personal> medicos = listarMedico();
        
        Integer indice = null;

        for (int i = 0; i <= medicos.size() - 1; ++i) {
            if (medicos.get(i).getIdPersonal().trim().toLowerCase().equals(id.trim().toLowerCase())) {
                indice = i;
                break;
            }
        }
        if (indice != null) {
            return medicos.get(indice);
        } else {
            return null;
        }
    }
    
    public class PersonaRowMapper implements RowMapper<Personal> {

        @Override
        public Personal mapRow(ResultSet rs, int i) throws SQLException {

            Personal personal = new Personal();
            
            personal.setIdPersonal(rs.getString("IdPersonal"));
            personal.setNombre(rs.getString("PersonalNombre"));
            personal.setApellidoPaterno(rs.getString("PersonalApePaterno"));
            personal.setApellidoMaterno(rs.getString("PersonalApeMaterno"));
            personal.setTipoDocumento(rs.getString("PersonalTipoDocumento"));
            personal.setNroDocumento(rs.getString("PersonalNroDocumento"));
            personal.setUnidad(rs.getString("PersonalUnidad"));
            personal.setCargo(rs.getString("PersonalCargo"));
            personal.setColegiatura(rs.getString("Colegiatura"));

            return personal;
        }
    }
    
}
