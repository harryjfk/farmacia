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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Intervencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

/**
 *
 *
 */
public class IntervencionPDF extends AbstractITextPdfView {

    public IntervencionPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Intervención";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportPDF utilExport = new UtilExportPDF();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        final Font BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        
        Intervencion intervencion = (Intervencion)model.get("Data");
        List<IntervProducto> productos = intervencion.getIntervProductos();
        Paciente paciente = intervencion.getPaciente();
        Prescriptor medico = intervencion.getMedico();
        String fechaInt = dateFormat.format(intervencion.getFechaIntervencion());
        String atendida = intervencion.getAtendida() == 0 ? "No": "Si";
        String programada = intervencion.getProgramada()== 0 ? "No": "Si";
        
        Phrase intervData = new Phrase();
        intervData.add(Chunk.NEWLINE);
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Paciente: ", BOLD));
        intervData.add(new Chunk(String.format("%s %s %s", paciente.getNombre(), paciente.getPaterno(), paciente.getMaterno())));
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Médico: ", BOLD));
        intervData.add(new Chunk(String.format("%s %s %s", medico.getNombre(), medico.getPaterno(), medico.getMaterno())));
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Especialidad: ", BOLD));
        intervData.add(new Chunk(intervencion.getEspecialidad()));
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Fecha de Intervención: ", BOLD));
        intervData.add(new Chunk(fechaInt));
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Atendida: ", BOLD));
        intervData.add(new Chunk(atendida));
        intervData.add(Chunk.NEWLINE);
        intervData.add(new Chunk("Programada: ", BOLD));
        intervData.add(new Chunk(programada));
        
        document.add(intervData);
        
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{6, 88, 6});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción del Medicamento/Material");
        utilExport.addCellHead(table, "Cantidad");

        
        for (IntervProducto producto : productos) {
            utilExport.addCell(table, String.valueOf(producto.getProducto().getIdProducto()));
            String desc = producto.getProducto().getDescripcion() != null ? producto.getProducto().getDescripcion(): "";
            utilExport.addCell(table, desc);
            utilExport.addCell(table, String.valueOf(producto.getCantidad()));
        }
        document.add(table);
    }

}
