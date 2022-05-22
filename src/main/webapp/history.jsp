<%@ page import="java.util.ArrayList" %>
<%@ page import="com.dokuny.find_public_wifi.model.entity.History" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <jsp:include page="static/component/head.jsp"/>
    </head>
    <body>
        <jsp:include page="static/component/header.jsp" flush="false"/>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/">Home</a></li>
                <li class="breadcrumb-item active">History</li>
                <li class="breadcrumb-item"><a href="/updateWifi">Update Wifi Info</a></li>
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
