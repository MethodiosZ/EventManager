<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <select  id="seattype" name="seatype">
                    <option value="none">-- Select an option --</option>
                    <option value="regular">Regular</option>
                    <option value="vip">V.I.P.</option>
                </select>
                <button type="submit">Book</button>
            </form>
        </div>

        <div id="Searching" class="form-container-hid">
            <form class="form">
                <h2>Searching</h2>
                <label for="eventnames">Event Name:</label>
                <input type="text" id="eventnames" name="eventnames">
                <label for="eventdates">Event Date:</label>
                <input type="text" id="eventdates" name="eventdates">
                <button type="submit">Search</button>
            </form>
        </div>

        <div id="Cancelling" class="form-container-hid">
            <form class="form">
                <h2>Cancelling</h2>
                <label for="eventnamec">Event Name:</label>
                <input type="text" id="eventnamec" name="eventnamec">
                <label for="customername">Customer Name:</label>
                <input type="text" id="customername" name="customername">
                <button type="submit">Cancel</button>
            </form>
        </div>

        <div id="Creating" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/EventCreate" method="post" class="form">
                <h2>Creating</h2>
                <label for="eventnamecr">Name:</label>
                <input type="text" id="eventnamecr" name="eventnamecr">
                <label for="eventdatec">Date:</label>
                <input type="date" id="eventdatec" name="eventdatec">
                <label for="capacity">Capacity:</label>
                <input type="text" id="capacity" name="capacity">
                <label for="eventtype">Type:</label>
                <input type="text" id="eventtype" name="eventtype">
                <button type="submit">Create</button>
            </form>
        </div>

        <div id="Deleting" class="form-container-hid">
            <form action="${pageContext.request.contextPath}/EventDelete" method="post" class="form">
                <h2>Deleting</h2>
                <label for="eventnamed">Event Name:</label>
                <input type="text" id="eventnamed" name="eventnamed">
                <label for="eventdated">Event Date:</label>
                <input type="date" id="eventdated" name="eventdated">
                <button type="submit">Delete</button>
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
    </script>
</body>
</html>
