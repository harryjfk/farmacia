package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.StockGeneralProductoDto;
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class StockGeneralProductoPDF extends AbstractITextPdfView{

    public StockGeneralProductoPDF() {
        setDocumentHorizontal(true);
        String titulo = "Stock General de Productos";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<StockGeneralProductoDto> stockGeneralProductos = (List<StockGeneralProductoDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF(6);
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 4, 2, 3, 2, 2, 2, 2, 2});
        table.setHeaderRows(1);
        
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Stock");
        utilExport.addCellHead(table, "Forma Farmacéutica");        
        utilExport.addCellHead(table, "Presentación");
        utilExport.addCellHead(table, "Concentración");
        utilExport.addCellHead(table, "Precio Ref.");
        utilExport.addCellHead(table, "Stock Min");
        utilExport.addCellHead(table, "Stock Max");
        
        DecimalFormat df = new DecimalFormat("0.0000");
        
        for (StockGeneralProductoDto stockGeneralProducto : stockGeneralProductos) {
       
            utilExport.addCell(table, stockGeneralProducto.getCodigoSismed());
            utilExport.addCell(table, stockGeneralProducto.getDescripcion());
            utilExport.addCell(table, stockGeneralProducto.getCantidad());
            utilExport.addCell(table, stockGeneralProducto.getNombreFormaFarmaceutica());
            utilExport.addCell(table, stockGeneralProducto.getPresentacion());
            utilExport.addCell(table, stockGeneralProducto.getConcentracion());
            utilExport.addCell(table, df.format(stockGeneralProducto.getPrecioRef().doubleValue()));
            utilExport.addCell(table, stockGeneralProducto.getStockMin());            
            utilExport.addCell(table, stockGeneralProducto.getStockMax());
        }

        document.add(table);
    }    
}
