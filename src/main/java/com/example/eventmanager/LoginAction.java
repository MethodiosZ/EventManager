package com.example.eventmanager;

import java.io.*;
import java.sql.*;
import java.util.Objects;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginAction(){
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
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean userExists = false;
        String url = "jdbc:mysql://localhost:3306/EventBooking?characterEncoding=UTF-8";
        String dbUser = "root";
        String dbPassword = "";
        String query = "SELECT * FROM customer WHERE username = ? AND password = ?";
        response.setContentType("text/html");
        try {
            Connection con = DriverManager.getConnection(url,dbUser,dbPassword);
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet res = stmt.executeQuery();
            userExists = res.next();
            if(userExists){
                session.setAttribute("username",username);
                if(Objects.equals(username, "root")){
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("main.jsp");
                }
            } else {
                request.setAttribute("errorMessage","Invalid username or password!");
                request.getRequestDispatcher("index.jsp").forward(request,response);
            }
        } catch (SQLException | ServletException e){
            e.printStackTrace();
        }
    }

    public void destroy() {

    }
}
