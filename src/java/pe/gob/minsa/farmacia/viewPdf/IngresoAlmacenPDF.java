package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.IngresoAlmacenDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class IngresoAlmacenPDF extends AbstractITextPdfView {
    
     public IngresoAlmacenPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Productos Ingresados";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IngresoAlmacenDto> ingresos = (List<IngresoAlmacenDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 1, 8});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Fecha Registro");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Medicamento o Insumo");
        
        GregorianCalendar grego = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());        
        
        for (IngresoAlmacenDto ingreso : ingresos) {
            grego.setTimeInMillis(ingreso.getFechaRegistro().getTime());
            utilExport.addCell(table, dateFormat.format(grego.getTime()));
            utilExport.addCell(table, ingreso.getCodigoSismed());
            utilExport.addCell(table, ingreso.getDescripcion());
        }

        document.add(table);
    }
    
}
