<script>
    $(document).ready(function () {
        $('body').addClass('no-change');
        $('#mdl-frm').validate({
            rules: {
                descripcion: {required: true}
            },
            messages: {
                descripcion: {required: 'Este campo es obligatorio'}
            }
        });
    });
</script>