<%@include file="../includeTagLib.jsp" %>

<div id="cabeceraSolicitud">
    <form id="form" name="form" action="<c:url value="${path}/modificar" />" target="ifrm_modificar" method="POST" enctype="multipart/form-data" autocomplete="off">
        <div class="row">
            <div class="modal-cabecera">
                <div class="col-sm-12 col-md-12">
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-4 col-md-4">
                                <label>M&eacute;dico <span class="f_req">*</span></label>
                                <select id="idMedico" name="idMedico" class="form-control" data-req="" onchange="obtenerMedico(this.value, event, this);">
                                    <option value="-1">-SELECCIONE-</option>
                                    <c:forEach var="medico" items="${medicos}">
                                        <option value="${fn:trim(medico.idPersonal)}" <c:if test="${fn:trim(solicitud.idMedico) == fn:trim(medico.idPersonal)}"> selected ="selected" </c:if>>${medico.nombre} ${medico.apellidoPaterno} ${medico.apellidoMaterno}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4 col-md-4">
                                <label>N&ordm; Colegiatura</label>
                                <input type="text" id="colegiatura" name="colegiatura" class="form-control" readonly="true" value="${personal.colegiatura}" />
                            </div>
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-4 col-md-4">
                                <label>Profesi&oacute;n / Especialidad</label>
                                <input type="text" id="profesion" name="profesion" class="form-control" readonly="true" value="${personal.cargo}" />
                            </div>
                            <div class="col-sm-4 col-md-4">
                                <label>Servicio / Departamento:</label>
                                <input type="text" id="unidad" name="unidad" class="form-control" readonly="true" value="${personal.unidad}" />
                            </div>
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-4 col-md-4" >
                                <label>Establecimiento <span class="f_req">*</span></label>
                                <input type="text" id="establecimiento" name="establecimiento" class="form-control uppercase" data-req="" maxlength="50" value="${solicitud.establecimiento}" />
                            </div>
                            <div class="col-sm-2 col-md-2">
                                <label>Fecha <span class="f_req">*</span></label>
                                <div class="input-group">
                                    <input type="text" id="fecha" name="fecha" class="form-control uppercase" data-field-date="" value='<fmt:formatDate pattern="<%=formatoFecha%>" value="${solicitud.fecha}" />' data-req=""/>
                                    <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecha');"></i></span>
                                </div>
                            </div>
                            <div class="col-sm-4 col-md-4">
                                <label>Instituci&oacute;n / DISA <span class="f_req">*</span></label>
                                <input type="text" id="institucion" name="institucion" class="form-control uppercase" data-req="" maxlength="50" value="${solicitud.institucion}" />
                            </div>
                        </div>
                    </div>
                    <div class="formSep">            
                        <div class="row">
                            <div class="col-sm-4 col-md-4">
                                <label>Motivos de la solicitud</label>
                                <textarea id="motivo" name="motivo" rows="4" class="form-control uppercase">${solicitud.motivo}</textarea>
                            </div>
                            <div class="col-sm-4 col-md-4">
                                <label>Justificaci&oacute;n de la solicitud</label>
                                <textarea id="justificacion" name="justificacion" rows="4" class="form-control uppercase">${solicitud.justificacion}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-4 col-md-4">
                                <label>Documento</label>
                                <c:if test="${fn:length(solicitud.extension) > 0}">
                                    <a href="#" class="btn btn-default" data-dismiss="fileinput" onclick="DeleteFile(this, event)">Remover Archivo</a>
                                </c:if>
                                <div id="controlFileUpload" class="fileinput fileinput-new input-group" data-provides="fileinput" <c:if test="${fn:length(solicitud.extension) > 0}"> style="display:none;" </c:if>>
                                        <div class="form-control" data-trigger="fileinput">
                                            <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                            <span class="fileinput-filename"></span>
                                        </div>
                                        <span class="input-group-addon btn btn-default btn-file">
                                            <span class="fileinput-new">Seleccionar</span>
                                            <span class="fileinput-exists">Cambiar</span>
                                            <input type="file" name="fileDocumento">
                                        </span>
                                        <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Quitar</a>
                                    </div>
                                    <span class="help-block">NOTA: Al remover el archivo debe guardar cambios.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Estado <span class="f_req">*</span></label>
                                    <div class="separator-form-checkbox"></div>                    
                                    <label class="checkbox-inline">
                                        <input type="checkbox" id="activo" name="activo" value="1" data-unchecked="0" <c:if test="${solicitud.activo == 1}"> checked="" </c:if> /> Activo
                                    </label>                    
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-10 col-md-10">
                                    <button id="btnAgregar" class="btn btn-primary">Agregar</button>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-12 col-md-12">
                                    <table id="tblDetalleMed" class="table table-bordered table-striped dTableR">
                                        <thead>
                                            <tr>
                                                <th>Id Detalle</th>
                                                <th>Id Producto</th>
                                                <th>Denominaci&oacute;n</th>
                                                <th>Concentraci&oacute;n</th>
                                                <th>Forma Farmac.</th>
                                                <th>V&iacute;a adminis.</th>
                                                <th>Dosis diaria</th>
                                                <th>Costo diario</th>
                                                <th>Duraci&oacute;n trat.</th>
                                                <th>Costo trat.</th>
                                                <th>Aprobado</th>
                                                <th>Acción</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div> 
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-4 col-md-4">
                                    <label>Existen medicamentos alternativos en el PNME <input type="checkbox" id="existeMedicamento" name="existeMedicamento" value="1" <c:if test="${solicitud.existeMedicamento == 1}"> checked="" </c:if> /></label>
                                </div> 
                            </div>
                        </div>
                        <div id="alternativo" <c:if test="${solicitud.existeMedicamento==0}"> style="display:none;" </c:if>>
                            <div class="formSep">
                                <div class="row">
                                    <div class="col-sm-10 col-md-10">
                                        <button id="btnAgregarAlt" class="btn btn-primary">Agregar</button>
                                    </div>
                                </div>
                            </div>
                            <div class="formSep">
                                <div class="row">
                                    <div class="col-sm-12 col-md-12">
                                        <table id="tblDetalleMedAlt" class="table table-bordered table-striped dTableR">
                                            <thead>
                                                <tr>
                                                    <th>Id Detalle</th>
                                                    <th>Id Producto</th>
                                                    <th>Denominaci&oacute;n</th>
                                                    <th>Concentraci&oacute;n</th>
                                                    <th>Forma Farmac.</th>
                                                    <th>V&iacute;a adminis.</th>
                                                    <th>Dosis diaria</th>
                                                    <th>Costo diario</th>
                                                    <th>Duraci&oacute;n trat.</th>
                                                    <th>Costo trat.</th>
                                                    <th>Aprobado</th>
                                                    <th>Acción</th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div> 
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <input type="hidden" id="idSolicitud" name="idSolicitud" value="${solicitud.idSolicitud}"/>
                        <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                        <button id="btnCancelar" class="btn btn-default">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="jsonForm" name="jsonForm" />
        <input type="hidden" id="DeleteFile" name="DeleteFile" value="false"/>
        <iframe id='ifrm_modificar' name='ifrm_modificar' src="" style="display:none;"></iframe>
    </form>
</div>

<div class="modal fade" id="modalDetalleMed">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">
                    <div class="row">
                        <div class="col-sm-12 col-md-12">
                            <label>Denominaci&oacute;n</label>
                            <select id="idProducto" name="idProducto" class="form-control" data-req="" onchange="obtenerProducto(this.value, event, this);" style="width: 550px" >
                                <option value="-1">-SELECCIONE-</option>
                                <c:forEach var="medicamento" items="${productos}">
                                    <option value="${medicamento.idProducto}">${medicamento.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Concentraci&oacute;n</label>
                            <input type="text" id="concentracion" name="concentracion" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Forma farmac&eacute;utica</label>
                            <input type="text" id="forma" name="forma" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>V&iacute;a de administraci&oacute;n</label>
                            <input type="text" id="via" name="via" class="form-control uppercase" maxlength="10" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Dosis diaria</label>
                            <input type="text" id="dosis" name="dosis" class="form-control uppercase" maxlength="5" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label>Costo diario</label>
                            <input type="text" id="costo" name="costo" class="form-control uppercase" maxlength="5" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Duraci&oacute;n de tratamiento</label>
                            <input type="text" id="duracion" name="duracion" class="form-control uppercase" maxlength="5" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Costo del tratamiento</label>
                            <input type="text" id="costotrat" name="costotrat" class="form-control uppercase" maxlength="5" />
                            <input type="hidden" id="idSolicitudDetalle" name="idSolicitudDetalle" />
                            <input type="hidden" id="idSol" name="idSol"/>
                        </div>
                    </div>
                    <div id="divMessage"></div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnGuardarMed" class="btn btn-default" type="submit">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modalDetalleMedAlt">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">
                     <div class="row">
                        <div class="col-sm-12 col-md-12">
                            <label>Denominaci&oacute;n</label>
                            <select id="idProducto" name="idProducto" class="form-control" data-req="" onchange="obtenerProductoAlt(this.value, event, this);" style="width: 550px" >
                                <option value="-1">-SELECCIONE-</option>
                                <c:forEach var="medicamento" items="${productos}">
                                    <option value="${medicamento.idProducto}">${medicamento.descripcion}</option>
                                </c:forEach>
                            </select>
                        </div>
                     </div>
                    
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Concentraci&oacute;n</label>
                            <input type="text" id="concentracion" name="concentracion" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Forma farmac&eacute;utica</label>
                            <input type="text" id="forma" name="forma" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>V&iacute;a de administraci&oacute;n</label>
                            <input type="text" id="via" name="via" class="form-control uppercase" maxlength="10" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Dosis diaria</label>
                            <input type="text" id="dosis" name="dosis" class="form-control uppercase" maxlength="5" />
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label>Costo diario</label>
                            <input type="text" id="costo" name="costo" class="form-control uppercase" maxlength="5" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Duraci&oacute;n de tratamiento</label>
                            <input type="text" id="duracion" name="duracion" class="form-control uppercase" maxlength="5" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Costo del tratamiento</label>
                            <input type="text" id="costotrat" name="costotrat" class="form-control uppercase" maxlength="5" />
                            <input type="hidden" id="idSolicitudDetalle" name="idSolicitudDetalle" />
                            <input type="hidden" id="idSol" name="idSol"/>
                        </div>
                    </div>
                    <div id="divMessage"></div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnGuardarMedAlt" class="btn btn-default" type="submit">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalAprobarMed">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" class="form_validation_reg" target="ifrm_modificarApr" enctype="multipart/form-data" autocomplete="off">
                <%--<form method="post" class="form_validation_reg" autocomplete="off">--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-12 col-md-12">
                            <label>Denominaci&oacute;n</label>
                            <input type="text" id="descripcionProd" name="descripcionProd" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Concentraci&oacute;n</label>
                            <input type="text" id="concentracion" name="concentracion" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Forma farmac&eacute;utica</label>
                            <input type="text" id="descripcionFarm" name="descripcionFarm" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12 col-md-12">
                            <label>Motivo <span class="f_req">*</span></label>
                            <input type="text" id="motivo" name="motivo" class="form-control uppercase" data-req=""/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Condición de autorización <span class="f_req">*</span></label>
                            <input type="text" id="condicion" name="condicion" class="form-control uppercase" data-req=""/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Cantidad autorizada <span class="f_req">*</span></label>
                            <input type="text" id="cantidad" name="cantidad" class="form-control uppercase" data-req=""/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <select id="aprobado" name="aprobado" class="form-control" data-req="">
                                <option value="1">Aprobado</option>
                                <option value="0">No Aprobado</option>
                            </select>
                            <input type="hidden" id="idSolicitudDetalle" name="idSolicitudDetalle" />
                            <input type="hidden" id="idSolicitud" name="idSolicitud"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Documento</label>
                            <%--<div id="removerApr"><a href="#" class="btn btn-default" data-dismiss="fileinput" onclick="DeleteFileApr(this, event)">Remover Archivo</a></div>--%>
                            <div id="controlFileUploadApr" class="fileinput fileinput-new input-group" data-provides="fileinput">
                                <div class="form-control" data-trigger="fileinput">
                                    <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                    <span class="fileinput-filename"></span>
                                </div>
                                <span class="input-group-addon btn btn-default btn-file">
                                    <span id="aprSeleccionar" class="fileinput-new">Seleccionar</span>
                                    <span id="aprCambiar" class="fileinput-exists">Cambiar</span>
                                    <input id="fileDocumentoAprobar" type="file" name="fileDocumentoAprobar" />
                                </span>
                                <a id="aprQuitar" href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Quitar</a>
                            </div>
                            <%--<span class="help-block">NOTA: Al remover el archivo debe guardar cambios.</span>--%>
                        </div>
                    </div>
                    <div id="divMessage"></div>
                    <div id="divMessageNew"></div>
                </div>
                <div class="modal-footer">
                    <button id="btnAprobarMed" class="btn btn-default" type="submit">Guardar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
                <input type="hidden" id="jsonFormApr" name="jsonFormApr" />
                <input type="hidden" id="DeleteFileApr" name="DeleteFileApr" value="false"/>
                <iframe id='ifrm_modificarApr' name='ifrm_modificarApr' src="" style="display:none;"></iframe>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="modalAprobarMedAlt">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" class="form_validation_reg" target="ifrm_modificarAprAlt" enctype="multipart/form-data" autocomplete="off">
                <%--<form method="post" class="form_validation_reg" autocomplete="off">--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label>Denominaci&oacute;n</label>
                            <input type="text" id="descripcionProd" name="descripcionProd" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Concentraci&oacute;n</label>
                            <input type="text" id="concentracion" name="concentracion" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Forma farmac&eacute;utica</label>
                            <input type="text" id="descripcionFarm" name="descripcionFarm" class="form-control uppercase" readonly="true" maxlength="20" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Motivo</label>
                            <input type="text" id="motivo" name="motivo" class="form-control uppercase" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Condición de autorización</label>
                            <input type="text" id="condicion" name="condicion" class="form-control uppercase" />
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Cantidad autorizada</label>
                            <input type="text" id="cantidad" name="cantidad" class="form-control uppercase" />
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <select id="aprobado" name="aprobado" class="form-control" data-req="">
                                <option value="1">Aprobado</option>
                                <option value="0">No Aprobado</option>
                            </select>
                            <input type="hidden" id="idSolicitudDetalle" name="idSolicitudDetalle" />
                            <input type="hidden" id="idSolicitud" name="idSolicitud"/>
                        </div>
                        <div class="col-sm-4 col-md-4">
                            <label>Documento</label>
                            <%--<div id="removerAprAlt"><a href="#" class="btn btn-default" data-dismiss="fileinput" onclick="DeleteFileAprAlt(this, event)">Remover Archivo</a></div>--%>
                            <div id="controlFileUploadAprAtl" class="fileinput fileinput-new input-group" data-provides="fileinput">
                                <div class="form-control" data-trigger="fileinput">
                                    <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                    <span class="fileinput-filename"></span>
                                </div>
                                <span class="input-group-addon btn btn-default btn-file">
                                    <span class="fileinput-new">Seleccionar</span>
                                    <span class="fileinput-exists">Cambiar</span>
                                    <input type="file" name="fileDocumentoAprobar" id="fileDocumentoAprobarAlt">
                                </span>
                                <a id="aprQuitarAlt" href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Quitar</a>
                            </div>                            
                        </div>
                    </div>
                    <div id="divMessage"></div>
                    <div id="divMessageNewAlt"></div>
                </div>
                <div class="modal-footer">
                    <button id="btnAprobarMedAlt" class="btn btn-default" type="submit">Guardar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
                <input type="hidden" id="jsonFormApr" name="jsonFormApr" />
                <input type="hidden" id="DeleteFileAprAlt" name="DeleteFileAprAlt" value="false"/>
                <iframe id='ifrm_modificarAprAlt' name='ifrm_modificarAprAlt' src="" style="display:none;"></iframe>
            </form>
        </div>
    </div>
</div>

<script>
    
    var controlFileUploadApr;
    
    $('#dosis').validCampo('0123456789');
    $('#costo').validCampo('0123456789');
    $('#duracion').validCampo('0123456789');
    $('#costotrat').validCampo('0123456789');
    $('#cantidad').validCampo('0123456789');

    $("#existeMedicamento").click(function() {
        if ($("#existeMedicamento").is(':checked')) {
            $('#alternativo').show();
        } else {
            $('#alternativo').hide();
        }
    });

    var tblDetalleMed = document.getElementById('tblDetalleMed');
    var tblDetalleMedAlt = document.getElementById('tblDetalleMedAlt');

    $(document).ready(function() {
        //$('select[name="idProducto"]').chosen();
        $('#cantidad').bind("cut copy paste",function(e) {
          e.preventDefault();
      });
        listarDetalleMed();
        listarDetalleMedAlt();
    });

    $('#ifrm_modificar').load(function() {
        var dataResponse = $(this).contents().find("body").html();
        if (dataResponse.length) {
            dataResponse = jQuery.parseJSON(dataResponse);
            functionResponse(dataResponse);
        }
    });

    function DeleteFile(btn, e) {
        e.preventDefault();

        $('#DeleteFile').val('true');
        $(btn).remove();
        $('#controlFileUpload').css({"display": ""});
    }
    function DeleteFileApr(btn, e) {
        e.preventDefault();

        $('#DeleteFileApr').val('true');
        $(btn).remove();
        $('#controlFileUploadApr').css({"display": ""});
    }
    function DeleteFileAprAtl(btn, e) {
        e.preventDefault();

        $('#DeleteFileAprAtl').val('true');
        $(btn).remove();
        $('#controlFileUploadAprAtl').css({"display": ""});
    }

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="${path}" />';
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('.modal-cabecera [data-req]');

        var fecha = Date.parseExact($('#fecha').val(), dateFormatJS);
        var fechaTime = null;

        if ($('#fecha').val().length > 0) {
            if (fecha === null) {
                dataResponse.mensajesRepuesta.push('Fecha inválida.');
                dataResponse.estado = false;
            } else {
                fechaTime = fecha.getTime();
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var documento = {
            idSolicitud: $('#idSolicitud').val(),
            idMedico: $('#idMedico').val(),
            establecimiento: $('#establecimiento').val(),
            institucion: $('#institucion').val(),
            justificacion: $('#justificacion').val(),
            motivo: $('#motivo').val(),
            existeMedicamento: $('#existeMedicamento').checkboxVal(),
            fecha: fechaTime,
            activo: $('#activo').checkboxVal()
        };

        $('#jsonForm').val(JSON.stringify(documento));

        $('#form').submit();
    });

    $('#btnAgregar').click(function(e) {
        e.preventDefault();
        $('.fileinput-filename').html('');
        
        var modalDetalleMed = $('#modalDetalleMed');
        modalDetalleMed.find('form').attr('action', 'registrarDetMed');
        modalDetalleMed.find('.modal-header .modal-title').html('Registrar Medicamento');
        cleanform('#modalDetalleMed');
        $('#modalDetalleMed form input[type="hidden"][name="idSol"]').val($('#cabeceraSolicitud form input[type="hidden"][name="idSolicitud"]').val());

        modalDetalleMed.modal('show');
        $('#modalDetalleMed select[name="idProducto"]').chosen();
    });

    $('#btnAgregarAlt').click(function(e) {
        e.preventDefault();

        var modalDetalleMedAlt = $('#modalDetalleMedAlt');
        modalDetalleMedAlt.find('form').attr('action', 'registrarDetMedAlt');
        modalDetalleMedAlt.find('.modal-header .modal-title').html('Registrar Medicamento Alternativo');
        cleanform('#modalDetalleMedAlt');
        $('#modalDetalleMedAlt form input[type="hidden"][name="idSol"]').val($('#cabeceraSolicitud form input[type="hidden"][name="idSolicitud"]').val());

        modalDetalleMedAlt.modal('show');
        //$('#modalDetalleMedAlt select[name="idProducto"]').chosen();
    });

    function obtenerProducto(id, e, element) {
        e.preventDefault();
        $('#modalDetalleMed form input[type="text"][name="concentracion"]').val("Cargando...");
        $('#modalDetalleMed form input[type="text"][name="forma"]').val("Cargando...");
        $.ajax({
            url: '<c:url value="${path}/productoJSON" />?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#modalDetalleMed form input[type="text"][name="concentracion"]').val(jsonData.concentracion);
                $('#modalDetalleMed form input[type="text"][name="forma"]').val(jsonData.formaFarmaceutica);
            }
        });
    }

    function obtenerProductoAlt(id, e, element) {
        e.preventDefault();
        $('#modalDetalleMedAlt form input[type="text"][name="concentracion"]').val("Cargando...");
        $('#modalDetalleMedAlt form input[type="text"][name="forma"]').val("Cargando...");
        $.ajax({
            url: '<c:url value="${path}/productoJSON" />?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#modalDetalleMedAlt form input[type="text"][name="concentracion"]').val(jsonData.concentracion);
                $('#modalDetalleMedAlt form input[type="text"][name="forma"]').val(jsonData.formaFarmaceutica);
            }
        });
    }

    $('#modalDetalleMed form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardarMed').click();
        }
    });

    $('#modalDetalleMedAlt form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardarMedAlt').click();
        }
    });

    function reloadDetalleMed() {
        var dataTable = $(tblDetalleMed).dataTable();
        dataTable.fnReloadAjax();
    }

    function reloadDetalleMedAlt() {
        var dataTable = $(tblDetalleMedAlt).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarDetalleMed() {
        $(tblDetalleMed).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idSolicitud', "value": $('#idSolicitud').val()});
                aoData.push({"name": 'tipoMedicamento', "value": "1"});
            },
            "sAjaxSource": "solicitudesDetJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitudDetalle", sWidth: "5%"},
                {mData: "idProducto", sWidth: "5%"},
                {mData: "nomProducto", sWidth: "10%"},
                {mData: "concentracion", sWidth: "8%"},
                {mData: "nomFarmaceutica", sWidth: "8%"},
                {mData: "via", sWidth: "8%"},
                {mData: "dosis", sWidth: "6%"},
                {mData: "costo", sWidth: "6%"},
                {mData: "duracion", sWidth: "6%"},
                {mData: "costotrat", sWidth: "6%"},
                {mData: "aprobadoTexto", sWidth: "8%"},
                {mData: "idSolicitudDetalle", sWidth: "12%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var downloadFile = '';

                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerMedicamento(' + data + ', event, this)"><i class="splashy-pencil" title="Editar Medicamento"></i></a>';
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="aprobarSolicitud(' + data + ', event, this)" class="separator-icon-td"><i class="splashy-pencil" title="Editar Medicamento"></i></a>';
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarSolicitudDetalle(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar Medicamento"></i></a>';
                        if (row.extension !== null) {
                            downloadFile = '<a href="descargarDet?idSolicitud=' + $('#idSolicitud').val() + '&idSolicitudDet=' + data + '&tipoMedicamento=1" class="separator-icon-td" title="Descargar"><i class="splashy-download"></i></a>';
                        }

                        return stateHTML + deleteHTML + downloadFile;
                    }
                }
            ]
        });
    }

    function listarDetalleMedAlt() {
        $(tblDetalleMedAlt).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idSolicitud', "value": $('#idSolicitud').val()});
                aoData.push({"name": 'tipoMedicamento', "value": "2"});
            },
            "sAjaxSource": "solicitudesDetJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitudDetalle", sWidth: "5%"},
                {mData: "idProducto", sWidth: "5%"},
                {mData: "nomProducto", sWidth: "10%"},
                {mData: "concentracion", sWidth: "8%"},
                {mData: "nomFarmaceutica", sWidth: "8%"},
                {mData: "via", sWidth: "8%"},
                {mData: "dosis", sWidth: "6%"},
                {mData: "costo", sWidth: "6%"},
                {mData: "duracion", sWidth: "6%"},
                {mData: "costotrat", sWidth: "6%"},
                {mData: "aprobadoTexto", sWidth: "8%"},
                {mData: "idSolicitudDetalle", sWidth: "12%", "bSortable": false, "mRender": function(data, type, row) {

                        var stateHTML = '';
                        var deleteHTML = '';
                        var downloadFile = '';

                        stateHTML += '<a href="#" class="separator-icon-td" onclick="aprobarSolicitudAlt(' + data + ', event, this)" class="separator-icon-td"><i class="splashy-pencil" title="Aprobar"></i></a>';
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarSolicitudDetalleAlt(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
                        if (row.extension !== null) {
                            downloadFile = '<a href="descargarDet?idSolicitud=' + $('#idSolicitud').val() + '&idSolicitudDet=' + data + '&tipoMedicamento=2" class="separator-icon-td" title="Descargar"><i class="splashy-download"></i></a>';
                        }

                        return stateHTML + deleteHTML + downloadFile;
                    }
                }
            ]
        });
    }

    $('#btnGuardarMed').click(function(e) {
        var frm = $('#modalDetalleMed form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('#modalDetalleMed .modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalDetalleMed #divMessage', '<c:url value="/"/>');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalDetalleMed #divMessage', '<c:url value="/"/>')) {
                    reloadDetalleMed();
                }
            }
        });

        e.preventDefault();
        /**/
        $('.fileinput-filename').html('');
        
        var modalDetalleMed = $('#modalDetalleMed');
        modalDetalleMed.find('form').attr('action', 'registrarDetMed');
        modalDetalleMed.find('.modal-header .modal-title').html('Registrar Medicamento');
        cleanform('#modalDetalleMed');
        $('#modalDetalleMed form input[type="hidden"][name="idSol"]').val($('#cabeceraSolicitud form input[type="hidden"][name="idSolicitud"]').val());
        /**/
        //$('#modalDetalleMed').modal('hide');
    });

    $('#btnGuardarMedAlt').click(function(e) {
        var frm = $('#modalDetalleMedAlt form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('#modalDetalleMedAlt .modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalDetalleMedAlt #divMessage', '<c:url value="/"/>');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalDetalleMedAlt #divMessage', '<c:url value="/"/>')) {
                    reloadDetalleMedAlt();
                }
            }
        });

        e.preventDefault();
        $('#modalDetalleMedAlt').modal('hide');
    });

    $('#btnAprobarMed').click(function(e) {
        /**/
        if( document.getElementById("fileDocumentoAprobar").files.length === 0 ){
            $('#divMessageNew').html('<div class="alert alert-danger" style="margin-top: 15px;margin-bottom: 0px;">  *&nbsp;&nbsp;Seleccione una imagen. </div>');
            setTimeout(function() {
                $('#divMessageNew').fadeOut();
            }, 1500);
            e.preventDefault();
            return;
        }
        var frm = $('#modalAprobarMed form');
        var dataResponse = validateForm('#modalAprobarMed [data-req]');
        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalAprobarMed #divMessage', '<c:url value="/"/>');
            return;
        }
        
        if (isNaN($('#cantidad').val())) {
            
        }

        var solicitud = {
            idSolicitudDetalle: $('#modalAprobarMed form input[type="hidden"][name="idSolicitudDetalle"]').val(),
            idSolicitud: $('#modalAprobarMed form input[type="hidden"][name="idSolicitud"]').val(),
            motivo: $('#modalAprobarMed form input[type="text"][name="motivo"]').val(),
            condicion: $('#modalAprobarMed form input[type="text"][name="condicion"]').val(),
            cantidad: $('#modalAprobarMed form input[type="text"][name="cantidad"]').val(),
            aprobado: $('#modalAprobarMed form select[name="aprobado"]').val()
        };

        $('#modalAprobarMed form input[type="hidden"][name="jsonFormApr"]').val(JSON.stringify(solicitud));

        var formData = new FormData();
        $.each(frm.find("input[type='file']"), function(i, tag) {
            $.each($(tag)[0].files, function(i, file) {
                formData.append(tag.name, file);
            });
        });
        var params = frm.serializeArray();
        $.each(params, function(i, val) {
            if (val.name == 'jsonFormApr') {
                formData.append(val.name, JSON.stringify(solicitud));
            }
        });

        $.ajax({
            url: frm.attr("action"),
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalAprobarMed #divMessage', '<c:url value="/"/>')) {
                    reloadDetalleMed();
                }
            }
        });

        e.preventDefault();
        $('#modalAprobarMed').modal('hide');
    });

    $('#btnAprobarMedAlt').click(function(e) {
        if( document.getElementById("fileDocumentoAprobarAlt").files.length === 0 ){   
            $('#divMessageNewAlt').html('<div class="alert alert-danger" style="margin-top: 15px;margin-bottom: 0px;">  *&nbsp;&nbsp;Seleccione una imagen. </div>');
            setTimeout(function() {
                $('#divMessageNewAlt').fadeOut();
            }, 1500);
            e.preventDefault();
            return;
        }
        var frm = $('#modalAprobarMedAlt form');
        var dataResponse = validateForm('#modalAprobarMedAlt .modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalAprobarMedAlt #divMessage', '<c:url value="/"/>');
            return;
        }

        var solicitud = {
            idSolicitudDetalle: $('#modalAprobarMedAlt form input[type="hidden"][name="idSolicitudDetalle"]').val(),
            idSolicitud: $('#modalAprobarMedAlt form input[type="hidden"][name="idSolicitud"]').val(),
            motivo: $('#modalAprobarMedAlt form input[type="text"][name="motivo"]').val(),
            condicion: $('#modalAprobarMedAlt form input[type="text"][name="condicion"]').val(),
            cantidad: $('#modalAprobarMedAlt form input[type="text"][name="cantidad"]').val(),
            aprobado: $('#modalAprobarMedAlt form select[name="aprobado"]').val()
        };

        $('#modalAprobarMedAlt form input[type="hidden"][name="jsonFormApr"]').val(JSON.stringify(solicitud));

        var formData = new FormData();
        $.each(frm.find("input[type='file']"), function(i, tag) {
            $.each($(tag)[0].files, function(i, file) {
                formData.append(tag.name, file);
            });
        });
        var params = frm.serializeArray();
        $.each(params, function(i, val) {
            if (val.name == 'jsonFormApr') {
                formData.append(val.name, JSON.stringify(solicitud));
            }
        });

        $.ajax({
            url: frm.attr("action"),
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: "POST",
            success: function(dataResponse) {
                if (jsonToDivError(dataResponse, '#modalAprobarMedAlt #divMessage', '<c:url value="/"/>')) {
                    reloadDetalleMedAlt();
                }
            },
            error: function(xhr, ajaxOptions, thrownError) {
                alert(xhr.statusxhr.status);
                alert(thrownError);
            }
        });

        e.preventDefault();
        $('#modalAprobarMedAlt').modal('hide');
    });

    function aprobarSolicitud(id, e, element) {
        e.preventDefault();

        var idProducto = $(element).parent().parent().find('td:eq(1)').text().trim();

        var modalAprobarMed = $('#modalAprobarMed');
        modalAprobarMed.find('form').attr('action', 'aprobarDetMed');
        modalAprobarMed.find('.modal-header .modal-title').html('Aprobar Medicamento');
        cleanform('#modalAprobarMed');
        $('#aprobado').val(0);

        $('#modalAprobarMed form input[type="hidden"][name="idSolicitudDetalle"]').val(id);
        $('#modalAprobarMed form input[type="hidden"][name="idSolicitud"]').val($('#cabeceraSolicitud form input[type="hidden"][name="idSolicitud"]').val());

        e.preventDefault();
        $('#modalAprobarMed form input[type="text"][name="descripcionProd"]').val("Cargando...");
        $('#modalAprobarMed form input[type="text"][name="concentracion"]').val("Cargando...");
        $('#modalAprobarMed form input[type="text"][name="descripcionFarm"]').val("Cargando...");
        $.ajax({
            url: 'productoSolicitudJSON?id=' + idProducto + '&idSolicitud=' + $('#idSolicitud').val(),
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#modalAprobarMed form input[type="text"][name="descripcionProd"]').val(jsonData.descripcion);
                $('#modalAprobarMed form input[type="text"][name="concentracion"]').val(jsonData.concentracion);
                $('#modalAprobarMed form input[type="text"][name="descripcionFarm"]').val(jsonData.formaFarmaceutica);
                
                $('#modalAprobarMed form select[name="aprobado"]').val(jsonData.aprobado);
                $('#modalAprobarMed form input[type="text"][name="motivo"]').val(jsonData.motivoAprobado);
                $('#modalAprobarMed form input[type="text"][name="condicion"]').val(jsonData.condicionAprobado);
                $('#modalAprobarMed form input[type="text"][name="cantidad"]').val(jsonData.cantidadAprobada);
            }
        });

        modalAprobarMed.modal('show');
        $('#aprQuitar').click();
    }

    function aprobarSolicitudAlt(id, e, element) {
        e.preventDefault();

        var idProducto = $(element).parent().parent().find('td:eq(1)').text().trim();

        var modalAprobarMedAlt = $('#modalAprobarMedAlt');
        modalAprobarMedAlt.find('form').attr('action', 'aprobarDetMed');
        modalAprobarMedAlt.find('.modal-header .modal-title').html('Aprobar Medicamento Alternativo');
        cleanform('#modalAprobarMedAlt');

        $('#modalAprobarMedAlt form input[type="hidden"][name="idSolicitudDetalle"]').val(id);
        $('#modalAprobarMedAlt form input[type="hidden"][name="idSolicitud"]').val($('#cabeceraSolicitud form input[type="hidden"][name="idSolicitud"]').val());

        e.preventDefault();
        $('#modalAprobarMedAlt form input[type="text"][name="descripcionProd"]').val("Cargando...");
        $('#modalAprobarMedAlt form input[type="text"][name="concentracion"]').val("Cargando...");
        $('#modalAprobarMedAlt form input[type="text"][name="descripcionFarm"]').val("Cargando...");
        $.ajax({
            //url: 'productoJSON?id=' + idProducto,
            url: 'productoSolicitudJSON?id=' + idProducto + '&idSolicitud=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
               
                $('#modalAprobarMedAlt form input[type="text"][name="descripcionProd"]').val(jsonData.descripcion);
                $('#modalAprobarMedAlt form input[type="text"][name="concentracion"]').val(jsonData.concentracion);
                $('#modalAprobarMedAlt form input[type="text"][name="descripcionFarm"]').val(jsonData.formaFarmaceutica);
                
                $('#modalAprobarMedAlt form select[name="aprobado"]').val(jsonData.aprobado);
                $('#modalAprobarMedAlt form input[type="text"][name="motivo"]').val(jsonData.motivoAprobado);
                $('#modalAprobarMedAlt form input[type="text"][name="condicion"]').val(jsonData.condicionAprobado);
                $('#modalAprobarMedAlt form input[type="text"][name="cantidad"]').val(jsonData.cantidadAprobada);
            }
        });

        modalAprobarMedAlt.modal('show');
        $('#aprQuitarAlt').click();
    }

    function eliminarSolicitudDetalle(id, e, element) {
        e.preventDefault();

        var solicitudTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar la solicitud ' + solicitudTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminarDet/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDetalleMed();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarSolicitudDetalleAlt(id, e, element) {
        e.preventDefault();

        var solicitudTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar la solicitud ' + solicitudTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminarDet/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDetalleMedAlt();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
    
    function GetElementInsideContainer(containerID, childID) {
        var elm = document.getElementById(childID);
        var parent = elm ? elm.parentNode : {};
        return (parent.id && parent.id === containerID) ? elm : {};
    }
</script>