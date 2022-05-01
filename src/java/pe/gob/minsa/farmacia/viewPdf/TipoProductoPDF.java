package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.TipoProducto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class TipoProductoPDF extends AbstractITextPdfView {

    public TipoProductoPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Tipos de Producto";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TipoProducto> tiposProducto = (List<TipoProducto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Tipo de Producto");
        utilExport.addCellHead(table, "Estado");

        for (TipoProducto tipoProducto : tiposProducto) {
            utilExport.addCell(table, tipoProducto.getIdTipoProducto());
            utilExport.addCell(table, tipoProducto.getNombreTipoProducto());
            utilExport.addCell(table, tipoProducto.getActivoTexto());
        }

        document.add(table);
    }
    
}
