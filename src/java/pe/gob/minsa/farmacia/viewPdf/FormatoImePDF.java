package pe.gob.minsa.farmacia.viewPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

public class FormatoImePDF extends AbstractView {

    private final String titulo = "INFORME MENSUAL ECONÓMICO SISMED - IME JULIO 2014";
    private BaseFont bfBold;
    private BaseFont bf;

    public FormatoImePDF() {
        initializeFonts();
    }

    @Override
    protected boolean generatesDownloadContent() {
        return false;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        setContentType("application/pdf");
        ByteArrayOutputStream baos = createTemporaryOutputStream();
        Document document = new Document(PageSize.A4.rotate());

        document.setMargins(20, 20, 20, 40);
        PdfWriter writer = PdfWriter.getInstance(document, baos);

        document.open();

        PdfContentByte cb = writer.getDirectContent();
        cb.setLineWidth(1f);

        generateLayoutBoletas(cb);
        generateLayoutBotelasDetalle(cb);
        generateLayoutGastosAdministrativos(cb);
        generateLayoutIME(cb);
        generateFooter(cb);
        document.close();

        response.setHeader("Content-Disposition", "attachment; filename=" + titulo + ".pdf");

        writeToResponse(response, baos);
    }

    private void generateLayoutBoletas(PdfContentByte cb) {
        cb.rectangle(145, 439, 260, 90);

        cb.moveTo(145, 518);
        cb.lineTo(405, 518);

        cb.moveTo(145, 498);
        cb.lineTo(405, 498);

        cb.moveTo(145, 483);
        cb.lineTo(320, 483);

        for (int i = 0; i < 4; ++i) {
            int y = 483 - ((i + 1) * 11);
            cb.moveTo(145, y);
            cb.lineTo(320, y);
        }

        cb.moveTo(192, 483);
        cb.lineTo(192, 439);

        cb.moveTo(320, 498);
        cb.lineTo(320, 439);

        cb.stroke();

        createContent(cb, 275, 521, "Periodo del 28 de Junio al 29 de Julio del 2014", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 147, 506, "SALIDAS POR VENTA O EXONERACIÓN (Boletas de venta)");
        createContent(cb, 232, 488, "Boletas emitidas", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 147, 475, "Serie:");
        createContent(cb, 147, 464, "Del:");
        createContent(cb, 147, 453, "Al:");
        createContent(cb, 147, 442, "Anuladas:");

        createContent(cb, 256, 475, "001", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 256, 464, "1566030", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 256, 453, "1586468", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 256, 442, "", PdfContentByte.ALIGN_CENTER);

        createContent(cb, 363, 488, "Importe total de ventas", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 363, 447, "95,210.60", PdfContentByte.ALIGN_CENTER);
    }

    private void generateLayoutBotelasDetalle(PdfContentByte cb) {
        createContentBold(cb, 296, 430, "EMITIDAS", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 430, "DISPENSADA", PdfContentByte.ALIGN_CENTER);

        cb.rectangle(145, 361, 260, 66);

        for (int i = 0; i < 6; ++i) {
            int y = 427 - ((i + 1) * 11);
            cb.moveTo(145, y);
            cb.lineTo(405, y);
        }

        cb.moveTo(271, 361);
        cb.lineTo(271, 427);

        cb.moveTo(320, 361);
        cb.lineTo(320, 427);

        cb.stroke();

        createContent(cb, 147, 419, "No. De Recetas por Demanda");
        createContent(cb, 147, 408, "No. De Recetas por SIS");
        createContent(cb, 147, 397, "No. Recetas Interven Sanit");
        createContent(cb, 147, 386, "No. Recetas por Soat");
        createContent(cb, 147, 375, "No. Recetas por Exoneración");
        createContent(cb, 147, 364, "N° de Recetas externas");

        createContentBold(cb, 295, 419, "19,729", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 295, 408, "8,682", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 295, 397, "859", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 295, 386, "246", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 295, 375, "4", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 295, 364, "0", PdfContentByte.ALIGN_CENTER);

        createContentBold(cb, 363, 419, "19,729", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 408, "8,682", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 397, "859", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 386, "246", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 375, "4", PdfContentByte.ALIGN_CENTER);
        createContentBold(cb, 363, 364, "0", PdfContentByte.ALIGN_CENTER);
    }

    private void generateLayoutGastosAdministrativos(PdfContentByte cb) {
        cb.rectangle(465, 361, 257, 168);

        cb.moveTo(465, 518);
        cb.lineTo(722, 518);

        cb.moveTo(465, 498);
        cb.lineTo(722, 498);

        for (int i = 0; i < 11; ++i) {
            int y = 498 - ((i + 1) * 11);
            cb.moveTo(465, y);
            cb.lineTo(722, y);
        }

        for (int i = 0; i < 4; ++i) {
            int y = 465 + ((i + 1) * 52);
            cb.moveTo(y, 518);
            cb.lineTo(y, 361);
        }

        cb.stroke();

        createContent(cb, 593, 521, "GASTOS ADMINISTRATIVOS DE LA UNIDAD EJECUTORA CON EL 20%", PdfContentByte.ALIGN_CENTER);

        createContent(cb, 491, 506, "FECHA", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 543, 506, "PARTIDA", PdfContentByte.ALIGN_CENTER);

        createContent(cb, 595, 509, "DETALLE", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 595, 503, "DEL GASTO", PdfContentByte.ALIGN_CENTER);

        createContent(cb, 647, 506, "DOC FUENTE", PdfContentByte.ALIGN_CENTER);

        createContent(cb, 697, 506, "IMPORTE", PdfContentByte.ALIGN_CENTER);

        for (int i = 0; i < 11; ++i) {
            int y = 501 - ((i + 1) * 11);

            for (int t = 0; t < 5; ++t) {
                if (t < 4) {
                    int x = 465 + ((t + 1) * 52);

                    switch (t) {
                        case 0:
                            createContent(cb, x - 26, y, "26/01/2015", PdfContentByte.ALIGN_CENTER);
                            break;
                        case 1:
                            createContent(cb, x - 26, y, "2.3.27.11.99", PdfContentByte.ALIGN_CENTER);
                            break;
                        case 2:
                            createContent(cb, x - 26, y, "Pago Terceros", PdfContentByte.ALIGN_CENTER);
                            break;
                        case 3:
                            createContent(cb, x - 26, y, "C/S 287-288", PdfContentByte.ALIGN_CENTER);
                            break;
                        default:
                    }
                } else {
                    int x = 465 + ((t + 1) * 51);
                    createContent(cb, x - 26, y, "34,654.93", PdfContentByte.ALIGN_LEFT);
                }
            }
        }
    }

    private void generateLayoutIME(PdfContentByte cb){
        cb.rectangle(145, 126, 577, 235);
        
        cb.moveTo(145, 324);
        cb.lineTo(722, 324);
        
        for (int i = 0; i < 17; ++i) {
            int y = 324 - ((i + 1) * 11);
            cb.moveTo(145, y);
            cb.lineTo(722, y);
        }
        
        cb.moveTo(271, 214);
        cb.lineTo(271, 361);
        
        cb.moveTo(320, 214);
        cb.lineTo(320, 361);
        
        cb.moveTo(405, 126);
        cb.lineTo(405, 350);
        
        cb.moveTo(465, 126);
        cb.lineTo(465, 339);
        
        cb.moveTo(517, 126);
        cb.lineTo(517, 361);
        
        cb.moveTo(569, 214);
        cb.lineTo(569, 361);
        
        cb.moveTo(621, 214);
        cb.lineTo(621, 361);
        
        cb.moveTo(320, 350);
        cb.lineTo(517, 350);
        
        cb.moveTo(405, 339);
        cb.lineTo(517, 339);
        
        cb.moveTo(199, 126);
        cb.lineTo(199, 225);
        
        cb.moveTo(673, 126);
        cb.lineTo(673, 225);
                
        cb.stroke();
        
        createContentSmall(cb, 208, 340, "IMPORTE", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 295, 353, "IMPORTE DE", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 295, 346, "ATENCIONES", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 295, 334, "FARMACIA", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 295, 327, "(FORMATO ICI)", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 418, 354, "CAPTACIÓN O REEMBOLSOS DEL MES", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 460, 343, "DISTRIBUCIÓN DE LO CAPTADO", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 362, 335, "TOTAL(B)", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 435, 330, "80%", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 491, 330, "20%", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 543, 349, "CUENTAS POR", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 543, 342, "COBRAR DEL MES", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 543, 335, "(C=A-B)", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 595, 354, "CUENTAS POR", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 595, 347, "COBRAR", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 595, 340, "ACUMULADAS", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 595, 333, "DEL MES ANTERIOR", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 595, 326, "(D)", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 673, 347, "CUENTAS POR", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 673, 340, "COBRAR ACUMULADAS EN EL", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 673, 333, "MES(E=C+D)", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 147, 317, "VENTAS (+)");
        createContentSmall(cb, 147, 306, "CRÉDITO HOSPITALIZADOS (+)");
        createContentSmall(cb, 147, 295, "SOAT (+)");
        createContentSmall(cb, 147, 284, "OTROS CONVENIOS O CRÉDITOS (+)");
        createContentSmall(cb, 147, 273, "TOTAL CAPTADO POR RDR");
        createContentSmall(cb, 147, 262, "SIS (+)");
        createContentSmall(cb, 147, 251, "SIS SALUD");
        createContentSmall(cb, 147, 240, "INTERVENCIONES SANITARIAS SOPORTE (+)");
        createContentSmall(cb, 147, 229, "DEFENSA NACIONAL (+)");
        
        createContentSmall(cb, 316, 317, "95,210.60", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 401, 317, "95,210.60", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 461, 317, "76,168.48", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 317, "19,042.12", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 565, 317, "", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 617, 317, "", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 719, 317, "", PdfContentByte.ALIGN_RIGHT);
        
        //LEFT
        createContentSmall(cb, 172, 218, "F", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 172, 207, "G", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 172, 196, "H", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 172, 163, "I", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 172, 141, "J", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 172, 130, "F=G+H+I+J", PdfContentByte.ALIGN_CENTER);
        
        //RIGHT
        createContentSmall(cb, 697, 218, "F", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 697, 207, "G", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 697, 196, "H", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 697, 163, "I", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 697, 141, "J", PdfContentByte.ALIGN_CENTER);
        createContentSmall(cb, 697, 130, "F=G+H+I+J", PdfContentByte.ALIGN_CENTER);
        
        createContentSmall(cb, 201, 218, "SUBTOTAL EN SOLES", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 316, 218, "231,483.88", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 401, 218, "244,153.19", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 462, 218, "195,322.55", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 218, "48,830.64", PdfContentByte.ALIGN_RIGHT);        
        createContentSmall(cb, 669, 218, "1,166,585.31", PdfContentByte.ALIGN_RIGHT);
        
        createContentSmall(cb, 201, 207, "SALDO DISPONIBLE DEL MES ANTERIOR PARA MEDICAMENTOS +", PdfContentByte.ALIGN_LEFT);
        createContentTiny(cb, 201, 196, "10% PARA FORTALECIMIENTO (EN CASO DE NO TENER STOCK MINIMO)+", PdfContentByte.ALIGN_LEFT);
        createContentTiny(cb, 201, 185, "PARA FORTALECIMIENTO (EN CASO DE NO TENER STOCK MINIMO X DON Y TRANS.)", PdfContentByte.ALIGN_LEFT);
        createContentTiny(cb, 201, 174, "PARA FORTALECIMIENTO (EN CASO DE NO TENER STOCK MINIMO X SOAT)", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 201, 163, "(+) EXONERACIONES", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 201, 152, "TOTAL DE ABASTECIMIENTO EN EL MES TRANSF. Y/O DONA (Reembolso SIS)", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 201, 141, "TOTAL DE ABASTECIMIENTO EN EL MES (Abril-14) (-) R.D.R *", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 201, 130, "SALDO DISPONIBLE PARA COMPRA DE MEDICAMENTOS PARA EL MES SIGUIENTE", PdfContentByte.ALIGN_LEFT);
        
        createContentSmall(cb, 462, 207, "00000", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 462, 196, "00001", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 462, 163, "00002", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 462, 141, "00003", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 462, 130, "00004", PdfContentByte.ALIGN_RIGHT);
        
        createContentSmall(cb, 513, 207, "B0000", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 196, "B0001", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 163, "B0002", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 141, "B0003", PdfContentByte.ALIGN_RIGHT);
        createContentSmall(cb, 513, 130, "B0004", PdfContentByte.ALIGN_RIGHT);
        
        createContentSmall(cb, 521, 207, "SALDO DISPONIBLE DEL MES PASADO PARA GASTOS ADM. +", PdfContentByte.ALIGN_LEFT);
        createContentTiny(cb, 521, 196, "(-) 10% PARA FORTALECIMIENTO (EN CASO DE NO TENER STOCK)", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 521, 185, "(-) TRANSFERENCIAS A OTRAS UNIDADES EJECUTORAS", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 521, 163, "(+) EXONERACIONES", PdfContentByte.ALIGN_LEFT);
        createContentSmall(cb, 521, 141, "(-) TOTAL DE GASTOS ADM EN EL MES (Abril-14)", PdfContentByte.ALIGN_LEFT);
        createContentTiny(cb, 521, 130, "SALDO DISPONIBLE PARA GASTOS ADM PARA EL MES SIGUIENTE", PdfContentByte.ALIGN_LEFT);
    }
    
    private void generateFooter(PdfContentByte cb){                
        createContentBold(cb, 201, 117, "* La afectación del Fondo Rotatorio se efectua posterior al cierre de cada mes,", PdfContentByte.ALIGN_LEFT, 8);
        
        cb.moveTo(145, 70);
        cb.lineTo(272, 70);
        
        cb.moveTo(326, 70);
        cb.lineTo(513, 70);
        
        cb.moveTo(578, 70);
        cb.lineTo(682, 70);
        
        createContent(cb, 208, 62, "FIRMA DIRECTORA DEL HOSPITAL", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 419, 62, "FIRMA DEL RESPONSABLE DE FARMACIA", PdfContentByte.ALIGN_CENTER);
        createContent(cb, 630, 62, "Vo. Bo. ECONOMIA", PdfContentByte.ALIGN_CENTER);
        
        cb.stroke();
    }
    
    private void createContentBold(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bfBold, 6);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createContentBold(PdfContentByte cb, float x, float y, String text, int align, int size) {
        cb.beginText();
        cb.setFontAndSize(bf, size);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();
    }
    
    private void createContentBold(PdfContentByte cb, float x, float y, String text, int align) {
        createContentBold(cb, x, y, text, align, 6);
    }

    private void createContent(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bf, 6);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }
    
    private void createContentTiny(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 4);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }

    private void createContentSmall(PdfContentByte cb, float x, float y, String text) {
        cb.beginText();
        cb.setFontAndSize(bf, 5);
        cb.setTextMatrix(x, y);
        cb.showText(text.trim());
        cb.endText();
    }
    
    private void createContentSmall(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 5);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();
    }
    
    private void createContent(PdfContentByte cb, float x, float y, String text, int align) {
        cb.beginText();
        cb.setFontAndSize(bf, 6);
        cb.showTextAligned(align, text.trim(), x, y, 0);
        cb.endText();
    }

    private void initializeFonts() {
        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {

        } catch (IOException e) {

        }
    }
}
