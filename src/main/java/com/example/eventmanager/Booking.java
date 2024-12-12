package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
@WebServlet("/Booking")
public class Booking extends HttpServlet{
    private static final long serialVersionUID = 1L;

    public Booking(){
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
        String eventname = request.getParameter("eventnameb");
        int ticketamount = Integer.parseInt(request.getParameter("ticketamount"));
        String seatype = request.getParameter("seattype");

        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(url,dbUser,dbPassword)){
            String eventquery = "SELECT eventid, capacity FROM event WHERE name = ?";
            PreparedStatement eventstmt = con.prepareStatement(eventquery);
            eventstmt.setString(1,eventname);
            ResultSet eventres = eventstmt.executeQuery();
            if(!eventres.next()){

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}
