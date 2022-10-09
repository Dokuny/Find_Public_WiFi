# [Find_Public_Wifi](https://www.notion.so/dokuny/Find-Public-Wifi-a7e28573a69c45bfac39ba39f9e10be4)

> 서울시 기준, 내 주변 공공 와이파이 조회 서비스입니다.

## 📜주요 기능 소개

- 현재 위치를 기반으로 주변 20개의 공공 와이파이 정보를 조회합니다.

- 조회 이력을 관리할 수 있습니다.

## 🧑‍🤝‍🧑 개발 인원

![194528630-1c4c1697-b463-4e30-b372-db609655d716](https://user-images.githubusercontent.com/49369306/194532101-beb7a539-2dc5-41a9-beb5-2cae516fe0ae.jpg)  
[이도훈](https://github.com/Dokuny)

## 🔧Skills
### Front-End
<img src="https://img.shields.io/badge/JSP-E34F26?style=for-the-badge&logo=JSP&logoColor=white"> 

### Back-End
<img src ="https://img.shields.io/badge/Java-007396.svg?&style=for-the-badge&logo=Java&logoColor=White"/> <img src ="https://img.shields.io/badge/SQLite-003B57.svg?&style=for-the-badge&logo=SQLite&logoColor=White"/> 

## 🏢 시연 영상
### 서울시 공공 와이파이 API 호출
![bandicam 2022-05-22 16-27-17-460](https://user-images.githubusercontent.com/87813831/194745260-6f1dc0b5-14db-45df-9cdc-dbb779c1cff6.gif)
### 내 위치 기반 와이파이 조회
![bandicam 2022-05-22 16-33-08-505](https://user-images.githubusercontent.com/87813831/194745859-2e1a508e-7c7c-4dde-b285-ba0b68b319ea.gif)
### 조회 이력 관리
![bandicam 2022-05-22 16-34-53-607](https://user-images.githubusercontent.com/87813831/194745869-d3b2b1d6-f311-4689-8085-389f8725580b.gif)


## 🎯 트러블 슈팅 
1. API로 한번에 대량의 정보를 긁어와 한건씩 INSERT를 하다보니 너무 많은 시간이 소모
```java
public class WifiRepositoryImpl implements WifiRepository {
    
    ...
    
    @Override
    public void saveAll() {
            ...
            
            for (WifiApiDto wifiApiDto : wifiDataAll) {
                sql = "INSERT INTO wifi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                pstmt = conn.prepareStatement(sql);
                ...
                pstmt.execute();
            }
            
            ...
    }

}
```
* 처리하는데 상당히 많은 시간이 소모되었습니다.

1-1. addBatch(), executeBatch() 사용하여 배치 처리
```java

            for (WifiApiDto wifiApiDto : wifiDataAll) {
                ...
                pstmt.addBatch();
                pstmt.clearParameters();
            }
            pstmt.executeBatch();
            pstmt.clearBatch();
```
* 이렇게 하더라도 SQLite는 모든 INSERT, UPDATE 문을 각각 하나의 트랜잭션으로 처리하기 때문에 성능상 이득을 보지는 못했습니다.

1-2. 하나의 트랜잭션으로 처리하기
```java
            pstmt = conn.prepareStatement("BEGIN TRANSACTION;");
            pstmt.execute();

            sql = "INSERT INTO wifi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pstmt = conn.prepareStatement(sql);

            for (WifiApiDto wifiApiDto : wifiDataAll) {
                ...
                pstmt.addBatch();
                pstmt.clearParameters();
            }
            
            pstmt.executeBatch();
            pstmt.clearBatch();

            pstmt = conn.prepareStatement("END TRANSACTION;");
            pstmt.execute();

```
* Begin Transaction, End Transaction 문을 직접 사용하여 트랜잭션을 관리했더니 처리시간이 540 ~ 600 ms 정도로 단축되었습니다.

1-3. 캐시사이즈 변경
```java
            pstmt = conn.prepareStatement("PRAGMA cache_size=10000");
            pstmt.execute();
```
* 캐시 사이즈를 조절해주었더니 500 초반으로 성능 형상이 되었습니다. (생각보다 그렇게 크지는 않은 것 같습니다)
