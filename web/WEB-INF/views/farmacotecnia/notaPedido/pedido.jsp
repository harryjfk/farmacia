<%@include file="../../includeTagLib.jsp" %>
<%@ page import="java.util.Date" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Año <span class="f_req">*</span></label>
                    <input id="selAnio" class="form-control" data-req=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Mes <span class="f_req">*</span></label>
                    <select id="selMes" class="form-control" data-req=""></select> 
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Concepto</label>
                    <select id="idConcepto" name="idConcepto" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="concepto" items="${conceptos}">
                            <option value="${concepto.idConcepto}">${concepto.nombreConcepto}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen</label>
                    <input type="hidden" id="idAlmacenOrigen" value="0"/>
                    <div class="input-group">
                        <select type="text" id="txtAlmacenOrigen" class="form-control">
                            <option value="0">-TODOS-</option>
                            <c:forEach items="${almacenes}" var="almacen">
                                <option value="${almacen.idAlmacen}">${almacen.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>            
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblPedido" class="table table-bordered table-striped table-condensed">
            <thead>
                <tr>
                    <th>Fecha de Registro</th>
                    <th>Almacén Origen</th>
                    <th>Tipo Documento</th>
                    <th>Nro. Documento</th>
                    <th>Tipo Concepto</th>
                    <th>Acciones</th>
                </tr>
            </thead>
        </table>
    </div>
</div>