function profileClick() {
    var $home_tab = $("#home-tab");
    var $profile_tab = $("#profile-tab");
    var $home = $("#home");
    var $profile = $("#profile");

    $home_tab.removeClass("active");
    $profile_tab.addClass("active");
    $profile.addClass("active");
    $home.removeClass("active");
}

function homeClick() {
    var $home_tab = $("#home-tab");
    var $profile_tab = $("#profile-tab");
    var $home = $("#home");
    var $profile = $("#profile");

    $home_tab.addClass("active");
    $profile_tab.removeClass("active");
    $profile.removeClass("active");
    $home.addClass("active");
}

function readURL(input) {
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
    $("#window").click();
}

function clientSubmit() {
    $("#window").clone().appendTo("#clientForm")
    $('#clientForm').submit();
}

function doctorSubmit() {
    $("#window").clone().appendTo("#doctorForm")
    $('#doctorForm').submit();
}