<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="en" class="login_page">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Hospital Vitarte - Inicio de Sesión</title>

        <!-- Bootstrap framework -->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
        <!-- theme color-->
        <link rel="stylesheet" href="css/blue.css" />
        <!-- tooltip -->    
        <link rel="stylesheet" href="lib/qtip2/jquery.qtip.min.css" />
        <!-- main styles -->
        <link rel="stylesheet" href="css/style.css" />

        <!-- favicon -->
        <link rel="shortcut icon" href="favicon.ico" />

        <link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>

        <!--[if lt IE 9]>
            <script src="js/ie/html5.js"></script>
                        <script src="js/ie/respond.min.js"></script>
        <![endif]-->

    </head>
    <body>

        <div class="login_box">
            <form:form id="login_form" method="post" action="index" modelAttribute="usuario" autocomplete="off">            
                <div class="top_b" style="background: white;height:auto;text-align: center;">
                    <img src="img/logo_hospvitarte.jpg" alt="Logo Hospital Vitarte"/>
                </div>
                <br />
                <div style="text-align: center;">
                    <h3 class="heading" style="border: 0px solid black; margin-bottom: 0px;padding-bottom: 0px;">
                        SERVICIO DE APOYO AL TRATAMIENTO<br/>AREA DE FARMACIA<br/>Software de Farmacia
                    </h3>
                </div>
                <div class="cnt_b">
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon input-sm"><i class="glyphicon glyphicon-user"></i></span>
                                <form:input path="nombreUsuario" type="text" id="nombreUsuario"  class="form-control input-sm" placeholder=""/>
                        </div>                                
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <span class="input-group-addon input-sm"><i class="glyphicon glyphicon-lock"></i></span>
                                <form:input path="clave" type="password" id="clave" class="form-control input-sm" placeholder="" />                         
                        </div>
                    </div>                    
                </div>
                <div class="btm_b clearfix">
                    <button class="btn btn-default btn-sm pull-right" type="submit">Iniciar Sesión</button>
                    <span class="link_reg">No olvide cerrar sesión al retirarse.</span>
                </div>  
            </form:form>

            <form action="rememberAccount" method="post" id="formRememberAccount" style="display:none;">
                <div class="top_b">¿Olvidó su cuenta?</div>    
                <div class="alert alert-info alert-login">                    
                    Por favor digite su correo electrónico. Recibirá un correo con sus datos de acceso.
                </div>
                <div class="cnt_b">
                    <div class="formRow clearfix">
                        <div class="input-group">
                            <span class="input-group-addon input-sm">@</span>
                            <input type="text" id="correo" name="correo" placeholder="Correo Electrónico" class="form-control input-sm" />
                        </div>
                    </div>
                </div>
                <div class="btm_b tac">
                    <button id="btnRecordarCuenta" class="btn btn-default" data-loading-text="Enviando..." type="submit">Enviar Solicitud de Cuenta</button>
                </div>  
            </form>

            <div class="links_b links_btm clearfix">
                <span class="linkform"><a href="#formRememberAccount">¿Olvidó su cuenta?, click aquí.</a></span>
                <span class="linkform" style="display:none"><a href="#login_form">Regresar al Inicio de Sesión</a></span>
            </div>

        </div>

        <script src="js/jquery.min.js"></script>
        <script src="js/jquery.actual.min.js"></script>
        <script src="lib/validation/jquery.validate.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function() {


                $('#nombreUsuario').focus();
                //* boxes animation
                form_wrapper = $('.login_box');
                function boxHeight() {
                    form_wrapper.animate({marginTop: (-(form_wrapper.height() / 2) - 24)}, 400);
                }                

                form_wrapper.css({marginTop: (-(form_wrapper.height() / 2) - 24)});
                $('.linkform a,.link_reg a').on('click', function(e) {
                    var target = $(this).attr('href'),
                            target_height = $(target).actual('height');
                    $(form_wrapper).css({
                        'height': form_wrapper.height()
                    });
                    $(form_wrapper.find('form:visible')).fadeOut(400, function() {
                        form_wrapper.stop().animate({
                            height: target_height,
                            marginTop: (-(target_height / 2) - 24)
                        }, 500, function() {
                            $(target).fadeIn(400);
                            $('.links_btm .linkform').toggle();
                            $(form_wrapper).css({
                                'height': ''
                            });
                        });
                    });
                    e.preventDefault();
                });

                //* validation
                $('#login_form').validate({
                    onkeyup: false,
                    errorClass: 'error',
                    validClass: 'valid',
                    rules: {
                        nombreUsuario: {required: true, minlength: 3},
                        clave: {required: true, minlength: 3}
                    },
                    highlight: function(element) {
                        $(element).closest('div').addClass("f_error");
                        setTimeout(function() {
                            boxHeight()
                        }, 200)
                    },
                    unhighlight: function(element) {
                        $(element).closest('div').removeClass("f_error");
                        setTimeout(function() {
                            boxHeight()
                        }, 200)
                    },
                    errorPlacement: function(error, element) {
                       $(element).parent("div").parent("div").closest('div').append(error.css("color","#b94a48"));                       
                    }
                });
            });

            $('#btnRecordarCuenta').click(function(e) {
                e.preventDefault();
                var frm = $('#formRememberAccount');
                var dataSend = frm.serialize();

                var btn = $(this);
                btn.button('loading');

                $.ajax({
                    url: frm.attr("action"),
                    data: dataSend,
                    type: "POST",
                    success: function(dataResponse) {
                        
                        btn.button('reset');
                        
                        if(dataResponse === 1){
                            alert('Se ha enviado sus datos de acceso.');
                        }else{
                            alert('El correo ingresado no está registrado.');
                        }
                    }
                });

            });
        </script>
    </body>
</html>


