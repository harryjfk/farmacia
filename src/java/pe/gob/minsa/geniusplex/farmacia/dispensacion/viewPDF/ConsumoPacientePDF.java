/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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
import pe.gob.minsa.geniusplex.web.Consumo;

/**
 *
 * @author stark
 */
public class ConsumoPacientePDF extends AbstractITextPdfView {

    public ConsumoPacientePDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Consumo";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Consumo> consumos = (List<Consumo>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        document.add(new Phrase(Chunk.NEWLINE));
        for (Consumo consumo : consumos) {
            String[] monthSplit = consumo.getMonthName().split("-");

            document.add(new Phrase(new Chunk(String.format("%s/%s", monthSplit[0], monthSplit[2]))));
            document.add(new Phrase(Chunk.NEWLINE));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{10, 60, 10, 10, 10});
            table.setHeaderRows(1);
            utilExport.addCellHead(table, "Código");
            utilExport.addCellHead(table, "Producto");
            utilExport.addCellHead(table, "Total");
            utilExport.addCellHead(table, "Precio");
            utilExport.addCellHead(table, "Importe");

            for (int i = 0; i < consumo.getIds().size(); i++) {
                utilExport.addCell(table, consumo.getCod().get(i), Element.ALIGN_CENTER);
                utilExport.addCell(table, consumo.getProducto().get(i), Element.ALIGN_CENTER);
                utilExport.addCell(table, String.valueOf(consumo.getTotal().get(i)), Element.ALIGN_CENTER);
                utilExport.addCell(table, String.valueOf(consumo.getPrice().get(i)), Element.ALIGN_CENTER);
                utilExport.addCell(table, String.valueOf(consumo.getImporte().get(i)), Element.ALIGN_CENTER);
            }
            document.add(table);
            document.add(new Phrase(Chunk.NEWLINE));
        }
    }

}
