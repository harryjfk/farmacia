package pe.gob.minsa.farmacia.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Esta clase trabaja para iText 5.x in Spring El código de aquí es parecido a
 * la clase AbstractPdfView
 */
public abstract class AbstractITextPdfView extends AbstractView {
    
    private boolean documentHorizontal = false;
    private String documentName = "Reporte";
    private String titulo;

    public AbstractITextPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return false;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ByteArrayOutputStream baos = createTemporaryOutputStream();

        Document document = newDocument();
        document.setMargins(20, 20, 20, 40);
        PdfWriter writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);        

        document.open();
        buildPdfDocument(model, document, writer, request, response);        
        document.close();
        
        response.setHeader("Content-Disposition","attachment; filename=" + documentName + ".pdf");
        
        writeToResponse(response, baos);
    }

    protected Document newDocument() {
        if (documentHorizontal) {
            return new Document(PageSize.A4.rotate());
        } else {
            return new Document(PageSize.A4);
        }
    }

    protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
            throws DocumentException {
        
        writer.setPageEvent(new PdfHandlerEvents(titulo, documentHorizontal));
        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }

    protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public boolean isDocumentHorizontal() {
        return documentHorizontal;
    }

    public void setDocumentHorizontal(boolean documentHorizontal) {
        this.documentHorizontal = documentHorizontal;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo.toUpperCase();
    }
    
    public static String getFormatoFecha(){
        return "dd/MM/yyyy";
    }
}
