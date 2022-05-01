<form method="POST" action="${action}" enctype="multipart/form-data">
    <div class="row">
        <div class="col-sm-4 col-md-4">
            <label>Documento</label>
            <div class="fileinput fileinput-new input-group" data-provides="fileinput">
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
        </div>
    </div>
<div class="form-actions">
    <button id="btnUpload" class="btn btn-default" type="submit">Guardar</button>
<!--    <button id="btnCancelar" class="btn btn-default">Cancelar</button>-->
</div>

</form>