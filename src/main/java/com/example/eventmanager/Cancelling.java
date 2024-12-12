package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
@WebServlet("/Cancelling")
public class Cancelling extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public Cancelling(){
        super();
    }

    public void init() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventname = request.getParameter("eventnamed");
        String eventdate = request.getParameter("eventdated");
        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";
        String query = "DELETE FROM EVENT WHERE NAME = ? AND EVENTDATE = ?";
        response.setContentType("text/html");
        try {
            Connection con = DriverManager.getConnection(url,dbUser,dbPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,eventname);
            stmt.setString(2,eventdate);
            int res = stmt.executeUpdate();
            if(res>0){
                request.setAttribute("deleteMessage","Event deleted successfully!");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            } else {
                request.setAttribute("deleteMessage","Failed to delete event!");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}
