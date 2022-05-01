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
import pe.gob.minsa.farmacia.domain.dto.ProductoStockMinimo;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProductosStockMinimoPDF extends AbstractITextPdfView {
    
      public ProductosStockMinimoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Productos Con Stock Mínimo";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductoStockMinimo> productos = (List<ProductoStockMinimo>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 1, 4, 2, 1, 2, 2, 2});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Almacén");
        utilExport.addCellHead(table, "Código");        
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "Lote");        
        utilExport.addCellHead(table, "Vence");        
        utilExport.addCellHead(table, "Stock Actual");
        utilExport.addCellHead(table, "Stock Mínimo");

       SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
       Calendar grego = new GregorianCalendar();
            
        for (ProductoStockMinimo producto : productos) {
            utilExport.addCell(table, producto.getAlmacen());
            utilExport.addCell(table, producto.getCodigoSismed());
            utilExport.addCell(table, producto.getProducto());
            utilExport.addCell(table, producto.getTipoProducto());
            utilExport.addCell(table, producto.getLote());
            grego.setTimeInMillis(producto.getFechaVencimiento().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));            
            utilExport.addCell(table, producto.getStock());
            utilExport.addCell(table, producto.getStockMin());
        }

        document.add(table);
    }
}
