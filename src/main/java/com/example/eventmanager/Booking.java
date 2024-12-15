package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        String eventname = request.getParameter("eventnameb");
        int ticketamount = Integer.parseInt(request.getParameter("ticketamount"));
        String seattype = request.getParameter("seattype");

        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection con = DriverManager.getConnection(url,dbUser,dbPassword)){
            String eventquery = "SELECT eventid, capacity,eventdate FROM event WHERE name = ?";
            PreparedStatement eventstmt = con.prepareStatement(eventquery);
            eventstmt.setString(1,eventname);
            ResultSet eventres = eventstmt.executeQuery();
            if(!eventres.next()){
                request.setAttribute("bookingMessage","Event not found!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            int evendId = eventres.getInt("eventid");
            int capacity = eventres.getInt("capacity");
            Date eventdate = eventres.getDate("eventdate");
            int vipcap = capacity*10/100;
            int regcap = capacity*90/100;
            int ticketid = 1;
            int customerid;

            String ticketreg = "SELECT * FROM ticket WHERE type = 'regular' ORDER BY eventid DESC LIMIT 1";
            PreparedStatement ticketregstmt = con.prepareStatement(ticketreg);
            ResultSet ticketregres = ticketregstmt.executeQuery();
            if(ticketregres.next()){
                ticketid = ticketregres.getInt("ticketid")+1;
                int regavail = ticketregres.getInt("availability_reg");
                regcap = regcap - (regcap-regavail+2);
            }

            String ticketvip = "SELECT * FROM ticket WHERE type = 'vip' ORDER BY eventid DESC LIMIT 1";
            PreparedStatement ticketvipstmt = con.prepareStatement(ticketvip);
            ResultSet ticketvipres = ticketvipstmt.executeQuery();
            if(ticketvipres.next()){
                if(ticketvipres.getInt("ticketid")>=ticketid){
                    ticketid = ticketvipres.getInt("ticketid")+1;
                }
                int vipavail = ticketvipres.getInt("availability_vip");
                vipcap = vipcap - (vipcap-vipavail+2);
            }

            //Check for availability
            if(vipcap<=0 || regcap<=0){
                request.setAttribute("bookingMessage","Tickets sold out!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }

            double price=0;
            switch (seattype){
                case "vip":
                    price = 100.0;
                    break;
                case  "regular":
                    price = 20.0;
                    break;
                default:
                    request.setAttribute("bookingMessage","Invalid seat type!");
                    request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            String ticketquery = "INSERT INTO ticket (price, type, availability_vip, availability_reg, eventid) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ticketstmt = con.prepareStatement(ticketquery);
            for(int i=0;i<ticketamount;i++){
                ticketstmt.setDouble(1,price);
                ticketstmt.setString(2,seattype);
                if(seattype.equals("vip")) {
                    ticketstmt.setInt(3,vipcap-i-1);
                    ticketstmt.setInt(4,regcap);
                } else {
                    ticketstmt.setInt(3,vipcap);
                    ticketstmt.setInt(4,regcap-i-1);
                }
                ticketstmt.setInt(5,evendId);
                ticketstmt.addBatch();
            }
            ticketstmt.executeBatch();

            String customerquery = "SELECT customerid FROM customer WHERE username = ?";
            PreparedStatement customerstmt = con.prepareStatement(customerquery);
            customerstmt.setString(1,username);
            ResultSet customerres = customerstmt.executeQuery();
            if(!customerres.next()){
                request.setAttribute("bookingMessage","You are not logged in!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
            customerid = customerres.getInt("customerid");

            double cost = price*ticketamount;
            String bookingquery = "INSERT INTO booking (cost, eventdate, ticketamount, ticketid, customerid, eventid) VALUES (?, ?, ?, ?, ?, ?) ";
            PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
            bookingstmt.setDouble(1,cost);
            bookingstmt.setDate(2,eventdate);
            bookingstmt.setInt(3,ticketamount);
            bookingstmt.setInt(4,ticketid);
            bookingstmt.setInt(5,customerid);
            bookingstmt.setInt(6,evendId);
            int res = bookingstmt.executeUpdate();
            if (res>0){
                request.setAttribute("bookingMessage","Successfully booked ticket(s)!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            } else {
                request.setAttribute("bookingMessage","Error at booking ticket(s)!");
                request.getRequestDispatcher("main.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}