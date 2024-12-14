package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
@WebServlet("/Searching")
public class Searching extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Searching(){
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
        String eventname = request.getParameter("eventnames");
        Date eventdate = Date.valueOf(request.getParameter("eventdates"));
        String seattype = request.getParameter("seattypes");

        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";

        try(Connection con = DriverManager.getConnection(url,dbUser,dbPassword)) {
            String eventquery = "SELECT eventid FROM event WHERE name = ? AND eventdate = ?";
            PreparedStatement eventstmt = con.prepareStatement(eventquery);
            eventstmt.setString(1,eventname);
            eventstmt.setDate(2,eventdate);
            ResultSet eventres = eventstmt.executeQuery();
            if(!eventres.next()){
                request.setAttribute("searchMessage","Event not found!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            int eventid = eventres.getInt("eventid");

            String ticketquery = "SELECT * FROM ticket WHERE eventid = ? AND type = ? ORDER BY ticketid DESC LIMIT 1";
            PreparedStatement ticketstmt = con.prepareStatement(ticketquery);
            ticketstmt.setInt(1,eventid);
            ticketstmt.setString(2,seattype);
            ResultSet ticketres = ticketstmt.executeQuery();
            if(!ticketres.next()){
                request.setAttribute("searchMessage","Error at getting available seats!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            if(seattype.equals("regular")){
                request.setAttribute("searchMessage","There are "+ticketres.getInt("availability_reg")+ " available "+seattype+" seats at "+eventname);
                request.getRequestDispatcher("main.jsp").forward(request,response);
            } else {
                request.setAttribute("searchMessage","There are "+ticketres.getInt("availability_vip")+ " available "+seattype+" seats at "+eventname);
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}