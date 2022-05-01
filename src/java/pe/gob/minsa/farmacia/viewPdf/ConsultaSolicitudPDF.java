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

public class ConsultaSolicitudPDF extends AbstractITextPdfView {

    public ConsultaSolicitudPDF() {
        setDocumentHorizontal(true);
        setDocumentName("");
        setTitulo("");
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        List<BalanceSemestral> balances = (List<BalanceSemestral>) model.get("Data");
        
        String titulo = "";
        switch(Integer.valueOf(request.getParameter("tipoConsulta")!=null?request.getParameter("tipoConsulta"):"0")){
            case 1: titulo = "Consulta de Medicamentos Adquiridos"; break;
            case 2: titulo = "Consulta de Medicamentos no aprobados"; break;
            case 3: titulo = "Consulta de consumo histórico"; break;
        }
        setDocumentName(titulo);
        setTitulo(titulo);
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {12, 12, 12, 12, 12, 12, 12, 12, 12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Establecimiento");
        utilExport.addCellHead(table, "Institución");
        utilExport.addCellHead(table, "Fecha");
        utilExport.addCellHead(table, "Tipo Medicamento");
        utilExport.addCellHead(table, "Medicamento");
        utilExport.addCellHead(table, "Concentración");
        utilExport.addCellHead(table, "Forma Farmaceútica");
        utilExport.addCellHead(table, "Forma Presentación");
        utilExport.addCellHead(table, "Cantidad Solicitada");
        utilExport.addCellHead(table, "Estado");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (BalanceSemestral balance : balances) {
            utilExport.addCell(table, balance.getEstablecimiento());
            utilExport.addCell(table, balance.getInstitucion());
            if(balance.getFecha() != null){
                grego.setTimeInMillis(balance.getFecha().getTime());
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
            utilExport.addCell(table, balance.getAprobado());
        }

        document.add(table);
    }

}
