/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;

/**
 *
 *
 */
public class VendedorPDF extends AbstractITextPdfView {

    public VendedorPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Vendedores";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Vendedor> vendedores = (List<Vendedor>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{13, 10, 10, 10, 10, 10});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Vendedor");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Código de Personal");
        utilExport.addCellHead(table, "Tipo de Personal");
        utilExport.addCellHead(table, "Dirección");
        utilExport.addCellHead(table, "Telefono");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (Vendedor vendedor : vendedores) {

            utilExport.addCell(table, vendedor.getNombreVendedor());
            utilExport.addCell(table, (int) vendedor.getIdVendedor());
            utilExport.addCell(table, vendedor.getPersonal());
            utilExport.addCell(table, vendedor.getTipoPersonal());
            utilExport.addCell(table, vendedor.getDireccion());
            utilExport.addCell(table, vendedor.getTelefonoUno());

        }
        document.add(table);
    }

}
