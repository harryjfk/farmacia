/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.TarjetaControlDto;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.TarjetaCtrlVisService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/tarjetacontrolvisible/*")
public class TarjetaControlVisibleController {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private FormaFarmaceuticaService ffService;
    @Autowired
    private TipoProductoService tipoProductoService;
    @Autowired
    private UnidadMedidaService umService;
    @Autowired
    private TarjetaCtrlVisService tcvService;

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public ModelAndView consultar(@PathVariable long idModulo, HttpServletRequest request, ModelAndView model) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("anios", periodoService.listarAnios());
            model.addObject("almacenes", almacenService.listarPorModulo(idModulo));
            model.addObject("formasFarmaceuticas", ffService.listarActivos());
            model.addObject("tiposProducto", tipoProductoService.listarActivos());
            model.addObject("unidadesMedida", umService.listarActivos());
            model.setViewName("TarjetaControlVisible/consultar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "JSON", method = RequestMethod.GET)
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
        Calendar calendar = new GregorianCalendar();
        String search = params.sSearch;
        String desde = request.getParameter("fechaDesde");
        String hasta = request.getParameter("fechaHasta");
        Date fDesde = null;
        Date fHasta = null;
        if (desde != null && desde.length() > 0) {
            fDesde = calendar.getTime();
            fDesde.setTime(Long.parseLong(desde));
        }
        if (hasta != null && hasta.length() > 0) {
            fHasta = calendar.getTime();
            fHasta.setTime(Long.parseLong(hasta));
        }
       
        List<TarjetaControlDto> lista = tcvService.obtenerTarjetasCtrlVis(fDesde, fHasta, range);

        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = lista.size();
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "TarjetaControlVisible/PDF", method = RequestMethod.GET)
    public ModelAndView rptPdfTarjetaControlVisible(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getPedidosJSON(idModulo, request);
        return new ModelAndView("TCVPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "TarjetaControlVisible/Excel", method = RequestMethod.GET)
    public ModelAndView rptExcelTarjetaControlVisible(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getPedidosJSON(idModulo, request);
        return new ModelAndView("TCVExcel", "Data", managerDatatables.getAaData());
    }
}
