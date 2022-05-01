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

/**
 *
 *
 */
public class PrescriptorPDF extends AbstractITextPdfView {

    public PrescriptorPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Prescriptores";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Prescriptor> prescriptores = (List<Prescriptor>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{12, 12, 12, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Prescriptor");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Dirección");
        utilExport.addCellHead(table, "Telefono");
        utilExport.addCellHead(table, "Código de Personal");
        utilExport.addCellHead(table, "Colegiatura");
        utilExport.addCellHead(table, "Tipo de Profesión");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        for (Prescriptor prescriptor : prescriptores) {

            utilExport.addCell(table, prescriptor.getNombrePrescriptor());
            utilExport.addCell(table, (int) prescriptor.getIdMedico());
            utilExport.addCell(table, prescriptor.getDireccion());
            utilExport.addCell(table, prescriptor.getTelefonoUno());
            utilExport.addCell(table, prescriptor.getPersonal());
            utilExport.addCell(table, prescriptor.getColegio());
            utilExport.addCell(table, prescriptor.getProfesion());

        }
        document.add(table);
    }

}
