package pe.gob.minsa.farmacia.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfHandlerEvents extends PdfPageEventHelper {
    
    final String tituloCabecera;
    final boolean rotateEvent;
    
    public PdfHandlerEvents(String tituloCabecera, boolean rotateEvent){
        this.tituloCabecera = tituloCabecera.toUpperCase();
        this.rotateEvent = rotateEvent;        
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            PdfPTable headTable = new PdfPTable(1);
            headTable.setWidthPercentage(100);
            headTable.setSpacingAfter(10);
            
            PdfPCell headCell;
            headCell = new PdfPCell(new Phrase("Ministerio de Salud", new Font(Font.FontFamily.HELVETICA, 8)));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headCell.setBorder(0);
            headCell.setPaddingBottom(8);
            headTable.addCell(headCell);
            
            headCell = new PdfPCell(new Phrase(tituloCabecera, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD)));
            headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headCell.setBorder(0);
            headTable.addCell(headCell);
            
            URL urlLogo = getClass().getResource("resources" + System.getProperty("file.separator") + "logominsa.jpg");
            Image image = Image.getInstance(urlLogo);
            image.setAbsolutePosition(20, 50);
            image.scaleAbsolute(100, 22);
            image.setAlignment(Image.ALIGN_LEFT);
            PdfContentByte cbhead = writer.getDirectContent();            
            PdfTemplate tp = cbhead.createTemplate(273, 95);
            
            tp.addImage(image);

            if(rotateEvent) {
                cbhead.addTemplate(tp, 0, 500);
            }else{
                cbhead.addTemplate(tp, 0, 842 - 95);
            }           
            
            document.add(headTable);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfHandlerEvents.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfHandlerEvents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document){
    }
}
