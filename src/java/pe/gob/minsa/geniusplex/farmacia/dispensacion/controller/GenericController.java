/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.farmacia.domain.TipoDocumento;
import pe.gob.minsa.farmacia.services.impl.TipoDocumentoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Farmacia;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.FarmaciaService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.FormaPago;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.DiagnosticoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.FormaPagoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProcesoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProductoLoteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VendedorService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.dtos.GpProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpPersonalService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;

/**
 *
 *
 */
@Controller
public class GenericController {

    @Autowired
    TipoDocumentoService tdService;
    @Autowired
    FarmaciaService farmaciaService;
    @Autowired
    VendedorService vendedorService;
    @Autowired
    GpPersonalService gpPersonalService;
    @Autowired
    ProductoService productoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ProductoLoteService productoLoteService;
    @Autowired
    private DiagnosticoService diagnosticService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private ProcesoService procesoService;
    @Autowired
    private GpProductoService gpProductoService;
    @Autowired
    private GpAlmacenService gpAlmacenService;

    /**
     *
     * @param request
     * @return lista de personal
     */
    @RequestMapping(value = "/personal/listarPersonal", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables genericListarPersonal(HttpServletRequest request) {
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
        String search = params.sSearch;

        List<GpPersonal> lista = gpPersonalService.listarRango(range, search, sort);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = gpPersonalService.contarPaginado(search);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "/GenericTipoDocumento/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<TipoDocumento>> genericListarTipoDocumento() {
        List<TipoDocumento> resultados = tdService.listar();
        AjaxResponse<List<TipoDocumento>> response = new AjaxResponse<List<TipoDocumento>>(resultados);
        return response;
    }

    @RequestMapping(value = "/GenericFarmacia/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<Farmacia>> genericListarFarmacia() {
        List<Farmacia> resultados = farmaciaService.listar();
        AjaxResponse<List<Farmacia>> response = new AjaxResponse<List<Farmacia>>(resultados);
        return response;
    }

    @RequestMapping(value = "/GenericProducto/listarconstock", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<GpProductoDTO>> genericListarProductosConStocks() {
        AjaxResponse<List<GpProductoDTO>> response = new AjaxResponse<List<GpProductoDTO>>();
        response.setData(productoLoteService.listarConStock());
        return response;
    }

    @RequestMapping(value = "/GenericProducto/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<GpProducto>> genericListarProductos() {
        AjaxResponse<List<GpProducto>> response = new AjaxResponse<List<GpProducto>>();
        response.setData(gpProductoService.listar());
        return response;
    }

    @RequestMapping(value = "/GenericProducto/listarpaginado", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables genericListarProductosPaginado(HttpServletRequest request) {
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
        String search = params.sSearch;
        //TODO Cambiar esto por el servicio de productos
        List<GpPersonal> lista = gpPersonalService.listarRango(range, search, sort);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = gpPersonalService.contarPaginado(search);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "/GenericPaciente/listar", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables genericListarPacientes(HttpServletRequest request) {
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
        String search = params.sSearch;

        List<Paciente> lista = pacienteService.listarRango(range, search, sort);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = pacienteService.contarPaginado(search);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "/GenericProductoLote/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<ProductoLote>> genericListarProductoLote(@RequestParam long idModulo) {
        AjaxResponse<List<ProductoLote>> lista = new AjaxResponse<List<ProductoLote>>();
        lista.setData(productoLoteService.listarPorModulo(idModulo));
        return lista;
    }

    @RequestMapping(value = "/GenericDiagnostico/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<DiagnosticoCIE>> genericListarDiagnostico() {
        AjaxResponse<List<DiagnosticoCIE>> lista = new AjaxResponse<List<DiagnosticoCIE>>();
        lista.setData(diagnosticService.listarActivos());
        return lista;
    }

    @RequestMapping(value = "/GenericFormaPago/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<FormaPago>> genericListarFormaPago() {
        AjaxResponse<List<FormaPago>> lista = new AjaxResponse<List<FormaPago>>();
        lista.setData(formaPagoService.listar());
        return lista;
    }

    @RequestMapping(value = "/GenericProceso/listar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<Proceso>> genericListarPr() {
        AjaxResponse<List<Proceso>> lista = new AjaxResponse<List<Proceso>>();
        lista.setData(procesoService.listar());
        return lista;
    }

    @RequestMapping(value = "/GenericAlmacen/listar")
    public @ResponseBody
    AjaxResponse<List<GpAlmacen>> genericListarAlmacenes() {
        AjaxResponse<List<GpAlmacen>> response = new AjaxResponse<List<GpAlmacen>>();
        response.setData(gpAlmacenService.listar());
        return response;
    }

    @RequestMapping(value = "/GenericAlmacen/listarFisicos")
    public @ResponseBody
    AjaxResponse<List<GpAlmacen>> genericListarAlmacenesFisicos() {
        AjaxResponse<List<GpAlmacen>> response = new AjaxResponse<List<GpAlmacen>>();
        response.setData(gpAlmacenService.listarFisicos());
        return response;
    }
    
    @RequestMapping(value = "/GenericAlmacen/listarVirtuales")
    public @ResponseBody
    AjaxResponse<List<GpAlmacen>> genericListarAlmacenesViruales(@RequestParam int idPadre) {
        AjaxResponse<List<GpAlmacen>> response = new AjaxResponse<List<GpAlmacen>>();
        response.setData(gpAlmacenService.listarVirtuales(idPadre));
        return response;
    }
     
    @RequestMapping(value = "/GenericProductoLote/listarTodos", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<ProductoLote>> genericAllListarProductoLote() {
        AjaxResponse<List<ProductoLote>> lista = new AjaxResponse<List<ProductoLote>>();
        lista.setData(productoLoteService.listar());
        return lista;
    }
}
