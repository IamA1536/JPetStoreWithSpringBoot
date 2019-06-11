$(document).ready(function () {
    $("a#product.Button").click(function () {
        $("table#choiceWindow").slideDown(300);
        $("div#backGround").show();
        $("table#choiceWindow").click(function () {
            $(this).slideUp(300);
            $("div#backGround").hide();
        })
    });
});
