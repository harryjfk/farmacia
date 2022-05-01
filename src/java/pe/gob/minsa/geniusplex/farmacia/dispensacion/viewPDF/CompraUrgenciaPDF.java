/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraDeUrgencia;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraUrgenciaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

/**
 *
 *
 */
public class CompraUrgenciaPDF extends AbstractITextPdfView {

    public CompraUrgenciaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Compras de Urgencia";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportPDF utilExport = new UtilExportPDF();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        final Font BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD);

        CompraDeUrgencia compra = (CompraDeUrgencia) model.get("Data");
        PdfPTable table = new PdfPTable(3);
        if (compra != null) {
            List<CompraUrgenciaProducto> productos = compra.getCompraUrgenciaProductos();
            Paciente paciente = compra.getPaciente();
            Prescriptor medico = compra.getMedico();

            Phrase compraData = new Phrase();
            compraData.add(Chunk.NEWLINE);
            compraData.add(Chunk.NEWLINE);
            compraData.add(new Chunk("Paciente: ", BOLD));
            compraData.add(new Chunk(String.format("%s %s %s", paciente.getNombre(), paciente.getPaterno(), paciente.getMaterno())));
            compraData.add(Chunk.NEWLINE);
            compraData.add(new Chunk("Médico: ", BOLD));
            compraData.add(new Chunk(String.format("%s %s %s", medico.getNombre(), medico.getPaterno(), medico.getMaterno())));

            document.add(compraData);
            
            table.setWidthPercentage(100);
            table.setWidths(new int[]{6, 88, 6});
            table.setHeaderRows(1);
            utilExport.addCellHead(table, "Código");
            utilExport.addCellHead(table, "Descripción del Medicamento/Material");
            utilExport.addCellHead(table, "Cantidad");

            for (CompraUrgenciaProducto producto : productos) {
                utilExport.addCell(table, String.valueOf(producto.getProducto().getIdProducto()));
                String desc = producto.getProducto().getDescripcion() != null ? producto.getProducto().getDescripcion() : "";
                utilExport.addCell(table, desc);
                utilExport.addCell(table, String.valueOf(producto.getCantidad()));
            }
        }
        document.add(table);
    }

}
