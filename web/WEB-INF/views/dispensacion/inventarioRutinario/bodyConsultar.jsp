<%@include file="../includes.jsp" %>
<div class="row">
    <div class="col-sm-8 col-md-8">
        <table id="tblData-table" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <c:forEach var="columnTitle" items="${tableHeaders}">
                        <th>${columnTitle}</th>
                    </c:forEach>
                </tr>
            </thead>
        </table>
    </div>
    <div id="otrosAlmacenes" class="col-sm-4 col-md-4">
        <span style="display: none; font-weight: bold">Stock En Otras Farmacias</span>
        <div></div>
    </div>
</div>