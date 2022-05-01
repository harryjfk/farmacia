<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row" id="mgn-preventa">
    <div class="col-sm-12 col-md-12">
        <form id="preventafrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="turno" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Turno</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <select id="turno" name="turno" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="vendedor" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Cajero</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="vendedor" name="vendedor" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaVen" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <div class="form-group">
                    <label for="formaPago" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Forma de Pago</label>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                        <select id="formaPago" name="formaPago" class="form-control"></select>
                    </div>
                </div>

            </div>
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="cliente" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Cliente</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="cliente" name="cliente" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaCli" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <c:if test="${showService == 1}">
                    <div class="form-group">
                        <label for="servicio" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Servicio</label>
                        <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                            <input id="servicio" name="servicio" class="form-control"/>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="startDate" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Del</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="startDate" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label for="endDate" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Al</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="endDate" class="form-control">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                    <button id="consultar" class="btn btn-primary">Consultar</button>
                </div>
            </div>
        </form>
    </div>
</div>