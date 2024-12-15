package com.example.eventmanager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/AdminStatistics")
public class AdminStatistics extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminStatistics(){
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
        String action = request.getParameter("action");

        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";

        try(Connection con = DriverManager.getConnection(url,dbUser,dbPassword)) {
            if("eventspace".equals(action)){
                handleeventspace(request,response,con);
            } else if("eventrevenue".equals(action)) {
                handleeventrevenue(request,response,con);
            } else if("popularevent".equals(action)) {
                handlepopularevent(request,response,con);
            } else if("profitableevent".equals(action)) {
                handleprofitableevent(request,response,con);
            } else if("bookingsperiod".equals(action)) {
                handlebookingsperiod(request,response,con);
            } else if("seattyperevenue".equals(action)) {
                handleseattyperevenue(request,response,con);
            } else {
                request.setAttribute("statisticMessage","Invalid statistic action!");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    private void handleeventspace(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        String eventname = request.getParameter("eventnamespace");
        Date eventdate = Date.valueOf(request.getParameter("eventdatespace"));

        String eventquery = "SELECT eventid, capacity FROM event WHERE name = ? AND eventdate = ?";
        PreparedStatement eventstmt = con.prepareStatement(eventquery);
        eventstmt.setString(1,eventname);
        eventstmt.setDate(2,eventdate);
        ResultSet eventres = eventstmt.executeQuery();
        if(!eventres.next()){
            request.setAttribute("statisticMessage","Event not found!");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        int eventid = eventres.getInt("eventid");
        int cap = eventres.getInt("capacity");
        int regcap = cap*90/100;
        int vipcap = cap*10/100;

        String ticketquery = "SELECT availability_reg,availability_vip FROM ticket WHERE eventid = ? ORDER BY ticketid DESC LIMIT 1";
        PreparedStatement ticketstmt = con.prepareStatement(ticketquery);
        ticketstmt.setInt(1,eventid);
        ResultSet ticketres = ticketstmt.executeQuery();
        if(!ticketres.next()){
            request.setAttribute("statisticMessage","There are "+regcap+"/"+regcap+" regular seats and "+vipcap+"/"+vipcap+" V.I.P seats available for this event");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        int bookedreg = ticketres.getInt("availability_reg");
        int bookedvip = ticketres.getInt("availability_vip");
        request.setAttribute("statisticMessage","There are "+bookedreg+"/"+regcap+" regular seats and "+bookedvip+"/"+vipcap+" V.I.P seats available for this event");
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }

    private void handleeventrevenue(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        String eventname =  request.getParameter("eventnamerev");

        String eventquery = "SELECT eventid FROM event WHERE name = ?";
        PreparedStatement eventstmt = con.prepareStatement(eventquery);
        eventstmt.setString(1,eventname);
        ResultSet eventres = eventstmt.executeQuery();
        if(!eventres.next()){
            request.setAttribute("statisticMessage","Event not found!");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        int eventid = eventres.getInt("eventid");

        String bookingquery = "SELECT * FROM booking WHERE eventid = ?";
        PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
        bookingstmt.setInt(1,eventid);
        ResultSet bookingres = bookingstmt.executeQuery();
        int revenue=0;
        while(bookingres.next()){
            int cost = bookingres.getInt("cost");
            revenue = revenue + cost;
        }
        request.setAttribute("statisticMessage","The total revenue for this event is "+revenue+" euros");
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }

    private void handlepopularevent(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        String bookingquery = "SELECT eventid, COUNT(*) AS bookingcount FROM booking GROUP By eventid ORDER BY bookingcount DESC LIMIT 1";
        PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
        ResultSet bookingres = bookingstmt.executeQuery();
        if(!bookingres.next()){
            request.setAttribute("statisticMessage","There are no active bookings!");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        int eventid = bookingres.getInt("eventid");

        String eventquery = "SELECT name FROM event WHERE eventid = ?";
        PreparedStatement eventstmt = con.prepareStatement(eventquery);
        eventstmt.setInt(1,eventid);
        ResultSet eventres = eventstmt.executeQuery();
        if(!eventres.next()){
            request.setAttribute("statisticMessage","Event not found!");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        String eventname = eventres.getString("name");
        request.setAttribute("statisticMessage",eventname+" is the most popular");
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }

    private void handleprofitableevent(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        Date start = Date.valueOf(request.getParameter("startdate"));
        Date end = Date.valueOf(request.getParameter("enddate"));

        String bookingquery = "SELECT eventid, SUM(cost) AS totalcost FROM booking WHERE eventdate BETWEEN ? AND ? GROUP BY eventid ORDER BY totalcost DESC LIMIT 1";
        PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
        bookingstmt.setDate(1,start);
        bookingstmt.setDate(2,end);
        ResultSet bookingres = bookingstmt.executeQuery();
        if(!bookingres.next()){
            request.setAttribute("statisticMessage","There are no active bookings between "+start+" and "+end);
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        int eventid = bookingres.getInt("eventid");

        String eventquery = "SELECT name FROM event WHERE eventid = ?";
        PreparedStatement eventstmt = con.prepareStatement(eventquery);
        eventstmt.setInt(1,eventid);
        ResultSet eventres = eventstmt.executeQuery();
        if(!eventres.next()){
            request.setAttribute("statisticMessage","Event not found!");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        String eventname = eventres.getString("name");
        request.setAttribute("statisticMessage",eventname+" is the most profitable between "+start+" and "+end);
        request.getRequestDispatcher("admin.jsp").forward(request,response);
    }

    private void handlebookingsperiod(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        Date start = Date.valueOf(request.getParameter("startdateb"));
        Date end = Date.valueOf(request.getParameter("enddateb"));

        String bookingquery = "SELECT * FROM booking WHERE eventdate BETWEEN ? AND ?";
        PreparedStatement bookingstmt = con.prepareStatement(bookingquery);
        bookingstmt.setDate(1,start);
        bookingstmt.setDate(2,end);
        ResultSet bookingres = bookingstmt.executeQuery();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Bookings</title></head><body>");
        out.println("<h1>Bookings from " + start + " to " + end + "</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>Event Name</th><<th>Cost</th><th>Booking Date</th></tr>");
        while (bookingres.next()) {
            int eventid = bookingres.getInt("ticketid");
            double cost = bookingres.getDouble("cost");
            String bookingDate = bookingres.getDate("eventdate").toString();

            String eventquery = "SELECT name FROM event WHERE eventid = ?";
            PreparedStatement eventstmt = con.prepareStatement(eventquery);
            eventstmt.setInt(1,eventid);
            ResultSet eventres = eventstmt.executeQuery();
            if(!eventres.next()){
                request.setAttribute("statisticMessage","Event not found!");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            }
            String eventname = eventres.getString("name");

            out.println("<tr>");
            out.println("<td>" + eventname + "</td>");
            out.println("<td>" + cost + "</td>");
            out.println("<td>" + bookingDate + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

    private void handleseattyperevenue(HttpServletRequest request, HttpServletResponse response, Connection con) throws IOException, SQLException, ServletException {
        String eventname = request.getParameter("eventnameseat");
        String seattype = request.getParameter("seattypeprof");

        String tikcetquery;
        PreparedStatement ticketstmt;
        if(eventname.isEmpty()){
            tikcetquery = "SELECT SUM(price) AS totalprice FROM ticket WHERE type = ?";
            ticketstmt = con.prepareStatement(tikcetquery);
            ticketstmt.setString(1,seattype);
        } else {
            String eventquery = "SELECT eventid FROM event WHERE name = ?";
            PreparedStatement eventstmt = con.prepareStatement(eventquery);
            eventstmt.setString(1,eventname);
            ResultSet eventres = eventstmt.executeQuery();
            if(!eventres.next()){
                request.setAttribute("statisticMessage","Event not found!");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            }
            int eventid = eventres.getInt("eventid");

            tikcetquery = "SELECT SUM(price) AS totalprice FROM ticket WHERE type = ? AND eventid = ?";
            ticketstmt = con.prepareStatement(tikcetquery);
            ticketstmt.setString(1,seattype);
            ticketstmt.setInt(2,eventid);
        }
        ResultSet ticketres = ticketstmt.executeQuery();
        if(!ticketres.next()){
            request.setAttribute("statisticMessage","There aren't any "+seattype+" sold tickets");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
        double totalPrice = ticketres.getDouble("totalprice");

        if(eventname.isEmpty()){
            request.setAttribute("statisticMessage","The total profit from "+seattype+" tickets is "+totalPrice);
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        } else {
            request.setAttribute("statisticMessage","The total profit in "+eventname+" from "+seattype+" tickets is "+totalPrice);
            request.getRequestDispatcher("admin.jsp").forward(request,response);
        }
    }

    public void destroy() {

    }
}
