package com.dokuny.find_public_wifi.repository;

import com.dokuny.find_public_wifi.model.History;

import java.util.ArrayList;

public interface HistoryRepository {
    void save(double lat,double lng);

    ArrayList<History> findAll();

    void delete(int id);
}
