package pe.gob.minsa.farmacia.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Opcion;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.OpcionService;
import pe.gob.minsa.farmacia.services.impl.SubmenuService;

public class InterceptorSecurity implements HandlerInterceptor {

    @Autowired
    SubmenuService submenuService;

    @Autowired
    OpcionService opcionService;

    @Autowired
    ServletContext context;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        return true;
        /*
         StatusNavigation statusNavigation;
         boolean isUrlAjax = false;

         //enlace sin contexto ejemplo: /Almacen/{opcion}/{id}
         String enlace = getUrl(request);
         String[] enlaceDirectorios = enlace.split("/");

         //Si es index retornar
         if (enlaceDirectorios[1].equalsIgnoreCase("index")
         || enlaceDirectorios[1].equalsIgnoreCase("")) {
         statusNavigation = StatusNavigation.OK;
            
         } else {
         if (getUsuario(request) != null) {
         //Como el usuario está logeado iniciamos en prohibido
         statusNavigation = StatusNavigation.FORBIDDEN;

         //Verificamos si es url blank o ajax de la barra menú
         if (enlaceDirectorios[1].equalsIgnoreCase("blank")) {
         statusNavigation = StatusNavigation.OK;
         }

         if (enlaceDirectorios.length > 2) {
         if (enlaceDirectorios[2].equalsIgnoreCase("barMenu")) {
         statusNavigation = StatusNavigation.OK;
         }
         }

         if (statusNavigation == StatusNavigation.FORBIDDEN) {
         //Verificamos en la lista de peticiones ajax permitidas                
         if (isUrlAjax(enlace, request)) {
         statusNavigation = StatusNavigation.OK;
         }
         }

         //Si sigue prohibido entonces buscar en las opciones permitidas para el usuario
         //Aqui ya no busca en index ni blank
         if (statusNavigation == StatusNavigation.FORBIDDEN) {

         Submenu submenu = submenuService.obtenerPorEnlace("/" + enlaceDirectorios[1]);

         if (submenu != null) {

         String opcion = "";

         if (enlaceDirectorios.length > 2) {
         opcion = enlaceDirectorios[2];
         }

         //si la el enlace viene con una opcion se verifica
         if (opcion.length() > 0) {

         //Obtenemos las opciones del usuario logeado
         List<Opcion> opcionesLogeado = opcionService.listarParaSession(getUsuario(request).getIdUsuario(), submenu.getIdSubmenu());

         //Verficar si la opcion a la que ingreso está dentro de las opciones del usuario logeado
         for (int i = 0; i < opcionesLogeado.size(); ++i) {
         if (opcionesLogeado.get(i).getAppOpcion().equalsIgnoreCase(opcion)) {
         statusNavigation = StatusNavigation.OK;
         break;
         }
         }
         } else {
         statusNavigation = StatusNavigation.OK;
         }
         }
         }
         } else {
         isUrlAjax = isUrlAjax(enlace, request);
         statusNavigation = StatusNavigation.UNAUTHORIZED;
         }
         }

         switch (statusNavigation) {
         case FORBIDDEN:
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         return false;
         case UNAUTHORIZED:
         if (isUrlAjax == false) {
         response.sendRedirect(request.getContextPath() + "/index");
         }
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         return false;
         case OK:
         return true;
         default:
         return false;
         }*/
    }

    private String getUrl(HttpServletRequest request) {
        return request.getRequestURI().replace(request.getContextPath(), "");
    }

    private Usuario getUsuario(HttpServletRequest request) {
        return (Usuario) request.getSession().getAttribute("usuarioIniciado");
    }

    public static int getIdUsuario(HttpServletRequest request) {
        return ((Usuario) request.getSession().getAttribute("usuarioIniciado")).getIdUsuario();
    }

    private boolean isUrlAjax(String enlace, HttpServletRequest request) {

        boolean isUrlAjax = false;
        String[] enlacesDirectorios = enlace.split("/");
        if (enlacesDirectorios.length > 3) {
            enlace = "/" + enlacesDirectorios[1] + "/" + enlacesDirectorios[2];
        }

        List<String> urlsAjax = (List<String>) request.getSession().getAttribute("urlsAjax");

        if (urlsAjax != null) {
            for (String url : urlsAjax) {
                if (enlace.equalsIgnoreCase(url.trim())) {
                    isUrlAjax = true;
                }
            }
        }
        return isUrlAjax;
    }

    private List<UrlAjax> getUrlsAjax() {

        List<UrlAjax> urlsAjax = new ArrayList<UrlAjax>();
        urlsAjax.add(new UrlAjax(1, "/Concepto/conceptosJSON"));
        urlsAjax.add(new UrlAjax(2, "/Concepto/conceptoJSON"));

        return urlsAjax;
    }

    private List<PageUrlAjax> getPageUrlAjax() {
        List<PageUrlAjax> pageUrlsAjax = new ArrayList<PageUrlAjax>();
        pageUrlsAjax.add(new PageUrlAjax(Arrays.asList(1,2), "/Concepto"));
        return pageUrlsAjax;
    }

    private enum StatusNavigation {

        FORBIDDEN,
        UNAUTHORIZED,
        OK
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void BussinessExceptionHandlerAjax(BusinessException businessException, HttpServletResponse response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String reponseText = mapper.writeValueAsString(new JsonResponse(false, businessException.getMensajesError()));
            response.setContentType("json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            PrintWriter out = response.getWriter();
            out.append(reponseText);
            out.close();
        } catch (IOException ex) {

        }
    }

    private class UrlAjax {

        private int id;
        private String url;

        public UrlAjax() {
        }

        public UrlAjax(int id, String url) {
            this.id = id;
            this.url = url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    private class PageUrlAjax {

        private List<Integer> idsUrlAjax;
        private String pageUrl;

        public PageUrlAjax() {
        }

        public PageUrlAjax(List<Integer> idsUrlAjax, String pageUrl) {
            this.idsUrlAjax = idsUrlAjax;
            this.pageUrl = pageUrl;
        }

        public List<Integer> getIdsUrlAjax() {
            return idsUrlAjax;
        }

        public void setIdsUrlAjax(List<Integer> idsUrlAjax) {
            this.idsUrlAjax = idsUrlAjax;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }
    }
}
