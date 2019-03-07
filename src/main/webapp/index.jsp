<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>login</title>
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body style="text-align: center">
<div class="container">
    <div class="row">
        <input id="userName" type="text" class="btn btn-default" placeholder="输入用户名">
        <button type="button" class="btn btn-primary" onclick="login()">进入聊天室</button>
    </div>
</div>

<script>
    function login() {
        $.ajax({
            url: "/spring_websocket_war_exploded/login",
            data: {userName: $("#userName").val()},
            type: "POST",
            dataType: "json",
            success: function(data) {
                if(data.result != "100"){
                    alert(data.message);
                }else{
                    location.href="/spring_websocket_war_exploded/toChatRoom?userId="+data.data;
                }
            }
        });
    }
</script>
</body>
</html>