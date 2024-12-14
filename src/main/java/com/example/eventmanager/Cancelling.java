package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        String eventname = request.getParameter("eventnamec");
        Date eventdate = Date.valueOf(request.getParameter("eventdateca"));

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
                request.setAttribute("cancelMessage","Event not found!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            int eventid = eventres.getInt("eventid");

            String customerquery = "SELECT customerid FROM customer WHERE username = ?";
            PreparedStatement customerstmt = con.prepareStatement(customerquery);
            customerstmt.setString(1,username);
            ResultSet customerres = customerstmt.executeQuery();
            if(!customerres.next()){
                request.setAttribute("cancelMessage","You are not logged in!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            int customerid = customerres.getInt("customerid");

            String bookingquery = "SELECT bookingid, cost, ticketamount, ticketid FROM booking WHERE eventdate = ? AND customerid = ? AND eventid = ?";
            PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
            bookingstmt.setDate(1,eventdate);
            bookingstmt.setInt(2,customerid);
            bookingstmt.setInt(3,eventid);
            ResultSet bookingres = bookingstmt.executeQuery();
            if(!bookingres.next()){
                request.setAttribute("cancelMessage","Booking not found!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            int bookingid = bookingres.getInt("bookingid");
            int cost = bookingres.getInt("cost");
            int ticketamount = bookingres.getInt("ticketamount");
            int ticketid = bookingres.getInt("ticketid");

            String ticketquery = "DELETE FROM ticket WHERE ticketid = ?";
            PreparedStatement ticketstmt = con.prepareStatement(ticketquery);
            for(int i=0;i<ticketamount;i++){
                ticketstmt.setInt(1,ticketid+i);
                ticketstmt.addBatch();
            }
            ticketstmt.executeBatch();

            String deletequery = "DELETE FROM booking WHERE bookingid =?";
            PreparedStatement deletestmt = con.prepareStatement(deletequery);
            deletestmt.setInt(1,bookingid);
            int res = deletestmt.executeUpdate();
            if(res>0){
                request.setAttribute("cancelMessage","Successfully deleted booking and refunded "+cost+" euros back to your account!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            } else {
                request.setAttribute("cancelMessage","Error at deleting booking!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}
