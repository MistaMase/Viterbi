<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	String errorMessage = "";
	if(request.getAttribute("validUsername") == null)
		errorMessage = "";
	else if((boolean)request.getAttribute("validUsername") == false)
		errorMessage = "This username is already taken";
	else if((boolean)request.getAttribute("validPassword") == false)
		errorMessage = "Your passwords don't match";

%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<link rel="stylesheet" type="text/css" href="register.css" />
	</head>
	<body>
		<div class="topbar">
			<h1>
				<a href="index.jsp" style="text-decoration: none; color: white;">WeatherMeister</a>
			</h1>
			
			<div class="topbarlogin">
				<h2>
					<a href=<%= (session.getAttribute("username") != null) ? "SearchHistory" : "login.jsp" %> id="login-profile"> <%= (session.getAttribute("username") != null) ? "Profile" : "Login" %></a>
				</h2>
				<h2>
					<a href=<%= (session.getAttribute("username") != null) ? "Logout" : "register.jsp" %> id="logout-register"> <%= (session.getAttribute("username") != null) ? "Logout" : "Register" %></a>
				</h2>
			</div>
		</div>
		
		<div class="background-img"></div>
		<div class="nav">
			<div class="accountIcon" id="logo"></div>
			<div class="accountforms">
				<form action="CreateAccount" method="POST">
					<div class="createAccountScreen" id="createAccountScreen">
						<label for="username">Username</label>
						<input type="text" name="username">
						<label for="password">Password</label>
						<input type="password" name="password">
						<label for="confirmPassword">Confirm Password</label>
						<input type="password" name="confirmPassword">
						<input type="submit" name="createAccount" value="Register"></button>
					</div>
				</form>
			</div>
			<div id="invalid"><%= errorMessage %></div>
		</div>
	</body>
</html>