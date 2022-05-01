/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;

/**
 *
 *
 */
public class KitAtencionPDF extends AbstractITextPdfView {

    public KitAtencionPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Kits de Atención";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<KitAtencion> kits = (List<KitAtencion>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{6, 34, 60});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Productos");

        for (KitAtencion kit : kits) {
            String desc = kit.getDescripcion() != null ? kit.getDescripcion() : "";
            
            utilExport.addCell(table, String.valueOf(kit.getId()));
            utilExport.addCell(table, desc);
            String productos = "";
            for (KitAtencionProducto kP : kit.getProductos()) {
                productos += kP.getProducto().getDescripcion() + " (" + (int)kP.getCantidad() + ")\n";
            }
            utilExport.addCell(table, productos);
        }
        document.add(table);
    }

}
