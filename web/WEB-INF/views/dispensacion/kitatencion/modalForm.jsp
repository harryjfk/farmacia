<%@include file="../../includeTagLib.jsp" %>
<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div class="form-group">
                <label>Nombre<span class="f_req">*</span></label>
                <input type="text" id="descripcion" name="descripcion"  class="form-control" data-req="" data-autoclose="true"/>
            </div>
            <div class="form-group">
                <label>Componente<span class="f_req">*</span></label>
                <select id="mcomponente" name="mcomponente" class="form-control">
                    <c:forEach items="${componentes}" var="componente">
                        <option value="${componente.id}">${componente.descripcion}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Sub Componente<span class="f_req">*</span></label>
                <select id="msubcomponente" name="msubcomponente" class="form-control">
                </select>
            </div>
            <div class="form-group">
                <label>Proceso<span class="f_req">*</span></label>
                <select id="mproceso" name="mproceso" class="form-control">
                    <option value="0">-- Seleccionar Proceso --</option>
                    <c:forEach items="${procesos}" var="proceso">
                        <option value="${proceso.id}">${proceso.descripcion}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo <span class="f_req">*</span></label>
                <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                    <input id="selMAnio" class="form-control" data-req=""/>
                </div>
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <select id="selMMes" class="form-control" data-req=""></select> 
                </div>
            </div>
        </div>
        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
    </div>
</form>

