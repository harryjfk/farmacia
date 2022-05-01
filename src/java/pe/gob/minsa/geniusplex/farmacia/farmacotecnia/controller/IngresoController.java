package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.IngresoDto;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.IngresoService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/ingresos/*")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GpAlmacenService almacenService;

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public ModelAndView consultar(@PathVariable long idModulo, HttpServletRequest request, ModelAndView model) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("anios", periodoService.listarAnios());
            model.addObject("almacenes", almacenService.listarPorModulo(idModulo));
            model.setViewName("Ingresos/consultar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "ingresosJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables getPedidosJSON(@PathVariable long idModulo, HttpServletRequest request) {
        DataTablesParam params = DataTablesParamUtility.getParam(request);
        ManagerDatatables manager = new ManagerDatatables();

        int iDisplayStart, iDisplayLength;
        if (params != null) {
            iDisplayStart = params.iDisplayStart;
            iDisplayLength = params.iDisplayLength + 1;
        } else {
            iDisplayStart = 0;
            iDisplayLength = 1;
            params = new DataTablesParam();
            params.sEcho = "2";
        }

        Object[] sort = new Object[]{
            params.iSortingCols == 0 ? null : 1,
            params.iSortColumnIndex,
            params.sSortDirection
        };
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String search = params.sSearch;
        String desde = request.getParameter("fechaDesde");
        String hasta = request.getParameter("fechaHasta");
        Date fDesde = null;
        Date fHasta = null;
        if(desde != null && desde.length() > 0) {
            try {
                fDesde = sdf.parse(desde);
            } catch (ParseException ex) {
                Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(hasta != null && hasta.length() > 0) {
            try {
                fHasta = sdf.parse(hasta);
            } catch (ParseException ex) {
                Logger.getLogger(IngresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        List<Materias> lista = ingresoService.listarIngresosPorAlmacen(idAlmacen, fDesde, fHasta, search, range);
        request.getSession().setAttribute("ingresos_dto_print", lista);//para imprimir en caso de que desee imprimir
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = lista.size();//notar que este es el total sin filtrar
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }
    
    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        List<IngresoDto> printData = (List<IngresoDto> )request.getSession().getAttribute("ingresos_dto_print");
        if(printData == null) {
            printData = new ArrayList<IngresoDto>();
        }
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Código", "Insumo", "Unidad de Medida", "Precio", "Ingreso");
        List<String> contentFields = Arrays.asList("id", "nombre", "unidad:nombreUnidadMedida", "precio", "cantidad");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 10);
        contentColumns.put(1, 40);
        contentColumns.put(2, 30);
        contentColumns.put(3, 10);
        contentColumns.put(4, 10);
        
        model.addObject("Title", "Ingresos");
        model.addObject("ContentData", printData);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericExcel");
        return model;
    }
}
