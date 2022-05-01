package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.NavegacionManager;
import pe.gob.minsa.farmacia.domain.Menu;

public class MenuDao implements NavegacionManager<Menu> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Menu> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Menu> menus = jdbcTemplate.query("{call Far_Menu_Listar}", new MenuRowMapper());

        return menus;
    }
    
    @Override
    public List<Menu> listarParaSession(int idUsuario){
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Menu> menus = jdbcTemplate.query("{call Far_Menu_ListarParaSession(?)}", new Object[] {idUsuario}, new MenuRowMapper());

        return menus;
    }

    @Override
    public Menu obtenerPorId(int id) {
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Menu menu = jdbcTemplate.queryForObject("{call Far_Menu_ListarPorId(?)}", new Object[]{ id }, new MenuRowMapper());
            return menu;
        }
        catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public void actualizar(Menu menu) {
         SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Menu_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreMenu", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmodulo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Orden", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMenu", menu.getIdMenu());
        maps.addValue("@NombreMenu", menu.getNombreMenu());
        maps.addValue("@IdSubmodulo", menu.getIdSubmodulo());
        maps.addValue("@Orden", menu.getOrden());
        maps.addValue("@Activo", menu.getActivo());
        maps.addValue("@UsuarioModificacion", menu.getActivo());

        jdbcCall.execute(maps);
    }

    @Override
    public void cambiarOrden(int idMenu, boolean subida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Menu_CambiarOrden");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Subida", java.sql.Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdMenu", idMenu);
        maps.addValue("@Subida", subida);

        jdbcCall.execute(maps);
    }

    private static class MenuRowMapper implements RowMapper<Menu> {

        @Override
        public Menu mapRow(ResultSet rs, int i) throws SQLException {

            Menu menu = new Menu();           

            menu.setIdMenu(rs.getInt("IdMenu"));
            menu.setNombreMenu(rs.getString("NombreMenu"));
            menu.setIdSubmodulo(rs.getInt("IdSubmodulo"));
            menu.setOrden(rs.getInt("Orden"));
            menu.setActivo(rs.getInt("Activo"));     

            return menu;
        }
    }

}
