package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.dto.PrecioUltimoDto;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class ProductoPrecioPDF extends AbstractITextPdfView {
    
    public ProductoPrecioPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Precios";
        setDocumentName(titulo);
        setTitulo(titulo);
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<PrecioUltimoDto> precios = (List<PrecioUltimoDto>) model.get("Data");

        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 6, 1, 1, 1});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Código Sismed");
        utilExport.addCellHead(table, "Medicamento o Insumo");
        utilExport.addCellHead(table, "Precio Adquisición");
        utilExport.addCellHead(table, "Precio Distribución");
        utilExport.addCellHead(table, "Precio Operación");
        DecimalFormat df = new DecimalFormat("0.000000");
        

        for (PrecioUltimoDto precio : precios) {
            utilExport.addCell(table, precio.getCodigoSismed());
            utilExport.addCell(table, precio.getNombreProducto());
            
            if(precio.getPrecioAdquisicion() != null){
                utilExport.addCell(table, df.format(precio.getPrecioAdquisicion().doubleValue()));
            }else{
                utilExport.addCell(table, "");
            }
            
            if(precio.getPrecioDistribucion() != null){
                utilExport.addCell(table, df.format(precio.getPrecioDistribucion().doubleValue()));
            }else{
                utilExport.addCell(table, "");
            }
            
            if(precio.getPrecioOperacion() != null){
                utilExport.addCell(table, df.format(precio.getPrecioOperacion().doubleValue()));
            }else{
                utilExport.addCell(table, "");
            }
        }

        document.add(table);
    }    
}
