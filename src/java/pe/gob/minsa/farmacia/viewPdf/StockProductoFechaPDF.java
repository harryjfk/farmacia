package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockFechaDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class StockProductoFechaPDF extends AbstractITextPdfView {

    public StockProductoFechaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Stock Producto a Fecha";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductoStockFechaDto> productosStockFecha = (List<ProductoStockFechaDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(8);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 4, 3, 3, 2});
        table.setHeaderRows(1);

        utilExport.addCellHead(table, "Código Sismed");
        utilExport.addCellHead(table, "Producto");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "F.F.");
        utilExport.addCellHead(table, "Stock");

        for (ProductoStockFechaDto productoStockFecha : productosStockFecha) {
       
            utilExport.addCell(table, productoStockFecha.getCodigoSismed());
            utilExport.addCell(table, productoStockFecha.getDescripcion());
            utilExport.addCell(table, productoStockFecha.getTipoProducto());
            utilExport.addCell(table, productoStockFecha.getFormaFarmaceutica());
            utilExport.addCell(table, productoStockFecha.getStock()); 
         }
        
        document.add(table);
    }
}