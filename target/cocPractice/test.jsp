<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/16 0016
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <script>
        $(function () {

            // onload是等所有的资源文件加载完毕以后再绑定事件
            window.onload = function(){
                // 获取图片列表，即img标签列表
                var imgs = document.querySelectorAll('img');

                // 获取到浏览器顶部的距离
                function getTop(e){
                    return e.offsetTop;
                }

                // 懒加载实现
                function lazyload(imgs){
                    // 可视区域高度
                    var h = window.innerHeight;
                    //滚动区域高度
                    var s = document.documentElement.scrollTop || document.body.scrollTop;
                    for(var i=0;i<imgs.length;i++){
                        //图片距离顶部的距离大于可视区域和滚动区域之和时懒加载
                        if ((h+s)>getTop(imgs[i])) {
                            // 真实情况是页面开始有2秒空白，所以使用setTimeout定时2s
                            (function(i){
                                setTimeout(function(){
                                    // 不加立即执行函数i会等于9
                                    // 隐形加载图片或其他资源，
                                    //创建一个临时图片，这个图片在内存中不会到页面上去。实现隐形加载
                                    var temp = new Image();
                                    temp.src = imgs[i].getAttribute('data-src');//只会请求一次
                                    // onload判断图片加载完毕，真是图片加载完毕，再赋值给dom节点
                                    temp.onload = function(){
                                        // 获取自定义属性data-src，用真图片替换假图片
                                        imgs[i].src = imgs[i].getAttribute('data-src')
                                    }
                                },2000)
                            })(i)
                        }
                    }
                }
                lazyload(imgs);

                // 滚屏函数
                window.onscroll =function(){
                    lazyload(imgs);
                }
            }
        });

    </script>
    <style>
        .img{
            width: 100px;
            height: 75px;
        }
    </style>
    <title>Title</title>
</head>
<body>
<div>
    <table>
        <tr>
            <td><img class="img" src="img/coc/loading.gif" data-src="img/coc/ballon.jpg"></td>
            <td><span>气球兵</span></td>
        </tr>
        <tr>
            <td><img class="img" src="img/coc/loading.gif" data-src="img/coc/dragon.jpg"></td>
            <td><span>龙</span></td>
        </tr>
        <tr>
            <td><img class="img" src="img/coc/loading.gif" data-src="img/coc/bluebeard.jpg"></td>
            <td><span>蓝胖子</span></td>
        </tr>

    </table>


</div>
</body>
</html>
