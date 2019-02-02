$(function() {
    $("#search").on("click", function() {find()});

    function find() {
        var regex = $("#regex").val().toLowerCase();
        makeAllNotVisible();

        $(".thumbnail").each(function (index) {

            var name = $(this).find("span").text();

            if (name.toLowerCase().indexOf(regex) >= 0) {
                showAttr($(this).parents()[0]);
            }
        });
    }

    function makeAllNotVisible() {
        $(".thumbnail").each(function (index) {
            var parent = $(this).parents()[0];

            $(parent).hide();
        });
    }

    function showAttr(attr) {
        $(attr).show();
    }
});