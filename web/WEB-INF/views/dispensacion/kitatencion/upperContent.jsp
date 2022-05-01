<%@include file="../../includeTagLib.jsp" %>
<div class="row" id="mgn-intervsanitarias">
    <div class="col-sm-12 col-md-12">
        <form id="preventafrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="componente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Componente</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="componente" name="componente" class="form-control">
                            <option value="0">-- Seleccionar Componente --</option>
                            <c:forEach items="${componentes}" var="componente">
                                <option value="${componente.id}">${componente.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="subcomponente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Sub Componente</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="subcomponente" name="subcomponente" class="form-control">
                            <option value="0">-- Seleccionar Subomponente --</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="proceso" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Proceso</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="proceso" name="proceso" class="form-control">
                            <option value="0">-- Seleccionar Proceso --</option>
                            <c:forEach items="${procesos}" var="proceso">
                                <option value="${proceso.id}">${proceso.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="selAnio" class="form-control" data-req=""/>
                    </div>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                        <select id="selMes" class="form-control" data-req=""></select> 
                    </div>
                </div>
                <div class="form-group">
                    <label for="proceso" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">&nbsp;</label>
                    <button id="btnBuscar" class="btn btn-primary pull-left">Buscar</button>
                </div>
            </div>
        </form>
    </div>
</div>
