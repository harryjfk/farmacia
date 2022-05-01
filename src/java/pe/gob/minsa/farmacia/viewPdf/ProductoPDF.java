package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProductoPDF extends AbstractITextPdfView {
    
    public ProductoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Productos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductoComp> productos = (List<ProductoComp>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{5, 5, 3, 3, 3, 3, 2});
        table.setHeaderRows(1);
       
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Presentación");
        utilExport.addCellHead(table, "Concentración");        
        utilExport.addCellHead(table, "Forma Farmacéutica");
        utilExport.addCellHead(table, "Tipo de Producto");
        utilExport.addCellHead(table, "Unidad de Medida");        
        utilExport.addCellHead(table, "Estado");

        for (ProductoComp producto : productos) {            
            utilExport.addCell(table, producto.getDescripcion());
            utilExport.addCell(table, producto.getPresentacion());
            utilExport.addCell(table, producto.getConcentracion());
            utilExport.addCell(table, producto.getFormaFarmaceutica());
            utilExport.addCell(table, producto.getTipoProducto());
            utilExport.addCell(table, producto.getUnidadMedida());
            utilExport.addCell(table, producto.getActivoTexto());
        }

        document.add(table);
    }
}