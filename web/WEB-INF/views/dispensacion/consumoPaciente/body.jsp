<%@include file="../includes.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12 consumos">
        <div style="display: none" class="table-container">
            <span class="monthName" style="text-transform: uppercase; margin-bottom: 10px"></span>
            <table class="tblData-table table table-bordered table-striped dTableR">
                <thead>
                    <tr>
                        <c:forEach var="columnTitle" items="${tableHeaders}">
                            <th>${columnTitle}</th>
                            </c:forEach>
                    </tr>
                </thead>
            </table>
        </div>
    </div>             
</div>
<script>

    $(document).ready(function () {
        window.gp = {};
        window.gp.data = {};
        window.gp.reportCondition = null;

        $('body').delegate('#btnPDF', 'click', function (event) {
            event.preventDefault();
            if (window.gp.reportCondition) {
                if (!window.gp.reportCondition())
                    return;
            }
            _setPathName("/reportePdf");
        });
        $('body').delegate('#btnExcel', 'click', function (event) {
            event.preventDefault();
            if (window.gp.reportCondition) {
                if (!window.gp.reportCondition())
                    return;
            }
            _setPathName("/reporteExcel");
        });
        $('body').delegate('#btnConsultaPDF', 'click', function (event) {
            event.preventDefault();
            if (window.gp.reportCondition) {
                if (!window.gp.reportCondition())
                    return;
            }
            _setPathName("/reporteConsultaPdf");
        });
        $('body').delegate('#btnConsultaExcel', 'click', function (event) {
            event.preventDefault();
            if (window.gp.reportCondition) {
                if (!window.gp.reportCondition())
                    return;
            }
            _setPathName("/reporteConsultaExcel");
        });
        function _setPathName(path) {
            var myLocation = window.location.href.split('/'),
                    result = "";
            myLocation.pop();

            for (var key in myLocation) {
                if (myLocation.hasOwnProperty(key)) {
                    result += myLocation[key] + "/";
                }
            }
            if (window.gp.reportData) {
                var data = window.gp.reportData;
                path += '?';
                for (var i in data) {
                    if (data.hasOwnProperty(i)) {
                        path += i + '=' + data[i] + '&';
                    }
                }
            }
            result += path;
            window.location.href = result;
        }
    });</script>
