var xmlHttpRequestUpdateCart;
var xmlHttpRequestRemove;

function createXMLHttpRequestUpdateCart() {
    if (window.XMLHttpRequest) {//非IE浏览器

        xmlHttpRequestUpdateCart = new XMLHttpRequest();
    } else if (window.ActiveXObject) {//IE6以上版本的IE

        xmlHttpRequestUpdateCart = new ActiveXObject("Msxm12.XMLHTTP");
    } else {//IE6及以下版本的IE
        xmlHttpRequestUpdateCart = new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function createXMLHttpRequestRemove() {
    if (window.XMLHttpRequest) {//非IE浏览器

        xmlHttpRequestRemove = new XMLHttpRequest();
    } else if (window.ActiveXObject) {//IE6以上版本的IE

        xmlHttpRequestRemove = new ActiveXObject("Msxm12.XMLHTTP");
    } else {//IE6及以下版本的IE
        xmlHttpRequestRemove = new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function updateCart(itemId, quantity) {

    if (isNaN(quantity)) {

        document.getElementById("quantityMsg").innerText = "请输入数字！"
    } else {
        document.getElementById("quantityMsg").innerText = "";
        if (quantity < 1) {
            sendRequestRemove("/remove?workingItemId=" + itemId);
        } else {
            sendRequestUpdateCart("/updateCart?itemId=" + itemId + "&&quantity=" + quantity);
        }
    }

}

// 清除该项
function sendRequestRemove(url) {
    createXMLHttpRequestRemove();
    xmlHttpRequestRemove.open("GET", url, true);
    xmlHttpRequestRemove.onreadystatechange = processResponseRemove;
    xmlHttpRequestRemove.send(null);
}

// 发送请求
function sendRequestUpdateCart(url) {

    createXMLHttpRequestUpdateCart();
    xmlHttpRequestUpdateCart.open("GET", url, true);
    xmlHttpRequestUpdateCart.onreadystatechange = processResponseUpdateCart;
    xmlHttpRequestUpdateCart.send(null);
}

function processResponseRemove() {

    if (xmlHttpRequestRemove.readyState == 4) {
        if (xmlHttpRequestRemove.status == 200) {
            var response = xmlHttpRequestRemove.responseXML.getElementsByTagName("Msg")[0].firstChild.data;

            var array = response.split("?");
            var div1 = document.getElementById(array[0]);
            div1.remove();

            var div2 = document.getElementById("total");
            div2.innerText = "$" + array[1];
        }
    }
}

// 取得回复
function processResponseUpdateCart() {

    if (xmlHttpRequestUpdateCart.readyState == 4) {
        if (xmlHttpRequestUpdateCart.status == 200) {
            // window.alert("进来了");

            var response = xmlHttpRequestUpdateCart.responseXML.getElementsByTagName("Msg")[0].firstChild.data;

            var array = response.split("?");

            var div1 = document.getElementById(array[0] + "Total");
            div1.innerText = "$" + array[1];

            var div2 = document.getElementById("total");
            div2.innerText = "$" + array[2];
        }
    }
}

var flag = false

$(document).ready(function () {
    prepared()
    suspension()
})

function suspension() {
    $("a#product").click(function () {
        var str = "table." + $(this).text()
        $(str).slideDown(300)
        $("div#backGround").show()
        $(str).click(function () {
            $(this).slideUp(300)
            $("div#backGround").hide()
        })
    })
}


function prepared() {
    $("#quantity").on("blur", function () {
        $("form").submit()
    })
    flag = true
    $("#Remove").parent().hide()
    removeChange()
}


function removeChange() {
    var timeout;
    var $r = $("#Remove").parents("tr").find("#productId")

    $r.css("cursor", "hand")
    $r.mousedown(function () {
        timeout = setTimeout(function () {
        }, 2000)
    })

    $r.mouseup(function () {
        clearTimeout(timeout)
        if (flag) {
            $("#Remove").parent().show(1000, function () {
            })
            changeStatus()
        }
    })
}

function changeStatus() {

    $("#Remove").parent().after("<td hidden='hidden'><a id='cancel' class='Button'>cancel</a></td>")
    $("#cancel").parent().show(1500, function () {
    })
    flag = false
    $("#cancel").click(function () {
        var $r = $("#cancel").parent()
        $r.animate({
            opacity: 0.0,
            paddingLeft: '-=80'
        }, 500, function () {
            $(this).remove()
            $("#Remove").parent().hide(1000, function () {
            })
        })
        flag = true
    })
}