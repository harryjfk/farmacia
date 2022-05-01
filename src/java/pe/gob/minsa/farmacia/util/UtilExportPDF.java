package pe.gob.minsa.farmacia.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class UtilExportPDF {

    private final Font fuenteTitle;
    private final Font fuenteCelda;
    private final BaseColor htmlBorderTable;
    private final BaseColor htmlBgColorTHead;

    public UtilExportPDF(int sizeFont) {
        fuenteTitle = new Font(Font.FontFamily.HELVETICA, sizeFont, Font.BOLD, BaseColor.BLACK);
        fuenteCelda = new Font(Font.FontFamily.HELVETICA, sizeFont, Font.NORMAL, BaseColor.BLACK);
        htmlBorderTable = new BaseColor(221, 221, 221);
        htmlBgColorTHead = new BaseColor(235, 242, 246);
    }
    
    public UtilExportPDF() {
        fuenteTitle = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        fuenteCelda = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        htmlBorderTable = new BaseColor(221, 221, 221);
        htmlBgColorTHead = new BaseColor(235, 242, 246);
    }

    public void addCellHead(PdfPTable table, String cellText) {

        PdfPCell cell;
        cell = new PdfPCell(new Paragraph(cellText, fuenteTitle));
        cell.setPadding(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(htmlBgColorTHead);
        cell.setBorderColor(htmlBorderTable);

        table.addCell(cell);

    }

    public void addCell(PdfPTable table, String cellText) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(cellText, fuenteCelda));
        cell.setBorderColor(htmlBorderTable);
        table.addCell(cell);
    }
    
    public void addCell(PdfPTable table, String cellText, int horizontalAligment) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(cellText, fuenteCelda));
        cell.setBorderColor(htmlBorderTable);
        cell.setHorizontalAlignment(horizontalAligment);
        table.addCell(cell);
    }

    public void addCell(PdfPTable table, Integer cellText) {
        PdfPCell cell;

        cell = new PdfPCell(new Phrase(cellText.toString(), fuenteCelda));
        cell.setBorderColor(htmlBorderTable);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }

    public Font getFuenteTitle() {
        return fuenteTitle;
    }

    public BaseColor getHtmlBorderTable() {
        return htmlBorderTable;
    }

    public BaseColor getHtmlBgColorTHead() {
        return htmlBgColorTHead;
    }

    public Font getFuenteCelda() {
        return fuenteCelda;
    }

}
