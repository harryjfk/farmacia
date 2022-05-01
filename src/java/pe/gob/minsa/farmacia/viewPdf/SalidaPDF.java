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
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.dto.SalidaDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class SalidaPDF extends AbstractITextPdfView {

    public SalidaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Salidas";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SalidaDto> movimientos = (List<SalidaDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 1, 4, 4, 3, 2});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Fecha de Registro");
        utilExport.addCellHead(table, "Número");        
        utilExport.addCellHead(table, "Almacén Origen");
        utilExport.addCellHead(table, "Almacén Destino");  
        utilExport.addCellHead(table, "Tipo Documento");
        utilExport.addCellHead(table, "Nro. Documento");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        
        for (SalidaDto movimiento : movimientos) {
            grego.setTimeInMillis(movimiento.getFechaRegistro().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));            
            utilExport.addCell(table, movimiento.getNumeroMovimiento());            
            utilExport.addCell(table, movimiento.getAlmacenOrigen());
            utilExport.addCell(table, movimiento.getAlmacenDestino());
            utilExport.addCell(table, movimiento.getTipoDocumento());
            utilExport.addCell(table, movimiento.getNumeroDocumento());
        }

        document.add(table);
    }
}
