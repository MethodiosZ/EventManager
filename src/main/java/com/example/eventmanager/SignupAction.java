package com.example.eventmanager;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/SignupAction")
public class SignupAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignupAction(){
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String card = request.getParameter("card");
        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";
        String query = "INSERT INTO CUSTOMER (USERNAME, PASSWORD, EMAIL, CARD) VALUES (?, ?, ?, ?)";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            Connection con = DriverManager.getConnection(url,dbUser,dbPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,username);
            stmt.setString(2,password);
            stmt.setString(3,email);
            stmt.setString(4,card);
            int res = stmt.executeUpdate();
            if(res>0){
                response.sendRedirect("main.jsp");
            } else {
                out.println("<h3>Failed to insert data.</h3>");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void destroy() {

    }
}