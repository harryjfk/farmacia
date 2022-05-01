package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.viewPDF;

import com.itextpdf.text.Document;
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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.TarjetaControlDto;

public class TarjetaControlVisiblePDF extends AbstractITextPdfView {

    public TarjetaControlVisiblePDF() {
        setDocumentHorizontal(true);
        String titulo = "Tarjeta de Control Visibe";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TarjetaControlDto> tarjetasControlDto = (List<TarjetaControlDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(10);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{50, 25, 7, 6, 6});
        table.setHeaderRows(1);
        
        utilExport.addCellHead(table, "Insumo");
        utilExport.addCellHead(table, "Unidad de Medida");
        utilExport.addCellHead(table, "Ingreso");
        utilExport.addCellHead(table, "Salidas");
        utilExport.addCellHead(table, "Saldo");

        for (TarjetaControlDto tarjetaControlDto : tarjetasControlDto) {
            utilExport.addCell(table, tarjetaControlDto.getInsumo().getNombre());
            utilExport.addCell(table, tarjetaControlDto.getInsumo().getUnidad().getNombreUnidadMedida());
            utilExport.addCell(table, tarjetaControlDto.getIngresos());
            utilExport.addCell(table, tarjetaControlDto.getSalidas());
            utilExport.addCell(table, tarjetaControlDto.getSaldo());
        }

        document.add(table);
    }
    
}
