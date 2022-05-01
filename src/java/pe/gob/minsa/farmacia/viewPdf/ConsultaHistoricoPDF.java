package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.Historico;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ConsultaHistoricoPDF extends AbstractITextPdfView {

    public ConsultaHistoricoPDF() {
        setDocumentHorizontal(true);
        setDocumentName("Consulta de consumo histórico");
        setTitulo("Consulta de consumo histórico");
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<Historico> balances = (List<Historico>) model.get("Data");
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(18);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Fecha Adq.");
        utilExport.addCellHead(table, "Medicamento");
        utilExport.addCellHead(table, "Concentración");
        utilExport.addCellHead(table, "Forma Presentación");
        utilExport.addCellHead(table, "Forma Farmaceútica");
        utilExport.addCellHead(table, "Año");
        utilExport.addCellHead(table, "Enero");
        utilExport.addCellHead(table, "Febrero");
        utilExport.addCellHead(table, "Marzo");
        utilExport.addCellHead(table, "Abril");
        utilExport.addCellHead(table, "Mayo");
        utilExport.addCellHead(table, "Junio");
        utilExport.addCellHead(table, "Julio");
        utilExport.addCellHead(table, "Agosto");
        utilExport.addCellHead(table, "Setiembre");
        utilExport.addCellHead(table, "Octubre");
        utilExport.addCellHead(table, "Noviembre");
        utilExport.addCellHead(table, "Diciembre");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (Historico balance : balances) {
            if(balance.getFechaAprobacion() != null){
                grego.setTimeInMillis(balance.getFechaAprobacion().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            }else{
                utilExport.addCell(table, "");
            }
            utilExport.addCell(table, balance.getDescripcionProducto());
            utilExport.addCell(table, balance.getConcentracion());
            utilExport.addCell(table, balance.getFormaPresentacion());
            utilExport.addCell(table, balance.getNombreFormaFarmaceutica());
            utilExport.addCell(table, balance.getAnio());
            utilExport.addCell(table, balance.getCantEnero());
            utilExport.addCell(table, balance.getCantFebrero());
            utilExport.addCell(table, balance.getCantMarzo());
            utilExport.addCell(table, balance.getCantAbril());
            utilExport.addCell(table, balance.getCantMayo());
            utilExport.addCell(table, balance.getCantJunio());
            utilExport.addCell(table, balance.getCantJulio());
            utilExport.addCell(table, balance.getCantAgosto());
            utilExport.addCell(table, balance.getCantSetiembre());
            utilExport.addCell(table, balance.getCantOctubre());
            utilExport.addCell(table, balance.getCantNoviembre());
            utilExport.addCell(table, balance.getCantDiciembre());
        }

        document.add(table);
    }

}
