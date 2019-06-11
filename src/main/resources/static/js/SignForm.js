document.getElementById("UserName").addEventListener("blur", function () {
    checkUserName()
}, false)

document.getElementById("Pw").addEventListener("blur", function () {
    checkPw()
}, false)

document.getElementById("RepeatPw").addEventListener("blur", function () {
    checkRePw()
}, false)

document.getElementById("Email").addEventListener("blur", function () {
    checkEmail()
}, false)

var un = false
var pw = false
var repw = false
var email = false
var uce = false

var xmlHttpRequest;

function creatXMLHttpRequest() {
    if (window.XMLHttpRequest) {
        xmlHttpRequest = new XMLHttpRequest()
    } else if (window.ActiveXObject) {
        xmlHttpRequest = new ActiveXObject("Maxm12.XMLHTTP")
    } else {
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP")
    }
}


function checkUserName() {


// <div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">--%>
// //         <%--<p id="UserError">--%>
// //
// //         <%--<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;">--%>
// //
// //         <%--</span>--%>
// //         <%--<strong>Error:</strong> Please input the username!--%>
// //     <%--</p>--%>
// //     <%--</div>

    var str = document.getElementById("UserName").value

    // var htmlstr = "<div id= \"UserError\" class=\"ui-state-error ui-corner-all\" style=\"padding: 0 .7em;\">\n" +
    //     "<span class=\"ui-icon ui-icon-alert\" style=\"float: left; margin-right: .3em;\"></span>\n" +
    //     "<strong>Error:</strong> Please input the username!\n" +
    //     "</div>"

    if (str == "") {
        // $("#UserName").after(htmlstr);
        document.getElementById("UserError").innerText = "Please input the username!"
        un = false
    } else {
        usernameIsExist(str)
        // $("#UserError").remove()
        document.getElementById("UserError").innerText = ""
        un = true
    }
    check()
}

function checkPw() {
    var str = document.getElementById("Pw").value

    if (str == "") {
        if (document.getElementById("PwError").innerText == "")
            document.getElementById("PwError").innerText = "Please input the password!"
        pw = false
    } else {
        document.getElementById("PwError").innerText = ""
        pw = true
    }
    check()
}

function checkRePw() {
    var str1 = document.getElementById("RepeatPw").value
    var str2 = document.getElementById("Pw").value

    if (str2 == "") {
        if (document.getElementById("PwError").innerText == "")
            document.getElementById("PwError").innerText = "Please input the password!"
        pw = false
    } else if (str1 != str2) {
        if (document.getElementById("RepeatPwError").innerText == "")
            document.getElementById("RepeatPwError").innerText = "Entered passwords differ!"
        repw = false
    } else {
        document.getElementById("RepeatPwError").innerText = ""
        repw = true
    }
    check()
}

function checkEmail() {
    var str = document.getElementById("Email").value
    var i1 = str.indexOf("@")
    var i2 = str.indexOf(".")
    if (str == "") {
        if (document.getElementById("EmailError").innerText == "")
            document.getElementById("EmailError").innerText = "Please input the Email!"
        email = false
    } else {
        if (i1 == -1 || i2 == -1 || i2 < i1) {
            if (document.getElementById("EmailError").innerText == "")
                document.getElementById("EmailError").innerText = "Please input the correct Email!"
            email = false
        } else {
            document.getElementById("EmailError").innerText = ""
            email = true
        }
    }
    check()
}

function usernameIsExist(username) {
    sendRequest("usenameisexist?username=" + username)
}

function sendRequest(url) {
    creatXMLHttpRequest()
    xmlHttpRequest.open("GET", url, true)
    xmlHttpRequest.onreadystatechange = processResponse
    xmlHttpRequest.send(null)
}

function processResponse() {
    if (xmlHttpRequest.readyState == 4) {
        if (xmlHttpRequest.status == 200) {
            // var info = xmlHttpRequest.responseXML.getElementsByTagName("msg")[0].childNodes[0].nodeValue
            var info = xmlHttpRequest.responseText
            if (info == "Exist") {
                document.getElementById("UserError").innerText = "This username has been exist!"
                uce = false
            } else {
                document.getElementById("UserError").innerText = ""
                uce = true
            }
        }
    }
}

function check() {
    if (un && pw && repw && email && uce) {
        document.getElementById("SubNew").style.visibility = "visible"
    } else {
        document.getElementById("SubNew").style.visibility = "hidden"
    }
}



$(document).ready(function () {
    $(this).click(function () {
        $("#warning").toggle("highlight")
        $("#warning").toggle("highlight")
    })
    $("#warning").toggle("highlight")
    $("#warning").toggle("highlight")
})