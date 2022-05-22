package com.dokuny.find_public_wifi.controller;

import com.dokuny.find_public_wifi.repository.WifiRepository;
import com.dokuny.find_public_wifi.repository.WifiRepositoryImpl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/updateWifi")
public class UpdateWifiServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");

        WifiRepository repo = new WifiRepositoryImpl();

        int num = repo.updateAll();
        request.setAttribute("num", num);

        request.getRequestDispatcher("/updateAlert.jsp").forward(request,response);
    }


}