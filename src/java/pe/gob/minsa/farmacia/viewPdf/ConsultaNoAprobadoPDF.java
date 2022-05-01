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
import pe.gob.minsa.farmacia.domain.BalanceSemestral;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ConsultaNoAprobadoPDF extends AbstractITextPdfView {

    public ConsultaNoAprobadoPDF() {
        setDocumentHorizontal(true);
        setDocumentName("Consulta de Medicamentos no aprobados");
        setTitulo("Consulta de Medicamentos no aprobados");
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<BalanceSemestral> balances = (List<BalanceSemestral>) model.get("Data");
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Establecimiento");
        utilExport.addCellHead(table, "Institución");
        utilExport.addCellHead(table, "Fecha");
        utilExport.addCellHead(table, "Tipo Medicamento");
        utilExport.addCellHead(table, "Medicamento");
        utilExport.addCellHead(table, "Concentración");
        utilExport.addCellHead(table, "Forma Farmaceútica");
        utilExport.addCellHead(table, "Forma Presentación");
        utilExport.addCellHead(table, "Dosis diaria");
        utilExport.addCellHead(table, "Precio adq.");
        utilExport.addCellHead(table, "Estado");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (BalanceSemestral balance : balances) {
            utilExport.addCell(table, balance.getEstablecimiento());
            utilExport.addCell(table, balance.getInstitucion());
            if(balance.getFechaAprobacion() != null){
                grego.setTimeInMillis(balance.getFechaAprobacion().getTime());
                utilExport.addCell(table, dateFormat.format(grego.getTime()));
            }else{
                utilExport.addCell(table, "");
            }
            utilExport.addCell(table, balance.getTipoMedicamento());
            utilExport.addCell(table, balance.getDescripcionProducto());
            utilExport.addCell(table, balance.getConcentracion());
            utilExport.addCell(table, balance.getNombreFormaFarmaceutica());
            utilExport.addCell(table, balance.getFormaPresentacion());
            utilExport.addCell(table, balance.getCantidadSol());
            utilExport.addCell(table, balance.getPrecio());
            utilExport.addCell(table, balance.getAprobado());
        }

        document.add(table);
    }

}
