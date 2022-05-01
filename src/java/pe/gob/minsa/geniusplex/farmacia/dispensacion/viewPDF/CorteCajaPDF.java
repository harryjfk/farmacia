/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.CorteCajaDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.CorteCajaVentaDTO;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author stark
 */
public class CorteCajaPDF extends AbstractITextPdfView {

    final Font BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    public CorteCajaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Corte de Caja de ";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    @SuppressWarnings("null")
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UtilExportPDF utilExport = new UtilExportPDF();
        CorteCajaDTO cajaDTO = (CorteCajaDTO) model.get("CorteCaja");
        FilterData fData = (FilterData) model.get("fData");
        String nombrePc = "";
        Venta venta = null;
        if (cajaDTO != null && !cajaDTO.getTblVentas().isEmpty()) {
            venta = cajaDTO.getTblVentas().get(0).getVenta();
            nombrePc = venta.getPuntoDeVenta().getNombrePc();
        }

        if (nombrePc != null && nombrePc.length() > 0) {
            String titulo = "Corte de Caja de " + nombrePc;
            setDocumentName(titulo);
            setTitulo(titulo);
            prepareWriter(model, writer, request);
        }

        final boolean periodo = fData.getParams().containsKey("periodo");
        final boolean ptoVenta = fData.getParams().containsKey("puntoDeVenta:id");
        final boolean turno = fData.getParams().containsKey("turno:idTurno");
        final boolean vendedor = fData.getParams().containsKey("vendedor:idVendedor");
        final boolean fecha = fData.getParams().containsKey("ventafechaRegistro");

        if (periodo || ptoVenta || turno || vendedor || fecha) {
            PdfPTable pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setWidths(new int[]{50});
            if (periodo) {
                Object per = fData.getParams().get("periodo");
                addHeaderCell(pdfPTable, "Periodo: " + per.toString(), true);
            }
            final boolean ventaNoEsNull = venta != null;
            if (ptoVenta && ventaNoEsNull) {
                addHeaderCell(pdfPTable, "Punto de Venta: " + nombrePc, true);
            }
            if (turno && ventaNoEsNull) {
                addHeaderCell(pdfPTable, "Turno: " + venta.getTurno().getDescripcion(), true);
            }
            if (vendedor && ventaNoEsNull) {
                addHeaderCell(pdfPTable, "Vendedor: " + venta.getVendedor().getNombreVendedor(), true);
            }
            if (fecha) {
                addHeaderCell(pdfPTable, "Fecha de Registro" + fData.getParams().get("ventafechaRegistro").toString(), true);
            }
            document.add(pdfPTable);
            document.add(new Phrase(Chunk.NEWLINE));
        }

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{10, 10, 10, 10, 16, 16, 10, 6, 6, 6});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Anulada");
        utilExport.addCellHead(table, "Nro. Oper.");
        utilExport.addCellHead(table, "Nro. Docum.");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "Cliente");
        utilExport.addCellHead(table, "Cajero");
        utilExport.addCellHead(table, "F.P.");
        utilExport.addCellHead(table, "Venta");
        utilExport.addCellHead(table, "IGV");
        utilExport.addCellHead(table, "Total");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        float tableWidth = 0;

        for (CorteCajaVentaDTO tblVenta : cajaDTO.getTblVentas()) {
            Boolean anulada = tblVenta.getVenta().isAnulada();
            anulada = anulada == null ? false : anulada;

            tableWidth += addCell(table, anulada ? "SI" : "NO");
            tableWidth += addCell(table, tblVenta.getVenta().getNroOperacion());
            tableWidth += addCell(table, tblVenta.getVenta().getNroVenta());
            tableWidth += addCell(table, tblVenta.getVenta().getDocTipo());
            tableWidth += addCell(table, tblVenta.getVenta().getCliente().getNombreCliente());
            tableWidth += addCell(table, tblVenta.getVenta().getVendedor().getNombreVendedor());
            tableWidth += addCell(table, tblVenta.getVenta().getFormaDePago().getDescripcion());
            
            addCell(table, tblVenta.getTotalVenta().toString());
            addCell(table, tblVenta.getVenta().getImpuestoPreventa().toString());
            addCell(table, tblVenta.getVenta().getPreventaNetoAPagar());
        }

        document.add(table);
        document.add(new Phrase(Chunk.NEWLINE));

        Phrase tPhrase = new Phrase();
        Font font = tPhrase.getFont();
        BaseFont cbf = font.getCalculatedBaseFont(false);
        BaseFont boldCbf = BOLD.getCalculatedBaseFont(false);
        float tRecaudadoWidth = boldCbf.getWidthPoint("Total Recaudado: ", 12);
        if(tableWidth > 0) {
            tableWidth -= tRecaudadoWidth;
        }

        tPhrase.add(new Chunk(String.format("%150s", "Total Recaudado: "), BOLD));
        final String totalVenta = cajaDTO.getTotalVenta().toString() + "(Venta) ";
        final String totalIgv = cajaDTO.getTotalImpuestoIGV().toString() + "(IGV) ";
        final String overallTotal = cajaDTO.getOverallTotal().toString() + "(Total)";

        tPhrase.add(new Chunk(totalVenta, font));
        tPhrase.add(new Chunk(totalIgv, font));
        tPhrase.add(new Chunk(overallTotal, font));

        document.add(tPhrase);
        document.add(new Phrase(Chunk.NEWLINE));

        PdfPTable fPTable = new PdfPTable(3);
        fPTable.setWidthPercentage(50);
        fPTable.setWidths(new int[]{30, 10, 10});
        table.setHeaderRows(1);
        utilExport.addCellHead(fPTable, "Forma de Pago");
        utilExport.addCellHead(fPTable, "Cantidad");
        utilExport.addCellHead(fPTable, "Monto");

        HashMap<String, String> resumenFormPago = cajaDTO.getResumenFormPago();
        for (Map.Entry<String, String> entrySet : resumenFormPago.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            String[] split = value.split("-");
            utilExport.addCell(fPTable, key);
            utilExport.addCell(fPTable, split[0]);
            utilExport.addCell(fPTable, split[1]);
        }

        document.add(fPTable);
        document.add(new Phrase(Chunk.NEWLINE));

        PdfPTable anuTable = new PdfPTable(2);
        anuTable.setWidthPercentage(50);
        anuTable.setWidths(new int[]{25, 25});
        anuTable.setHeaderRows(1);
        utilExport.addCellHead(anuTable, "Cantidad");
        utilExport.addCellHead(anuTable, "Monto");

        utilExport.addCell(anuTable, cajaDTO.getCantidadAnulados());
        utilExport.addCell(anuTable, cajaDTO.getMontoAnulados().toString());

        document.add(anuTable);
//        PdfContentByte directContent = writer.getDirectContent();
//        ColumnText columnText = new ColumnText(directContent);
//        columnText.setSimpleColumn(0, 50, 1, 100);
//        columnText.addElement(fPTable);
//        columnText.addElement(anuTable);
//        columnText.go();

    }

    private void addHeaderCell(PdfPTable pdfPTable, String text, boolean bold) {
        PdfPCell lblCell = new PdfPCell();
        lblCell.setBorder(0);
        if (bold) {
            lblCell.addElement(new Phrase(new Chunk(text, BOLD)));
        } else {
            lblCell.addElement(new Phrase(new Chunk(text)));
        }
        pdfPTable.addCell(lblCell);
    }

    private float addCell(PdfPTable table, String cellText) {
        PdfPCell cell;
        Font fuenteCelda = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
        BaseColor htmlBorderTable = new BaseColor(221, 221, 221);
        cell = new PdfPCell(new Phrase(cellText, fuenteCelda));
        cell.setBorderColor(htmlBorderTable);
        table.addCell(cell);
        return cell.getWidth();
    }

}
