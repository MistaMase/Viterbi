<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
	String errorMessage = "";
	if(request.getAttribute("validUsername") == null)
		errorMessage = "";
	else if((boolean)request.getAttribute("validUsername") == false)
		errorMessage = "Invalid Username";
	else if((boolean)request.getAttribute("validPassword") == false)
		errorMessage = "Invalid Password";
%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>WeatherMeister</title>
		<!-- linking CSS -->
		<link rel="stylesheet" type="text/css" href="login.css" />
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
			<div class="keys" id="logo"></div>
			<div class="loginforms">
				<form action="Authenticate" method="POST">
					<div class="loginScreen" id="loginScreen">
						<label for="username">Username</label><br />
						<input type="text" name="username"><br />
						<label for="password">Password</label><br />
						<input type="password" name="password"><br />
						<input type="submit" name="login" value="Login"></input>
					</div>
				</form>
			</div>
			<div id="invalid"><%= errorMessage %></div>
		</div>
	</body>
</html>