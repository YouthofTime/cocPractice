<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/13 0013
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="https://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="https://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
</head>
<script>
    $(function () {
        // 快速登录按钮
        $("button.QuickLogin").click(function () {
            $.ajax({
                url:"/user/quickLogin",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                success:function (judge) {
                    // 没有登陆过
                    if(judge.status==0){
                        alert(judge.meg);
                    }
                    // 登录过
                    if(judge.status==1){
                        alert(judge.meg);
                        $("#Pagego").append("<span></span>");  // 跳转到成功页面(使用隐藏超链接+点击超链接函数实现)
                        $("#Pagego span").click();    // 超链接点击函数需要添加子元素
                    }

                }
            })
        });
        // 登录
        $("button.loginButton").click(function () {
            var name = $("input.account").val();
            var password = $("input.password").val();

            // 为空判断
            if (name == "") {
                alert("登录名不能为空");
                return;
            }
            if (password == "") {
                alert("密码不能为空");
                return;
            }
            // json数据
            data={"name":name,"password":password};
            $.ajax({
                url:"/user/login",
                contentType:"application/json;charset=UTF-8",
                data:JSON.stringify(data),
                dataType:"json",
                type:"post",
                success:function (judge) { // 成功执行
                    // 登录失败
                    if(judge.status==0){
                        alert(judge.meg);
                    }
                    // 登录成功
                    if(judge.status==1){
                        alert(judge.meg);
                        $("#Pagego").append("<span></span>");  // 跳转到成功页面(使用隐藏超链接+点击超链接函数实现)
                        $("#Pagego span").click();    // 超链接点击函数需要添加子元素


                    }
                }
            });

        });

    });

</script>
<style>
    body{
        font-size:12px;
        font-family:Arial;
    }
    /*整个登录Div*/
    div.LoginPageDiv{
        width:80%;
        margin:20px auto;
    }
    /*一.天猫logo*/
    div.LoginPageDiv div.tmallLogoDiv{
        padding-bottom:30px;
    }
    /*二.背景图片Div*/
    div.LoginPageDiv div.backgroundImgDiv{
        position:relative;/*相对定位*/
    }
    /*背景图片 登录注册Div*/
    div.backgroundImgDiv div.loginContentDiv{
        /*1.位置*/
        position:absolute;
        top:80px;
        right:50px;
        /*2大小*/
        width:350px;
        height:400px;
        /*3.背景*/
        background-color:white;
        /*4.内边距*/
        padding:50px 25px;
    }
    /*登录注册Div 账户登录*/
    div.loginContentDiv div.accountLogin{
        font-size:16px;
        color:#3C3C3C;
        font-weight:bold;
        margin-bottom:20px;
    }
    /*登录注册Div 账号,密码边距*/
    div.loginContentDiv div.account,div.password{
        height:40px;
        margin-bottom:20px;
        border:1px solid #CBCBCB;/*和图标背景颜色一样*/
        position:relative;
    }
    /*登录注册Div 账号,密码Ico加背景颜色*/
    div.account span.accountIcon,div.password span.passwordIcon{
        display:inline-block;
        width:40px;
        background-color:#CBCBCB;
        height:39px;

        position:relative;/*垂直居中没用*/
    }
    /*Icon设置大小(字体大小)*/
    div.account span.glyphicon,div.password span.glyphicon{
        font-size:22px;
        color:#606060;
        position:absolute;
        top:9px;
        left:9px;
    }
    /*登录注册Div 账号,密码输入框注释要写完整ok???*/
    div.account input,div.password input{
        display:inline-block;
        position:absolute;
        bottom:0px;
        right:5px;
        width:245px;
        height:30px;
        border:0px;

    }

    /*登录注册Div 登录注册超链接*/
    div.forgetPasswordAndRegister a{
        color:#999999;
    }
    div.forgetPasswordAndRegister a:hover{
        color:#C40000;
        text-decoration:none;
    }
    /*登录注册Div 按钮*/
    div.loginContentDiv button.loginButton,button.QuickLogin{
        color:white;
        line-height:30px;
        border:0px;
        font-size:14px;
        font-weight:bold;
        border-radius:3px;/*圆角*/
    }
    div.loginContentDiv button.loginButton{
        background-color:#C40000;
    }
    div.loginContentDiv button.QuickLogin{
        background-color:#0084ff;
    }
</style>
<body>
<div class="LoginPageDiv">
    <!-- 天猫logo -->
    <div class="tmallLogoDiv">
        <img src="/img/site/simpleLogo.png">

    </div>
    <!-- 背景图片 宽度占满-->
    <div class="backgroundImgDiv">
        <img src="/img/site/loginBackground.png" style="width:100%">
        <div class="loginContentDiv">

                <div class="accountLogin">账户登录</div>

                <div class="account">
                    <!-- 这里不包一下总有点边距 -->
                    <span class="accountIcon"><span class="glyphicon glyphicon-user"></span></span>
                    <span class="accountInput" style="display:inline-block">
					 	<input class="account" type="text" placeholder="手机/用户名/邮箱" name="account">
					 </span>
                </div>
                <div class="password">
                    <span class="passwordIcon"><span class="glyphicon glyphicon-lock"></span></span>
                    <span class="passwordInput">
					 	<input class="password" type="password" placeholder="密码" name="password">
					 </span>
                </div>

                <div class="forgetPasswordAndRegister">
                    <a href="#nowhere" class="forgetPasswordLink">忘记登录密码</a>
                    <a href="index.jsp" class="RegisterLink pull-right">免费注册</a>
                </div>
                <button class="loginButton" style="width:100%;margin-top:20px;">登录</button>
                <button class="QuickLogin" style="width:100%;margin-top:20px;">快速登录</button>

                <span style="display: none"><a id="Pagego" href="/user/success">成功</a></span>
        </div>
    </div>

</div>
</body>
</html>
