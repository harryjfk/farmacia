package pe.gob.minsa.farmacia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.domain.dto.AlmacenTree;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.services.impl.TipoAlmacenService;
import pe.gob.minsa.farmacia.services.impl.UbigeoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Almacen")
public class AlmacenController {

    @Autowired
    AlmacenService almacenService;

    @Autowired
    TipoAlmacenService tipoAlmacenService;

    @Autowired
    UbigeoService ubigeoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getAlmacenDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Almacen> almacenes = almacenService.listarPadres();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= almacenes.size() - 1; ++i) {

            Almacen c = almacenes.get(i);
            if (c.getDescripcion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                almacenes.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(almacenes, new Comparator<Almacen>() {
            @Override
            public int compare(Almacen o1, Almacen o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer) o1.getIdAlmacen()).compareTo(o2.getIdAlmacen()) * sortDirection;
                    case 1:
                        return o1.getDescripcion().toLowerCase().compareTo(o2.getDescripcion().toLowerCase()) * sortDirection;
                    case 2:
                        Integer cantHijosUno = o1.getCantidadHijos();
                        Integer cantHijosDos = o2.getCantidadHijos();
                        return cantHijosUno.compareTo(cantHijosDos) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(almacenes.size());

        if (almacenes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            almacenes = almacenes.subList(dataTablesParam.iDisplayStart, almacenes.size());
        } else {
            almacenes = almacenes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(almacenes);

        return managerDatatables;
    }

    private ManagerDatatables getSubAlmacenDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        String idAlmacenPadre = request.getParameter("idAlmacenPadre");
        List<Almacen> almacenes = almacenService.listarPorPadre(Integer.parseInt(idAlmacenPadre));

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= almacenes.size() - 1; ++i) {

            Almacen c = almacenes.get(i);
            if (c.getDescripcion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                almacenes.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(almacenes, new Comparator<Almacen>() {
            @Override
            public int compare(Almacen o1, Almacen o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return String.valueOf(o1.getIdAlmacen()).toLowerCase().compareTo(String.valueOf(o2.getIdAlmacen()).toLowerCase()) * sortDirection;
                    case 1:
                        return o1.getDescripcion().toLowerCase().compareTo(o2.getDescripcion().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(almacenes.size());

        if (almacenes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            almacenes = almacenes.subList(dataTablesParam.iDisplayStart, almacenes.size());
        } else {
            almacenes = almacenes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(almacenes);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarAlmacen(@RequestParam(required = false) String idAlmacenPadre, HttpServletRequest request) {
        return new ModelAndView("Almacen");
    }

    @RequestMapping(value = "/almacenesTree", method = RequestMethod.GET)
    @ResponseBody
    public List<AlmacenTree> almacenesTreeGET() {
        return almacenService.cargarTree();
    }

    @RequestMapping(value = "/almacenesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerAlmacenesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getAlmacenDatatables(request, response);
    }
    
    @RequestMapping(value = "/subalmacenesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerSubAlmacenesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSubAlmacenDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarAlmacenGET(HttpServletRequest request) {
        try{
            
            List<TipoAlmacen> tiposAlmacen = tipoAlmacenService.listarActivos();
            List<Ubigeo> departamentos = ubigeoService.listarDepartamentosActivos();
            List<Ubigeo> provincias = null;
            List<Ubigeo> distritos = null;
            
            ModelMap model = new ModelMap();            

            if(request.getParameter("idAlmacenPadre") != null){
                 Almacen almacen = almacenService.obtenerPorId(Integer.parseInt(request.getParameter("idAlmacenPadre")));
                 
                departamentos = ubigeoService.listarDepartamentosActivos();
                String idDepartamento;
                String idProvincia;

                if (almacen.getIdUbigeo() != null) {
                    idDepartamento = almacen.getIdUbigeo().substring(0, 2);
                    idProvincia = almacen.getIdUbigeo().substring(0, 4);
                    String idDistrito = almacen.getIdUbigeo();

                    provincias = ubigeoService.listarProvinciasActivos(idDepartamento, idProvincia);
                    distritos = ubigeoService.listarDistritosActivos(idProvincia, idDistrito);
                } else {
                    idDepartamento = null;
                    idProvincia = null;

                    provincias = new ArrayList<Ubigeo>();
                    distritos = new ArrayList<Ubigeo>();
                }
                 
                 model.put("almacenPadre", almacen);                 
            }else{
                provincias = new ArrayList<Ubigeo>();
                distritos = new ArrayList<Ubigeo>();
            }
            
            model.put("tiposAlmacen", tiposAlmacen);
            model.put("departamentos", departamentos);
            model.put("provincias", provincias);
            model.put("distritos", distritos);
            
            return new ModelAndView("Almacen/registrar", model);
        
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Almacen");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarAlmacenPOST(@RequestBody Almacen almacen, HttpServletRequest request) {

        try {
            almacen.setUsuarioCreacion(InterceptorSecurity.getIdUsuario(request));
            almacenService.insertar(almacen);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarAlmacenGET(@PathVariable int id, HttpServletRequest request) {
        try {
            Almacen almacen = almacenService.obtenerPorId(id);

            List<Ubigeo> departamentos = null;
            List<Ubigeo> provincias = null;
            List<Ubigeo> distritos = null;

            departamentos = ubigeoService.listarDepartamentosActivos();
            String idDepartamento;
            String idProvincia;

            if (almacen.getIdUbigeo() != null) {
                idDepartamento = almacen.getIdUbigeo().substring(0, 2);
                idProvincia = almacen.getIdUbigeo().substring(0, 4);
                String idDistrito = almacen.getIdUbigeo();

                provincias = ubigeoService.listarProvinciasActivos(idDepartamento, idProvincia);
                distritos = ubigeoService.listarDistritosActivos(idProvincia, idDistrito);
            } else {
                idDepartamento = null;
                idProvincia = null;

                provincias = new ArrayList<Ubigeo>();
                distritos = new ArrayList<Ubigeo>();
            }

            List<TipoAlmacen> tiposAlmacen = tipoAlmacenService.listarActivos(almacen.getIdTipoAlmacen());

            ModelMap model = new ModelMap();
            model.put("almacen", almacen);
            model.put("tiposAlmacen", tiposAlmacen);
            model.put("departamentos", departamentos);
            model.put("provincias", provincias);
            model.put("distritos", distritos);

            return new ModelAndView("Almacen/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Almacen");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarAlmacen(@RequestBody Almacen almacen, HttpServletRequest request) {
        try {
            almacen.setUsuarioModificacion(InterceptorSecurity.getIdUsuario(request));
            almacenService.actualizar(almacen);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id, HttpServletRequest request) {
        try {            
            almacenService.cambiarEstado(id, InterceptorSecurity.getIdUsuario(request));
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarAlmacen(@PathVariable int id) {
        try {
            almacenService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/subalmacen/{idAlmacenPadre}", method = RequestMethod.GET)
    public ModelAndView subalmacen(@PathVariable int idAlmacenPadre, HttpServletRequest request) {
        try {
            Almacen almacen = almacenService.obtenerPorId(idAlmacenPadre);            
            return new ModelAndView("Almacen/subalmacen", "almacen", almacen);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Almacen");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getAlmacenDatatables(request, response);

        return new ModelAndView("AlmacenPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getAlmacenDatatables(request, response);

        return new ModelAndView("AlmacenExcel", "Data", managerDatatables.getAaData());
    }
    
    @RequestMapping(value = "/pdfAll", method = RequestMethod.GET)
    public ModelAndView rptPDFAll(HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("AlmacenAllPDF", "Data", almacenService.listar());
    }
}
