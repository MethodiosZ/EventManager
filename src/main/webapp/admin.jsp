<%@ page contentType="text/html;charset=UTF-8" language="java" import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession session1 = request.getSession(false);
    String username = (String) session1.getAttribute("username");
%>
<html>
<head>
    <title>Control Panel</title>
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
            <option value="Creating">Create Event</option>
            <option value="Deleting">Delete Event</option>
            <option value="eventspace">Available seats in an event</option>
            <option value="eventrevenue">Revenue of an event</option>
            <option value="popularevent">Most popular event</option>
            <option value="profitableevent">Most profitable event in a period</option>
            <option value="bookingsperiod">Booking per period</option>
            <option value="seattyperevenue">Revenue per seat type</option>
        </select>

        <div id="Booking" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/Booking" method="post" class="form">
                <h2>Booking</h2>
                <label for="eventnameb">Event Name:</label>
                <input type="text" id="eventnameb" name="eventnameb" required>
                <label for="ticketamount">Ticket Amount:</label>
                <input type="text" id="ticketamount" name="ticketamount" required>
                <label for="seattype">Seat Type:</label>
                <select  id="seattype" name="seattype" required>
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
                <input type="text" id="eventnames" name="eventnames" required>
                <label for="eventdates">Event Date:</label>
                <input type="date" id="eventdates" name="eventdates" required>
                <label for="seattypes">Seat Type:</label>
                <select  id="seattypes" name="seattypes" required>
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
                <input type="text" id="eventnamec" name="eventnamec" required>
                <label for="eventdateca">Event Date:</label>
                <input type="date" id="eventdateca" name="eventdateca" required>
                <button type="submit">Cancel</button>
            </form>
        </div>

        <div id="Creating" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/EventCreate" method="post" class="form">
                <h2>Creating</h2>
                <label for="eventnamecr">Name:</label>
                <input type="text" id="eventnamecr" name="eventnamecr" required>
                <label for="eventdatec">Date:</label>
                <input type="date" id="eventdatec" name="eventdatec" required>
                <label for="capacity">Capacity:</label>
                <input type="text" id="capacity" name="capacity" required>
                <label for="eventtype">Type:</label>
                <input type="text" id="eventtype" name="eventtype" required>
                <button type="submit">Create</button>
            </form>
        </div>

        <div id="Deleting" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/EventDelete" method="post" class="form">
                <h2>Deleting</h2>
                <label for="eventnamed">Event Name:</label>
                <input type="text" id="eventnamed" name="eventnamed" required>
                <label for="eventdated">Event Date:</label>
                <input type="date" id="eventdated" name="eventdated" required>
                <button type="submit">Delete</button>
            </form>
        </div>

        <div id="eventspace" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Searching seat state in event</h2>
                <label for="eventnamespace">Event Name:</label>
                <input type="text" id="eventnamespace" name="eventnamespace" required>
                <label for="eventdatespace">Event Date:</label>
                <input type="date" id="eventdatespace" name="eventdatespace" required>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="eventrevenue" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Searching event revenue</h2>
                <label for="eventnamerev">Event Name:</label>
                <input type="text" id="eventnamerev" name="eventnamerev" required>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="popularevent" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Searching most popular event</h2>
                <label for="eventnamepop">Event Name:</label>
                <input type="text" id="eventnamepop" name="eventnamepop" required>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="profitableevent" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Searching most profitable event in a time period</h2>
                <label for="eventnameprof">Event Name:</label>
                <input type="text" id="eventnameprof" name="eventnameprof" required>
                <label for="startdate">Start Date:</label>
                <input type="date" id="startdate" name="startdate" required>
                <label for="enddate">End Date:</label>
                <input type="date" id="enddate" name="enddate" required>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="bookingsperiod" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Searching bookings in a time period</h2>
                <label for="startdateb">Start Date:</label>
                <input type="date" id="startdateb" name="startdateb" required>
                <label for="enddateb">End Date:</label>
                <input type="date" id="enddateb" name="enddateb" required>
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="seattyperevenue" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/AdminStatistics" method="post" class="form">
                <h2>Revenue per seat type in an event or in total</h2>
                <label for="eventnameseat">Event Name:</label>
                <input type="text" id="eventnameseat" name="eventnameseat">
                <select  id="seattypeprof" name="seattypeprof" required>
                    <option value="none">-- Select an option --</option>
                    <option value="regular">Regular</option>
                    <option value="vip">V.I.P.</option>
                </select>
                <button type="submit">Search</button>
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
            String createMessage = (String) request.getAttribute("createMessage");
            if(createMessage!=null){
        %>
        alert("<%= createMessage%>");
        <% } %>

        <%
            String deleteMessage = (String) request.getAttribute("deleteMessage");
            if(deleteMessage!=null){
        %>
        alert("<%= deleteMessage%>");
        <% } %>

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
