package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProveedorPDF extends AbstractITextPdfView {

    public ProveedorPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Proveedores";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Proveedor> proveedores = (List<Proveedor>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 8, 3, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Razón Social");
        utilExport.addCellHead(table, "Ruc");
        utilExport.addCellHead(table, "Estado");

        for (Proveedor proveedor : proveedores) {
            utilExport.addCell(table, proveedor.getIdProveedor());
            utilExport.addCell(table, proveedor.getRazonSocial());
            utilExport.addCell(table, proveedor.getRuc());
            utilExport.addCell(table, proveedor.getActivoTexto());
        }

        document.add(table);
    }
}
