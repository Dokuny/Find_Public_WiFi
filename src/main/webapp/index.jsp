<%@ page import="com.dokuny.find_public_wifi.model.WifiListDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="com.dokuny.find_public_wifi.service.ApiService" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Properties prop = new Properties();
    String key = null;
    try {
        InputStream is = ApiService.class.getClassLoader().getResourceAsStream("config.properties");
        prop.load(is);
        key = prop.getProperty("map_key");
    } catch (IOException e) {
        e.printStackTrace();
    }
    ArrayList<WifiListDto> list = (ArrayList<WifiListDto>) request.getAttribute("list");
    double pos1 = 37.578706;
    double pos2 = 126.976703;
    if (list != null && list.size()>0) {
        pos1= (double) request.getAttribute("pos1");
        pos2= (double) request.getAttribute("pos2");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <link href="static/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="static/css/index.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
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
                <input type="text" id="lat" name="lat" placeholder="위도를 입력해주세요.">
                <label for="lng"> , 경도</label>
                <input type="text" id="lng" name="lng" placeholder="경도를 입력해주세요.">
                <input type="submit" value="근처 WIFI 찾기">
                <button onclick="getLocation()" type="button">내 위치정보 가져오기</button>
            </form>
            <div id="map" style="width:100%;height:400px;"></div>
            <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=<%=key%>"></script>
            <script>
                var container = document.getElementById('map');
                var options = {
                    center: new kakao.maps.LatLng(<%=pos1%>, <%=pos2%>),
                    level: 5
                };

                var map = new kakao.maps.Map(container, options);

                var positions = [
                    <%if(list !=null){%>
                    {title: '내 위치',
                        latlng: new kakao.maps.LatLng(<%=pos1%>,<%=pos2%>)
                    },

                    <%
                    for (WifiListDto wifi : list) { %>
                    {
                        title: '<%=wifi.getRoadAddr()%>',
                        latlng: new kakao.maps.LatLng(<%=wifi.getLat()%>,<%=wifi.getLng()%>)
                    },
                    <%}}%>


                ];

                var imageSrc = " http://t1.daumcdn.net/localimg/localimages/07/2018/pc/img/marker_spot.png";

                for (var i = 0; i < positions.length; i++) {

                    // 마커 이미지의 이미지 크기 입니다
                    var imageSize = new kakao.maps.Size(24, 35);

                    // 마커 이미지를 생성합니다

                    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
                    if (i == 0) {
                        markerImage = new kakao.maps.MarkerImage('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png',imageSize)
                    }

                    // 마커를 생성합니다
                    var marker = new kakao.maps.Marker({
                        map: map, // 마커를 표시할 지도
                        position: positions[i].latlng, // 마커를 표시할 위치
                        title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                        image: markerImage // 마커 이미지
                    });
                }

            </script>
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
                    for (WifiListDto wifiListDto : list) {%>
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