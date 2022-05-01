<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Archivo a Desempaquetar <span class="f_req">*</span></label>
                    <div data-provides="fileupload" class="fileupload fileupload-new">
                        <div class="input-group">
                            <div class="form-control">
                                <i class="icon-file fileupload-exists"></i> <span class="fileupload-preview"></span>
                            </div>
                            <div class="input-group-btn">
                                <a data-dismiss="fileupload" class="btn btn-default fileupload-exists" href="#">Quitar</a>
                                <span class="btn btn-default btn-file">
                                    <span class="fileupload-new">Seleccionar</span>
                                    <span class="fileupload-exists">Cambiar</span>
                                    <input type="file">
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <button class="btn btn-default">Actualizar</button>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <div class="alert alert-info">
            <h4 class="alert-heading">Puntos a Actualizar</h4>
            <ol>
                <li>Establecimientos</li>
                <li>Medicamentos</li>
                <li>Redes</li>
                <li>Micros Redes</li>
                <li>Compra Nacional Cabecera</li>
                <li>Compra Nacional Detalle</li>
            </ol>
        </div>        
    </div>    
</div>