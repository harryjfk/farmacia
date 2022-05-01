<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="mdl-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="nombre" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">nombre</label>
                    <div class="col-md-9 col-sm-9 col-lg-9 col-xs-9">
                        <input id="nombre" name="nombre" class="form-control"  />
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="unidad" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Unidad de Medida</label>
                    <div class="col-md-9 col-sm-9 col-lg-9 col-xs-9">
                        <select id="unidad" name="unidad" class="form-control">
                            <c:forEach items="${umLista}" var="um">
                                <option value="${um.idUnidadMedida}">${um.nombreUnidadMedida}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="porcentaje" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Porcentaje</label>
                    <div class="col-md-9 col-sm-9 col-lg-9 col-xs-9">
                        <input id="porcentaje" name="porcentaje" class="form-control" value="100" />
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="precio" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Precio</label>
                    <div class="col-md-9 col-sm-9 col-lg-9 col-xs-9">
                        <input id="precio" name="precio" class="form-control"  />
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="form-group">
                    <label for="cantidad" class="col-md-2 col-sm-2 col-lg-2 col-xs-2 control-label">Cantidad</label>
                    <div class="col-md-9 col-sm-9 col-lg-9 col-xs-9">
                        <input id="cantidad" name="cantidad" class="form-control"  />
                    </div>
                </div>
            </div>
            <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />

        </form>
    </div>
</div>