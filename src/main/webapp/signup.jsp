<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up Form</title>
    <link rel="stylesheet" href="Standard.css">
</head>
<body>
    <div class="form-container">
        <form id="SignUpForm" action = "${pageContext.request.contextPath}/SignupAction" method = "post" class="form" onsubmit="return validateForm()">
            <h2>Sign Up</h2>
            <label for="username">Username:</label>
            <input type = "text" id="username" name = "username" required><br><br>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <label style="white-space: nowrap; display: inline-flex; align-items: center; gap: 5px; margin-top: 8px;">
                <input type="checkbox" onclick="togglePassword()"> <span>Show Password</span>
            </label><br><br>

            <label for="confpass">Confirm Password:</label>
            <input type="password" id="confpass" name = "confpass" required>
            <label style="white-space: nowrap; display: inline-flex; align-items: center; gap: 5px; margin-top: 8px;">
                <input type="checkbox" onclick="toggleConfPassword()"> <span>Show Password</span>
            </label><br><br>

            <label for="email">E-mail:</label>
            <input type = "email" id="email" name = "email" placeholder="example@gmail.com" required><br><br>

            <label for="card">Card Number:</label>
            <input type = "text" id="card" name = "card" maxlength="19" placeholder="0000 0000 0000 0000" required><br><br>

            <button type="submit">Sign Up</button>
            <h6>You already have an account? <a href="index.jsp">Log in.</a> </h6>
        </form>
    </div>
    <script>
        document.getElementById('card').addEventListener('input',function (e){
            let value = e.target.value.replace(/\D/g,'').substring(0,16);
            let formattedValue = value.match(/.{1,4}/g)?.join(' ') || '';
            e.target.value = formattedValue;
        });

        function togglePassword(){
            const passwordField = document.getElementById('password');
            const type = passwordField.type === 'password' ? 'text' : 'password';
            passwordField.type = type;
        }

        function toggleConfPassword(){
            const passwordField = document.getElementById('confpass');
            const type = passwordField.type === 'password' ? 'text' : 'password';
            passwordField.type = type;
        }

        function validateForm() {
            const password = document.getElementById('password').value;
            const confpass = document.getElementById('confpass').value;
            const card = document.getElementById('card').value.replace(/\s/g,'');
            if(password !== confpass){
                alert("Password do not match!");
                return false;
            }
            if(card.length !== 16){
                alert("Card number must be 16 digits long!");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>