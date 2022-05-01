package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;

/**
 *
 * @author stark
 */
public class GenericPdf extends AbstractITextPdfView {

    private int contentRows;
    public GenericPdf(String title) {
        setDocumentHorizontal(true);

        String titulo = "Reporte ";
        if (title != null && title.length() > 0) {
            titulo = title;
        }
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    public GenericPdf() {
        setDocumentHorizontal(true);

        String titulo = "Reporte ";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportPDF utilExport = new UtilExportPDF();
        final Font BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        String tlt = (String) model.get("Title");
        if (tlt != null && tlt.length() > 0) {
            setDocumentName(tlt);
            setTitulo(tlt);
            prepareWriter(model, writer, request);
            buildPdfMetadata(model, document, request);
        }
        PdfPTable headerTable = addHeader(model, BOLD, utilExport, document);
        if (headerTable != null) {
            document.add(headerTable);
            document.add(new Phrase(Chunk.NEWLINE));
        }
        PdfPTable contentTable = addContentTable(model, utilExport);
        if (contentTable != null) {
            document.add(contentTable);
        }

        PdfPTable subContent1Table = addSubContent1Table(model, utilExport);
        if (subContent1Table != null) {
            document.add(new Phrase(Chunk.NEWLINE));
            document.add(subContent1Table);
        }

    }

    private PdfPTable addSubContent1Table(Map<String, Object> model, UtilExportPDF utilExport) throws SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<String> contentLabels = (List<String>) model.get("SubContent1Labels");
        List<String> contentFields = (List<String>) model.get("SubContent1Fields");
        List<Object> contentData = (List<Object>) model.get("SubContent1Data");
        HashMap<Integer, Integer> contentColumns = (HashMap<Integer, Integer>) model.get("SubContent1Columns");
        if (contentData == null) {
            return null;
        }
        PdfPTable contentTable = new PdfPTable(contentColumns.size());
        contentTable.setWidthPercentage(100);
        int[] headerWidths = new int[contentColumns.size()];
        for (int i = 0; i < headerWidths.length; i++) {
            headerWidths[i] = contentColumns.get(i);
        }
        for (String label : contentLabels) {
            utilExport.addCellHead(contentTable, label);
        }
        contentTable.setHeaderRows(++contentRows);
        String value;
        Object obj = null;
        for (Object item : contentData) {
            for (String field : contentFields) {

                try {
                    obj = invokeMethod(field, item);

                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(GenericPdf.class.getName()).log(Level.SEVERE, null, ex);
                }

                value = getValue(obj);
                utilExport.addCell(contentTable, value, Element.ALIGN_CENTER);
            }
        }
        return contentTable;
    }

    private PdfPTable addContentTable(Map<String, Object> model, UtilExportPDF utilExport) throws InvocationTargetException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException {
        List<String> contentLabels = (List<String>) model.get("ContentLabels");
        List<String> contentFields = (List<String>) model.get("ContentFields");
        List<Object> contentData = (List<Object>) model.get("ContentData");
        HashMap<Integer, Integer> contentColumns = (HashMap<Integer, Integer>) model.get("ContentColumns");
        PdfPTable contentTable = new PdfPTable(contentColumns.size());
        contentTable.setWidthPercentage(100);
        int[] headerWidths = new int[contentColumns.size()];
        for (int i = 0; i < headerWidths.length; i++) {
            headerWidths[i] = contentColumns.get(i);
        }
        for (String label : contentLabels) {
            utilExport.addCellHead(contentTable, label);
        }
        contentTable.setHeaderRows(1);
        String value;
        Object obj = null;

        if (contentData != null) {
            for (Object item : contentData) {
                contentRows++;
                for (String field : contentFields) {

                    try {
                        obj = invokeMethod(field, item);

                    } catch (NoSuchFieldException ex) {
                        Logger.getLogger(GenericPdf.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    value = getValue(obj);
                    utilExport.addCell(contentTable, value, Element.ALIGN_CENTER);
                }
            }
        }
        return contentTable;
    }

    private Object invokeMethod(String field, Object item) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String[] tmp = null;
        Object obj = item;
        String method = "get" + toCapital(field);
        if (field.contains(":")) {
            tmp = field.split(":");
            field = tmp[0];
            method = "get" + toCapital(field);
        }

        obj = obj.getClass().getMethod(method, new Class<?>[]{}).invoke(obj, new Object[]{});
        if (obj != null) {
            if (null != tmp) {
                for (int i = 1; i < tmp.length; i++) {//navegar hacia el campo en la otra entidad
                    method = "get" + toCapital(tmp[i]);
                    obj = obj.getClass().getMethod(method, new Class<?>[]{}).invoke(obj, new Object[]{});
                }
            }
        } else {
            obj = "";
        }
        return obj;
    }

    private PdfPTable addHeader(Map<String, Object> model, final Font BOLD, UtilExportPDF utilExport, Document document) throws DocumentException {
        HashMap<Integer, Integer> headerColumns = (HashMap<Integer, Integer>) model.get("HeaderColumns");
        if (headerColumns == null) {
            return null;
        }
        HashMap<String, String>[] headerData = (HashMap<String, String>[]) model.get("HeaderData");
        PdfPTable headerTable = new PdfPTable(headerColumns.size());
        headerTable.setWidthPercentage(100);
        int[] headerWidths = new int[headerColumns.size()];
        for (int i = 0; i < headerWidths.length; i++) {
            headerWidths[i] = headerColumns.get(i);
        }
        headerTable.setWidths(headerWidths);
        for (HashMap<String, String> map : headerData) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String label = entry.getKey();
                String value = entry.getValue();

                Phrase phrase = new Phrase(label + ": ", BOLD);
                phrase.add(value);
                PdfPCell cell = new PdfPCell(phrase);
                cell.setBorder(0);
                headerTable.addCell(cell);
            }
        }
        return headerTable;
    }

    private String getValue(Object temp) {
        if (temp != null) {
            if (temp.getClass().getName().equalsIgnoreCase("java.util.Date")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
                return dateFormat.format(temp);
            }
            return temp.toString();
        } else {
            return "";
        }
    }

    private String toCapital(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
