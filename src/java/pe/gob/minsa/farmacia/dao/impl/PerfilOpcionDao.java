package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.PerfilOpcion;
import pe.gob.minsa.farmacia.domain.dto.PerfilOpcionConfiguracion;

public class PerfilOpcionDao implements DaoManager<PerfilOpcion> {

    @Autowired
    DataSource dataSource;

    public List<PerfilOpcionConfiguracion> listarParaConfiguracion(int idSubmenu, int idPerfil) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Opcion_ListarConf")
                .returningResultSet("results", new PerfilOpcionConfiguracionRowMapper());

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdSubmenu", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdSubmenu", idSubmenu);
        maps.addValue("@IdPerfil", idPerfil);

        Map<String, Object> res = jdbcCall.execute(maps);
        List<PerfilOpcionConfiguracion> perfilConOpciones = (List<PerfilOpcionConfiguracion>) res.get("results");

        return perfilConOpciones;
    }

    @Override
    public List<PerfilOpcion> listar() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Opcion_Listar")
                .returningResultSet("results", new PerfilOpcionRowMapper());
        
         Map<String, Object> res = jdbcCall.execute();
         List<PerfilOpcion> perfilOpciones = (List<PerfilOpcion>) res.get("results");
        
         return perfilOpciones;
    }

    @Override
    public PerfilOpcion obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(PerfilOpcion perfilOpcion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Opcion_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdOpcion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPerfil", perfilOpcion.getIdPerfil());
        maps.addValue("@IdOpcion", perfilOpcion.getIdOpcion());
        maps.addValue("@Activo", perfilOpcion.getActivo());
        maps.addValue("@UsuarioCreacion", perfilOpcion.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(PerfilOpcion perfilOpcion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Perfil_Opcion_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfilOpcion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdOpcion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPerfilOpcion", perfilOpcion.getIdPerfilOpcion());
        maps.addValue("@IdPerfil", perfilOpcion.getIdPerfil());
        maps.addValue("@IdOpcion", perfilOpcion.getIdOpcion());
        maps.addValue("@Activo", perfilOpcion.getActivo());
        maps.addValue("@UsuarioModificacion", perfilOpcion.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class PerfilOpcionRowMapper implements RowMapper<PerfilOpcion> {
        
        @Override
        public PerfilOpcion mapRow(ResultSet rs, int i) throws SQLException {
            
            PerfilOpcion perfilOpcion = new PerfilOpcion();
            
            perfilOpcion.setIdPerfilOpcion(rs.getInt("IdPerfilOpcion"));
            perfilOpcion.setIdOpcion(rs.getInt("IdOpcion"));
            perfilOpcion.setIdPerfil(rs.getInt("IdPerfil"));
            perfilOpcion.setActivo(rs.getInt("Activo"));
            
            return perfilOpcion;
        }        
    }

    public class PerfilOpcionConfiguracionRowMapper implements RowMapper<PerfilOpcionConfiguracion> {

        @Override
        public PerfilOpcionConfiguracion mapRow(ResultSet rs, int i) throws SQLException {

            PerfilOpcionConfiguracion perfilOpcionConfiguracion = new PerfilOpcionConfiguracion();

            perfilOpcionConfiguracion.setIdPerfilOpcion(rs.getInt("IdPerfilOpcion"));
            perfilOpcionConfiguracion.setIdOpcion(rs.getInt("IdOpcion"));
            perfilOpcionConfiguracion.setNombreOpcion(rs.getString("NombreOpcion"));
            perfilOpcionConfiguracion.setActivo(rs.getInt("Activo"));

            return perfilOpcionConfiguracion;
        }
    }
}
