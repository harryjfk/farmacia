<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Año <span class="f_req">*</span></label>
                    <select class="form-control">
                        <option value="2014">2014</option>
                        <option value="2013">2013</option>
                        <option value="2012">2012</option>
                    </select> 
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Mes <span class="f_req">*</span></label>
                    <select class="form-control">
                        <option value="0">Todos</option>
                        <option value="1">Enero</option>
                        <option value="2">Febrero</option>
                        <option value="3">Marzo</option>
                        <option value="4">Abril</option>
                        <option value="5">Mayo</option>
                        <option value="6">Junio</option>
                        <option value="7">Julio</option>
                        <option value="8">Agosto</option>
                        <option value="9">Setiembre</option>
                        <option value="10">Octubre</option>
                        <option value="11">Noviembre</option>
                        <option value="12">Diciembre</option>
                    </select>
                </div>
                <div class="col-sm-5 col-md-5">
                    <label>Formato</label>
                    <label class="radio-inline">
                        <input type="radio" name="formato" value="ICIIDIIME"> ICI/IDI/IME
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="formato" value="Precio"> Listado de precios
                    </label>                    
                </div>
            </div>            
        </div>
        <div class="form-actions">
            <button class="btn btn-default">Descargar</button>
        </div>
    </div>
</div>