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
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class TarjetaControlVisiblePDF extends AbstractITextPdfView {

    public TarjetaControlVisiblePDF() {
        setDocumentHorizontal(true);
        String titulo = "Tarjeta de Control Visibe (Kardex)";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TarjetaControlDto> tarjetasControlDto = (List<TarjetaControlDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(6);
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 1, 1, 4, 4, 3, 2, 2, 4, 2});
        table.setHeaderRows(1);
        
        utilExport.addCellHead(table, "F. Registro");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "N° Mov.");
        utilExport.addCellHead(table, "Producto");        
        utilExport.addCellHead(table, "Concepto");
        utilExport.addCellHead(table, "Lote");
        utilExport.addCellHead(table, "Fecha Vcto.");
        utilExport.addCellHead(table, "Ingresos");
        utilExport.addCellHead(table, "Salidas");
        utilExport.addCellHead(table, "Saldo");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (TarjetaControlDto tarjetaControlDto : tarjetasControlDto) {

            grego.setTimeInMillis(tarjetaControlDto.getFechaRegistro().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));
            
            utilExport.addCell(table, tarjetaControlDto.getTipoMovimiento().toString());
            utilExport.addCell(table, tarjetaControlDto.getNumeroMovimiento());
            utilExport.addCell(table, tarjetaControlDto.getProducto());
            utilExport.addCell(table, tarjetaControlDto.getNombreConcepto());
            utilExport.addCell(table, tarjetaControlDto.getLote());
            
            if (tarjetaControlDto.getFechaVencimiento() == null) {
                utilExport.addCell(table, "");
            } else {
                grego.setTimeInMillis(tarjetaControlDto.getFechaVencimiento().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            }

            utilExport.addCell(table, tarjetaControlDto.getIngresos());
            utilExport.addCell(table, tarjetaControlDto.getSalidas());
            utilExport.addCell(table, tarjetaControlDto.getSaldo());

        }

        document.add(table);
    }
    
}
