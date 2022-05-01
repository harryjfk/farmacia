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
import pe.gob.minsa.farmacia.domain.Submenu;

public class SubmenuDao implements NavegacionManager<Submenu>{
    
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Submenu> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Submenu> submenus = jdbcTemplate.query("{call Far_Submenu_Listar}", new SubmenuRowMapper());
        
        return submenus;
    }
    
    @Override
    public List<Submenu> listarParaSession(int idUsuario) {
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Submenu> submenus = jdbcTemplate.query("{call Far_Submenu_ListarParaSession(?)}" , new Object[]{idUsuario}, new SubmenuRowMapper());

        return submenus;
    }    
    
    public Submenu obtenerPorEnlace(String enlace) {
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            Submenu submenu = jdbcTemplate.queryForObject("{call Far_Submenu_ListarPorEnlace(?)}" , new Object[]{enlace}, new SubmenuRowMapper());
            return submenu;
        }catch(EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public Submenu obtenerPorId(int id) {
        List<Submenu> submenus = listar();
        Integer indice = null;

        for (int i = 0; i <= submenus.size() - 1; ++i) {
            if (submenus.get(i).getIdSubmenu() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return submenus.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void actualizar(Submenu submenu) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Submenu_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreSubmenu", java.sql.Types.VARCHAR));        
        jdbcCall.addDeclaredParameter(new SqlParameter("@Enlace", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdMenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Orden", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));        

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSubmenu", submenu.getIdSubmenu());
        maps.addValue("@NombreSubmenu", submenu.getNombreSubmenu());
        maps.addValue("@Enlace", submenu.getEnlace());
        maps.addValue("@IdMenu", submenu.getIdMenu());
        maps.addValue("@Orden", submenu.getOrden());
        maps.addValue("@Activo", submenu.getActivo());
        maps.addValue("@UsuarioModificacion", submenu.getUsuarioModificacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void cambiarOrden(int idSubmenu, boolean subida) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Submenu_CambiarOrden");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Subida", java.sql.Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSubmenu", idSubmenu);
        maps.addValue("@Subida", subida);

        jdbcCall.execute(maps);
    }
    
    public class SubmenuRowMapper implements RowMapper<Submenu> {           

        @Override
        public Submenu mapRow(ResultSet rs, int i) throws SQLException {
            
            Submenu submenu = new Submenu();

            submenu.setIdSubmenu(rs.getInt("IdSubmenu"));
            submenu.setNombreSubmenu(rs.getString("NombreSubmenu"));
            submenu.setEnlace(rs.getString("Enlace"));            
            submenu.setIdMenu(rs.getInt("IdMenu"));
            submenu.setOrden(rs.getInt("Orden"));
            submenu.setActivo(rs.getInt("Activo"));

            return submenu;
        }
    }
    
}
