function showItem(val) {
    deleteActive();
    var idItem = val.id + '-tab';

    $("#" + idItem).removeClass('hidden');
    $(val).addClass('active');
}

function deleteActive() {
    $('[role=tabpanel]').each(function (index) {
        $(this).addClass('hidden');
    });

    $('[role=presentation]').each(function (index) {
        $(this).removeClass('active');
    });
}

function change() {
    $('#password').removeAttr('readonly');
    $('#password').val('');
    $('#firstname').removeAttr('readonly');
    $('#lastname').removeAttr('readonly');
    $('#phoneNumber').removeAttr('readonly');
    $('#description').removeAttr('readonly');
    $('#price').removeAttr('readonly');
    $('#city').removeAttr('readonly');

    $('#confirm-password').removeClass('hidden');
    $('#type').removeClass('hidden');
    $('#update').removeClass('hidden');
    $('#cancel').removeClass('hidden');
    $('#change').addClass('hidden');
    $('#type_text').addClass('hidden');
}

function readURL(input) {
    console.log('call input');
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blah1')
                .attr('src', e.target.result)
                .width(150)
                .height(200);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function clickWindow() {
    if ($('#change').hasClass('hidden')) {
        $("#window").click();
    }
}

function cancel() {
    location.reload();
}

