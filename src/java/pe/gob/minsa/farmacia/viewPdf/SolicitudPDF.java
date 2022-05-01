package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.Solicitud;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class SolicitudPDF extends AbstractITextPdfView {

    public SolicitudPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Solicitudes";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<Solicitud> solicitudes = (List<Solicitud>) model.get("Data");
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {5, 12, 12, 12, 12, 8});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Médico");
        utilExport.addCellHead(table, "Establecimiento");
        utilExport.addCellHead(table, "Insitución");
        utilExport.addCellHead(table, "Fecha");
        utilExport.addCellHead(table, "Estado");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (Solicitud solicitud : solicitudes) {
            utilExport.addCell(table, solicitud.getIdSolicitud());
            utilExport.addCell(table, solicitud.getMedico());
            utilExport.addCell(table, solicitud.getEstablecimiento());
            utilExport.addCell(table, solicitud.getInstitucion());
            if(solicitud.getFecha() != null){
                grego.setTimeInMillis(solicitud.getFecha().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            }else{
                utilExport.addCell(table, "");
            }
            utilExport.addCell(table, solicitud.getActivoTexto());
        }

        document.add(table);
    }

}
