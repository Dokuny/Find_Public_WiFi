<%@ page import="com.dokuny.find_public_wifi.model.dto.WifiListDto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.dokuny.find_public_wifi.util.PropertyManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    String key =  PropertyManager.getProp("config.properties").getProperty("map_key");

    ArrayList<WifiListDto> list = (ArrayList<WifiListDto>) request.getAttribute("list");

    double pos1 = 37.578706;
    double pos2 = 126.976703;

    if (list != null && list.size() > 0) {
        pos1 = (double) request.getAttribute("pos1");
        pos2 = (double) request.getAttribute("pos2");
    }
%>

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
        {
            title: '내 위치',
            latlng: new kakao.maps.LatLng(<%=pos1%>, <%=pos2%>)
        },

        <%
        for (WifiListDto wifi : list) { %>
        {
            title: '<%=wifi.getRoadAddr()%>',
            latlng: new kakao.maps.LatLng(<%=wifi.getLat()%>, <%=wifi.getLng()%>)
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
            markerImage = new kakao.maps.MarkerImage('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png', imageSize)
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

