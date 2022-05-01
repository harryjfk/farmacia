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
import pe.gob.minsa.farmacia.domain.dto.IngresoDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class IngresoPDF extends AbstractITextPdfView {

    public IngresoPDF() {
        setDocumentHorizontal(true);        
        String titulo = "Reporte de Ingresos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IngresoDto> movimientos = (List<IngresoDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 2, 4, 4, 3, 2, 3, 2});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Fecha de Registro");
        utilExport.addCellHead(table, "Número");        
        utilExport.addCellHead(table, "Almacén Origen");
        utilExport.addCellHead(table, "Almacén Destino");  
        utilExport.addCellHead(table, "Tipo Documento");
        utilExport.addCellHead(table, "Nro. Documento");
        utilExport.addCellHead(table, "Doc. Origen");
        utilExport.addCellHead(table, "Nro. Doc. Origen");        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        
        for (IngresoDto movimiento : movimientos) {
            
            grego.setTimeInMillis(movimiento.getFechaRegistro().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));            
            utilExport.addCell(table, movimiento.getNumeroMovimiento());            
            utilExport.addCell(table, movimiento.getAlmacenOrigen());
            utilExport.addCell(table, movimiento.getAlmacenDestino());
            utilExport.addCell(table, movimiento.getTipoDocumento());
            utilExport.addCell(table, movimiento.getNumeroDocumento());
            utilExport.addCell(table, movimiento.getDocumentoOrigen());
            utilExport.addCell(table, movimiento.getNumeroDocumentoOrigen());
        }

        document.add(table);
    }
}
