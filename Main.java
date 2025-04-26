import java.sql.*;

public class Main {
    private static void initTables(Connection con) throws SQLException {
        String createEvent = new String(
                "CREATE TABLE EVENT (" +
                        "EVENTID INT AUTO_INCREMENT PRIMARY KEY, "+
                        "NAME VARCHAR(25),  " +
                        "EVENTDATE DATE, " +
                        "CAPACITY INT, " +
                        "TYPE VARCHAR(25)" +
                        ");"
        );
        String createCustomer = new String(
                "CREATE TABLE CUSTOMER (" +
                        "CUSTOMERID INT AUTO_INCREMENT PRIMARY KEY, " +
                        "USERNAME VARCHAR(25), " +
                        "PASSWORD VARCHAR(25), " +
                        "EMAIL VARCHAR(40), " +
                        "CARD VARCHAR(19)" +
                        ");"
        );
        String createTicket = new String(
                "CREATE TABLE TICKET (" +
                        "TICKETID INT AUTO_INCREMENT PRIMARY KEY, " +
                        "PRICE FLOAT, " +
                        "TYPE VARCHAR(25), " +
                        "AVAILABILITY INT, " +
                        "EVENTID INT" +
                        ");"
        );
        String createBooking = new String(
                "CREATE TABLE BOOKING (" +
                        "BOOKINGID INT AUTO_INCREMENT PRIMARY KEY, " +
                        "COST FLOAT, " +
                        "EVENTDATE DATE, " +
                        "TICKETAMOUNT INT, " +
                        "TICKETID INT, " +
                        "CUSTOMERID INT" +
                        ");"
        );
        Statement stmt = con.createStatement();
        stmt.executeUpdate(createEvent);
        stmt.executeUpdate(createCustomer);
        stmt.executeUpdate(createTicket);
        stmt.executeUpdate(createBooking);
        stmt.close();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost";
        String databaseName = "EventBooking";
        int port = 3306;
        String username = "root";
        String password = "";
        Connection con = DriverManager.getConnection(url+":"+port+"/"+databaseName+"?characterEncoding=UTF-8",username,password);
        initTables(con);
        con.close();
    }
}
