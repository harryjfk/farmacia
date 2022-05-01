package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.Periodo;
import static pe.gob.minsa.farmacia.util.AbstractITextPdfView.getFormatoFecha;
import pe.gob.minsa.farmacia.util.PdfHandlerEvents;

public class NotaSalidaPDF extends AbstractView {
    
    private final String titulo = "Nota de Salida";
    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;

    @Override
    protected boolean generatesDownloadContent() {
        return false;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        setContentType("application/pdf");
        pageNumber = 0;
        ByteArrayOutputStream baos = createTemporaryOutputStream();
        Document document = new Document();
        initializeFonts();

        document.setMargins(20, 20, 20, 40);
        PdfWriter writer = newWriter(document, baos);

        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".pdf");

        writeToResponse(response, baos);
    }

    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {
        writer.setPageEvent(new PdfHandlerEvents(titulo, false));
        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {        
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }

    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Movimiento movimiento = (Movimiento) model.get("movimiento");
        List<MovimientoProducto> movimientosProductos = (List<MovimientoProducto>) model.get("movimientosProducto");
        
        PdfContentByte cb = writer.getDirectContent();
        boolean beginPage = true;
        int y = 0;

        for (int i = 0; i < movimientosProductos.size(); i++) {
            if (beginPage) {
                beginPage = false;
                generateHeader(doc, cb, movimiento);
                generateLayout(doc, cb);                
                y = 615;
            }
            generateDetail(doc, cb, i, y, movimientosProductos.get(i));
            y = y - 15;
            if (y < 50) {
                printPageNumber(cb);
                doc.newPage();
                beginPage = true;
            }
        }
        
        printPageNumber(cb);
    }    

    private void generateLayout(Document doc, PdfContentByte cb) {

        try {

            cb.setLineWidth(1f);
            
            // Cuadro de cabecera detalle
            // Marco de detalle
            cb.rectangle(20, 55, 555, 635);
            
            //Primera linea divisora horizontal
            cb.moveTo(20, 664);
            cb.lineTo(305, 664);
            
            //Segunda linea divisora horizontal
            cb.moveTo(20, 644);
            cb.lineTo(575, 644);
            
            //Segunda linea divisora horizontal
            cb.moveTo(20, 630);
            cb.lineTo(575,630);
            
            //Primera linea divisora vertical
            cb.moveTo(40, 55);
            cb.lineTo(40, 664);
            
            //Segunda linea divisora vertical
            cb.moveTo(80, 55);
            cb.lineTo(80, 664);
            
            //Tercera linea divisora vertical
            cb.moveTo(250, 55);
            cb.lineTo(250, 664);
            
            //Cuarta linea divisora vertical
            cb.moveTo(305, 55);
            cb.lineTo(305, 690);
            
            //Quinta linea divisora vertical
            cb.moveTo(360, 55);
            cb.lineTo(360, 690);
            
            //Sexta linea divisora vertical
            cb.moveTo(420, 55);
            cb.lineTo(420, 690);
            
            //Septima linea divisora vertical
            cb.moveTo(470, 55);
            cb.lineTo(470, 690);
            
            //Octava linea divisora vertical
            cb.moveTo(520, 55);
            cb.lineTo(520, 690);
            
            cb.stroke();

            // Nota de Ingreso Cabecera Texto
            createHeadings(cb, 70, 679, "PRODUCTO FARMACEUTICO");
            createHeadings(cb, 26, 650, "N°");
            createHeadings(cb, 44, 650, "CÓDIGO");
            createHeadings(cb, 135, 650, "DESCRIPCIÓN");
            createHeadings(cb, 271, 650, "F.F");
            createHeadings(cb, 321, 680, "LOTE");
            createHeadings(cb, 370, 680, "FEC. VEC.");
            createHeadings(cb, 428, 680, "PRECIO U.");
            createHeadings(cb, 474, 680, "CANTIDAD");
            createHeadings(cb, 534, 680, "TOTAL");
            
            //Nota de Ingreso Letras
            createHeadings(cb, 26, 634, "A");
            createHeadings(cb, 57, 634, "B");
            createHeadings(cb, 155, 634, "C");
            createHeadings(cb, 275, 634, "D");
            createHeadings(cb, 330, 634, "E");
            createHeadings(cb, 384, 634, "F");
            createHeadings(cb, 439, 634, "G");
            createHeadings(cb, 488, 634, "H");
            createHeadings(cb, 546, 634, "I");

        } catch (Exception ex) {
            
        }

    }

    private void generateHeader(Document doc, PdfContentByte cb, Movimiento movimiento) {

        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(movimiento.getIdPeriodo());
        
        try {

           createHeadings(cb, 46, 763, "NÚMERO");
            createHeadings(cb, 46, 750, "ORIGEN");
            createHeadings(cb, 46, 736, "DESTINO");

            createHeadings(cb, 90, 763, String.valueOf(movimiento.getNumeroMovimiento()));
            createHeadings(cb, 90, 750, movimiento.getAlmacenOrigen().getDescripcion());
            createHeadings(cb, 90, 736, movimiento.getAlmacenDestino().getDescripcion());

            createHeadings(cb, 384, 750, "PERÍODO");
            createHeadings(cb, 384, 736, "FECHA REGISTRO");

            SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
            Calendar grego = new GregorianCalendar();
            grego.setTime(movimiento.getFechaRegistro());

            createHeadings(cb, 468, 750, periodo.getNombreMes() + " - "+String.valueOf(periodo.getAnio()));
//            createHeadings(cb, 468, 736, dateFormat.format(grego.getTime()));
//            createHeadings(cb, 468, 750, periodo.getNombreMes() + " - "+String.valueOf(periodo.getAnio()));
            createHeadings(cb, 468, 736, dateFormat.format(grego.getTime()));
            
            createHeadings(cb, 43, 720, "CONCEPTO");
            createHeadings(cb, 103,720,movimiento.getConcepto().getNombreConcepto());
            
            createHeadings(cb, 43, 700, "Referencia");
            createHeadings(cb, 103,700, (movimiento.getReferencia()==null)?"":movimiento.getReferencia());
            
            createHeadings(cb, 370,710, "Tipo Documento");
            createHeadings(cb, 440,710, movimiento.getTipoDocumentoMov().getNombreTipoDocumentoMov());
            
            createHeadings(cb, 370,700, "Nro. Documento");
            createHeadings(cb, 440,700, movimiento.getNumeroDocumentoMov());
            
            //cuadro de numero, origen y destino
            cb.rectangle(43, 733, 250, 39);
            cb.moveTo(43, 759);
            cb.lineTo(293, 759);
            cb.moveTo(43, 746);
            cb.lineTo(293, 746);
            cb.moveTo(88, 772);
            cb.lineTo(88, 733);
            cb.stroke();
                       
            //cuadro periodo y fecha
            cb.rectangle(380, 733, 160, 26);
            cb.moveTo(380, 746);
            cb.lineTo(540, 746);
            cb.moveTo(463, 759);
            cb.lineTo(463, 733);
            cb.stroke();
            
        } catch (Exception ex) {
            
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y, MovimientoProducto movimientoProducto) {
        DecimalFormat df = new DecimalFormat("0.0000");
        DecimalFormat dfEntero = new DecimalFormat("0");

        try {

            createContent(cb, 30, y, String.valueOf(index + 1), PdfContentByte.ALIGN_CENTER);
            createContent(cb, 60, y, String.valueOf(movimientoProducto.getIdProducto()), PdfContentByte.ALIGN_CENTER);
            createContent(cb, 85, y, movimientoProducto.getNombreProducto(), PdfContentByte.ALIGN_LEFT);
            createContent(cb, 276, y, "TABLETA", PdfContentByte.ALIGN_CENTER);
            createContent(cb, 316, y, movimientoProducto.getLote(), PdfContentByte.ALIGN_LEFT);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
            Calendar grego = new GregorianCalendar();
            grego.setTime(movimientoProducto.getFechaVencimiento());
            createContent(cb, 390, y, dateFormat.format(grego.getTime()), PdfContentByte.ALIGN_CENTER);
                        
            createContent(cb, 465, y, df.format(movimientoProducto.getPrecio()), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 515, y, dfEntero.format(movimientoProducto.getCantidad()), PdfContentByte.ALIGN_RIGHT);
            createContent(cb, 569, y, df.format(movimientoProducto.getTotal()), PdfContentByte.ALIGN_RIGHT);

        } catch (Exception ex) {
            
        }
    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text) {

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {

        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();

    }
    
     private void printPageNumber(PdfContentByte cb){ 

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Página Nro. " + (pageNumber + 1), 570 , 25, 0);
        cb.endText(); 

        pageNumber++;
    }

    private void initializeFonts() throws IOException {

        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            
        } catch (IOException e) {
            
        }

    }
}
