/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.web;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.Usuario;

/**
 *
 * 
 */
public class WebUtil {
    
    public static Usuario obtenerUsuarioEnSesion(HttpServletRequest request) {
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioIniciado");
        return usuario;
    }
    
    public static String generarClaveUsuario(int length) {
        List<String> dic = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H",
                                         "I", "J", "K", "L", "M", "N", "O", "P",
                                         "Q", "R", "S", "T", "U", "V", "W", "X",
                                         "Y", "Z", "a", "b", "c", "d", "f", "g",
                                         "h", "i", "j", "k", "l", "m", "n", "o",
                                         "p", "q", "r", "s", "t", "u", "v", "w",
                                         "x", "y", "z", "0", "1", "2", "3", "4",
                                         "5", "6", "7", "8", "9", "+", "*", "-",
                                         "#", "$", "&", "@", "!");
        String clave = "";
        Random r = new Random(0);
        while (length-- > 0) {            
            int i = r.nextInt(dic.size());
            clave += dic.get(i);
        }
        return clave;
    }
    
    public static Perfil usuarioPertenecePerfil(String perfil, Usuario usuario) {
        for (Perfil p : usuario.getPerfiles()) {
            if(perfil.trim().equalsIgnoreCase(p.getNombrePerfil().toLowerCase())) {
                return p;
            }
        }
        return null;
    }
    
}
