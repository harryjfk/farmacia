package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.MovimientoDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class MovimientoPDF extends AbstractITextPdfView {
    
     public MovimientoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Movimientos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<MovimientoDto> movimientos = (List<MovimientoDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(6);
        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 1, 1, 4, 4, 3, 2, 2, 4, 2, 5});
        table.setHeaderRows(1);
        
        utilExport.addCellHead(table, "F. Registro");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "N° Mov.");
        utilExport.addCellHead(table, "Almacén Origen");
        utilExport.addCellHead(table, "Almacén Destino");
        utilExport.addCellHead(table, "Concepto");
        utilExport.addCellHead(table, "Importe");
        utilExport.addCellHead(table, "F. Recepción");
        utilExport.addCellHead(table, "Tipo Doc.");
        utilExport.addCellHead(table, "N° Doc.");
        utilExport.addCellHead(table, "Proveedor");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        DecimalFormat df = new DecimalFormat("0.0000");
        
        for (MovimientoDto movimiento : movimientos) {
            
            grego.setTimeInMillis(movimiento.getFechaRegistro().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));
            utilExport.addCell(table, movimiento.getTipoMovimiento().toString());
            utilExport.addCell(table, movimiento.getNumeroMovimiento());
            utilExport.addCell(table, movimiento.getAlmacenOrigen());
            utilExport.addCell(table, movimiento.getAlmacenDestino());
            utilExport.addCell(table, movimiento.getNombreConcepto());
            utilExport.addCell(table, df.format(movimiento.getTotal().doubleValue()));
            if(movimiento.getFechaRecepcion() == null){
                utilExport.addCell(table, "");
            }else{
                grego.setTimeInMillis(movimiento.getFechaRecepcion().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            }
            
            utilExport.addCell(table, movimiento.getNombreTipoDocumento());
            utilExport.addCell(table, movimiento.getNumeroDocumentoMov());
            utilExport.addCell(table, movimiento.getRazonSocial());           
            
        }

        document.add(table);
    }
    
}
