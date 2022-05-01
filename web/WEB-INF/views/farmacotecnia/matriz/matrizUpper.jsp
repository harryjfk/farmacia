<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="upper-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">

                <div class="form-group">
                    <label for="descripcion" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Descripción</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="descripcion" name="descripcion" class="form-control" />
                    </div>
                </div>
                <!--                <div class="form-group">
                                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                                        <button id="guardar" class="btn btn-success">Guardar</button>
                                        <button id="buscar" class="btn btn-primary">Buscar</button>
                                    </div>
                                </div>-->
                <div class="form-group">
                    <label for="startDate" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Fecha Inicio</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="startDate" name="startDate" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="endDate" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Fecha Final</label>
                    <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                        <input id="endDate" name="endDate" class="form-control" />
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <button id="consultar" class="btn btn-primary" >Consultar</button>
            <button id="inicializar" class="btn btn-warning" >Inicializar</button>
        </div>
    </div>
</div>

<div id="insumos-mdl" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Agregar Insumos</h4>
            </div>
            <div class="modal-body">
                <div id="addmatcontainer">
                    <form role="form" class="form-horizontal">
                        <div class="form-group">
                            <label for="selectinsumo" class="col-md-1 col-sm-1 col-lg-1 col-xs-1 control-label">Insumo</label>
                            <div class="col-md-5 col-sm-5 col-lg-5 col-xs-5">
                                <select id="selectinsumo" class="form-control">
                                    <c:forEach items="${materias}" var="mat">
                                        <option value="${mat.id}">${mat.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3 col-sm-3 col-lg-3 col-xs-3">
                                <input id="icantidad" class="form-control" placeholder="Cantidad" />
                            </div>
                            <input type="hidden" id="matrizId" value="" />
                            <div class="col-md-1 col-sm-1 col-lg-1 col-xs-1">
                                <button id="agregarInsumo" class="btn btn-primary">Agregar</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div>
                    <table id="gp-tblInsumos" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Precio</th>
                                <th>Cantidad</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->