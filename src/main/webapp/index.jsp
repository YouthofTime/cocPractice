<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/13 0013
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <script>
        $(function () {
            $("input.submitButton").click(function () {
                // 获得输入的值
                var name = $("input.inputLoginName").val();
                var password = $("input.inputLoginPassword").val();
                var password1 = $("input.ReInputPassword").val();
                // 为空判断 // 输入框显示错误信息
                var result=0;
                if (name == "") {
                    $("input.inputLoginName").attr("placeholder","请输入用户名");
                    f1("input.inputLoginName");

                    return;
                }

                if (password == "") {
                    alert("请输入密码");
                    return;
                }
                if (password1 == "") {
                    alert("请确认密码");
                    return;
                }
                // 密码不一致判断
                if (password != "" && password != "" && (password1 != password)) {
                    alert("前后密码不一致");
                    return;
                }
                data={"name":name,"password":password};
                // 后端判断
                $.ajax({
                    type:"post",
                    contentType:"application/json;charset=UTF-8",
                    url:"/user/register",
                    data:JSON.stringify(data),
                    dataType:"json",
                    success:function (judge){
                        if(judge.status==1){ // 注册成功

                            alert(judge.meg);
                            // 触发返回登录链接按钮(超链接感觉没有点击函数啊)
                            $("input.backLogin").click();
                        }
                        else if(judge.status==0){ // 用户已经存在
                            alert(judge.meg);
                        }
                    }
                    });

            });
            // 提示信息为红色
            function f1(a){
            $('[placeholder]').focus(function() {
                var input = $(a);

                if (input.val() == input.attr('placeholder')) {
                    input.val('');
                    input.removeClass('placeholder');
                }
            }).blur(function() {
                var input = $(a);
                if (input.val() == '' || input.val() == input.attr('placeholder')) {
                    input.addClass('placeholder');
                    input.val(input.attr('placeholder'));
                }
            }).blur();
            $('[placeholder]').parents('form').submit(function() {
                $(this).find('[placeholder]').each(function() {
                    var input = $(a);
                    if (input.val() == input.attr('placeholder')) {
                        input.val('');
                    }
                })
            });
            }
        })
    </script>
    <style>
        .placeholder{
            color: red;
            /* etc */
        }
        div.cocRegister{
            width:100%;
            height:100%;
        }
        /*注册页面*/
        div.register{
            margin:0px auto;
            text-align: center;
            width:400px ;

            background-color:white!important;
        }
        div.topTitle{
            text-align:center;
        }
        div.left{
            display:inline-block;
            text-align:left;
            padding:40 0 10 25;
            font-size:16px;
        }

        div.top{
            display:inline-block;
            text-align:left;
            padding:40 0 10 40;
            font-size:16px;
        }

        /*用户输入框*/

        div.userDiv input{
            margin-top:30px;
            width:352px;
            height:48px;
            border:0;
            border-bottom:1px solid lightgray;
        }
        /*密码框*/
        div.passwordDiv input{
            width:352px;
            height:48px;
            border:0;
            border-bottom:1px solid lightgray;
        }
        div.passwordAgainDiv input{
            width:352px;
            height:48px;
            border:0;
            border-bottom:1px solid lightgray;
        }
        /*按钮*/
        div.submitDiv input.submitButton{
            margin-top:40px;
            border:0px;
            width:360px;
            height:35px;
            color:white;
            background-color:#C40000;
            border-radius:3px;/*圆角*/
        }

        div.submitDiv input.backLogin{
            display: inline-block;
            line-height: 35px;
            font-size: 14px;
            margin-top:10px;
            border:0px;
            width:360px;
            height:35px;
            color:white;
            background-color:#0084ff;
            border-radius:3px;/*圆角*/
        }
        div.messageDiv{
            margin-left:15px;
            margin-top:40px;
            width:360px;
            font-size:13px;
            color:gray;
            border-bottom:1px solid lightgray;
            padding-bottom:20px;
            margin-bottom:10px;
        }
        div.bottomDiv{
            text-align:left;
            height:60px;
            background-color:#f6f6f6;
        }
        a{
            text-decoration:none;
            color:#0084ff;
        }



    </style>
    <title>Title</title>
</head >
<body style="background-image:url('img/register/back1.jpg')">


<div class="cocRegister">
    <div class="topTitle"><h1 style="color:blue;">COC注册</h1></div>
    <div class="register">
        <div align="left">
            <div class="left">
                免密码登录
            </div>
            <div class="top">
                <b>开始注册</b>
            </div>
        </div>
        <form action="login.jsp" method="post">
            <div class="userDiv" ><input class="inputLoginName" name="name" type="text" placeholder="输入用户名"></div>
            <div class="passwordDiv"><input  class="inputLoginPassword" name="password" type="password" placeholder="输入密码"></div>
            <div class="passwordAgainDiv"><input  class="ReInputPassword" type="password" placeholder="再次输入密码"></div>
            <div class="submitDiv">
                <input class="submitButton" type="button" value="注册">
                <input type="submit" class="backLogin" value="返回登录">
            </div>
        </form>
        <div class="messageDiv">

            <div >未注册手机验证后自动登录，注册即代表同意	《COC协议》

            </div>
        </div>
        <div class="bottomDiv">
            <div style="display:inline-block;margin:20px 20px"><a href="#">开通机构号</a></div>
            <div style="display:inline-block;float:right;margin:20px 20px"><a href="#">下载知乎app</a></div>
        </div>
    </div>

</div>
</body>
</html>



