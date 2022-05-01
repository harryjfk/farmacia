<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row" id="mgn-preventa">
    <div class="col-sm-12 col-md-12">
        <form id="preventafrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="preventa" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Pre Venta</label>
                    <div class="col-md-2 col-sm-2 col-lg-2 col-xs-2">
                        <input id="periodo" name="periodo" class="form-control" disabled="true" />
                    </div>
                    <div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
                        <input id="preventa" name="preventa" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="vendedor" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Vendedor</label>
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
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="show-hist" class="splashy-application_windows" 
                             style="height: 16px;
                             margin-top: -16px;
                             cursor: pointer;
                             " title="Ver Historial de Ventas"></div>
                        <div></div></div>
                </div>
                <c:if test="${showService == 1}">
                    <div class="form-group">
                        <label for="servicio" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Servicio</label>
                        <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                            <input id="servicio" name="servicio" class="form-control" />
                        </div>
                        <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                            <div id="seleccionaServ" class="splashy-box_add" 
                                 style="height: 16px;
                                 margin-top: 10px;
                                 margin-left: -22px;
                                 cursor: pointer;
                                 "></div>
                            <div></div></div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="medico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Prescriptor</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="medico" name="medico" class="form-control" />
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaMed" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
                <div class="form-group">
                    <label for="diagnostico" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Diag&oacute;stico CIE</label>
                    <div class="col-md-7 col-sm-7 col-lg-7 col-xs-7">
                        <input id="diagnostico" name="diagnostico" class="form-control" value="SIN DIAGNOSTICO"/>
                    </div>
                    <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                        <div id="seleccionaDiag" class="splashy-box_add" 
                             style="height: 16px;
                             margin-top: 10px;
                             margin-left: -22px;
                             cursor: pointer;
                             "></div>
                        <div></div></div>
                </div>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="fechaRegistro" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha de Registro</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="fechaRegistro" name="fechaRegistro" class="form-control" disabled="true"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="turno" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Turno</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="turno" name="turno" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="formaPago" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Forma de Pago</label>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4">
                        <select id="formaPago" name="formaPago" class="form-control"></select>
                    </div>
                    <div class="col-md-4 col-sm-4 col-lg-4 col-xs-4 checkbox">
                        <label>
                            <input type="checkbox" value="" name="exonerados" id="exonerados" />
                            Exonerados
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="matriz" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Matriz</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="matriz" name="matriz" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tipoDeReceta" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Tipo de Receta</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="tipoDeReceta" name="tipoDeReceta" class="form-control">
                            <option value="0">Receta Interna</option>
                            <option value="1">Receta Externa</option>
                            <option value="2">Sin Receta</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="numeroDeReceta" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">N&uacute;mero De Receta</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="numeroDeReceta" name="numeroDeReceta" class="form-control" />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
