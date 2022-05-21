package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.WifiListDto;

import java.util.ArrayList;

public interface WifiRepository {

    int updateAll();

    ArrayList<WifiListDto> findAll(double lat, double lnt);
}
