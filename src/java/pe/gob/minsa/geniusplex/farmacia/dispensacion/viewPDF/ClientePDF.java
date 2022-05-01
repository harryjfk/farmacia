/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.viewPDF;

import com.itextpdf.text.Document;
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

/**
 *
 * @author stark
 */
public class ClientePDF extends AbstractITextPdfView {
    
    public ClientePDF() {
        setDocumentHorizontal(true);
        String titulo = "Reporte de Clientes";
        setDocumentName(titulo);
        setTitulo(titulo);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Cliente> clientes = (List<Cliente>)model.get("Data");
        
        UtilExportPDF utilExport = new UtilExportPDF();
        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {12, 12, 12, 12, 12, 12, 12,12,12,12, 12});
        table.setHeaderRows(1);
        utilExport.addCellHead(table, "Dni");
        utilExport.addCellHead(table, "Nombre");
        utilExport.addCellHead(table, "A Paterno");
        utilExport.addCellHead(table, "A Materno");
        utilExport.addCellHead(table, "Código");
        utilExport.addCellHead(table, "No Afilicación");
        utilExport.addCellHead(table, "Dirección");
        utilExport.addCellHead(table, "Telefono");
        utilExport.addCellHead(table, "CodPersonal");
        utilExport.addCellHead(table, "TD");
        utilExport.addCellHead(table, "NoDocumento");

        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatoFecha());
        Calendar grego = new GregorianCalendar();
        
        for (Cliente cliente : clientes) {
            
            utilExport.addCell(table, (cliente.getDni() == null)? "" :cliente.getDni());          
            utilExport.addCell(table, (cliente.getNombre() == null)? "" :cliente.getNombre());          
            utilExport.addCell(table, (cliente.getApellidoPaterno() == null)? "" :cliente.getApellidoPaterno());            
            utilExport.addCell(table, (cliente.getApellidoMaterno() == null)? "" :cliente.getApellidoMaterno());            
            utilExport.addCell(table, (cliente.getCodigo()== null)?"":cliente.getCodigo());
            utilExport.addCell(table, (cliente.getNoAfiliacion()== null)?"":cliente.getNoAfiliacion());
            utilExport.addCell(table, (cliente.getDireccion() == null)?"":cliente.getDireccion());
            utilExport.addCell(table, (cliente.getTelefono()== null)?"":cliente.getTelefono());
            utilExport.addCell(table, (cliente.getCodPersonal() ==null)?"":cliente.getCodPersonal());
            utilExport.addCell(table, (cliente.getTipoDocumento() == null)?"":cliente.getTipoDocumento().getNombreTipoDocumento());
            utilExport.addCell(table, cliente.getNoDocumento());
            
        }

        document.add(table);
    
    }
    
}
