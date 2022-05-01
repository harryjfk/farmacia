
<%@include file="../includes.jsp" %>

<body>
    <div class="row">
        <div class="col-sm-10 col-md-10">
            <table id="tblCliente" class="table table-bordered table-striped dTableR">
                <thead>
                    <tr>
                    <th>Nombre o Razón Social</th>
                    <th>Código</th>
                    <th>DNI</th>
                    <th>Acciones</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>

<button id="crearCliente" value="Crear">Crear</button>

<div class="modal fade crud-modal" id="modalCliente">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off" action="insertar">
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Código <span class="f_req">*</span></label>
                            <input type="text" id="codigo" name="codigoCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Fecha de Registro <span class="f_req">*</span></label>
                            <input type="text" id="fechaRegistro" name="fechaRegistroCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Cod.Personal<span class="f_req">*</span></label>
                            <input type="text" id="codPersonal" name="codPersonalCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Nombre o razón social<span class="f_req">*</span></label>
                            <input type="text" id="nombre" name="nombreCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Numero de documento social<span class="f_req">*</span></label>
                            <input type="text" id="noDocumento" name="noDocumentoCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Telefono<span class="f_req">*</span></label>
                            <input type="text" id="telefono" name="telefonoCliente" class="form-control" data-req="" maxlength="70"/>
                        </div>

                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idPerfil" name="idPerfil" />
                        </div>
                    </div>
                    <div id="divMessage">

                    </div>
                    <!--</div>-->
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        listarPerfiles();

        var modal = $('.crud-modal');

        $('body').delegate('.row-data-edit', 'click', function(event) {
            event.preventDefault();
            fillForm(modal.find('form'), $(this).attr('data-id'));
            modal.modal('show');
        });

        $('body').delegate('#crearCliente', 'click', function(event) {
            event.preventDefault();
            modal.modal('show');
        });

        $('body').delegate('.row-data-change', 'click', function(event) {
            event.preventDefault();
            alert('change');
        });

        $('body').delegate('.row-data-remove', 'click', function(event) {
            event.preventDefault();
            alert('remove');
        });

        $('#btnGuardar').click(function(event) {
            event.preventDefault();
            var frm = modal.find('form');
            var dataSend = frm.serialize();
            $.ajax({
                url: frm.attr("action"),
                data: dataSend,
                type: "POST",
                success: function(r) {
                    console.log(r);
                }
            });
        });

    });

    function fillForm(form, id) {
        $.getJSON('getCliente', {id: id}, function(r) {
            var input = null;
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    input = form.find('#' + key);
                    if (input) {
                        input.val(r[key]);
                    }
                }
            }
        });
    }

    function listarPerfiles() {
        var table = $('#tblCliente').dataTable(),
                cliente = null,
                edit = '',
                change = '',
                remove = '';

        $.getJSON('getClientes', function(data) {
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    cliente = data[key];
                    console.log(cliente);
                    edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + cliente.idCliente + '"><i class="splashy-pencil" title="Editar"></i></a>';
                    change = '<a  href="#" class="row-data-change separator-icon-td" data-id="' + cliente.idCliente + '"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
                    remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + cliente.idCliente + '"><i class="splashy-remove" title="Eliminar"></a>'
                    table.fnAddData([
                        data[key].nombre,
                        data[key].codigo,
                        '',
                        edit + change + remove
                    ]);
                }
            }
        });
    }

</script>
</body>
