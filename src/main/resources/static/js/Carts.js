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
        $("#Remove").parent().show(1000, function () {
        })
        changeStatus()
    })
}

function changeStatus() {
    $("#Remove").parent().after("<td hidden='hidden'><a id='cancel' class='Button'>cancel</a></td>")
    $("#cancel").parent().show(1500, function () {
    })
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
    })
}

var xmlHttpRequest;
var id;
function creatXMLHttpRequest() {
    if(window.XMLHttpRequest)
    {
        xmlHttpRequest=new XMLHttpRequest();
    }
    else if(window.ActiveXObject)
    {
        xmlHttpRequest=new ActiveXObject("Msxml2.XMLHTTP");
    }
    else
    {
        xmlHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function processResponse() {
    if(xmlHttpRequest.readyState==4)
    {
        if(xmlHttpRequest.status==200)
        {

            var text=xmlHttpRequest.responseText;
            var result=text.split(",");
            var el2=document.getElementById(id+"total");
            el2.innerHTML=result[1];
            var el3=document.getElementById("subtotal");
            el3.innerHTML="Sub Total:"+result[2]+"<input type=\"submit\" value=\"Update Cart\"/>";
        }
    }
}
function sendRequest(url) {
    creatXMLHttpRequest();
    xmlHttpRequest.open("GET",url,true);
    xmlHttpRequest.onreadystatechange=processResponse;
    xmlHttpRequest.send(null);
}
function update(ID) {
    id=ID;
    var quantity=document.getElementById(ID).value;
    sendRequest("totalPrice?itemId="+ID+"&&quantity="+quantity);
}
