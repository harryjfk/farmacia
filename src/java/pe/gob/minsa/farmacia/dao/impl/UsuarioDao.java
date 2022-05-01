package pe.gob.minsa.farmacia.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import pe.gob.minsa.farmacia.dao.DaoManager;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.domain.dto.UsuarioPerfilesMap;

public class UsuarioDao implements DaoManager<Usuario> {

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Usuario> listar() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_Listar")
                .returningResultSet("results", new UsuarioPerfilesRowMapper());

        Map<String, Object> res = jdbcCall.execute();
        List<UsuarioPerfilesMap> usuarioConPerfiles = (List<UsuarioPerfilesMap>) res.get("results");
        return UsuarioPerfiles(usuarioConPerfiles);
    }

    private List<Usuario> UsuarioPerfiles(List<UsuarioPerfilesMap> usuarioConPerfiles) {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        for (UsuarioPerfilesMap uMap : usuarioConPerfiles) {
            if (usuarios.isEmpty()) {

                usuarios.add(uMap);
                usuarios.get(0).getPerfiles().add(uMap.getPerfil());

            } else {
                boolean agregado = false;

                for (Usuario u : usuarios) {
                    if (u.getIdUsuario() == uMap.getIdUsuario()) {
                        u.getPerfiles().add(uMap.getPerfil());
                        agregado = true;
                    }
                }

                if (agregado == false) {
                    usuarios.add(uMap);
                    usuarios.get(usuarios.size() - 1).getPerfiles().add(uMap.getPerfil());
                }
            }
        }

        return usuarios;
    }

    @Override
    public Usuario obtenerPorId(int idUsuario) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("Far_Usuario_ListarPorId");

            jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
            jdbcCall.declareParameters(new SqlReturnResultSet("#results", new UsuarioPerfilesRowMapper()));

            MapSqlParameterSource maps = new MapSqlParameterSource();
            maps.addValue("@IdUsuario", idUsuario);

            Map<String, Object> res = jdbcCall.execute(maps);
            List<UsuarioPerfilesMap> usuarioConPerfiles = (List<UsuarioPerfilesMap>) res.get("#results");
            return UsuarioPerfiles(usuarioConPerfiles).get(0);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public void insertar(Usuario usuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_Insertar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPersonal", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUsuario", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Clave", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Correo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioCreacion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));

        jdbcCall.addDeclaredParameter(new SqlOutParameter("@IdUsuario", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdPersonal", usuario.getPersonal().getIdPersonal());
        maps.addValue("@NombreUsuario", usuario.getNombreUsuario());
        maps.addValue("@Clave", usuario.getClave());
        maps.addValue("@Correo", usuario.getCorreo());
        maps.addValue("@UsuarioCreacion", usuario.getUsuarioCreacion());
        maps.addValue("@Activo", usuario.getActivo());

        Map<String, Object> res = jdbcCall.execute(maps);
        int idUsuario = (Integer) res.get("@IdUsuario");
        usuario.setIdUsuario(idUsuario);
    }

    @Override
    public void actualizar(Usuario usuario) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_Modificar");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@IdPersonal", java.sql.Types.CHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUsuario", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Clave", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Correo", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@UsuarioModificacion", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Activo", java.sql.Types.INTEGER));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", usuario.getIdUsuario());
        maps.addValue("@IdPersonal", usuario.getPersonal().getIdPersonal());
        maps.addValue("@NombreUsuario", usuario.getNombreUsuario());
        maps.addValue("@Clave", usuario.getClave());
        maps.addValue("@Correo", usuario.getCorreo());
        maps.addValue("@UsuarioModificacion", usuario.getUsuarioModificacion());
        maps.addValue("@Activo", usuario.getActivo());

        jdbcCall.execute(maps);        
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean existeCorreo(String correo){
        return this.existeCorreo(correo, 0);
    }
    
    public boolean existeUsuario(String nombreUsuario){
        return this.existeUsuario(nombreUsuario, 0);
    }
    
    public boolean existeCorreo(String correo, int idUsuarioPertenece){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_ExisteCorreo");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Correo", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", idUsuarioPertenece);
        maps.addValue("@Correo", correo);

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }
    
    public boolean existeUsuario(String nombreUsuario, int idUsuarioPertenece){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_ExisteUsuario");

        jdbcCall.addDeclaredParameter(new SqlParameter("@IdUsuario", java.sql.Types.INTEGER));
        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUsuario", java.sql.Types.VARCHAR));
        jdbcCall.declareParameters(new SqlOutParameter("@Existe", Types.BIT));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@IdUsuario", idUsuarioPertenece);
        maps.addValue("@NombreUsuario", nombreUsuario);        

        Map<String, Object> resultMap = jdbcCall.execute(maps);
        return ((Boolean) resultMap.get("@Existe"));
    }

    public Usuario iniciarSesion(String nombreUuario, String clave) {

        Usuario usuario;

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("Far_Usuario_IniciarSesion")
                .returningResultSet("results", new UsuarioPerfilesRowMapper());

        jdbcCall.addDeclaredParameter(new SqlParameter("@NombreUsuario", java.sql.Types.VARCHAR));
        jdbcCall.addDeclaredParameter(new SqlParameter("@Clave", java.sql.Types.VARCHAR));

        MapSqlParameterSource maps = new MapSqlParameterSource();
        maps.addValue("@NombreUsuario", nombreUuario);
        maps.addValue("@Clave", clave);

        Map<String, Object> res = jdbcCall.execute(maps);
        List<UsuarioPerfilesMap> usuarioConPerfiles = (List<UsuarioPerfilesMap>) res.get("results");
        List<Usuario> usuarios = new ArrayList<Usuario>();

        for (UsuarioPerfilesMap uMap : usuarioConPerfiles) {

            if (usuarios.isEmpty()) {

                usuarios.add(uMap);
                usuarios.get(0).getPerfiles().add(uMap.getPerfil());

            } else {
                boolean agregado = false;

                for (Usuario u : usuarios) {
                    if (u.getIdUsuario() == uMap.getIdUsuario()) {
                        u.getPerfiles().add(uMap.getPerfil());
                        agregado = true;
                    }
                }

                if (agregado == false) {
                    usuarios.add(uMap);
                    usuarios.get(usuarios.size() - 1).getPerfiles().add(uMap.getPerfil());
                }
            }
        }

        if (usuarios.isEmpty()) {
            usuario = new Usuario();
        } else {
            usuario = usuarios.get(0);
        }

        return usuario;
    }

    public class UsuarioPerfilesRowMapper implements RowMapper<UsuarioPerfilesMap> {

        @Override
        public UsuarioPerfilesMap mapRow(ResultSet rs, int i) throws SQLException {

            UsuarioPerfilesMap usuario = new UsuarioPerfilesMap();

            usuario.setIdUsuario(rs.getInt("IdUsuario"));
            usuario.getPersonal().setIdPersonal(rs.getString("IdPersonal"));
            usuario.getPersonal().setNombre(rs.getString("PersonalNombre"));
            usuario.getPersonal().setApellidoPaterno(rs.getString("PersonalApePaterno"));
            usuario.getPersonal().setApellidoMaterno(rs.getString("PersonalApeMaterno"));
            usuario.getPersonal().setTipoDocumento(rs.getString("PersonalTipoDocumento"));
            usuario.getPersonal().setNroDocumento(rs.getString("PersonalNroDocumento"));
            usuario.getPersonal().setUnidad(rs.getString("PersonalUnidad"));
            usuario.getPersonal().setCargo(rs.getString("PersonalCargo"));
            usuario.setNombreUsuario(rs.getString("NombreUsuario"));
            usuario.setClave(rs.getString("Clave"));
            usuario.setCorreo(rs.getString("Correo"));
            usuario.setActivo(rs.getInt("Activo"));
            usuario.getPerfil().setIdPerfil(rs.getInt("IdPerfil"));
            usuario.getPerfil().setNombrePerfil(rs.getString("NombrePerfil"));

            return usuario;
        }
    }
}
