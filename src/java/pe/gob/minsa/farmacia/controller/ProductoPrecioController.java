package pe.gob.minsa.farmacia.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.ProductoPrecio;
import pe.gob.minsa.farmacia.domain.dto.HistoricoPrecioDto;
import pe.gob.minsa.farmacia.domain.dto.PrecioUltimoDto;
import pe.gob.minsa.farmacia.services.impl.ParametroService;
import pe.gob.minsa.farmacia.services.impl.ProductoPrecioService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
public class ProductoPrecioController {

    @Autowired
    ProductoPrecioService productoPrecioService;
    
    @Autowired
    ParametroService parametroService;
    
    JsonResponse jsonResponse;

    public ManagerDatatables getPreciosDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<PrecioUltimoDto> precios = productoPrecioService.listarUltimoPrecio(dataTablesParam.sSearch);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(precios.size());

        if (precios.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            precios = precios.subList(dataTablesParam.iDisplayStart, precios.size());
        } else {
            precios = precios.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(precios);

        return managerDatatables;
    }

    @RequestMapping(value = "/Precio", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView listarPrecio() {
        
        ModelMap model = new ModelMap();
        model.put("porcentajeDistribucion", parametroService.obtenerPorNombre("DISTRIBUCION").getValor());
        model.put("porcentajeOperacion", parametroService.obtenerPorNombre("OPERACION").getValor());
        return new ModelAndView("Precio", model);
    }

    @RequestMapping(value = "/Precio/preciosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerPreciosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getPreciosDatatables(request, response);
    }

    @RequestMapping(value = "/Precio/historicoJSON/{idProducto}")
    @ResponseBody
    public List<HistoricoPrecioDto> obtenerHistoricoPrecio(@PathVariable int idProducto) {
        List<ProductoPrecio> productosPrecio = productoPrecioService.listarPorProducto(idProducto);
        List<HistoricoPrecioDto> historicoPrecioDtos = new ArrayList<HistoricoPrecioDto>();

        for (ProductoPrecio p : productosPrecio) {
            historicoPrecioDtos.add(
                    new HistoricoPrecioDto(
                            p.getFechaRegistro(),
                            p.getTipoPrecio(),
                            p.getPrecioAdquisicion(), p.getPrecioDistribucion(), p.getPrecioOperacion()
                    )
            );
        }

        return historicoPrecioDtos;
    }
    
    @RequestMapping(value = "/Precio/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarPrecio(@RequestBody ProductoPrecio productoPrecio){
        try{
            productoPrecioService.insertar(productoPrecio);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        return jsonResponse;
    }
    
    @RequestMapping(value = "/Precio/excel", method = RequestMethod.GET)
    public ModelAndView rptProductoPrecioExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getPreciosDatatables(request, response);

        return new ModelAndView("ProductoPrecioExcel", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/Precio/pdf", method = RequestMethod.GET)
    public ModelAndView rptProductoPrecioPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getPreciosDatatables(request, response);

        return new ModelAndView("ProductoPrecioPDF", "Data", managerDatatables.getAaData());
    }    
}
