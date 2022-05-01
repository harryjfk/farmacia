package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class FormaFarmaceuticaPDF extends AbstractITextPdfView {

    public FormaFarmaceuticaPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Formas Farmaceuticas";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FormaFarmaceutica> formasFarmaceuticas = (List<FormaFarmaceutica>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 6, 3, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Forma Farmaceutica");
        utilExport.addCellHead(table, "Abreviatura");
        utilExport.addCellHead(table, "Estado");

        for (FormaFarmaceutica formaFarmaceutica : formasFarmaceuticas) {
            utilExport.addCell(table, formaFarmaceutica.getIdFormaFarmaceutica());
            utilExport.addCell(table, formaFarmaceutica.getNombreFormaFarmaceutica());
            utilExport.addCell(table, formaFarmaceutica.getAbreviatura());
            utilExport.addCell(table, formaFarmaceutica.getActivoTexto());
        }

        document.add(table);
    }
}
