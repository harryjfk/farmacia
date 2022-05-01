/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportPDF;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author stark
 */
public class DemandaInsatisfechaPDF extends AbstractITextPdfView {

    public DemandaInsatisfechaPDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Demandas Insatisfechas";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<DemandaInsatisfecha> demandas = (List<DemandaInsatisfecha>) model.get("Data");
        Cliente cliente = (Cliente) model.get("Cliente");
        final Font BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        UtilExportPDF utilExport = new UtilExportPDF();
        if (cliente != null) {

            Phrase data = setPhrase(BOLD, cliente);

            document.add(data);
        }

        PdfPTable table = addTable(utilExport, demandas);

        document.add(table);

    }

    private Phrase setPhrase(final Font BOLD, Cliente cliente) {
        Phrase data = new Phrase();
        data.add(Chunk.NEWLINE);
        data.add(Chunk.NEWLINE);
        data.add(new Chunk("Código: ", BOLD));
        data.add(new Chunk(cliente.getCodigo()));
        data.add(Chunk.NEWLINE);
        data.add(new Chunk("Nombre: ", BOLD));
        data.add(new Chunk(cliente.getNombreCliente()));
        data.add(Chunk.NEWLINE);
        return data;
    }

    private PdfPTable addTable(UtilExportPDF utilExport, List<DemandaInsatisfecha> demandas) throws DocumentException {
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{11, 11, 13, 11, 11, 11, 11, 11, 11});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Fecha");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "Descripción");
        utilExport.addCellHead(table, "Tipo");
        utilExport.addCellHead(table, "F.F");
        utilExport.addCellHead(table, "Lote");
        utilExport.addCellHead(table, "Precio");
        utilExport.addCellHead(table, "F.V.");
        utilExport.addCellHead(table, "Saldo");
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        for (DemandaInsatisfecha demanda : demandas) {
            utilExport.addCell(table, dateFormat.format(demanda.getFecha()), Element.ALIGN_CENTER);
            utilExport.addCell(table, demanda.getId().toString(), Element.ALIGN_CENTER);
            utilExport.addCell(table, demanda.getProducto().getProducto().getDescripcion(), Element.ALIGN_CENTER);
            utilExport.addCell(table, demanda.getProducto().getProducto().getIdTipoProducto().getNombreTipoProducto(), Element.ALIGN_CENTER);
            utilExport.addCell(table, demanda.getProducto().getProducto().getIdFormaFarmaceutica().getNombreFormaFarmaceutica(), Element.ALIGN_CENTER);
            utilExport.addCell(table, demanda.getProducto().getLote().getDescripcion(), Element.ALIGN_CENTER);
            
            DecimalFormat df = new DecimalFormat("0.00########");
            BigDecimal precio = demanda.getProducto().getPrecio().stripTrailingZeros();
            
            utilExport.addCell(table, String.valueOf(df.format(precio)), Element.ALIGN_CENTER);
            utilExport.addCell(table, dateFormat.format(demanda.getProducto().getFechaVencimiento()), Element.ALIGN_CENTER);
            
            BigDecimal saldo = demanda.getSaldo().stripTrailingZeros();
            utilExport.addCell(table, String.valueOf(df.format(saldo)), Element.ALIGN_CENTER);
//            utilExport.addCell(table, demanda.getProducto().getProducto());

        }
        return table;
    }

}
