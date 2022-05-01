<div class="row">
    <div class="col-sm-3 col-md-3">
        <button class="btn btn-warning" id="agregarVendedores">Agregar Vendedores</button>
    </div>
</div>
<div class="row">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    <div class="modal fade" id="vendedores-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Vendedores por Turno</h3>
                </div>
                <div class="modal-body">
                    
                    <div>
                        <select id="vendedoresSelect" class="form-control" style="margin-bottom:20px;">
                            <option value="0">--Seleccione un Turno--</option>
                        </select>
                    </div>
                    <div id="agregarVendedoresbtn-frame" class="hidden" style="margin-bottom:20px;">
                        <button id="agregarVendedoresbtn" class="btn btn-primary" >Agregar</button>
                    </div>
                    <table id="tblVendedoresTurnoExt" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Apellido Paterno</th>
                            <th>Apellido Materno</th>
                            <th>Acciones</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
