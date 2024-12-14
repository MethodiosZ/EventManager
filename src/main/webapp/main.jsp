<%@ page contentType="text/html;charset=UTF-8" language="java" import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession session1 = request.getSession(false);
    String username = (String) session1.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Action Panel</title>
    <link rel="stylesheet" href="Standard.css">
</head>
<body>
    <div>
        <!-- Dropdown Menu -->
        <label for="formSelector">Choose an action:</label>
        <select id="formSelector" class="dropdown">
            <option value="none">-- Select an option --</option>
            <option value="Booking">Book events</option>
            <option value="Searching">Search seats</option>
            <option value="Cancelling">Cancel tickets</option>
        </select>

        <!-- Forms -->
        <div id="Booking" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/Booking" method="post" class="form">
                <h2>Booking</h2>
                <label for="eventnameb">Event Name:</label>
                <input type="text" id="eventnameb" name="eventnameb">
                <label for="ticketamount">Ticket Amount:</label>
                <input type="text" id="ticketamount" name="ticketamount">
                <label for="seattype">Seat Type:</label>
                <select  id="seattype" name="seattype">
                    <option value="none">-- Select an option --</option>
                    <option value="regular">Regular</option>
                    <option value="vip">V.I.P.</option>
                </select>
                <button type="submit">Book</button>
            </form>
        </div>

        <div id="Searching" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/Searching" method="post" class="form">
                <h2>Searching</h2>
                <label for="eventnames">Event Name:</label>
                <input type="text" id="eventnames" name="eventnames">
                <label for="eventdates">Event Date:</label>
                <input type="date" id="eventdates" name="eventdates">
                <label for="seattypes">Seat Type:</label>
                <select  id="seattypes" name="seattypes">
                    <option value="none">-- Select an option --</option>
                    <option value="regular">Regular</option>
                    <option value="vip">V.I.P.</option>
                </select>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="Cancelling" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/Cancelling" method="post" class="form">
                <h2>Cancelling</h2>
                <label for="eventnamec">Event Name:</label>
                <input type="text" id="eventnamec" name="eventnamec">
                <label for="eventdateca">Event Date:</label>
                <input type="date" id="eventdateca" name="eventdateca">
                <button type="submit">Cancel</button>
            </form>
        </div>
    </div>
    <script>
        const formSelector = document.getElementById('formSelector');
        const forms = document.querySelectorAll('.form-container, .form-container-hid');

        // Event listener for dropdown changes
        formSelector.addEventListener('change', () => {
            const selectedValue = formSelector.value;

            // Hide all forms first
            forms.forEach(form => {
                form.className = 'form-container-hid'; // Switch to hidden class
            });

            // Show the selected form, if valid
            if (selectedValue !== 'none') {
                const selectedForm = document.getElementById(selectedValue);
                if (selectedForm) {
                    selectedForm.className = 'form-container'; // Switch to visible class
                }
            }
        });

        <%
            String bookingMessage = (String) request.getAttribute("bookingMessage");
            if(bookingMessage!=null){
        %>
        alert("<%= bookingMessage%>");
        <% } %>

        <%
            String searchMessage = (String) request.getAttribute("searchMessage");
            if(searchMessage!=null){
        %>
        alert("<%= searchMessage%>");
        <% } %>

        <%
            String cancelMessage = (String) request.getAttribute("cancelMessage");
            if(cancelMessage!=null){
        %>
        alert("<%= cancelMessage%>");
        <% } %>
    </script>
</body>
</html>