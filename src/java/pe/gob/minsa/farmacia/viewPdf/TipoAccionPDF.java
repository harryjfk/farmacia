package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class TipoAccionPDF extends AbstractITextPdfView {

    public TipoAccionPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Tipos de Acciones";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TipoAccion> tiposAcciones = (List<TipoAccion>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Tipo de Acción");
        utilExport.addCellHead(table, "Estado");

        for (TipoAccion tipoAccion : tiposAcciones) {
            utilExport.addCell(table, tipoAccion.getIdTipoAccion());
            utilExport.addCell(table, tipoAccion.getNombreTipoAccion());
            utilExport.addCell(table, tipoAccion.getActivoTexto());
        }

        document.add(table);
    }

}
