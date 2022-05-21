<%@ page import="com.dokuny.find_public_wifi.model.WifiListDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.dokuny.find_public_wifi.model.History" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <link href="static/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="static/css/index.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="static/component/header.jsp" flush="false"/>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/index.jsp">Home</a></li>
                <li class="breadcrumb-item active">History</li>
                <li class="breadcrumb-item"><a href="/updateWifi.jsp">Update Wifi Info</a></li>
            </ol>
        </nav>
        <section id="main">
            <table id="mainTable">
                <tr>
                    <th>ID</th>
                    <th>위도</th>
                    <th>경도</th>
                    <th>조회일자</th>
                    <th>비고</th>
                </tr>
                <%
                    ArrayList<History> list = (ArrayList<History>) request.getAttribute("list");

                    if (list.size() == 0) { %>
                <tr>
                    <td colspan="5">조회 이력이 없습니다</td>
                </tr>
                <% } else {
                    for (History history : list) {%>
                <tr>
                    <td><%=history.getId()%>
                    </td>
                    <td><%=history.getLat()%>
                    </td>
                    <td><%=history.getLng()%>
                    </td>
                    <td><%=history.getCheckDate()%>
                    </td>
                    <td>
                        <form action="/history/delete" method="post">
                            <input type="hidden" name="id" value="<%=history.getId()%>">
                            <button type="submit">삭제</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                    request.setAttribute("list", null);
                %>

            </table>
        </section>

    </body>
</html>
