package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.Parametro;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ParametroPDF extends AbstractITextPdfView  {
    
    public ParametroPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Parámetros";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Parametro> parametros = (List<Parametro>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 3, 5, 2});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Parámetro");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Valor");

        for (Parametro parametro : parametros) {
            utilExport.addCell(table, parametro.getIdParametro());
            utilExport.addCell(table, parametro.getNombreParametro());
            utilExport.addCell(table, parametro.getDescripcionParametro());
            utilExport.addCell(table, parametro.getValor());
        }

        document.add(table);
    }
}
