package com.dokuny.find_public_wifi.service;

import com.dokuny.find_public_wifi.model.WifiApiDto;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class ApiService {
    private static String key = null;

    static {
        Properties prop = new Properties();
        try {
            InputStream is = ApiService.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(is);
            key = prop.getProperty("public_wifi_key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/"+key+"/json/TbPublicWifiInfo/";

    private static JsonObject getWifiInfo(int first, int second) {
        String url = BASE_URL + first + "/" + second + "/";

        try {
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url).get();
            Request request = builder.build();

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

    private static int getTotalNum() {
        JsonObject wifiData = getWifiInfo(1, 1);
        return wifiData.get("list_total_count").getAsInt();
    }

    public static ArrayList<WifiApiDto> getWifiDataAll() {
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
