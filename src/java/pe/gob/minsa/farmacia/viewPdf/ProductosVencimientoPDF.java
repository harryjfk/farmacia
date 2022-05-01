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
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProductosVencimientoPDF extends AbstractITextPdfView {
    
    public ProductosVencimientoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Productos Vencimento";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductoAlertaVencimientoDto> productos = (List<ProductoAlertaVencimientoDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 1, 4, 2, 1, 1, 2, 2, 2, 2});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Almacén");
        utilExport.addCellHead(table, "Código");        
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "Lote");
        utilExport.addCellHead(table, "Estado");
        utilExport.addCellHead(table, "Vence");
        utilExport.addCellHead(table, "Precio");
        utilExport.addCellHead(table, "Stock");
        utilExport.addCellHead(table, "Total");

       SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
       Calendar grego = new GregorianCalendar();
       DecimalFormat df = new DecimalFormat("0.0000");
            
        for (ProductoAlertaVencimientoDto producto : productos) {
            utilExport.addCell(table, producto.getAlmacen());
            utilExport.addCell(table, producto.getCodigoSismed());
            utilExport.addCell(table, producto.getProducto());
            utilExport.addCell(table, producto.getTipoProducto());
            utilExport.addCell(table, producto.getLote());
            utilExport.addCell(table, producto.getEstado());
            grego.setTimeInMillis(producto.getFechaVencimiento().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));
            utilExport.addCell(table, df.format(producto.getPrecio().doubleValue()));
            utilExport.addCell(table, producto.getStock());
            utilExport.addCell(table, df.format(producto.getTotal().doubleValue()));
        }

        document.add(table);
    }
}
