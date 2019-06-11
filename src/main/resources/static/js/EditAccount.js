document.getElementById("Pw").addEventListener("blur", function () {
    checkPw()
}, false)

document.getElementById("RePw").addEventListener("blur", function () {
    checkRePw()
}, false)

function checkPw() {
    var str = document.getElementById("Pw").value
    if (str == "") {
        document.getElementById("Sub").style.visibility = "visible"
    } else {
        document.getElementById("Sub").style.visibility = "hidden"
    }
}

function checkRePw() {
    var str1 = document.getElementById("Pw").value
    var str2 = document.getElementById("RePw").value
    if (str1 != str2) {
        document.getElementById("Error").innerText = "Enter differ password!"
        document.getElementById("Sub").style.visibility = "hidden"
    } else {
        document.getElementById("Error").innerText = ""
        document.getElementById("Sub").style.visibility = "visible"
    }
}