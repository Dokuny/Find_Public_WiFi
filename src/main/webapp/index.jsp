<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>공공 와이파이 찾기</title>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="css/index.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
        <script type="text/javascript" src="js/geolocation.js"></script>

    </head>
    <body>
        <jsp:include page="component/header.jsp" flush="false"/>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item active">Home</li>
                <li class="breadcrumb-item"><a href="/history.jsp">History</a></li>
                <li class="breadcrumb-item"><a href="#">Update Wifi Info</a></li>
            </ol>
        </nav>
        <section id="main">
            <form action="" id="mainForm">
                <label for="lat">위도</label>
                <input type="number" id="lat" placeholder="위도를 입력해주세요.">
                <label for="lnt"> , 경도</label>
                <input type="number" id="lnt" placeholder="경도를 입력해주세요.">
                <input type="submit" value="근처 WIFI 찾기">
                <button onclick="getLocation()" type="button">내 위치정보 가져오기</button>
            </form>

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


                    <tr>
                        <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
                    </tr>

            </table>
        </section>
    </body>
</html>