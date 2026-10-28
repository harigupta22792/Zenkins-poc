package com.example.poc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/calculate")
public class CalculatorServlet extends HttpServlet {

    private final CalculationHandler handler = new CalculationHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CalculationHandler.Response r = handler.handle(
                req.getParameter("op"), req.getParameter("a"), req.getParameter("b"));
        resp.setStatus(r.status());
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(r.json());
    }
}
