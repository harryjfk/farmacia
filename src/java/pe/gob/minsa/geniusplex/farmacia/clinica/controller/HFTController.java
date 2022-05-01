package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.HFT;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.HFTMedicamento;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.HFTMedicamentoService;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.HFTService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/hft/*")
public class HFTController {

    @Autowired
    private HFTService hftService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private GpProductoService productoService;

    @Autowired
    private HFTMedicamentoService medicamentoService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Procesar Hoja Farmacoterapeutica");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Nombre DCI", "Dosis", "F.F", "Via", "Frecuencia", "Total Med Alt", "Interv.Farm/Priv", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,producto.descripcion,dosis,producto.idFormaFarmaceutica.nombreFormaFarmaceutica,via,frecuencia,total,prm");
        model.addAttribute("findItem", "");
        return "HFT/procesar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultar(Model model) {
        model.addAttribute("viewTitle", "Consultar Hoja Farmacoterapeutica");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Nombre DCI", "Dosis", "F.F", "Via", "Frecuencia", "Total Med Alt", "Interv.Farm/Priv"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,producto.descripcion,dosis,producto.idFormaFarmaceutica.nombreFormaFarmaceutica,via,frecuencia,total,prm");
        model.addAttribute("findItem", "");
        return "HFT/consultar";
    }

    @RequestMapping(value = "guardarDatos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<HFT>> insertarHFT(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<HFT>> response = new AjaxResponse<List<HFT>>();
        HFT hft;
        boolean exist = false;

        if (!"".equals(request.getParameter("id"))) {
            long id = Long.parseLong(request.getParameter("id"));
            hft = hftService.obtenerPorId(id);
            exist = true;
        } else {
            hft = new HFT();
        }
        getData(hft, request);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (!exist) {
            hft.setUsuarioCreacion(usuario.getIdUsuario());
        }
        hft.setUsuarioModificacion(usuario.getIdUsuario());
        hft.setIdModulo(idModulo);

        ArrayList<HFT> data = new ArrayList<HFT>();
        boolean result;
        if (exist) {
            result = hftService.actualizar(hft);
        } else {
            result = hftService.insertar(hft);
        }
        data.add(hft);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el HFT.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este HFT.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<HFT>> insertarMedicamentos(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<HFT>> response = new AjaxResponse<List<HFT>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        long id = Long.parseLong(request.getParameter("hft"));
        HFT hft = hftService.obtenerReferencia(id);

        int idProducto = Integer.parseInt(request.getParameter("producto"));
        GpProducto producto = productoService.obtenerPorId(idProducto);
        String dosis = request.getParameter("dosis");
        String via = request.getParameter("via");
        String total = request.getParameter("total");
        String prm = request.getParameter("prm");
        String frecuencia = request.getParameter("frecuencia");

        HFTMedicamento medicamento = new HFTMedicamento();
        medicamento.setUsuarioCreacion(usuario.getIdUsuario());
        medicamento.setUsuarioModificacion(usuario.getIdUsuario());
        medicamento.setProducto(producto);
        medicamento.setDosis(dosis);
        medicamento.setPRM(prm);
        medicamento.setFrecuencia(frecuencia);
        medicamento.setVia(via);
        medicamento.setTotal(total);
        medicamento.setIdModulo(idModulo);

        List<HFTMedicamento> listMedicamentos = hft.getMedicamentos();

        if (listMedicamentos.contains(medicamento)) {
            response.addMensaje("Ya existe este medicamento.");
            response.setHasError(true);
            return response;
        }

        listMedicamentos.add(medicamento);
        hft.setMedicamentos(listMedicamentos);

        boolean result = hftService.actualizar(hft);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el HFT.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este HFT.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "getOneHft", method = RequestMethod.POST)
    public @ResponseBody
    HFT getOneHFT(@PathVariable long idModulo, HttpServletRequest request) {
        String idPaciente = request.getParameter("paciente");
        Paciente paciente = pacienteService.obtenerPorId(idPaciente);
        List<HFT> lista = hftService.listarPorModuloPaciente(idModulo, paciente);
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return new HFT();
    }

    @RequestMapping(value = "getMedicamentos")
    public @ResponseBody
    List<HFTMedicamento> getMedicamentos(@PathVariable long idModulo, HttpServletRequest request) {
        if ("".equals(request.getParameter("hft"))) {
            return null;
        }
        Long idHft = Long.parseLong(request.getParameter("hft"));
        HFT hft = hftService.obtenerPorId(idHft);
        hftService.sincronizar(hft);
        return hft.getMedicamentos();
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarCliente(@RequestParam long id) {
        AjaxResponse<HFTMedicamento> response = new AjaxResponse<HFTMedicamento>();

        boolean result = medicamentoService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el medicamento.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este medicamento");
        }
        return response;
    }

    public void getData(HFT hft, HttpServletRequest request) {
        String idPaciente = request.getParameter("idPaciente");
        Paciente paciente = pacienteService.obtenerPorId(idPaciente);
        String edad = request.getParameter("edad");
        String peso = request.getParameter("peso");
        String sis = request.getParameter("sis");
        String cama = request.getParameter("cama");
        String servicio = request.getParameter("servicio");
        String diagnostico = request.getParameter("diagnostico");
        String alergias = request.getParameter("alergias");
        String evolucion = request.getParameter("evolucion");
        String observaciones = request.getParameter("observaciones");
        String imc = request.getParameter("imc");
        String examenes = request.getParameter("examenes");
        String terapia = request.getParameter("terapia");

        hft.setPaciente(paciente);
        hft.setEdad(edad);
        hft.setPeso(peso);
        hft.setSis(sis);
        hft.setCama(cama);
        hft.setServicio(servicio);
        hft.setDiagnostico(diagnostico);
        hft.setAlergias(alergias);
        hft.setEvolucion(evolucion);
        hft.setObservacione(observaciones);
        hft.setImc(imc);
        hft.setExamenes(examenes);
        hft.setTerapia(terapia);

        setDates(request, hft);

    }

    private void setDates(HttpServletRequest request, HFT hft) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("fechaIngreso");
            String endDate = request.getParameter("fechaAlta");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(HFTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        hft.setFechaIngreso(start);
        hft.setFechaAlta(end);
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "HFTPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "HFTExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        long idHft = Long.parseLong(request.getParameter("hft"));
        HFT hft = hftService.obtenerPorId(idHft);
        List<HFTMedicamento> lista = hft.getMedicamentos();
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Nombre DCI", "Dosis", "F.F", "Via", "Frecuencia", "Total Med Alt", "Interv.Farm/Priv");
        List<String> contentFields = Arrays.asList("producto:descripcion", "dosis", "producto:idFormaFarmaceutica:nombreFormaFarmaceutica", "via", "frecuencia", "total", "PRM");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 5);
        contentColumns.put(2, 15);
        contentColumns.put(3, 15);
        contentColumns.put(4, 15);
        contentColumns.put(5, 10);
        contentColumns.put(6, 10);
        String fechaAlta = "";
        String fechaIngreso="";

        if (hft.getFechaAlta() != null) {
            fechaAlta = new SimpleDateFormat("dd/MM/yyyy").format(hft.getFechaAlta());
        }
        if (hft.getFechaIngreso() != null) {
            fechaIngreso = new SimpleDateFormat("dd/MM/yyyy").format(hft.getFechaIngreso());
        }
        model.addObject("Title", "Reporte Hoja Farmacoterapeutica");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        headerData.put("Paciente", hft.getPaciente().getNombres());
        headerData.put("Edad", hft.getEdad());
        headerData.put("Peso", hft.getPeso());
        headerData.put("Peso", hft.getPeso());
        headerData.put("Historia Clinica", hft.getPaciente().getHistoria());
        headerData.put("Sexo", hft.getPaciente().getSexo().toString());
        headerData.put("Fecha de Alta", fechaAlta);
        headerData.put("Fecha de Ingreso", fechaIngreso);
        headerData.put("No.Cama", hft.getCama());
        headerData.put("SIS", hft.getSis());
        headerData.put("Servicio", hft.getServicio());
        headerData.put("Diagnostico", hft.getDiagnostico());
        headerData.put("Alergias", hft.getAlergias());
        headerData.put("Alergias", hft.getAlergias());
        headerData.put("Evolución", hft.getEvolucion());
        headerData.put("Observaciones", hft.getObservacione());
        headerData.put("IMC", hft.getImc());
        headerData.put("Examenes", hft.getExamenes());
        headerData.put("Terapia", hft.getTerapia());
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        headerColumns.put(1, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

}
