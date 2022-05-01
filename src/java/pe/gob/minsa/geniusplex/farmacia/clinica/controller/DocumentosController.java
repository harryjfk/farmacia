/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.UploadFile;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.UploadFileService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;

/**
 *
 * @author stark
 */
@Controller
@RequestMapping("/{idModulo}/documentos/*")
public class DocumentosController {

    @Autowired
    ServletContext context;
    @Autowired
    UploadFileService uploadService;

    int tamanioDocumento;

    String rutaDocumentos;

    public void setTamanioDocumento(int tamanioDocumento) {
        this.tamanioDocumento = tamanioDocumento * 1024;
    }

    public void setRutaDocumentos(String rutaDocumentos) {
        this.rutaDocumentos = rutaDocumentos;
    }

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Registro UploadFile");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Documento", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "");
        model.addAttribute("findItem", "");
        return "Files/Listar";
    }

    @RequestMapping(value = "listarFormatoDevolucion", method = RequestMethod.GET)
    public String listarFormato(Model model) {
        model.addAttribute("viewTitle", "Registro Formato de Devolución");
        model.addAttribute("ajaxList", "getFormatoDevolucion");
        getConfig(model);
        model.addAttribute("action", "registrarFormato");
        return "Files/Listar";
    }

    @RequestMapping(value = "listarMedidasEducativas", method = RequestMethod.GET)
    public String listarMedidas(Model model) {
        model.addAttribute("viewTitle", "Registro Acciones Educativas y/o Preventivas de los Medicamentos");
        model.addAttribute("ajaxList", "getMedidas");
        getConfig(model);
        model.addAttribute("action", "registrarMedidas");
        return "Files/Listar";
    }

    @RequestMapping(value = "listarFormatoRAM", method = RequestMethod.GET)
    public String listarRam(Model model) {
        model.addAttribute("viewTitle", "Formato Ram");
        model.addAttribute("ajaxList", "getRam");
        getConfig(model);
        model.addAttribute("action", "registrarRam");
        return "Files/Listar";
    }

    @RequestMapping(value = "listarFormatoHFT", method = RequestMethod.GET)
    public String listarHft(Model model) {
        model.addAttribute("viewTitle", "Formato Hft");
        model.addAttribute("ajaxList", "getHft");
        getConfig(model);
        model.addAttribute("action", "registrarHft");
        return "Files/Listar";
    }

    @RequestMapping(value = "mostrarErrorTamanioMedidas", method = RequestMethod.GET)
    public String errorTamanio(Model model) {

        model.addAttribute("redirect", "listarMedidasEducativas");
        model.addAttribute("error", "El tamaño del fichero es mayor que el permitido");
        return "Files/Error";
    }

    @RequestMapping(value = "mostrarErrorTamanioRam", method = RequestMethod.GET)
    public String errorTamanioRAM(Model model) {
        model.addAttribute("redirect", "listarFormatoRAM");
        model.addAttribute("error", "El tamaño del fichero es mayor que el permitido");
        return "Files/Error";
    }

    @RequestMapping(value = "mostrarErrorTamanioHFT", method = RequestMethod.GET)
    public String errorTamanioHFT(Model model) {
        model.addAttribute("redirect", "listarFormatoHFT");
        model.addAttribute("error", "El tamaño del fichero es mayor que el permitido");
        return "Files/Error";
    }

    @RequestMapping(value = "mostrarErrorTamanioDevoluciones", method = RequestMethod.GET)
    public String errorTamanioDevoluciones(Model model) {
        model.addAttribute("redirect", "listarFormatoDevolucion");
        model.addAttribute("error", "El tamaño del fichero es mayor que el permitido");
        return "Files/Error";
    }

    private void getConfig(Model model) {
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Documento", "Descarga", "Acciones"}));
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,nombre,id:download");
        model.addAttribute("findItem", "");
    }

    @RequestMapping(value = "getFormatoDevolucion", method = RequestMethod.GET)
    public @ResponseBody
    List<UploadFile> getUploadFile(@PathVariable long idModulo, HttpServletRequest request) {

        List<UploadFile> lista = uploadService.listarPorModuloSubModulo(idModulo, "Formato Devolucion");
        return lista;
    }

    @RequestMapping(value = "getMedidas", method = RequestMethod.GET)
    public @ResponseBody
    List<UploadFile> getMedidas(@PathVariable long idModulo, HttpServletRequest request) {

        List<UploadFile> lista = uploadService.listarPorModuloSubModulo(idModulo, "Medidas");
        return lista;
    }

    @RequestMapping(value = "getRam", method = RequestMethod.GET)
    public @ResponseBody
    List<UploadFile> getRam(@PathVariable long idModulo, HttpServletRequest request) {

        List<UploadFile> lista = uploadService.listarPorModuloSubModulo(idModulo, "RAM");
        return lista;
    }

    @RequestMapping(value = "getHft", method = RequestMethod.GET)
    public @ResponseBody
    List<UploadFile> getHft(@PathVariable long idModulo, HttpServletRequest request) {

        List<UploadFile> lista = uploadService.listarPorModuloSubModulo(idModulo, "HFT");
        return lista;
    }

    @RequestMapping(value = "registrarFormato", method = RequestMethod.POST)
    public String registrar(@RequestParam("fileDocumento") MultipartFile fileDocumento, @PathVariable long idModulo) {
        RegistrandoDocumento(fileDocumento, idModulo, "Formato Devolucion");

        if (!fileDocumento.isEmpty()) {
            try {
                ValidateSize(fileDocumento);
            } catch (BusinessException ex) {
                Logger.getLogger(DocumentosController.class.getName()).log(Level.SEVERE, null, ex);
                return "redirect:mostrarErrorTamanioDevoluciones";
            }

        }
        return "redirect:listarFormatoDevolucion";
    }

    @RequestMapping(value = "registrarMedidas", method = RequestMethod.POST)
    public String registrarMedidas(@RequestParam("fileDocumento") MultipartFile fileDocumento, @PathVariable long idModulo) {
        RegistrandoDocumento(fileDocumento, idModulo, "Medidas");

        if (!fileDocumento.isEmpty()) {
            try {
                ValidateSize(fileDocumento);
            } catch (BusinessException ex) {
                Logger.getLogger(DocumentosController.class.getName()).log(Level.SEVERE, null, ex);
                return "redirect:mostrarErrorTamanioMedidas";
            }

        }
        return "redirect:listarMedidasEducativas";
    }

    @RequestMapping(value = "registrarRam", method = RequestMethod.POST)
    public String registrarRam(@RequestParam("fileDocumento") MultipartFile fileDocumento, @PathVariable long idModulo, Model model) {
        RegistrandoDocumento(fileDocumento, idModulo, "RAM");
        if (!fileDocumento.isEmpty()) {
            try {
                ValidateSize(fileDocumento);
            } catch (BusinessException ex) {
                Logger.getLogger(DocumentosController.class.getName()).log(Level.SEVERE, null, ex);
                model.addAttribute("redirect", "listarFormatoRAM");
                return "redirect:mostrarErrorTamanioRam";
            }

        }
        return "redirect:listarFormatoRAM";
    }

    @RequestMapping(value = "registrarHft", method = RequestMethod.POST)
    public String registrarHft(@RequestParam("fileDocumento") MultipartFile fileDocumento, @PathVariable long idModulo) {
        RegistrandoDocumento(fileDocumento, idModulo, "HFT");
        if (!fileDocumento.isEmpty()) {
            try {
                ValidateSize(fileDocumento);
            } catch (BusinessException ex) {
                Logger.getLogger(DocumentosController.class.getName()).log(Level.SEVERE, null, ex);
                return "redirect:mostrarErrorTamanioHFT";
            }

        }
        return "redirect:listarFormatoHFT";
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminar(@RequestParam long id) {
        AjaxResponse<UploadFile> response = new AjaxResponse<UploadFile>();
        UploadFile uploadFile = uploadService.obtenerPorId(id);
        File file = new File(uploadFile.getCamino());
        if (!file.exists()) {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este documento fisicamente");
            return response;
        }
        boolean result = uploadService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente este documento.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este documento");
        }
        return response;
    }

    @RequestMapping(value = "/descargar/{id}", method = RequestMethod.GET)
    public ModelAndView descargar(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {

        try {
            UploadFile uploadFile = uploadService.obtenerPorId(id);

            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + uploadFile.getNombre() + "\"");

            InputStream is = new FileInputStream(uploadFile.getCamino());
            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (IOException ex) {

        }

        return null;
    }

    private void RegistrandoDocumento(MultipartFile fileDocumento, long idModulo, String SubModulo) {
        String extension;
        try {

            if (!fileDocumento.isEmpty()) {

                ValidateSize(fileDocumento);

                int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                extension = fileDocumento.getOriginalFilename().substring(posicionIndex);

                byte[] bytes = fileDocumento.getBytes();

                File directorioArchivos = CreatingDirs(idModulo);

                UploadingFile(directorioArchivos, fileDocumento, bytes, SubModulo, idModulo);
            }

        } catch (BusinessException e) {
            System.out.println(e);
        } catch (IOException ex) {
            System.out.println("test" + ex);

        }
    }

    private void UploadingFile(File directorioArchivos, MultipartFile fileDocumento, byte[] bytes, String Submodulo, long idModulo) throws FileNotFoundException, IOException {
        File serverFile = new File(directorioArchivos.getAbsolutePath() + File.separator + fileDocumento.getOriginalFilename());
        List<UploadFile> existe = uploadService.existe(idModulo, Submodulo, fileDocumento.getOriginalFilename());
        UploadFile upload;

        if (existe.size() > 0) {
            upload = existe.get(0);
        } else {
            upload = new UploadFile();
        }
        upload.setNombre(fileDocumento.getOriginalFilename());
        upload.setCamino(serverFile.getAbsolutePath());
        upload.setSubmodulo(Submodulo);
        upload.setIdModulo(idModulo);

        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();
        uploadService.insertar(upload);
    }

    private File CreatingDirs(long idModulo) {
        File directorioArchivos = new File(context.getRealPath(rutaDocumentos) + File.separator + idModulo);
        if (!directorioArchivos.exists()) {
            directorioArchivos.mkdirs();
        }
        return directorioArchivos;
    }

    private void ValidateSize(MultipartFile fileDocumento) throws BusinessException {
        if (fileDocumento.getSize() > tamanioDocumento) {
            throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
        }
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "DocumentosPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "DocumentosExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<UploadFile> lista = uploadService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Documento");
        List<String> contentFields = Arrays.asList("nombre");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 100);
        model.addObject("Title", "Registro UploadFile");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

}
