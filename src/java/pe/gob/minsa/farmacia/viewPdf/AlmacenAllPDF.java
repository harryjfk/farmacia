package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

public class AlmacenAllPDF extends AbstractITextPdfView {

    public AlmacenAllPDF() {
        setDocumentHorizontal(false);
        String titulo = "Reporte de Almacenes y Subalmacenes";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Almacen> almacenes = (List<Almacen>) model.get("Data");

        String AlmacenPadre = "ALMACÉN PADRE : ";
        String AlmacenHijo = "SUBALMACÉN ";

        List<Almacen> almacenesPadre = new ArrayList<Almacen>();
        List<Almacen> almacenesHijos = new ArrayList<Almacen>();

        for (Almacen almacen : almacenes) {
            if (almacen.getIdAlmacenPadre() == null) {
                almacenesPadre.add(almacen);
            } else {
                almacenesHijos.add(almacen);
            }
        }

        for (Almacen almacenPadre : almacenesPadre) {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(80);
            table.setWidths(new int[]{3, 8, 2});
            table.setHeaderRows(0);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            this.addCell(table, AlmacenPadre);
            this.addCell(table, almacenPadre.getDescripcion());
            this.addCell(table, almacenPadre.getAbreviatura());
            int i = 1;
            for (Almacen almacenHijo : almacenesHijos) {
                if (almacenPadre.getIdAlmacen() == almacenHijo.getIdAlmacenPadre()) {
                    this.addCell(table, AlmacenHijo + String.valueOf(i) + " :");
                    this.addCell(table, almacenHijo.getDescripcion());
                    this.addCell(table, almacenHijo.getAbreviatura());
                    i += 1;
                }
            }

            document.add(table);
        }
    }
    
    public void addCell(PdfPTable table, String cellText) {
        Font fuenteTitle = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);
        PdfPCell cell;
        cell = new PdfPCell(new Paragraph(cellText, fuenteTitle));
        cell.setPadding(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);     

        table.addCell(cell);
    }
}
