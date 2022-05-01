package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;
import pe.gob.minsa.farmacia.domain.ProductoSismed;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProductoSismedPDF extends AbstractITextPdfView {
    
    public ProductoSismedPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Productos Sismed";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ProductoSismed> productosSismed = (List<ProductoSismed>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Producto Sismed");        
        utilExport.addCellHead(table, "Estado");

        for (ProductoSismed productoSismed : productosSismed) {
            utilExport.addCell(table, productoSismed.getCodigoSismed());
            utilExport.addCell(table, productoSismed.getNombreProductoSismed());
            utilExport.addCell(table, productoSismed.getActivoTexto());
        }

        document.add(table);
    }
    
}
