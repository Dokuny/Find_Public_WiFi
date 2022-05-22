package com.dokuny.find_public_wifi.service;

import com.dokuny.find_public_wifi.model.dto.WifiApiDto;
import com.dokuny.find_public_wifi.util.PropertyManager;
import com.google.gson.*;
import okhttp3.*;

import java.util.ArrayList;

public class ApiService {

    private final String KEY;
    private final String BASE_URL;

    public ApiService() {
        KEY = PropertyManager.getProp("config.properties").getProperty("public_wifi_key");
        BASE_URL = "http://openapi.seoul.go.kr:8088/"+ KEY +"/json/TbPublicWifiInfo/";
    }


    private JsonObject getWifiInfo(int first, int second) {
        String url = BASE_URL + first + "/" + second + "/";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).get().build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    String json = body.string();
                    JsonElement element = JsonParser.parseString(json);
                    JsonObject object = element.getAsJsonObject();
                    JsonObject info = object.get("TbPublicWifiInfo").getAsJsonObject();
                    return info;
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getTotalNum() {
        JsonObject wifiData = getWifiInfo(1, 1);
        return wifiData.get("list_total_count").getAsInt();
    }

    public ArrayList<WifiApiDto> getWifiDataAll() {
        int totalNum = getTotalNum();
        int carryMaxNum = 1000;
        int first = 1;
        int second = 1000;

        ArrayList<WifiApiDto> list = new ArrayList<>();
        Gson gson = new Gson();

        while (first < totalNum) {
            if (second > totalNum) {
                second = totalNum;
            }
            JsonObject wifiInfo = getWifiInfo(first, second);

            JsonArray row = wifiInfo.get("row").getAsJsonArray();
            for (JsonElement jsonElement : row) {
                WifiApiDto wifiApiDto = gson.fromJson(jsonElement, WifiApiDto.class);
                list.add(wifiApiDto);
            }
            first = second + 1;
            second = first + carryMaxNum - 1;
        }
        return list;
    }
}
