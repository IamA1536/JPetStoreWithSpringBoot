document.getElementById("btn").onclick = function (obj) {

    var timenow = new Date().getTime();
    //每次请求需要一个不同的参数，否则可能会返回同样的验证码
    //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
    obj.src = "securityCode.action?d=" + timenow;
    
};