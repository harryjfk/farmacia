<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <span class="title">Hospital: VITARTE</span><br/>
    <span class="title">Direcci&oacute;n: AV NICOLAS AYLLON 5880-ATE</span><br/>
    <span class="title">RUC: 20512338756</span><br/>
    <span class="title">Comprobante número y serie: </span>${venta.nroVenta}<br/>
    <span class="title">N&uacute;mero de Serie: </span>${venta.puntoDeVenta.serieBoleta}<br/>
    <span class="title">Fecha y Hora: </span>${ventafechaRegistro}<br/>
</div>
<hr/>
<table id="detalle">
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Unidad de Medida</th>
            <th>Cantidad</th>
            <th>Precio</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td style="text-align: left">${venta.matriz.nombre}</td>
            <td style="text-align: left">${venta.unidad.nombreUnidadMedida}</td>
            <td style="text-align: center"><fmt:formatNumber pattern="0.##">${venta.cantidad}</fmt:formatNumber></td>
            <td style="text-align: center"><fmt:formatNumber pattern="0.00">${venta.precio}</fmt:formatNumber></td>
        </tr>
    </tbody>
</table>
<hr/>
<div>
    <table id="bottom-tbl">
        <tbody>
            <tr>
                <td class="tlt">Subtotal:</td>
                <td>${venta.subTotalPreventa + venta.redondeoPreventa}</td>
            </tr>
            <tr>
                <td class="tlt">Impuesto:</td>
                <td>${venta.impuestoPreventa}</td>
            </tr>
            <tr>
                <td class="tlt">Total:</th>
                <td>${venta.preventaNetoAPagar}</td>
            </tr>
        </tbody>
    </table>
</div>