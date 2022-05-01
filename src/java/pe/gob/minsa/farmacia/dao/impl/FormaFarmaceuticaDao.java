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
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;

public class FormaFarmaceuticaDao implements DaoManager<FormaFarmaceutica> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<FormaFarmaceutica> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<FormaFarmaceutica> formasFarmaceutica = jdbcTemplate.query("{call Far_Forma_Farmaceutica_Listar}", new FormaFarmaceuticaRowMapper());

        return formasFarmaceutica;
    }

    @Override
    public FormaFarmaceutica obtenerPorId(int id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        FormaFarmaceutica formaFarmaceutica = jdbcTemplate.queryForObject("{call Far_Forma_Farmaceutica_ListarPorId(?)}", new Object[]{id}, new FormaFarmaceuticaRowMapper());

        return formaFarmaceutica;
    }

    @Override
    public void insertar(FormaFarmaceutica formaFarmaceutica) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Forma_Farmaceutica_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreFormaFarmaceutica", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreFormaFarmaceutica", formaFarmaceutica.getNombreFormaFarmaceutica());
        maps.addValue("@Abreviatura", formaFarmaceutica.getAbreviatura());
        maps.addValue("@Activo", formaFarmaceutica.getActivo());
        maps.addValue("@UsuarioCreacion", formaFarmaceutica.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(FormaFarmaceutica formaFarmaceutica) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Forma_Farmaceutica_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreFormaFarmaceutica", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Abreviatura", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdFormaFarmaceutica", formaFarmaceutica.getIdFormaFarmaceutica());
        maps.addValue("@NombreFormaFarmaceutica", formaFarmaceutica.getNombreFormaFarmaceutica());
        maps.addValue("@Abreviatura", formaFarmaceutica.getAbreviatura());
        maps.addValue("@Activo", formaFarmaceutica.getActivo());        
        maps.addValue("@UsuarioModificacion", formaFarmaceutica.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Forma_Farmaceutica_Eliminar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdFormaFarmaceutica", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdFormaFarmaceutica", id);

        jdbcCall.execute(maps);
    }

    public class FormaFarmaceuticaRowMapper implements RowMapper<FormaFarmaceutica> {

        @Override
        public FormaFarmaceutica mapRow(ResultSet rs, int i) throws SQLException {
            FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica();

            formaFarmaceutica.setIdFormaFarmaceutica(rs.getInt("IdFormaFarmaceutica"));
            formaFarmaceutica.setNombreFormaFarmaceutica(rs.getString("NombreFormaFarmaceutica"));
            formaFarmaceutica.setAbreviatura(rs.getString("Abreviatura"));
            formaFarmaceutica.setActivo(rs.getInt("Activo"));

            return formaFarmaceutica;
        }
    }
}
