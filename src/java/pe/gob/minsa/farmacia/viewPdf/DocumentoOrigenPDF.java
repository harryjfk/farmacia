package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class DocumentoOrigenPDF extends AbstractITextPdfView {

    public DocumentoOrigenPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Documentos Origen";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DocumentoOrigen> documentosOrigen = (List<DocumentoOrigen>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Documento Origen");
        utilExport.addCellHead(table, "Estado");

        for (DocumentoOrigen documentoOrigen : documentosOrigen) {
            utilExport.addCell(table, documentoOrigen.getIdDocumentoOrigen());
            utilExport.addCell(table, documentoOrigen.getNombreDocumentoOrigen());
            utilExport.addCell(table, documentoOrigen.getActivoTexto());
        }

        document.add(table);
    }
}