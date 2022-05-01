/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;

/**
 *
 * @author stark
 */
public class TurnoPDF extends AbstractITextPdfView {
    
    public TurnoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Turnos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Turno> turnos = (List<Turno>)model.get("Data");
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {12, 12, 12,12});
        table.setHeaderRows(1);
                
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Hora Inicio");
        utilExport.addCellHead(table, "Hora Termino");
        
        SimpleDateFormat format = new SimpleDateFormat("H:mm");
        String inicio ="";
        String termino ="";
        for (Turno turno : turnos) {
            
            utilExport.addCell(table, turno.getCodigo(),Element.ALIGN_CENTER);
            utilExport.addCell(table, turno.getDescripcion(),Element.ALIGN_CENTER);
            inicio = format.format(turno.getHoraInicio());
            utilExport.addCell(table, inicio,Element.ALIGN_CENTER);
            termino = format.format(turno.getHoraFinal());
            utilExport.addCell(table,termino,Element.ALIGN_CENTER);
            
        }

        document.add(table);
    
    }
}
