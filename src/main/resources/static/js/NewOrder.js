var flag = true
$(document).ready(function () {
    prepareShip()
})

function prepareShip() {
    $("a#shippingAddress").click(function () {
        if (flag) {
            $("tr.ship").hide(1000, function () {
            })
            changeShip()
        }
    })
}

function changeShip() {

    $("table.shiptable").show(1500, function () {
    })

    $("table.shiptable").after("<div hidden='hidden'><a id='cancel' class='Button'>cancel</a></div>")
    $("a#cancel").parent().show(1000, function () {
    })
    flag = true
    $("a#cancel").click(function () {
        var $r = $("#cancel").parent()
        $r.animate({
            opacity: 0.0,
            paddingLeft: '+=80'
        }, 500, function () {
            $(this).remove()
            $("table.shiptable").hide(1000, function () {
            })
            $("tr.ship").show(1000, function () {
            })
        })
        flag = false
    })
}