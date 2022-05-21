package com.dokuny.find_public_wifi.controller;

import com.dokuny.find_public_wifi.model.WifiListDto;
import com.dokuny.find_public_wifi.repository.HistoryRepository;
import com.dokuny.find_public_wifi.repository.HistoryRepositoryImpl;
import com.dokuny.find_public_wifi.repository.WifiRepository;
import com.dokuny.find_public_wifi.repository.WifiRepositoryImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/getNearWifi")
public class GetNearWifiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        WifiRepository wifiRepo = new WifiRepositoryImpl();
        HistoryRepository hisRepo = new HistoryRepositoryImpl();

        String lng = request.getParameter("lng");
        String lat = request.getParameter("lat");

        if (lng == null || lat == null || lng == "" || lat == "") {
            out.println("<script>" +
                    "alert('경도와 위도를 입력해주세요');" +
                    "window.location = '/';" +
                    "</script>");
            return;
        }


        ArrayList<WifiListDto> list = wifiRepo.findAll(Double.parseDouble(lat),Double.parseDouble(lng));
        request.setAttribute("list", list);

        hisRepo.save(Double.parseDouble(lat),Double.parseDouble(lng));

        request.getRequestDispatcher("/").forward(request,response);
    }
}
