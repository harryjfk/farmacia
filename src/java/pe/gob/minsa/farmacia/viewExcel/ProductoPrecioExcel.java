package pe.gob.minsa.farmacia.viewExcel;

import java.text.DecimalFormat;
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
import pe.gob.minsa.farmacia.domain.dto.PrecioUltimoDto;
import pe.gob.minsa.farmacia.util.UtilExportExcel;

public class ProductoPrecioExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest hsr, HttpServletResponse response) throws Exception {
        List<PrecioUltimoDto> precios = (List<PrecioUltimoDto>) model.get("Data");

        UtilExportExcel utilExportExcel = new UtilExportExcel();

        String titulo = "Reporte de Precios";

        HSSFSheet sheet = utilExportExcel.getSheet(titulo, workbook);
        List<String> cabeceras = new ArrayList<String>();
        cabeceras.add("Código Sismed");
        cabeceras.add("Medicamento o Insumo");
        cabeceras.add("Precio Adquisición");
        cabeceras.add("Precio Distribución");
        cabeceras.add("Precio Operación");

        utilExportExcel.addRowHead(cabeceras, sheet, workbook);

        CellStyle rowStyle = utilExportExcel.getCellRowStyle(workbook);

        int rowCount = UtilExportExcel.rowDataCount;

        DecimalFormat df = new DecimalFormat("0.000000");

        for (PrecioUltimoDto precio : precios) {
            HSSFRow aRow = sheet.createRow(rowCount++);

            aRow.createCell(0).setCellValue(precio.getCodigoSismed());
            aRow.createCell(1).setCellValue(precio.getNombreProducto());

            if (precio.getPrecioAdquisicion() != null) {
                aRow.createCell(2).setCellValue(df.format(precio.getPrecioAdquisicion().doubleValue()));
            } else {
                aRow.createCell(2).setCellValue("");
            }

            if (precio.getPrecioDistribucion() != null) {
                aRow.createCell(3).setCellValue(df.format(precio.getPrecioDistribucion().doubleValue()));
            } else {
                aRow.createCell(3).setCellValue("");
            }

            if (precio.getPrecioOperacion() != null) {
                aRow.createCell(4).setCellValue(df.format(precio.getPrecioOperacion().doubleValue()));
            } else {
                aRow.createCell(4).setCellValue("");
            }

            for (int i = 0; i < cabeceras.size(); ++i) {
                aRow.getCell(i).setCellStyle(rowStyle);
            }
        }

        response.addHeader("content-disposition", "attachment;  filename=" + titulo + ".xls");
    }

}
