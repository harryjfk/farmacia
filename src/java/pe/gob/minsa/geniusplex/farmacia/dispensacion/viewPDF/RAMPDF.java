/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedicamentoSospechoso;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.OtrosMedicamentos;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.RAM;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.ReaccionesAdversas;

/**
 *
 * @author stark
 */
public class RAMPDF extends AbstractITextPdfView {

    public RAMPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte RAM";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RAM ram = (RAM) model.get("Data");
        final Font BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        setPaciente(document, BOLD, ram);
        setMedico(document, BOLD, ram);
        setObservAdicionales(document, BOLD, ram);
        setMedicamentos(document, BOLD, ram);
        setReacciones(document, BOLD, ram);
        setOtros(document, BOLD, ram);
    }

    private void setMedicamentos(Document document, final Font BOLD, RAM ram) throws DocumentException {
        UtilExportPDF utilExport = new UtilExportPDF();
        if (ram.getMedicamentos().isEmpty()) {
            return;
        }

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{32, 12, 12, 12, 12, 12, 8,12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Nombre Comercial o Generico");
        utilExport.addCellHead(table, "Laboratorio");
        utilExport.addCellHead(table, "Lote");
        utilExport.addCellHead(table, "Dosis Diaria");
        utilExport.addCellHead(table, "Via de Administración");
        utilExport.addCellHead(table, "Fecha Inicio");
        utilExport.addCellHead(table, "Fecha Final");
        utilExport.addCellHead(table, "Motivo");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        document.add(new Phrase("Medicamentos Sospechosos", BOLD));

        for (MedicamentoSospechoso med : ram.getMedicamentos()) {

            utilExport.addCell(table, med.getNombre());
            utilExport.addCell(table, med.getLaboratorio());
            utilExport.addCell(table, med.getLote());
            utilExport.addCell(table, med.getDosis());
            utilExport.addCell(table, med.getVia());
            utilExport.addCell(table, dateFormat.format(med.getFechaInicio()));
            utilExport.addCell(table, dateFormat.format(med.getFechaFinal()));
            utilExport.addCell(table, med.getMotivo());
        }

        document.add(table);
    }

    private void setReacciones(Document document, final Font BOLD, RAM ram) throws DocumentException {
        UtilExportPDF utilExport = new UtilExportPDF();
        if (ram.getReacciones().isEmpty()) {
            return;
        }

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{32, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Reacción Adversa");
        utilExport.addCellHead(table, "Fecha Inicio");
        utilExport.addCellHead(table, "Fecha Final");
        utilExport.addCellHead(table, "Evoloción");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        document.add(new Phrase("Reacciones Adversas", BOLD));

        for (ReaccionesAdversas reaccione : ram.getReacciones()) {

            utilExport.addCell(table, reaccione.getReaccion());
            utilExport.addCell(table, dateFormat.format(reaccione.getFechaInicio()));
            utilExport.addCell(table, dateFormat.format(reaccione.getFechaFinal()));
            utilExport.addCell(table, reaccione.getEvolucion());

        }

        document.add(table);
    }

    private void setOtros(Document document, final Font BOLD, RAM ram) throws DocumentException {
        UtilExportPDF utilExport = new UtilExportPDF();
        if (ram.getOtros().isEmpty()) {
            return;
        }

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{32, 12, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Nombre Comercial o Generico");
        utilExport.addCellHead(table, "Dosis Diaria");
        utilExport.addCellHead(table, "Via de Administración");
        utilExport.addCellHead(table, "Fecha Inicio");
        utilExport.addCellHead(table, "Fecha Final");
        utilExport.addCellHead(table, "Indicación Terapeutica");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        document.add(new Phrase("Otros Medicamentos Usados en los Ultimos 3 Meses incluyendo Automedicación", BOLD));

        for (OtrosMedicamentos otro : ram.getOtros()) {

            utilExport.addCell(table, otro.getNombre());
            utilExport.addCell(table, otro.getDosis());
            utilExport.addCell(table, otro.getVia());
            utilExport.addCell(table, dateFormat.format(otro.getFechaInicio()));
            utilExport.addCell(table, dateFormat.format(otro.getFechaFinal()));
            utilExport.addCell(table, otro.getIndicacion());
        }

        document.add(table);
    }

    private void setPaciente(Document document, final Font BOLD, RAM ram) throws DocumentException {
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{32, 12, 12, 12, 12, 8});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Nombre");
        utilExport.addCellHead(table, "Establecimiento de Salud");
        utilExport.addCellHead(table, "H.C");
        utilExport.addCellHead(table, "Edad");
        utilExport.addCellHead(table, "Sexo");
        utilExport.addCellHead(table, "Peso");

        document.add(new Phrase("Datos del Paciente", BOLD));

        utilExport.addCell(table, ram.getPaciente());
        utilExport.addCell(table, ram.getEstablecimiento());
        utilExport.addCell(table, ram.getHistoria());
        utilExport.addCell(table, ram.getEdad());
        utilExport.addCell(table, ram.getSexo().equals("0") ? "M": "F");
        utilExport.addCell(table, ram.getPeso());

        document.add(table);
    }

    private void setMedico(Document document, final Font BOLD, RAM ram) throws DocumentException {
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{32, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Nombre");
        utilExport.addCellHead(table, "Categoria");
        utilExport.addCellHead(table, "Dirección");
        utilExport.addCellHead(table, "Telefono");
        utilExport.addCellHead(table, "Fecha");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        document.add(new Phrase("Persona que Notifica", BOLD));

        utilExport.addCell(table, ram.getMedico());
        utilExport.addCell(table, ram.getCategoria());
        utilExport.addCell(table, ram.getDireccion());
        utilExport.addCell(table, ram.getTelefono());
        utilExport.addCell(table, dateFormat.format(ram.getFecha()));

        document.add(table);
    }
    
    public void setObservAdicionales(Document document, final Font BOLD, RAM ram) throws DocumentException {
        document.add(new Phrase("Observaciones Adicionales", BOLD));
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(ram.getObservaciones()));
        document.add(new Phrase(Chunk.NEWLINE));
    }
    
}
