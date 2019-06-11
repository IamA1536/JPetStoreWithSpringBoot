$(document).ready(function () {
    $("a#product").click(function () {
        var str = "table." + $(this).text()
        $(str).slideDown(300);
        $("div#backGround").show();
        $(str).click(function () {
            $(this).slideUp(300);
            $("div#backGround").hide();
        })
    });
});
