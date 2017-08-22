<%--
  Created by IntelliJ IDEA.
  User: wangergou
  Date: 2017/8/20
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json交互测试</title>
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
    <script type="text/javascript">
        //        请求的是json，输出的是json
        function requestJson() {
            $.ajax({
                type: 'post',
                url: '${pageContext.request.contextPath}/requestJson.action',
                contentType: "application/json;charset=UTF-8",
                //数据格式是json串
                data: '{"name":"手机","price":999}',
                success: function (data) {//返回json结果
                    alert(data.price);
                }

            })
        }

        //请求的是key/value，输出的是json
        function responseJson() {
            $.ajax({
                type: 'post',
                url: '${pageContext.request.contextPath}/responseJson.action',
                //因为默认使用的是application/x-www-form-urlencoded;charset=UTF-8，所以说如果不指定的话就使用默认的（key/value）
//                contentType: "application/json;charset=UTF-8",
                //数据格式是json串
                data: 'name=手机&price=999',
                success: function (data) {//返回json结果
                    alert(data.name);
                }

            })
        }
    </script>
</head>
<body>
<input type="button" onclick="requestJson()" value="请求的是json，输出的是json"/>
<input type="button" onclick="responseJson()" value="请求的是key/value，输出的是json"/>
</body>
</html>
