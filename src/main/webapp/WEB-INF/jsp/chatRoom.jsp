<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>聊天室</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body class="container">
    <input id="userId" type="hidden" value="${userId}">
    <button onclick="close()">关闭连接</button>
    <div id="message" style="height: 200px;background-color:gray;">

    </div>

    <hr>
    <input id="text" type="text" class="form-control" placeholder="在此输入消息"/>
    <button class="btn btn-default" onclick="send()">发送</button>

</body>
<script type="text/javascript">

    var websocket = null;

    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/spring_websocket_war_exploded/websocket");
    }
    else {
        alert("对不起！你的浏览器不支持webSocket")
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        // setMessageInnerHTML("加入连接");
    };

    function close() {
        websocket.close();
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        // setMessageInnerHTML("断开连接");
    };

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        var p =$("<p></p>").text(innerHTML);
        $("#message").append(p);
    };



    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    };



    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，
    // 防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        var is = confirm("确定关闭窗口？");
        if (is){
            websocket.close();
        }
    };


    //发送消息
    function send() {
        var message = $("#text").val() ;
        websocket.send(message);
        $("#text").val("") ;
    }
</script>
</html>