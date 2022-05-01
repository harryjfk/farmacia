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

public class ReporteDocumentosPDF extends AbstractITextPdfView {
    
    public ReporteDocumentosPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Documentos Detallado";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DocumentoComp> documentos = (List<DocumentoComp>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(6);
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 3, 4, 3, 5, 8, 8, 8, 8});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Num. Interna");
        utilExport.addCellHead(table, "Fecha Documento");
        utilExport.addCellHead(table, "Tipo Documento");
        utilExport.addCellHead(table, "Nro Documento");
        utilExport.addCellHead(table, "Num. Dirección");
        utilExport.addCellHead(table, "Asunto");
        utilExport.addCellHead(table, "Observación");
        utilExport.addCellHead(table, "Remitente");
        utilExport.addCellHead(table, "Destino");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (DocumentoComp documento : documentos) {

            utilExport.addCell(table, documento.getNumeracionInterna());
            grego.setTimeInMillis(documento.getFechaDocumento().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));            
            utilExport.addCell(table, documento.getNombreTipoDocumento());
            utilExport.addCell(table, documento.getNroDocumento());
            utilExport.addCell(table, documento.getNumeracionDireccion());
            utilExport.addCell(table, documento.getAsunto());
            utilExport.addCell(table, documento.getObservacion());
            utilExport.addCell(table, documento.getRemitente());
            utilExport.addCell(table, documento.getDestino());
        }

        document.add(table);
    }
}
