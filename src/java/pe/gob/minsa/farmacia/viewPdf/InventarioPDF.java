package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class InventarioPDF extends AbstractITextPdfView {
    
    public InventarioPDF() {
        setDocumentHorizontal(true);
        String titulo = "Inventario";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
     @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<InventarioProductoTotalDto> inventarios = (List<InventarioProductoTotalDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(13);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1,1,1,1,1,1,1,1,1,1,1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "F. F.");
        utilExport.addCellHead(table, "Valor Actual Cant.");
        utilExport.addCellHead(table, "Valor Actual Precio");
        utilExport.addCellHead(table, "Valor Actual Total");
        utilExport.addCellHead(table, "Conteo Físico");
        utilExport.addCellHead(table, "Faltantes Cant.");
        utilExport.addCellHead(table, "Faltantes Total");
        utilExport.addCellHead(table, "Sobrantes Cant.");
        utilExport.addCellHead(table, "Sobrantes Total");
        utilExport.addCellHead(table, "Total");
        utilExport.addCellHead(table, "Alter./Rotos");
        
         DecimalFormat df = new DecimalFormat("0.000000");

        for (InventarioProductoTotalDto inventario : inventarios) {
            
            utilExport.addCell(table, inventario.getIdProducto());            
            utilExport.addCell(table, inventario.getDescripcion());
            utilExport.addCell(table, inventario.getFormaFarmaceutica());
            utilExport.addCell(table, inventario.getCantidad());
            utilExport.addCell(table, df.format(inventario.getPrecio().doubleValue()));
            utilExport.addCell(table, df.format(inventario.getTotal().doubleValue()));
            utilExport.addCell(table, inventario.getConteo());
            utilExport.addCell(table, inventario.getCantidadFaltante());            
            utilExport.addCell(table, df.format(inventario.getTotalFaltante().doubleValue()));            
            utilExport.addCell(table, inventario.getCantidadSobrante());
            utilExport.addCell(table, df.format(inventario.getTotalSobrante().doubleValue()));
            utilExport.addCell(table, df.format(inventario.getTotalFisico().doubleValue()));
            utilExport.addCell(table,inventario.getCantidadAlterado());            
        }

        document.add(table);
    }
}
