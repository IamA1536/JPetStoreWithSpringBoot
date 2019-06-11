$(document).ready(function () {
    $("a#product.Button").click(function () {
        // <input type="hidden" th:value="${item.itemId}" id="itemId"/>
        var str = "table." + $(this).text()
        console.log(str)
        $(str).slideDown(300);
        $("div#backGround").show();
        $(str).click(function () {
            $(this).slideUp(300);
            $("div#backGround").hide();
        })
    });
});
