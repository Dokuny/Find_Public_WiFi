<%@ page import="com.dokuny.find_public_wifi.model.WifiListDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <jsp:include page="static/component/head.jsp"/>
        <script type="text/javascript" src="static/js/geolocation.js"></script>

    </head>
    <body>
        <jsp:include page="static/component/header.jsp" flush="false"/>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item active">Home</li>
                <li class="breadcrumb-item"><a href="/history">History</a></li>
                <li class="breadcrumb-item"><a href="/updateWifi">Update Wifi Info</a></li>
            </ol>
        </nav>
        <section id="main">
            <form action="/getNearWifi" id="mainForm" method="post">
                <label for="lat">위도</label>
                <input type="number" id="lat" name="lat" placeholder="위도를 입력해주세요." step="0.0000000000000001">
                <label for="lng"> , 경도</label>
                <input type="number" id="lng" name="lng" placeholder="경도를 입력해주세요." step="0.0000000000000001">
                <input type="submit" value="근처 WIFI 찾기">
                <button onclick="getLocation()" type="button">내 위치정보 가져오기</button>
            </form>
            <jsp:include page="static/component/map.jsp" flush="false"/>
            <table id="mainTable">
                <tr>
                    <th>거리(km)</th>
                    <th>관리번호</th>
                    <th>자치구</th>
                    <th>와이파이명</th>
                    <th>도로명주소</th>
                    <th>상세주소</th>
                    <th>설치위치(층)</th>
                    <th>설치유형</th>
                    <th>설치기관</th>
                    <th>서비스구분</th>
                    <th>망종류</th>
                    <th>설치년도</th>
                    <th>실내외구분</th>
                    <th>WIFI접속환경</th>
                    <th>경도</th>
                    <th>위도</th>
                    <th>작업일자</th>
                </tr>

                <%if (request.getAttribute("list") == null) {%>
                <tr>
                    <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
                </tr>
                <%
                } else {
                    for (WifiListDto wifiListDto : (ArrayList<WifiListDto>)request.getAttribute("list")) {%>
                <tr>
                    <td><%=wifiListDto.getDistance()%>
                    </td>
                    <td><%=wifiListDto.getManagementNum()%>
                    </td>
                    <td><%=wifiListDto.getGu()%>
                    </td>
                    <td><%=wifiListDto.getName()%>
                    </td>
                    <td><%=wifiListDto.getRoadAddr()%>
                    </td>
                    <td><%=wifiListDto.getDetailAddr()%>
                    </td>
                    <td><%=wifiListDto.getInstallFloor()%>
                    </td>
                    <td><%=wifiListDto.getInstallType()%>
                    </td>
                    <td><%=wifiListDto.getInstallAgency()%>
                    </td>
                    <td><%=wifiListDto.getServiceType()%>
                    </td>
                    <td><%=wifiListDto.getNetworkType()%>
                    </td>
                    <td><%=wifiListDto.getInstallYear()%>
                    </td>
                    <td><%=wifiListDto.getInOut()%>
                    </td>
                    <td><%=wifiListDto.getEnv()%>
                    </td>
                    <td><%=wifiListDto.getLat()%>
                    </td>
                    <td><%=wifiListDto.getLng()%>
                    </td>
                    <td><%=wifiListDto.getWorkedTime()%>
                    </td>
                </tr>
                <%
                        }
                    }
                    %>
            </table>
        </section>
    </body>
</html>