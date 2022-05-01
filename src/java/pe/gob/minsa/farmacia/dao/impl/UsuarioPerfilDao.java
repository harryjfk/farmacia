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
import pe.gob.minsa.farmacia.domain.UsuarioPerfil;

public class UsuarioPerfilDao implements DaoManager<UsuarioPerfil> {

    @Autowired
    DataSource dataSource;

    @Override
    public List<UsuarioPerfil> listar() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<UsuarioPerfil> usuarioPerfiles = jdbcTemplate.query("{call Far_Usuario_Perfil_Listar}", new UsuarioPerfilRowMapper());

        return usuarioPerfiles;
    }

    @Override
    public UsuarioPerfil obtenerPorId(int id) {
        List<UsuarioPerfil> usuarioPerfiles = listar();
        Integer indice = null;

        for (int i = 0; i <= usuarioPerfiles.size() - 1; ++i) {
            if (usuarioPerfiles.get(i).getIdUsuarioPerfil() == id) {
                indice = i;
                break;
            }
        }

        if (indice != null) {
            return usuarioPerfiles.get(indice);
        } else {
            return null;
        }
    }

    @Override
    public void insertar(UsuarioPerfil usuarioPerfil) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_Perfil_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", usuarioPerfil.getIdUsuario());
        maps.addValue("@IdPerfil", usuarioPerfil.getIdPerfil());
        maps.addValue("@Activo", usuarioPerfil.getActivo());
        maps.addValue("@UsuarioCreacion", usuarioPerfil.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void actualizar(UsuarioPerfil usuarioPerfil) {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_Perfil_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuarioPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPerfil", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuarioPerfil", usuarioPerfil.getIdUsuarioPerfil());
        maps.addValue("@IdUsuario", usuarioPerfil.getIdUsuario());
        maps.addValue("@IdPerfil", usuarioPerfil.getIdPerfil());
        maps.addValue("@Activo", usuarioPerfil.getActivo());
        maps.addValue("@UsuarioModificacion", usuarioPerfil.getUsuarioCreacion());

        jdbcCall.execute(maps);
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class UsuarioPerfilRowMapper implements RowMapper<UsuarioPerfil> {

        @Override
        public UsuarioPerfil mapRow(ResultSet rs, int i) throws SQLException {

            UsuarioPerfil usuarioPerfil = new UsuarioPerfil();

            usuarioPerfil.setIdUsuarioPerfil(rs.getInt("IdUsuarioPerfil"));
            usuarioPerfil.setIdUsuario(rs.getInt("IdUsuario"));
            usuarioPerfil.setIdPerfil(rs.getInt("IdPerfil"));
            usuarioPerfil.setActivo(rs.getInt("Activo"));

            return usuarioPerfil;
        }
    }
}
