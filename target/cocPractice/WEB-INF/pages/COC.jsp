<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/15 0015
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<script src="/js/jquery/2.0.0/jquery.min.js"></script>
<link href="/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
<script src="/js/bootstrap/3.3.6/bootstrap.min.js"></script>
<head>
    <script>
        $(function () {
            // 添加后缀
            var imgs = document.querySelectorAll('img');
            for(var i=0;i<imgs.length;i++){
                if("img"==imgs[i].className) { // 只修改class为img的图片
                    var src = imgs[i].src + ".png";
                    imgs[i].src = src;
                }
            }
            // 上下页跳转
            $("button.nextPageButton").click(function () {
                $(".nextPagego span").click();    // 超链接点击函数需要子元素
                // 因为每次都是一个新的链接了,所以默认隐藏了夜世界

            });
            $("button.upPageButton").click(function () {
                $(".upPagego span").click();    // 超链接点击函数需要子元素
            });

            // 添加士兵
            $("button#addButton").click(function () {
                var name=$("input.addinput").val();
                $.post(
                    "/coc1/add",
                    {"name":name},
                    function (judge) {
                        if(judge.status==0){ // 已经有了
                            alert(judge.meg);
                        }
                        if(judge.status==1){ //没有该士兵
                           alert(judge.meg);
                        }
                        if(judge.status==2){ // 添加成功
                            // 请求路径
                            $("span#addspan a span").click();
                        }
                    }
                );
            });
            // 升级士兵
            $("button.updateSoldier").click(function () {
                var id=$(this).attr("name");
                $.post(
                    "/coc1/upgrade",
                    {"id":id},
                    function (judge) {

                        if(judge.status==0){ // 最高级
                            alert(judge.meg);
                        }
                        if(judge.status==1){
                            $("span#upgradespan a span").click();
                        }
                    }
                );
            });

        });

    </script>
    <style>
        /*nav.top*/
        nav.top{
            background-color:#f2f2f2;

        }
        a{
            color:white;/*灰色*/
        }
        /*超链接,nav.top.span(这里a和span与class之间用空格隔开)*/
        nav.top a,nav.top span{
            font-weight: bold;
            color:#999;/*这里不要设置优先级啊*/
            margin:0px 10px 0px 10px;
        }

        /*图标设置天猫红*/
        .redColor{
            color:#C24000!important;
        }
        /*超链接悬浮状态*/
        nav.top a:hover{
            color:#C24000;
            text-decoration:none;
        }
        /*退出登录*/
        span.back form{
            display: inline-block;
        }
        div.coc{
            position: relative;
            left:200px;
            top:50px;
            width: 80%;
            height: 80%;
        }
        /*设置主夜世界的位置*/
        div.toggleWorldDiv{
            display: inline-block;
            position: relative;
            width: 20%;
            height: 100%;
        }
        /*设置兵种信息大div*/
        div.rightCoc{
            display: inline-block;
            position: relative;
            width: 60%;
            height: 100%;
        }
        /*上一页,下一页div*/
        div.upPageDiv{
            display: inline-block;
            position: relative;
            width: 10%;
            height: 100%;

        }
        div.nextPageDiv{
            display: inline-block;
            position: relative;
            width: 16%;
            left:100%;
            height: 100%;

        }

        /*主夜世界 按钮大小,位置*/
        div.toggleWorldDiv a.main{
            border: 0;
            position: absolute;
            left: 5%;
            top:30%;
            width: 90%;
            height: 20%;
        }
        div.toggleWorldDiv a.night{
            border: 0;
            position: absolute;
            bottom: 15%;
            left: 5%;
            width: 90%;
            height:20%;
        }
        /*设置主夜世界图片大小*/
        div.toggleWorldDiv img{
            width: 100%;
            height: 100%;
        }
        /*设置上下页button大小*/

        button.upPageButton{
            border: 0;
            top:50%;
            left: 5%;
            width: 90%;
            height:20%;
            position: absolute;
        }
        button.nextPageButton{
            border: 0;
            position: absolute;
            bottom: 30%;
            left: 5%;
            width: 90%;
            height:20%;
        }
        /*添加用户div*/
        div.nextPageDiv div#addDiv{
            display: inline-block;
            left:5%;
            position: absolute;
            bottom: 58.5%;
            width: 200%;
            height: 20%;
        }
        /*设置上下页按钮图片大小*/

        button.upPageButton img{
            width: 100%;
            height: 100%;
        }
        button.nextPageButton img{
            width: 100%;
            height: 100%;
        }

        /*右边div*/
        /*兵种数据div*/
        div.dataDiv{
            display: inline-block;
            top:20%;
            width: 100%;
            position: absolute;
            height: 70%;

        }
        /*标题居中*/
        div.headDiv span{
            position: absolute;
            display: inline-block;
            left:45%;
            top:40%;
        }
        /*表格相对位置设置*/
        td{
            font-weight: bold;
            color:darkblue;
        }
        /*士兵详细介绍图标*/
        div.msg{
            padding-top:20px;
        }
        div.msg span img{
            width: 26px;
            height: 22px;
        }
        /*删除超链接*/
        a.deleteSoldier,a.updateSoldier{
            display: inline-block;
            font-weight: normal;
            width: 54px;
            height: 34px;
            line-height:34px;
            text-align: center;
            border:0px;
            font-size:14px;
            cursor: hand;
            border-radius:3px;/*圆角*/
        }
        a.deleteSoldier {
            background-color: #C40000;
        }
        a.updateSoldier {
            background-color:#f0ad4e;
        }
        a.deleteSoldier:hover, a.updateSoldier:hover{
            text-decoration: none;
            color: white;
        }
        .img{

            width: 110px;
            height: 154px;
        }








    </style>
    <title>Title</title>
</head>
<body style="background-image:url('/img/coc/back.jpg')">
<nav class="top" >
    <a href="#nowhere">
        <span class="glyphicon glyphicon-home redColor"></span>
        部落冲突首页
    </a>
    <span>喵,欢迎来到部落冲突</span>
    <span>欢迎${username}</span>
    <span><!-- 向右浮动 -->
        <span style="font-size:18px;" >
            <form style="display: inline-block" action="/coc1/collect" method="post">
                <button  class="btn btn-success">收集</button>
            </form>
            <img  src="/img/coc/msg/holyWater.png">:${holyWater}
        </span>
        <span style="font-size: 18px;display: inline-block;margin-left: 30px">
             <form style="display: inline-block" action="/coc1/collect" method="post">
                <button class="btn btn-success">收集</button>
            </form>
            <img src="/img/coc/msg/blackWater.png">:${blackWater}
        </span>
        <span style="margin-left: 50px;float:right;margin-top:10px;" class="back">
            <form action="/user/back" method="post">
                <input type="submit" class="btn btn-danger" value="退出"/>
            </form>
        </span>
	</span>
</nav>
<div class="coc" style="clear: right">
    <div class="toggleWorldDiv">
        <div ><a href="/user/success?world=1" class="main"><img src="/img/coc/mainWorld.jpg"></a></div>
        <div ><a href="/user/success?world=2" class="night"><img src="/img/coc/nightWorld.jpg"></a></div>
    </div>
    <div class="upPageDiv">

            <button class="upPageButton"><img src="/img/page/上一页.png"></button>
        <c:if test="${not empty soldiers}">
        <span style="display: none"><a class="upPagego" href="/user/success?start=${up}&aid=${soldiers.get(0).arm.id}"><span>成功</span></a></span>
        </c:if>
    </div>
    <div class="rightCoc">

        <div class="dataDiv">

        <table id="main" class="data table table-bordered table-hover table-condensed">

            <tr>
                <td >图片</td>
                <td>介绍</td>
                <td width="30%">操作</td>
            </tr>
            <c:forEach items="${soldiers}" var="solder">
                <tr>
                    <td width="110px" height="150px"><img class="img" src="/img/coc/${solder.name}"></td>
                    <td>
                        <div class="msg">
                            <span style="font-size: 18px!important;">${solder.name}(${solder.level}级)</span> <br>
                            <span><img src="/img/coc/msg/damageSecond.png">:${solder.damageSecond}</span><br>
                            <span><img src="/img/coc/msg/hp.png">:${solder.hp}</span><br>
                            <span><img src="/img/coc/msg/soldier.png">:${solder.arm.name}</span><br>
                            <span><img src="/img/coc/msg/holyWater.png">:${solder.holyWater}</span><br>
                        </div>
                    </td>
                    <td>
                        <button class="updateSoldier btn btn-warning" name="${solder.id}" >升级</button>
                        <a class="deleteSoldier" href="/coc1/delete?id=${solder.id}&aid=${solder.arm.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </div>

        <div class="nextPageDiv">
            <!--添加-->
                <div id="addDiv">
                    <input class="addinput" style="width: 40%;height: 25%;display: inline-block" type="text" class="form-control" placeholder="用户名">
                    <button id="addButton"style="width: 20%;height: 25%;line-height: 20%" class="btn btn-success">添加</button>
                    <!--添加成功后跳转-->
                    <c:if test="${not empty soldiers}">
                    <span id="addspan" style="display: none"><a href="/user/success?start=${start}&aid=${soldiers.get(0).arm.id}"><span>成功</span></a></span>
                    </c:if>
                </div>
            <!--升级成功后跳转-->
            <c:if test="${not empty soldiers}">
                <span id="upgradespan" style="display: none"><a href="/user/success?start=${start}&aid=${soldiers.get(0).arm.id}"><span>成功</span></a></span>
            </c:if>
            <!--下一页-->
                <button class="nextPageButton"><img src="/img/page/下一页.png"></button>
                <c:if test="${not empty soldiers}">
                <span style="display: none"><a class="nextPagego" href="/user/success?start=${next}&aid=${soldiers.get(0).arm.id}"><span>成功</span></a></span>
                </c:if>
        </div>
    </div>


</div>

</body>
</html>
