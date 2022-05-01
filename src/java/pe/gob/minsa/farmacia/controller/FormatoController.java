package pe.gob.minsa.farmacia.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Ici;
import pe.gob.minsa.farmacia.domain.Idi;
import pe.gob.minsa.farmacia.domain.Ime;
import pe.gob.minsa.farmacia.domain.ImeB;
import pe.gob.minsa.farmacia.domain.Ime_III;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.services.impl.IciDetalleService;
import pe.gob.minsa.farmacia.services.impl.IciService;
import pe.gob.minsa.farmacia.services.impl.IdiDetalleService;
import pe.gob.minsa.farmacia.services.impl.IdiService;
import pe.gob.minsa.farmacia.services.impl.ImeService;
import pe.gob.minsa.farmacia.services.impl.Ime_IIIService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProcesoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.TipoSuministroService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
public class FormatoController {

    @Autowired
    PeriodoService periodoService;

    @Autowired
    IciDetalleService iciDetalleService;

    @Autowired
    IdiDetalleService idiDetalleService;

    @Autowired
    IciService iciService;

    @Autowired
    IdiService idiService;
    
    @Autowired
    TipoSuministroService tipoSuministroService;

    @Autowired
    AlmacenService almacenService;

    @Autowired
    TipoProductoService tipoProductoService;
    
    @Autowired
    TipoProcesoService tipoProcesoService;
    
    @Autowired
    ImeService imeService;

    @Autowired
    Ime_IIIService ime_iiiService;

    String SessionDetGasto = "detalleGastos";
    JsonResponse jsonResponse;

    @RequestMapping(value = "/FormatoICI", method = RequestMethod.GET)
    public ModelAndView formatoICI() {
        List<Almacen> listaAlmacenes = almacenService.listarPadres();
        int idAlmacenLogistica = almacenService.obtenerIdAlmacenLogistica();
        int idAlmacenEspecializado = almacenService.obtenerIdAlmacenEspecializado();
        for(int i = 0; i<listaAlmacenes.size();i++){
            if(listaAlmacenes.get(i).getIdAlmacen() == idAlmacenLogistica) listaAlmacenes.remove(i);
            if(listaAlmacenes.get(i).getIdAlmacen() == idAlmacenEspecializado) listaAlmacenes.remove(i);
        }
        
        ModelMap model = new ModelMap();
        model.put("anios", periodoService.listarAnios());
        model.put("suministros", tipoSuministroService.listar());
        model.put("almacenes", listaAlmacenes);
        return new ModelAndView("FormatoICI", model);
    }

    @RequestMapping(value = "/FormatoIDI", method = RequestMethod.GET)
    public ModelAndView formatoIDI() {
        ModelMap model = new ModelMap();
        model.put("anios", periodoService.listarAnios());
        model.put("suministros", tipoSuministroService.listar());
        model.put("tiposProducto", tipoProductoService.listar());
        model.put("procesos", tipoProcesoService.listarActivos());
        return new ModelAndView("FormatoIDI", model);
    }
    
    @RequestMapping(value = "/FormatoIME", method = RequestMethod.GET)
    public ModelAndView formatoIME() {
        
        List<Almacen> listaAlmacenes = almacenService.listarPadres();
        int idAlmacenLogistica = almacenService.obtenerIdAlmacenLogistica();
        int idAlmacenEspecializado = almacenService.obtenerIdAlmacenEspecializado();
        for(int i = 0; i<listaAlmacenes.size();i++){
            Almacen alm = listaAlmacenes.get(i);
            if(listaAlmacenes.get(i).getIdAlmacen() == idAlmacenLogistica) listaAlmacenes.remove(i);
            if(listaAlmacenes.get(i).getIdAlmacen() == idAlmacenEspecializado) listaAlmacenes.remove(i);
        }
        for(int i = 0; i<listaAlmacenes.size();i++){
            Almacen alm = listaAlmacenes.get(i);
            listaAlmacenes.get(i).setIdAlmacen(alm.getIdAlmacen()+1); //Almacen hijo SISMED
        }
        
        
        ModelMap model = new ModelMap();
        model.put("anios", periodoService.listarAnios());
        model.put("suministros", tipoSuministroService.listar());
        model.put("almacenes", listaAlmacenes);
        return new ModelAndView("FormatoIME", model);
    }
    
    @RequestMapping(value = "/FormatoICI/existe", method = RequestMethod.POST)
    @ResponseBody
    public Ici existeFormatoICI(@RequestParam int idPeriodo, @RequestParam int idAlmacen, @RequestParam int idTipoSuministro) {        
        return iciService.obtenerPorPeriodo(idPeriodo, idAlmacen, idTipoSuministro);
    }
    
    @RequestMapping(value = "/FormatoIDI/existe", method = RequestMethod.POST)
    @ResponseBody
    public Idi existeFormatoIDI(@RequestParam int idPeriodo, @RequestParam int idTipoSuministro, @RequestParam int idTipoProceso) {        
        return idiService.obtenerPorPeriodo(idPeriodo, idTipoSuministro, idTipoProceso);
    }
    
    @RequestMapping(value = "/FormatoIME/existe", method = RequestMethod.POST)
    @ResponseBody
    public Ime existeFormatoIME(@RequestParam int idAlmacen) {        
        return imeService.obtenerIme(idAlmacen);
    }
    
    @RequestMapping(value = "/FormatoIDI/procesar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse procesarFormatoIDI(@RequestParam int idPeriodo, @RequestParam int idTipoSuministro, @RequestParam int idTipoProceso) {
        try {
            idiDetalleService.procesar(idPeriodo, idTipoSuministro, idTipoProceso);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se procesó correctamente"));
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/FormatoICI/procesar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse procesarFormatoICI(@RequestParam int idAlmacen, @RequestParam int idPeriodo/**/, @RequestParam int idTipoSuministro) {
        try {
            iciDetalleService.procesar(idAlmacen, idPeriodo/**/, idTipoSuministro);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se procesó correctamente"));
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        return jsonResponse;
    }
    
    @RequestMapping(value = "/FormatoIME/procesar", method = RequestMethod.POST)
    public @ResponseBody JsonResponse procesarFormatoIME(@RequestBody Map mapa, HttpServletRequest rq, HttpSession sesion) {
        
        Timestamp fechaDesde = new Timestamp(Long.parseLong(rq.getParameter("fechaDesde")));
        Timestamp fechaHasta = new Timestamp(Long.parseLong(rq.getParameter("fechaHasta")));
        int idAlmacen = Integer.parseInt(rq.getParameter("idAlmacen"));
        
        ImeB imeB = new ImeB();
        imeB.setAVenta(mapa.get("aVenta").toString());
        imeB.setAIntSan(mapa.get("aIntsan").toString());
        imeB.setRecetasDemandaE(Integer.parseInt(mapa.get("recetasDemandaE").toString()));
        imeB.setRecetasSisE(Integer.parseInt(mapa.get("recetasSisE").toString()));
        imeB.setRecetasIntsanE(Integer.parseInt(mapa.get("recetasIntsanE").toString()));
        imeB.setRecetasSoatE(Integer.parseInt(mapa.get("recetasSoatE").toString()));
        imeB.setRecetasExoneracionE(Integer.parseInt(mapa.get("recetasExoneracionE").toString()));
        imeB.setRecetasExternasE(Integer.parseInt(mapa.get("recetasExternasE").toString()));
        
        imeB.setRecetasDemandaD(Integer.parseInt(mapa.get("recetasDemandaD").toString()));
        imeB.setRecetasSisD(Integer.parseInt(mapa.get("recetasSisD").toString()));
        imeB.setRecetasIntsanD(Integer.parseInt(mapa.get("recetasIntsanD").toString()));
        imeB.setRecetasSoatD(Integer.parseInt(mapa.get("recetasSoatD").toString()));
        imeB.setRecetasExoneracionD(Integer.parseInt(mapa.get("recetasExoneracionD").toString()));
        imeB.setRecetasExternasD(Integer.parseInt(mapa.get("recetasExternasD").toString()));
        
        imeB.setNumSerie(mapa.get("numSerie").toString());
        imeB.setNumBoletaDe(mapa.get("numBoletaDe").toString());
        imeB.setNumBoletaA(mapa.get("numBoletaA").toString());
        imeB.setBCredito(mapa.get("bcredito").toString());
        imeB.setBIntSan(mapa.get("bintSan").toString());
        imeB.setBOtros(mapa.get("botros").toString());
        imeB.setBSis(mapa.get("bsis").toString());
        imeB.setBSoat(mapa.get("bsoat").toString());
        imeB.setBdn(mapa.get("bdn").toString());
        imeB.setExonNegativo(mapa.get("exonNegativo").toString());
        imeB.setTotalGAdmin(mapa.get("totalGAdmin").toString());
        imeB.setTotalGastosAbastecimiento(mapa.get("totalGastosAbastecimiento").toString());
        
        ArrayList<Ime_III> detGasto;
        
        if(sesion.getAttribute(SessionDetGasto)==null){
            detGasto = new ArrayList<Ime_III>();
        }else{
            detGasto = (ArrayList<Ime_III>)sesion.getAttribute(SessionDetGasto);
        }
                
        try {
            int idIme = imeService.procesar(fechaDesde, fechaHasta, idAlmacen, imeB, detGasto);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se procesó correctamente con el n&uacute;mero "+String.valueOf(idIme)+"."));
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/FormatoIME/procesar2", method = RequestMethod.POST)
    public @ResponseBody JsonResponse procesarFormatoIME2(@RequestBody Map mapa, HttpServletRequest rq, HttpSession sesion) {
        
        Timestamp fechaDesde = new Timestamp(Long.parseLong(rq.getParameter("fechaDesde")));
        Timestamp fechaHasta = new Timestamp(Long.parseLong(rq.getParameter("fechaHasta")));

        ImeB imeB = new ImeB();
        imeB.setNumSerie(mapa.get("NumSerie").toString());
        imeB.setNumBoletaDe(mapa.get("NumBoletaDe").toString());
        imeB.setNumBoletaA(mapa.get("NumBoletaA").toString());
        imeB.setAVenta(mapa.get("AVenta").toString());
        imeB.setRecetasDemandaD(Integer.parseInt(mapa.get("RecetasDemandaD").toString()));
        imeB.setRecetasDemandaE(Integer.parseInt(mapa.get("RecetasDemandaE").toString()));
        imeB.setRecetasSisD(Integer.parseInt(mapa.get("RecetasSisD").toString()));
        imeB.setRecetasSisE(Integer.parseInt(mapa.get("RecetasSisE").toString()));
        imeB.setRecetasIntsanD(Integer.parseInt(mapa.get("RecetasIntsanD").toString()));
        imeB.setRecetasIntsanE(Integer.parseInt(mapa.get("RecetasIntsanE").toString()));
        imeB.setRecetasSoatD(Integer.parseInt(mapa.get("RecetasSoatD").toString()));
        imeB.setRecetasSoatE(Integer.parseInt(mapa.get("RecetasSoatE").toString()));
        imeB.setRecetasExoneracionD(Integer.parseInt(mapa.get("RecetasExoneracionD").toString()));
        imeB.setRecetasExoneracionE(Integer.parseInt(mapa.get("RecetasExoneracionE").toString()));
        imeB.setDSoat(Float.parseFloat(mapa.get("DSoat").toString()));
        imeB.setESoat(Float.parseFloat(mapa.get("ESoat").toString()));
        imeB.setDSis(Float.parseFloat(mapa.get("DSis").toString()));
        imeB.setESis(Float.parseFloat(mapa.get("ESis").toString()));
        imeB.setDIntsan(Float.parseFloat(mapa.get("DIntsan").toString()));
        imeB.setEIntsan(Float.parseFloat(mapa.get("EIntsan").toString()));
        imeB.setCuentaCobrarAcumMesAnt_Ventas(Float.parseFloat(mapa.get("CuentaCobrarAcumMesAnt_Ventas").toString()));
        imeB.setCuentaCobrarAcumMesAnt_Soat(Float.parseFloat(mapa.get("CuentaCobrarAcumMesAnt_Soat").toString()));
        imeB.setCuentaCobrarAcumMesAnt_Sis(Float.parseFloat(mapa.get("CuentaCobrarAcumMesAnt_Sis").toString()));
        imeB.setCuentaCobrarAcumMesAnt_InterSanit(Float.parseFloat(mapa.get("CuentaCobrarAcumMesAnt_InterSanit").toString()));
        imeB.setSaldoDisponibleMesAnt_Medicamentos(Float.parseFloat(mapa.get("SaldoDisponibleMesAnt_Medicamentos").toString()));
        imeB.setSaldoDisponibleMesAnt_GastosAdmin(Float.parseFloat(mapa.get("SaldoDisponibleMesAnt_GastosAdmin").toString()));
        imeB.setExoneraciones_negativo(Float.parseFloat(mapa.get("Exoneraciones_negativo").toString()));
        imeB.setTotalAbastecimientoMes(Float.parseFloat(mapa.get("TotalAbastecimientoMes").toString()));
        //
            
        ArrayList<Ime_III> detGasto;
        
        if(sesion.getAttribute(SessionDetGasto)==null){
            detGasto = new ArrayList<Ime_III>();
        }else{
            detGasto = (ArrayList<Ime_III>)sesion.getAttribute(SessionDetGasto);
        }
        
        try {
            int idIme = imeService.procesar2(fechaDesde, fechaHasta, imeB, detGasto);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se procesó correctamente con el n&uacute;mero "+String.valueOf(idIme)+"."));
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        return jsonResponse;
       
        //return new JsonResponse(true, Arrays.asList("OKIS"));
    }
    
    @RequestMapping(value = "/FormatoICI/excel", method = RequestMethod.GET)
    public ModelAndView rptFormatoIciExcel(@RequestParam int idPeriodo, @RequestParam int idAlmacen, @RequestParam int idTipoSuministro) {
        Ici ici = iciService.obtenerPorPeriodo(idPeriodo, idAlmacen, idTipoSuministro);

        ModelMap model = new ModelMap();
        model.put("Data", iciDetalleService.listarPorIci(ici.getIdICI()));
        return new ModelAndView("IciExcel", model);
    }
    
    @RequestMapping(value = "/FormatoICI/consulta", method = RequestMethod.GET)
    public ModelAndView rptFormatoIciExcelConsulta(@RequestParam Long fechaDesde, @RequestParam Long fechaHasta, @RequestParam int idAlmacen, @RequestParam int idTipoSuministro) {        
        ModelMap model = new ModelMap();
        model.put("Data", iciDetalleService.listarPorFechas(new Timestamp(fechaDesde), new Timestamp(fechaHasta), idAlmacen, idTipoSuministro));
        return new ModelAndView("IciExcel", model);
    }
    
    @RequestMapping(value = "/FormatoIDI/consulta", method = RequestMethod.GET)
    public ModelAndView rptFormatoIdiExcel(@RequestParam Long fechaDesde, @RequestParam Long fechaHasta, @RequestParam int idTipoSuministro, int idTipoProceso) {
        ModelMap model = new ModelMap();
        model.put("Data", idiDetalleService.listarPorFechas(new Timestamp(fechaDesde), new Timestamp(fechaHasta), idTipoSuministro, idTipoProceso));
        return new ModelAndView("IdiExcel", model);
    }

    @RequestMapping(value = "/FormatoIDI/excel", method = RequestMethod.GET)
    public ModelAndView rptFormatoIdiExcel(@RequestParam int idPeriodo, @RequestParam int idTipoSuministro, @RequestParam int idTipoProceso) {
        Idi idi = idiService.obtenerPorPeriodo(idPeriodo, idTipoSuministro, idTipoProceso);

        ModelMap model = new ModelMap();
        model.put("Data", idiDetalleService.listarPorIdi(idi.getIdIDI()));
        return new ModelAndView("IdiExcel", model);
    }
    
    @RequestMapping(value = "/FormatoIME/pdf", method = RequestMethod.GET)
    public ModelAndView rptFormatoImePDF() {
        
        return new ModelAndView("FormatoImePDF");
    }
    //@TERIMNAR
    @RequestMapping(value = "/FormatoIME/excel", method = RequestMethod.GET)
    public ModelAndView rptFormatoImeExcel(@RequestParam int idAlmacen) {
        
        Ime ime = imeService.obtenerIme(idAlmacen);
        
        ModelMap model = new ModelMap();
        model.put("IME", ime);
        model.put("detalle", ime_iiiService.listar(ime.getIdIme()));
        return new ModelAndView("ImeExcel", model);
    }
    
    @RequestMapping(value = "/FormatoIME/excel2", method = RequestMethod.GET)
    public ModelAndView rptFormatoImeExcel2() {
        
        Ime ime = imeService.obtenerIme(100);
        
        ModelMap model = new ModelMap();
        model.put("IME", ime);
        model.put("detalle", ime_iiiService.listar(ime.getIdIme()));
        return new ModelAndView("ImeExcel", model);
    }
    
    @RequestMapping(value = "/FormatoIME/agregarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse formatoImeAgregarDetalle(@RequestBody Ime_III detalle,@RequestParam String fecha, HttpSession sesion){        
        List<Ime_III> detGastos;
        
        jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        Timestamp fechaGasto = new Timestamp(Long.parseLong(fecha));
        detalle.setFecha(fechaGasto);
        if(sesion.getAttribute(SessionDetGasto)==null){
            detGastos = new ArrayList<Ime_III>();
        }else{
           detGastos = (List<Ime_III>) sesion.getAttribute(SessionDetGasto);
        }
        
        detGastos.add(detalle);
        
        if (mensajeRespuesta.isEmpty() == false) {
            jsonResponse.setEstado(false);
            jsonResponse.setMensajesRepuesta(mensajeRespuesta);
        } else {
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se agregó el detalle."));
            sesion.setAttribute(SessionDetGasto, detGastos);
        }
        
        return jsonResponse;
        
    }
    
    @RequestMapping(value="/FormatoIME/cargarDetalle")
    @ResponseBody
    public List<Ime_III> formatoImeListarDetalle(HttpSession sesion){
        return (List<Ime_III>)sesion.getAttribute(SessionDetGasto);
    }
    
    
    @RequestMapping(value="/FormatoIME/borrarDetalle")
    @ResponseBody
    public JsonResponse formatoImeBorrarDetalle(HttpSession sesion){
        sesion.removeAttribute(SessionDetGasto);
        return new JsonResponse(true, Arrays.asList("Detalles eliminados"));
    }
    
    
    @RequestMapping(value="/FormatoIME/quitarDetalle")
    @ResponseBody
    public JsonResponse formatoImeQuitarDetalle(HttpSession sesion, HttpServletRequest rq){

        int indice = Integer.parseInt(rq.getParameter("indice"));
        List<Ime_III> lista = (List<Ime_III>)sesion.getAttribute(SessionDetGasto);
        for(int i = 0; i<lista.size(); i++){
            if(i == indice) lista.remove(i);
        }
        sesion.setAttribute(SessionDetGasto, lista);
        
        return new JsonResponse(true, Arrays.asList("Eliminado"));
    }
}