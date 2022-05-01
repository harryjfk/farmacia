package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class AlmacenPDF extends AbstractITextPdfView {

    @Autowired
    AlmacenService almacenService;

    public void setAlmacenService(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }
    
    public AlmacenPDF() {
        setDocumentHorizontal(false);        
    }
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        String idAlmacenPadre = request.getParameter("idAlmacenPadre");
        
        if (idAlmacenPadre == null) {
            String titulo = "Reporte de Almacenes";
            setDocumentName(titulo);
            setTitulo(titulo);
        }else{
            Almacen almacen = almacenService.obtenerPorId(Integer.parseInt(idAlmacenPadre));
            String titulo = "Reporte de Subalmacenes de " + almacen.getDescripcion();
            setDocumentName(titulo);
            setTitulo(titulo);
        }
        
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        Document document = newDocument();
        document.setMargins(20, 20, 20, 40);
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);        

        document.open();
        buildPdfDocument(model, document, writer, request, response);        
        document.close();
        
        response.setHeader("Content-Disposition","attachment; filename=" + getDocumentName() + ".pdf");
        
        writeToResponse(response, baos);              
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Almacen> almacenes = (List<Almacen>) model.get("Data");

        String idAlmacenPadre = request.getParameter("idAlmacenPadre");
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table;
        
        if (idAlmacenPadre == null) {
            table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 6, 2, 1});
            table.setHeaderRows(1);
            utilExport.addCellHead(table, "Código");
            utilExport.addCellHead(table, "Almacén");
            utilExport.addCellHead(table, "Subalmacenes");
            utilExport.addCellHead(table, "Estado");

            for (Almacen almacen : almacenes) {
                utilExport.addCell(table, almacen.getIdAlmacen());
                utilExport.addCell(table, almacen.getDescripcion());
                utilExport.addCell(table, almacen.getCantidadHijos());
                utilExport.addCell(table, almacen.getActivoTexto());
            }
        }else{
            table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 7, 1});
            table.setHeaderRows(1);
            utilExport.addCellHead(table, "Código");
            utilExport.addCellHead(table, "Almacén");            
            utilExport.addCellHead(table, "Estado");
        
            for (Almacen almacen : almacenes) {
                utilExport.addCell(table, almacen.getIdAlmacen());
                utilExport.addCell(table, almacen.getDescripcion());                
                utilExport.addCell(table, almacen.getActivoTexto());
            }
        }
        
        document.add(table);
    }
}
