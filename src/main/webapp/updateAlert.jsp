<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <script>
            alert(<%=request.getAttribute("num")%>+'개의 WIFI 정보를 정상적으로 저장하였습니다.');
            window.location='/';
        </script>
    </head>
    <body>

    </body>
</html>
