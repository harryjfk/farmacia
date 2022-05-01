package pe.gob.minsa.farmacia.viewExcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import pe.gob.minsa.farmacia.domain.TipoProceso;
import pe.gob.minsa.farmacia.util.AbstractITextPdfView;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class TipoProcesoExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<TipoProceso> tiposProceso = (List<TipoProceso>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Tipos de Documentos";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código");
        cabeceras.add("Tipo de Proceso");
        cabeceras.add("Estado");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        for (TipoProceso tipoProceso : tiposProceso) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(tipoProceso.getIdTipoProceso());
            aRow.createCell(1).setCellValue(tipoProceso.getNombreTipoProceso());
            aRow.createCell(2).setCellValue(tipoProceso.getActivoTexto());

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }
}