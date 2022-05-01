/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitaria;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitariaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;

/**
 *
 * @author armando
 */
public class IntervSalidaPDF extends AbstractITextPdfView {

    final Font BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    String tmp = "";

    public IntervSalidaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Salida por Intervención Sanitaria";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportPDF utilExport = new UtilExportPDF();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();

        IntervSanitaria interv = (IntervSanitaria) model.get("Data");
        List<IntervSanitariaProducto> productos = interv.getIntervSanitariaProductos();
        String nroSalida = interv.getNroSalida();
        String nroHistClinica = interv.getNroHistClinica();
        Cliente cliente = interv.getCliente();
        String nombreCliente = String.format("%s %s %s", cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno());
        Prescriptor prescriptor = interv.getPrescriptor();
        String nombrePresc = String.format("%s %s %s", prescriptor.getNombre(), prescriptor.getPaterno(), prescriptor.getMaterno());
        String diagnostico = (interv.getDiagnostico() != null) ? interv.getDiagnostico().getDescripcion() : "";
        String fechaRegistro = dateFormat.format(interv.getFechaRegistro());
        String componente = interv.getComponente().getDescripcion();
        String subComponente = interv.getSubComponente().getDescripcion();
        String proceso = interv.getProceso().getDescripcion();
        String coordinador = "";
        if (interv.getCoordinador() != null) {
            coordinador = String.format("%s %s %s", interv.getDatosCoor().get("nombre"), interv.getDatosCoor().get("apellidoPaterno"), interv.getDatosCoor().get("apellidoMaterno"));
        }
        String referencia = interv.getReferencia();

        PdfPTable headTable = new PdfPTable(2);
        headTable.setWidthPercentage(100);
        headTable.setWidths(new int[]{50, 50});

        addRow(headTable, addLeft("Nro. Salida: ", nroSalida), addRight("Fecha de Registro: ", fechaRegistro));
        addRow(headTable, addLeft("Cliente: ", nombreCliente), addRight("Componente: ", componente));
        addRow(headTable, addLeft("Nro. Hist. Clínica: ", nroHistClinica), addRight("Sub Componente: ", subComponente));
        addRow(headTable, addLeft("Prescriptor: ", nombrePresc), addRight("Proceso: ", proceso));
        addRow(headTable, addLeft("Diagnostico CIE: ", diagnostico), addRight("Coordinador: ", ""));
        addRow(headTable, addLeft("Coordinador", coordinador), addRight("Referencia: ", referencia));

        document.add(headTable);

        Phrase newLine = new Phrase(Chunk.NEWLINE);
        document.add(newLine);
        Phrase newLine2 = new Phrase(Chunk.NEWLINE);
        document.add(newLine2);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{50, 10, 10, 15, 15});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Producto");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Cantidad");
        utilExport.addCellHead(table, "Precio");
        utilExport.addCellHead(table, "Impo. Parcial");

        DecimalFormat df = new DecimalFormat("#.####");

        for (IntervSanitariaProducto producto : productos) {
            utilExport.addCell(table, producto.getProducto().getDescripcion());
            utilExport.addCell(table, String.valueOf(producto.getProducto().getIdProducto()));
            utilExport.addCell(table, String.valueOf(producto.getCantidad()));
            utilExport.addCell(table, df.format(producto.getPrecio()));
            utilExport.addCell(table, df.format(producto.getImporteParcial()));
        }

        document.add(table);

    }

    private Phrase addLeft(String label, String value) {
        Phrase dataPhrase = new Phrase();
        dataPhrase.add(new Chunk(label, BOLD));
        dataPhrase.add(value);
        return dataPhrase;
    }

    private Phrase addRight(String label, String value) {
        Phrase dataPhrase = new Phrase();
        dataPhrase.add(new Chunk(label, BOLD));
        dataPhrase.add(value);
        return dataPhrase;
    }

    private void addRow(PdfPTable table, Phrase left, Phrase right) {
        PdfPCell leftCell = new PdfPCell(left);
        leftCell.setBorder(0);

        PdfPCell rightCell = new PdfPCell(right);
        rightCell.setBorder(0);

        table.addCell(leftCell);
        table.addCell(rightCell);
    }

}
