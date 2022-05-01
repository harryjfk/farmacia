package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.DocumentoComp;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class DocumentoPDF extends AbstractITextPdfView {

    public DocumentoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Documentos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DocumentoComp> documentos = (List<DocumentoComp>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{10, 10, 10, 10, 12, 10, 30, 8});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Numeración Interna");
        utilExport.addCellHead(table, "Fecha Documento");
        utilExport.addCellHead(table, "Fecha Salida");
        utilExport.addCellHead(table, "Tipo Acción");
        utilExport.addCellHead(table, "Tipo Documento");
        utilExport.addCellHead(table, "Nro Documento");
        utilExport.addCellHead(table, "Asunto");
        utilExport.addCellHead(table, "Estado");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (DocumentoComp documento : documentos) {

            utilExport.addCell(table, documento.getNumeracionInterna());
             
            grego.setTimeInMillis(documento.getFechaDocumento().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));

            if (documento.getFechaSalida() != null) {
                grego.setTimeInMillis(documento.getFechaSalida().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            } else {
                utilExport.addCell(table, "");
            }

            utilExport.addCell(table, documento.getNombreTipoAccion());
            utilExport.addCell(table, documento.getNombreTipoDocumento());
            utilExport.addCell(table, documento.getNroDocumento());
            utilExport.addCell(table, documento.getAsunto());
            utilExport.addCell(table, documento.getActivoTexto());

        }

        document.add(table);
    }
    
}
