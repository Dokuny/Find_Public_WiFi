package com.dokuny.find_public_wifi.controller;

import com.dokuny.find_public_wifi.repository.HistoryRepository;
import com.dokuny.find_public_wifi.repository.HistoryRepositoryImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/history/delete")
public class HistoryDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HistoryRepository hisRepo = new HistoryRepositoryImpl();

        String id = request.getParameter("id");
        hisRepo.delete(Integer.parseInt(id));

        response.sendRedirect("/history");
    }
}
