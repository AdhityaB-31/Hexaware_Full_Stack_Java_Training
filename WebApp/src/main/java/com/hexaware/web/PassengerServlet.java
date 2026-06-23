package com.hexaware.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PassengerServlet")
public class PassengerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String pid = request.getParameter("pid");
        String pname = request.getParameter("pname");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Passenger Details Received Using GET</h1>");

        out.println("<p>Passenger Id : " + pid + "</p>");
        out.println("<p>Passenger Name : " + pname + "</p>");
        out.println("<p>Age : " + age + "</p>");
        out.println("<p>Gender : " + gender + "</p>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String pid = request.getParameter("pid");
        String pname = request.getParameter("pname");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Passenger Details Received Using POST</h1>");

        out.println("<p>Passenger Id : " + pid + "</p>");
        out.println("<p>Passenger Name : " + pname + "</p>");
        out.println("<p>Age : " + age + "</p>");
        out.println("<p>Gender : " + gender + "</p>");

        out.println("</body></html>");
    }
}
