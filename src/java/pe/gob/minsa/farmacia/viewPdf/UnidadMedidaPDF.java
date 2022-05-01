package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.UnidadMedida;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class UnidadMedidaPDF extends AbstractITextPdfView {

    public UnidadMedidaPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Unidades de Medida";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<UnidadMedida> unidadesMedida = (List<UnidadMedida>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 6, 3, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Unidad de Medida");
        utilExport.addCellHead(table, "Abreviatura");
        utilExport.addCellHead(table, "Estado");

        for (UnidadMedida unidadMedida : unidadesMedida) {
            utilExport.addCell(table, unidadMedida.getIdUnidadMedida());
            utilExport.addCell(table, unidadMedida.getNombreUnidadMedida());
            utilExport.addCell(table, unidadMedida.getAbreviatura());
            utilExport.addCell(table, unidadMedida.getActivoTexto());
        }

        document.add(table);
    }

}
