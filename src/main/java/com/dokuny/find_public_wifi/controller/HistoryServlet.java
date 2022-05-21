package com.dokuny.find_public_wifi.controller;

import com.dokuny.find_public_wifi.model.History;
import com.dokuny.find_public_wifi.repository.HistoryRepository;
import com.dokuny.find_public_wifi.repository.HistoryRepositoryImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HistoryRepository hisRepo = new HistoryRepositoryImpl();

        ArrayList<History> list = hisRepo.findAll();

        request.setAttribute("list", list);

        request.getRequestDispatcher("/history.jsp").forward(request,response);
    }
}
