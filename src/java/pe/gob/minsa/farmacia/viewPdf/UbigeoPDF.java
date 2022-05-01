package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.services.impl.UbigeoService;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class UbigeoPDF extends AbstractITextPdfView {

    @Autowired
    UbigeoService ubigeoService;

    public void setUbigeoService(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    public UbigeoPDF() {
        setDocumentHorizontal(false);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        String idUbigeo = request.getParameter("idUbigeo");

        if (idUbigeo == null) {
            String titulo = "Reporte de Departamentos";
            setDocumentName(titulo);
            setTitulo(titulo);
        } else {            
            Ubigeo ubigeo = ubigeoService.obtenerPorId(idUbigeo);

            String titulo = "";

            if (idUbigeo.length() == 2) {
                titulo = "Reporte de Provincias de " + ubigeo.getNombreUbigeo();
            } else if (idUbigeo.length() == 4) {
                titulo = "Reporte de Distritos de " + ubigeo.getNombreUbigeo();
            }

            setDocumentName(titulo);
            setTitulo(titulo);
        }

        ByteArrayOutputStream baos = createTemporaryOutputStream();

        Document document = newDocument();
        document.setMargins(20, 20, 20, 40);
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        response.setHeader("Content-Disposition", "attachment; filename=" + getDocumentName() + ".pdf");

        writeToResponse(response, baos);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Ubigeo> ubigeos = (List<Ubigeo>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table;

        table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Ubigeo");
        utilExport.addCellHead(table, "Estado");

        for (Ubigeo ubigeo : ubigeos) {
            utilExport.addCell(table, ubigeo.getIdUbigeo());
            utilExport.addCell(table, ubigeo.getNombreUbigeo());                
            utilExport.addCell(table, ubigeo.getActivoTexto());
        }

        document.add(table);
    }
}
