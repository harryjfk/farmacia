<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@include file="../includes.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title><tiles:insertAttribute name="title" ignore="true" ></tiles:insertAttribute></title>
        <tiles:insertAttribute name="meta" ></tiles:insertAttribute>
        <tiles:insertAttribute name="head" ></tiles:insertAttribute>
        <tiles:insertAttribute name="headScript" ></tiles:insertAttribute>        
        </head>
        <body class="full_width">
            <div id="maincontainer" class="clearfix">

            <tiles:insertAttribute name="menu" ></tiles:insertAttribute>

                <div id="contentwrapper">
                    <div class="main_content">
                    <tiles:insertAttribute name="breadcrumb" ></tiles:insertAttribute>



                        <style>
                            #modalData-modal .modal-dialog
                            {
                                margin-top: 5%
                            }
                            a:hover{
                                background: rgba(12, 35, 48, 0)!important;
                            }
                            .popover.clockpicker-popover{
                                z-index: 9999 !important;
                            }
                            .pagination.pagination-sm li.active a {
                                background-color: #428bca !important;
                            }

                        </style>

                        <h3 class="heading">${viewTitle}</h3>


                    <tiles:insertAttribute name="actions" ></tiles:insertAttribute>
                    <tiles:insertAttribute name="upper-content" ></tiles:insertAttribute>
                    <tiles:insertAttribute name="body"></tiles:insertAttribute>
                       
                    <tiles:insertAttribute name="lower-content" ></tiles:insertAttribute>

                        <div class="modal fade" id="modalData-modal">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h3 class="modal-title"></h3>
                                    </div>
                                    <div class="modal-body">
                                    <tiles:insertAttribute name="modal-form" ></tiles:insertAttribute>
                                    </div>
                                    <div class="modal-footer">
                                        <button id="btnGuardar" class="btn btn-default">Guardar</button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                          

                    <tiles:insertAttribute name="custom-script" ></tiles:insertAttribute>
                    </div>
                </div>

            <tiles:insertAttribute name="sidebar" ></tiles:insertAttribute>            

        </div>
        <script>
            elementForm();
        </script>
    </body>
</html>