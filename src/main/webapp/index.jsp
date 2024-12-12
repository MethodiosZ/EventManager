<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
    <link rel="stylesheet" href="Standard.css">
</head>
<body>
    <div class="form-container">
        <form action = "${pageContext.request.contextPath}/LoginAction" method = "POST" class="form">
            <h2>Login</h2>
            <label for="username">Username:</label>
            <input type = "text" id="username" name = "username" required><br><br>

            <label for="password">Password:</label>
            <input type = "password" id="password" name = "password" required>
            <label style="white-space: nowrap; display: inline-flex; align-items: center; gap: 5px; margin-top: 8px;">
                <input type="checkbox" onclick="togglePassword()"> <span>Show Password</span>
            </label><br><br>

            <button type="submit">Login</button>
            <h6>You don't have an account? <a href="signup.jsp">Sign up.</a> </h6>
        </form>
    </div>
    <script>
        function togglePassword(){
            const passwordField = document.getElementById('password');
            const type = passwordField.type === 'password' ? 'text' : 'password';
            passwordField.type = type;
        }

        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if(errorMessage!=null){
        %>
            alert("<%= errorMessage%>");
        <% } %>
    </script>
</body>
</html>