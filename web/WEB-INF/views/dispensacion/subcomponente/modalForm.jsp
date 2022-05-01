<form id="mdl-form" method="" class="form_validation_reg form-horizontal" autocomplete="off" action="">
        <div class="form-group">
            <label for="descripcion" class="col-lg-2 ">Descripci&oacute;n<span class="f_req">*</span></label>
            <div class="col-lg-10">
                <input type="text" id="descripcion" name="descripcion"  class="form-control" data-req="" maxlength="70" data-autoclose="true"/>
            </div>
        </div>

        <div class="form-group">
            <label for="coor" class="col-lg-2 ">Coordinador<span class="f_req">*</span></label>
            <div class="col-lg-10 col-md-10">
                <input id="coor" name="coor" class="form-control" readonly="" data-req="" style="width: 95%; display: inline"/>
                <div id="seleccionaCoor" class="splashy-box_add" 
                     style="height: 16px;
                     margin-top: 2px;
                     cursor: pointer;
                     "></div>
            </div>
        </div>
        <div class="form-group">
            <label for="diag" class="col-lg-2 ">Diag&oacute;stico CIE<span class="f_req">*</span></label>
            <div class="col-lg-10 col-md-10">
                <input id="diag" name="diag" class="form-control" readonly="" style="width: 95%; display: inline" value="SIN DIAGNOSTICO"/>
                <div id="seleccionaDiag" class="splashy-box_add" 
                     style="height: 16px;
                     margin-top: 2px;
                     cursor: pointer;
                     "></div>
            </div>
        </div>

        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
        <input type="hidden" id="coordinador" class="coordinador" name="coordinador" value="" />
        <input type="hidden" id="diagnostico" class="diagnostico" name="diagnostico" value="" />
        <input type="hidden" id="componente" class="componente" name="componente" value="" />
</form>