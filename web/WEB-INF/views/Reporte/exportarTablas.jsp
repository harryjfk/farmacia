<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-5 col-md-5">
                    <label>Formato</label>
                    <label class="radio-inline">
                        <input type="radio" name="formato" value="Excel"> Excel
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="formato" value="Texto"> Texto
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="formato" value="DBaseIV"> DBase IV
                    </label> 
                </div>
            </div>
        </div>
        <div class="form-actions">
            <button class="btn btn-default">Exportar</button>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th style="width: 20%">Opción</th>
                    <th style="width: 75%">Descripción</th>
                    <th style="width: 5%"><input type="checkbox" id="checkAll"/></th>                    
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Usuarios</td>
                    <td>Mantenimiento de Usuarios</td>
                    <td><input type="checkbox" data-export=""/></td>
                </tr>
                <tr>
                    <td>Perfiles</td>
                    <td>Mantenimiento de Perfiles</td>
                    <td><input type="checkbox" data-export=""/></td>
                </tr>
                <tr>
                    <td>Parámetros</td>
                    <td>Mantenimiento de Parámetros</td>
                    <td><input type="checkbox" data-export=""/></td>
                </tr>
            </tbody>
        </table>
    </div>    
</div>

<script>
    $('#checkAll').click(function (e){
        if($(this).prop('checked')){
            $('[data-export]').prop('checked', true);
        }else{
            $('[data-export]').prop('checked', false);
        }
    });
</script>
